package io.shiftleft.semanticcpg.passes.linking.linker

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated._
import io.shiftleft.passes.{CpgPass, DiffGraph}
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import overflowdb._
import overflowdb.traversal._

import scala.collection.mutable
import scala.jdk.CollectionConverters._

/**
  * This pass has MethodStubCreator and TypeDeclStubCreator as prerequisite for
  * language frontends which do not provide method stubs and type decl stubs.
  */
class Linker(cpg: Cpg) extends CpgPass(cpg) {
  import Linker.{linkToSingle, logFailedDstLookup, logFailedSrcLookup, logger}

  private val typeDeclFullNameToNode = mutable.Map.empty[String, nodes.StoredNode]
  private val typeFullNameToNode = mutable.Map.empty[String, nodes.StoredNode]
  private val methodFullNameToNode = mutable.Map.empty[String, nodes.StoredNode]
  private val namespaceBlockFullNameToNode = mutable.Map.empty[String, nodes.StoredNode]

  override def run(): Iterator[DiffGraph] = {
    val dstGraph = DiffGraph.newBuilder

    initMaps()

    linkAstChildToParent(dstGraph)

    // Create REF edges from TYPE nodes to TYPE_DECL nodes.

    linkToSingle(
      cpg,
      srcLabels = List(NodeTypes.TYPE),
      dstNodeLabel = NodeTypes.TYPE_DECL,
      edgeType = EdgeTypes.REF,
      dstNodeMap = typeDeclFullNameToNode,
      dstFullNameKey = nodes.Type.PropertyNames.TypeDeclFullName,
      dstGraph,
      None
    )

    // Create EVAL_TYPE edges from nodes of various types
    // to TYPE nodes.

    linkToSingle(
      cpg,
      srcLabels = List(
        NodeTypes.METHOD_PARAMETER_IN,
        NodeTypes.METHOD_PARAMETER_OUT,
        NodeTypes.METHOD_RETURN,
        NodeTypes.MEMBER,
        NodeTypes.LITERAL,
        NodeTypes.CALL,
        NodeTypes.LOCAL,
        NodeTypes.IDENTIFIER,
        NodeTypes.BLOCK,
        NodeTypes.METHOD_REF,
        NodeTypes.UNKNOWN
      ),
      dstNodeLabel = NodeTypes.TYPE,
      edgeType = EdgeTypes.EVAL_TYPE,
      dstNodeMap = typeFullNameToNode,
      dstFullNameKey = "TYPE_FULL_NAME",
      dstGraph,
      None
    )

    // Create REF edges from METHOD_REFs to
    // METHOD nodes.

    linkToSingle(
      cpg,
      srcLabels = List(NodeTypes.METHOD_REF),
      dstNodeLabel = NodeTypes.METHOD,
      edgeType = EdgeTypes.REF,
      dstNodeMap = methodFullNameToNode,
      dstFullNameKey = nodes.MethodRef.PropertyNames.MethodFullName,
      dstGraph,
      None
    )

    // Create INHERITS_FROM nodes from TYPE_DECL
    // nodes to TYPE nodes.

    linkToMultiple(
      srcLabels = List(NodeTypes.TYPE_DECL),
      dstNodeLabel = NodeTypes.TYPE,
      edgeType = EdgeTypes.INHERITS_FROM,
      dstNodeMap = typeFullNameToNode,
      getDstFullNames = (srcNode: nodes.TypeDecl) => {
        if (srcNode.inheritsFromTypeFullName != null) {
          srcNode.inheritsFromTypeFullName
        } else {
          Seq()
        }
      },
      dstFullNameKey = nodes.TypeDecl.PropertyNames.InheritsFromTypeFullName,
      dstGraph
    )

    // Create ALIAS_OF edges from TYPE_DECL nodes to
    // TYPE nodes.

    linkToMultiple(
      srcLabels = List(NodeTypes.TYPE_DECL),
      dstNodeLabel = NodeTypes.TYPE,
      edgeType = EdgeTypes.ALIAS_OF,
      dstNodeMap = typeFullNameToNode,
      getDstFullNames = (srcNode: nodes.TypeDecl) => {
        srcNode.aliasTypeFullName
      },
      dstFullNameKey = NodeKeyNames.ALIAS_TYPE_FULL_NAME,
      dstGraph
    )

    Iterator(dstGraph.build())
  }

  private def initMaps(): Unit = {
    cpg.graph.graph.vertices().asScala.foreach {
      case node: nodes.TypeDecl       => typeDeclFullNameToNode += node.fullName -> node
      case node: nodes.Type           => typeFullNameToNode += node.fullName -> node
      case node: nodes.Method         => methodFullNameToNode += node.fullName -> node
      case node: nodes.NamespaceBlock => namespaceBlockFullNameToNode += node.fullName -> node
      case _                          => // ignore
    }
  }

  private def linkToMultiple[SRC_NODE_TYPE <: nodes.StoredNode](srcLabels: List[String],
                                                                dstNodeLabel: String,
                                                                edgeType: String,
                                                                dstNodeMap: mutable.Map[String, nodes.StoredNode],
                                                                getDstFullNames: SRC_NODE_TYPE => Iterable[String],
                                                                dstFullNameKey: String,
                                                                dstGraph: DiffGraph.Builder): Unit = {
    var loggedDeprecationWarning = false
    // TODO MP use `cpg.local` once that's defined in odb api
    Traversal(cpg.graph.nodes(srcLabels: _*)).cast[SRC_NODE_TYPE].foreach { srcNode =>
      if (!srcNode.outE(edgeType).hasNext) {
        getDstFullNames(srcNode).foreach { dstFullName =>
          dstNodeMap.get(dstFullName) match {
            case Some(dstNode) => dstGraph.addEdgeInOriginal(srcNode, dstNode, edgeType)
            case None          => logFailedDstLookup(edgeType, srcNode.label, srcNode.id.toString, dstNodeLabel, dstFullName)
          }
        }
      } else {
        val dstFullNames = srcNode.out(edgeType).property(NodeKeysOdb.FULL_NAME).l
        srcNode.setProperty(dstFullNameKey, dstFullNames)
        if (!loggedDeprecationWarning) {
          logger.warn(
            s"Using deprecated CPG format with already existing $edgeType edge between" +
              s" a source node of type $srcLabels and a $dstNodeLabel node.")
          loggedDeprecationWarning = true
        }
      }
    }
  }

  private def linkAstChildToParent(dstGraph: DiffGraph.Builder): Unit = {
    Traversal(cpg.graph.nodes(NodeTypes.METHOD, NodeTypes.TYPE_DECL))
      .cast[nodes.HasAstParentType with nodes.HasAstParentFullName with nodes.StoredNode]
      .filter(astChild => astChild.inE(EdgeTypes.AST).isEmpty)
      .foreach { astChild =>
        val astParentOption: Option[nodes.StoredNode] =
          astChild.astParentType match {
            case NodeTypes.METHOD          => methodFullNameToNode.get(astChild.astParentFullName)
            case NodeTypes.TYPE_DECL       => typeDeclFullNameToNode.get(astChild.astParentFullName)
            case NodeTypes.NAMESPACE_BLOCK => namespaceBlockFullNameToNode.get(astChild.astParentFullName)
            case _ =>
              logger.warn(
                s"Invalid AST_PARENT_TYPE=${astChild.propertyOption(NodeKeysOdb.AST_PARENT_FULL_NAME)};" +
                  s" astChild LABEL=${astChild.label};" +
                  s" astChild FULL_NAME=${astChild.propertyOption(NodeKeysOdb.FULL_NAME)}")
              None
          }

        astParentOption match {
          case Some(astParent) =>
            dstGraph.addEdgeInOriginal(astParent, astChild, EdgeTypes.AST)
          case None =>
            logFailedSrcLookup(EdgeTypes.AST,
                               astChild.astParentType,
                               astChild.astParentFullName,
                               astChild.label,
                               astChild.id.toString)
        }
      }
  }
}

object Linker {
  private val logger: Logger = LoggerFactory.getLogger(classOf[Linker])

  /**
    * For all nodes `n` with a label in `srcLabels`, determine
    * the value of `n.\$dstFullNameKey`, use that to lookup the
    * destination node in `dstNodeMap`, and create an edge of type
    * `edgeType` between `n` and the destination node.
    * */
  def linkToSingle(cpg: Cpg,
                   srcLabels: List[String],
                   dstNodeLabel: String,
                   edgeType: String,
                   dstNodeMap: mutable.Map[String, nodes.StoredNode],
                   dstFullNameKey: String,
                   dstGraph: DiffGraph.Builder,
                   dstNotExistsHandler: Option[(nodes.StoredNode, String) => Unit]): Unit = {
    var loggedDeprecationWarning = false
    Traversal(cpg.graph.nodes(srcLabels: _*)).foreach { srcNode =>
      // If the source node does not have any outgoing edges of this type
      // This check is just required for backward compatibility
      if (srcNode.outE(edgeType).isEmpty) {
        srcNode.propertyOption(PropertyKey[String](dstFullNameKey)).foreach { dstFullName =>
          // for `UNKNOWN` this is not always set, so we're using an Option here
          val srcStoredNode = srcNode.asInstanceOf[nodes.StoredNode]
          dstNodeMap.get(dstFullName) match {
            case Some(dstNode) =>
              dstGraph.addEdgeInOriginal(srcStoredNode, dstNode, edgeType)
            case None =>
              if (dstNotExistsHandler.isDefined) {
                dstNotExistsHandler.get(srcStoredNode, dstFullName)
              } else {
                logFailedDstLookup(edgeType, srcNode.label, srcNode.id.toString, dstNodeLabel, dstFullName)
              }
          }
        }
      } else {
        srcNode.out(edgeType).property(NodeKeysOdb.FULL_NAME).nextOption match {
          case Some(dstFullName) => srcNode.property(dstFullNameKey, dstFullName)
          case None              => logger.warn(s"Missing outgoing edge of type ${edgeType} from node ${srcNode}")
        }
        if (!loggedDeprecationWarning) {
          logger.warn(
            s"Using deprecated CPG format with already existing $edgeType edge between" +
              s" a source node of type $srcLabels and a $dstNodeLabel node.")
          loggedDeprecationWarning = true
        }
      }
    }
  }

  @inline
  private def logFailedDstLookup(edgeType: String,
                                 srcNodeType: String,
                                 srcNodeId: String,
                                 dstNodeType: String,
                                 dstFullName: String): Unit = {
    logger.warn(
      "Could not create edge. Destination lookup failed. " +
        s"edgeType=$edgeType, srcNodeType=$srcNodeType, srcNodeId=$srcNodeId, " +
        s"dstNodeType=$dstNodeType, dstFullName=$dstFullName")
  }

  @inline
  private def logFailedSrcLookup(edgeType: String,
                                 srcNodeType: String,
                                 srcFullName: String,
                                 dstNodeType: String,
                                 dstNodeId: String): Unit = {
    logger.warn(
      "Could not create edge. Source lookup failed. " +
        s"edgeType=$edgeType, srcNodeType=$srcNodeType, srcFullName=$srcFullName, " +
        s"dstNodeType=$dstNodeType, dstNodeId=$dstNodeId")
  }
}

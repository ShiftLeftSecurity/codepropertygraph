package io.shiftleft.semanticcpg.passes.linking.linker

import gremlin.scala._
import io.shiftleft.codepropertygraph.generated._
import io.shiftleft.passes.{CpgPass, DiffGraph}
import io.shiftleft.semanticcpg.language.Steps
import io.shiftleft.Implicits.JavaIteratorDeco
import java.lang.{Long => JLong}

import io.shiftleft.codepropertygraph.Cpg
import org.apache.tinkerpop.gremlin.structure.Direction
import org.apache.tinkerpop.gremlin.structure.VertexProperty.Cardinality
import org.apache.logging.log4j.{LogManager, Logger}

import scala.collection.JavaConverters._

/**
  * This pass has MethodStubCreator and TypeDeclStubCreator as prerequisite for
  * language frontends which do not provide method stubs and type decl stubs.
  */
class Linker(cpg: Cpg) extends CpgPass(cpg) {
  import Linker.logger

  private var typeDeclFullNameToNode = Map.empty[String, nodes.StoredNode]
  private var typeFullNameToNode = Map.empty[String, nodes.StoredNode]
  private var methodFullNameToNode = Map.empty[String, nodes.StoredNode]
  private var methodInstFullNameToNode = Map.empty[String, nodes.StoredNode]
  private var namespaceBlockFullNameToNode = Map.empty[String, nodes.StoredNode]

  override def run(): Iterator[DiffGraph] = {
    val dstGraph = new DiffGraph

    initMaps()

    linkAstChildToParent(dstGraph)

    linkToSingle(
      srcLabels = List(NodeTypes.TYPE),
      dstNodeLabel = NodeTypes.TYPE_DECL,
      edgeType = EdgeTypes.REF,
      dstNodeMap = typeDeclFullNameToNode,
      dstFullNameKey = nodes.Type.Keys.TypeDeclFullName,
      dstGraph
    )

    linkToSingle(
      srcLabels = List(NodeTypes.CALL),
      dstNodeLabel = NodeTypes.METHOD_INST,
      edgeType = EdgeTypes.CALL,
      dstNodeMap = methodInstFullNameToNode,
      dstFullNameKey = nodes.Call.Keys.MethodInstFullName,
      dstGraph
    )

    linkToSingle(
      srcLabels = List(NodeTypes.METHOD_INST),
      dstNodeLabel = NodeTypes.METHOD,
      edgeType = EdgeTypes.REF,
      dstNodeMap = methodFullNameToNode,
      dstFullNameKey = nodes.MethodInst.Keys.MethodFullName,
      dstGraph
    )

    linkToSingle(
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
        NodeTypes.UNKNOWN,
      ),
      dstNodeLabel = NodeTypes.TYPE,
      edgeType = EdgeTypes.EVAL_TYPE,
      dstNodeMap = typeFullNameToNode,
      dstFullNameKey = "TYPE_FULL_NAME",
      dstGraph
    )

    linkToSingle(
      srcLabels = List(NodeTypes.METHOD_REF),
      dstNodeLabel = NodeTypes.METHOD_INST,
      edgeType = EdgeTypes.REF,
      dstNodeMap = methodInstFullNameToNode,
      dstFullNameKey = nodes.MethodRef.Keys.MethodInstFullName,
      dstGraph
    )

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
      dstFullNameKey = nodes.TypeDecl.Keys.InheritsFromTypeFullName,
      dstGraph
    )

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

    Iterator(dstGraph)
  }

  private def initMaps(): Unit = {
    cpg.graph.graph.vertices().asScala.foreach {
      case node: nodes.TypeDecl       => typeDeclFullNameToNode += node.fullName -> node
      case node: nodes.Type           => typeFullNameToNode += node.fullName -> node
      case node: nodes.Method         => methodFullNameToNode += node.fullName -> node
      case node: nodes.MethodInst     => methodInstFullNameToNode += node.fullName -> node
      case node: nodes.NamespaceBlock => namespaceBlockFullNameToNode += node.fullName -> node
      case _                          => // ignore
    }
  }

  private def linkToSingle(srcLabels: List[String],
                           dstNodeLabel: String,
                           edgeType: String,
                           dstNodeMap: Map[String, nodes.StoredNode],
                           dstFullNameKey: String,
                           dstGraph: DiffGraph): Unit = {
    var loggedDeprecationWarning = false
    val sourceTraversal = cpg.graph.V.hasLabel(srcLabels.head, srcLabels.tail: _*)
    val sourceIterator = new Steps(sourceTraversal).toIterator()
    sourceIterator.foreach { srcNode =>
      if (!srcNode.edges(Direction.OUT, edgeType).hasNext) {
        // for `UNKNOWN` this is not always set, so we're using an Option here
        srcNode.valueOption[String](dstFullNameKey).map { dstFullName =>
          val dstNode: Option[nodes.StoredNode] = dstNodeMap.get(dstFullName)
          dstNode match {
            case Some(dstNode) =>
              dstGraph
                .addEdgeInOriginal(srcNode.asInstanceOf[nodes.StoredNode], dstNode, edgeType)
            case None =>
              logFailedDstLookup(edgeType, srcNode.label, srcNode.id.toString, dstNodeLabel, dstFullName)
          }
        }
      } else {
        val dstFullName =
          srcNode.vertices(Direction.OUT, edgeType).nextChecked.value2(NodeKeys.FULL_NAME)
        srcNode.property(dstFullNameKey, dstFullName)
        if (!loggedDeprecationWarning) {
          logger.warn(
            s"Using deprecated CPG format with already existing $edgeType edge between" +
              s" a source node of type $srcLabels and a $dstNodeLabel node.")
          loggedDeprecationWarning = true
        }
      }
    }
  }

  private def linkToMultiple[SRC_NODE_TYPE <: nodes.StoredNode](srcLabels: List[String],
                                                                dstNodeLabel: String,
                                                                edgeType: String,
                                                                dstNodeMap: Map[String, nodes.StoredNode],
                                                                getDstFullNames: SRC_NODE_TYPE => Iterable[String],
                                                                dstFullNameKey: String,
                                                                dstGraph: DiffGraph): Unit = {
    var loggedDeprecationWarning = false
    cpg.graph.V
      .hasLabel(srcLabels.head, srcLabels.tail: _*)
      .sideEffect {
        case srcNode: SRC_NODE_TYPE @unchecked =>
          if (!srcNode.edges(Direction.OUT, edgeType).hasNext) {
            getDstFullNames(srcNode).foreach { dstFullName =>
              dstNodeMap.get(dstFullName) match {
                case Some(dstNode) =>
                  dstGraph.addEdgeInOriginal(srcNode, dstNode, edgeType)
                case None =>
                  logFailedDstLookup(edgeType, srcNode.label, srcNode.id.toString, dstNodeLabel, dstFullName)
              }
            }
          } else {
            val dstFullNames = srcNode
              .vertices(Direction.OUT, edgeType)
              .asScala
              .map(_.value2(NodeKeys.FULL_NAME))
              .toIterable
            srcNode.removeProperty(Key(dstFullNameKey))
            dstFullNames.foreach { name =>
              srcNode.property(Cardinality.list, dstFullNameKey, name)
            }
            if (!loggedDeprecationWarning) {
              logger.warn(
                s"Using deprecated CPG format with already existing $edgeType edge between" +
                  s" a source node of type $srcLabels and a $dstNodeLabel node.")
              loggedDeprecationWarning = true
            }
          }
      }
      .iterate()
  }

  private def linkAstChildToParent(dstGraph: DiffGraph): Unit = {
    var loggedDeprecationWarning = false

    cpg.graph.V
      .hasLabel(NodeTypes.METHOD, NodeTypes.TYPE_DECL)
      .sideEffect {
        case astChild: nodes.HasAstParentType with nodes.HasAstParentFullName with nodes.StoredNode =>
          astChild.edges(Direction.IN, EdgeTypes.AST).nextOption match {
            case None =>
              val astParentOption: Option[nodes.StoredNode] =
                astChild.astParentType match {
                  case NodeTypes.METHOD          => methodFullNameToNode.get(astChild.astParentFullName)
                  case NodeTypes.TYPE_DECL       => typeDeclFullNameToNode.get(astChild.astParentFullName)
                  case NodeTypes.NAMESPACE_BLOCK => namespaceBlockFullNameToNode.get(astChild.astParentFullName)
                  case _ =>
                    logger.error(
                      s"Invalid AST_PARENT_TYPE=${astChild.valueOption(NodeKeys.AST_PARENT_FULL_NAME)};" +
                        s" astChild LABEL=${astChild.label};" +
                        s" astChild FULL_NAME=${astChild.valueOption(NodeKeys.FULL_NAME)}")
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
                                     astChild.id.toString())
              }
            case Some(astEdge) if !loggedDeprecationWarning =>
              logger.info(
                "Using deprecated CPG format with already existing AST edge between" +
                  s" ${astEdge.outVertex().label()} and ${astChild.label()} node.")
              loggedDeprecationWarning = true
            case _ =>
          }
      }
      .iterate()
  }

  @inline
  private def logFailedDstLookup(edgeType: String,
                                 srcNodeType: String,
                                 srcNodeId: String,
                                 dstNodeType: String,
                                 dstFullName: String): Unit = {
    logger.error(
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
    logger.error(
      "Could not create edge. Source lookup failed. " +
        s"edgeType=$edgeType, srcNodeType=$srcNodeType, srcFullName=$srcFullName, " +
        s"dstNodeType=$dstNodeType, dstNodeId=$dstNodeId")
  }

}

object Linker {
  private val logger: Logger = LogManager.getLogger(classOf[Linker])
}

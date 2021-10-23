package io.shiftleft.semanticcpg.passes.linking.linker

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.nodes._
import io.shiftleft.codepropertygraph.generated.{PropertyNames, _}
import io.shiftleft.passes.{CpgPass, DiffGraph}
import io.shiftleft.semanticcpg.language._
import io.shiftleft.semanticcpg.passes.linking.linker.Linker.{
  linkToMultiple,
  linkToSingle,
  methodFullNameToNode,
  namespaceBlockFullNameToNode,
  typeDeclFullNameToNode,
  typeFullNameToNode
}
import org.slf4j.{Logger, LoggerFactory}
import overflowdb._
import overflowdb.traversal._

import scala.collection.mutable
import scala.jdk.CollectionConverters._

/**
  * Create INHERITS_FROM edges from `TYPE_DECL` nodes to `TYPE` nodes.
  * */
class TypeHierarchyPass(cpg: Cpg) extends CpgPass(cpg) {

  override def run(): Iterator[DiffGraph] = {
    val dstGraph = DiffGraph.newBuilder
    linkToMultiple(
      cpg,
      srcLabels = List(NodeTypes.TYPE_DECL),
      dstNodeLabel = NodeTypes.TYPE,
      edgeType = EdgeTypes.INHERITS_FROM,
      dstNodeMap = typeFullNameToNode(cpg, _),
      getDstFullNames = (srcNode: TypeDecl) => {
        if (srcNode.inheritsFromTypeFullName != null) {
          srcNode.inheritsFromTypeFullName
        } else {
          Seq()
        }
      },
      dstFullNameKey = PropertyNames.INHERITS_FROM_TYPE_FULL_NAME,
      dstGraph
    )
    Iterator(dstGraph.build())
  }
}

class TypeUsagePass(cpg: Cpg) extends CpgPass(cpg) {

  override def run(): Iterator[DiffGraph] = {

    val dstGraph = DiffGraph.newBuilder

    // Create REF edges from TYPE nodes to TYPE_DECL

    linkToSingle(
      cpg,
      srcLabels = List(NodeTypes.TYPE),
      dstNodeLabel = NodeTypes.TYPE_DECL,
      edgeType = EdgeTypes.REF,
      dstNodeMap = typeDeclFullNameToNode(cpg, _),
      dstFullNameKey = PropertyNames.TYPE_DECL_FULL_NAME,
      dstGraph,
      None
    )

    // Create EVAL_TYPE edges from nodes of various types
    // to TYPE

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
        NodeTypes.TYPE_REF,
        NodeTypes.UNKNOWN
      ),
      dstNodeLabel = NodeTypes.TYPE,
      edgeType = EdgeTypes.EVAL_TYPE,
      dstNodeMap = typeFullNameToNode(cpg, _),
      dstFullNameKey = "TYPE_FULL_NAME",
      dstGraph,
      None
    )

    Iterator(dstGraph.build())
  }
}

class AstLinkerPass(cpg: Cpg) extends CpgPass(cpg) {

  import Linker.{logFailedSrcLookup, logger}

  override def run(): Iterator[DiffGraph] = {
    val dstGraph = DiffGraph.newBuilder
    cpg.method.whereNot(_.inE(EdgeTypes.AST)).foreach(addAstEdge(_, dstGraph))
    cpg.typeDecl.whereNot(_.inE(EdgeTypes.AST)).foreach(addAstEdge(_, dstGraph))
    Iterator(dstGraph.build())
  }

  /**
    * For the given method or type declaration, determine its parent in the AST
    * via the AST_PARENT_TYPE and AST_PARENT_FULL_NAME fields and create an
    * AST edge from the parent to it. AST creation to methods and type declarations
    * is deferred in frontends in order to allow them to process methods/type-
    * declarations independently.
    * */
  private def addAstEdge(methodOrTypeDecl: HasAstParentType with HasAstParentFullName with StoredNode,
                         dstGraph: DiffGraph.Builder): Unit = {
    val astParentOption: Option[StoredNode] =
      methodOrTypeDecl.astParentType match {
        case NodeTypes.METHOD          => methodFullNameToNode(cpg, methodOrTypeDecl.astParentFullName)
        case NodeTypes.TYPE_DECL       => typeDeclFullNameToNode(cpg, methodOrTypeDecl.astParentFullName)
        case NodeTypes.NAMESPACE_BLOCK => namespaceBlockFullNameToNode(cpg, methodOrTypeDecl.astParentFullName)
        case _ =>
          logger.warn(
            s"Invalid AST_PARENT_TYPE=${methodOrTypeDecl.propertyOption(Properties.AST_PARENT_FULL_NAME)};" +
              s" astChild LABEL=${methodOrTypeDecl.label};" +
              s" astChild FULL_NAME=${methodOrTypeDecl.propertyOption(Properties.FULL_NAME)}")
          None
      }

    astParentOption match {
      case Some(astParent) =>
        dstGraph.addEdgeInOriginal(astParent, methodOrTypeDecl, EdgeTypes.AST)
      case None =>
        logFailedSrcLookup(EdgeTypes.AST,
                           methodOrTypeDecl.astParentType,
                           methodOrTypeDecl.astParentFullName,
                           methodOrTypeDecl.label,
                           methodOrTypeDecl.id.toString)
    }
  }
}

/**
  * This pass has MethodStubCreator and TypeDeclStubCreator as prerequisite for
  * language frontends which do not provide method stubs and type decl stubs.
  */
class Linker(cpg: Cpg) extends CpgPass(cpg) {
  import Linker.linkToSingle

  override def run(): Iterator[DiffGraph] = {
    val dstGraph = DiffGraph.newBuilder

    // Create REF edges from METHOD_REFs to
    // METHOD

    linkToSingle(
      cpg,
      srcLabels = List(NodeTypes.METHOD_REF),
      dstNodeLabel = NodeTypes.METHOD,
      edgeType = EdgeTypes.REF,
      dstNodeMap = methodFullNameToNode(cpg, _),
      dstFullNameKey = PropertyNames.METHOD_FULL_NAME,
      dstGraph,
      None
    )

    // Create ALIAS_OF edges from TYPE_DECL nodes to
    // TYPE

    linkToMultiple(
      cpg,
      srcLabels = List(NodeTypes.TYPE_DECL),
      dstNodeLabel = NodeTypes.TYPE,
      edgeType = EdgeTypes.ALIAS_OF,
      dstNodeMap = typeFullNameToNode(cpg, _),
      getDstFullNames = (srcNode: TypeDecl) => {
        srcNode.aliasTypeFullName
      },
      dstFullNameKey = PropertyNames.ALIAS_TYPE_FULL_NAME,
      dstGraph
    )

    Iterator(dstGraph.build())
  }

}

object Linker {
  val logger: Logger = LoggerFactory.getLogger(classOf[Linker])

  def typeDeclFullNameToNode(cpg: Cpg, x: String): Option[TypeDecl] =
    nodesWithFullName(cpg, x).collectFirst { case x: TypeDecl => x }

  def typeFullNameToNode(cpg: Cpg, x: String): Option[Type] =
    nodesWithFullName(cpg, x).collectFirst { case x: Type => x }

  def methodFullNameToNode(cpg: Cpg, x: String): Option[Method] =
    nodesWithFullName(cpg, x).collectFirst { case x: Method => x }

  def namespaceBlockFullNameToNode(cpg: Cpg, x: String): Option[NamespaceBlock] =
    nodesWithFullName(cpg, x).collectFirst { case x: NamespaceBlock => x }

  def nodesWithFullName(cpg: Cpg, x: String): mutable.Seq[NodeRef[_ <: NodeDb]] =
    cpg.graph.indexManager.lookup(PropertyNames.FULL_NAME, x).asScala

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
                   dstNodeMap: String => Option[StoredNode],
                   dstFullNameKey: String,
                   dstGraph: DiffGraph.Builder,
                   dstNotExistsHandler: Option[(StoredNode, String) => Unit]): Unit = {
    var loggedDeprecationWarning = false
    Traversal(cpg.graph.nodes(srcLabels: _*)).foreach { srcNode =>
      // If the source node does not have any outgoing edges of this type
      // This check is just required for backward compatibility
      if (srcNode.outE(edgeType).isEmpty) {
        val key = new PropertyKey[String](dstFullNameKey)
        srcNode
          .propertyOption(key)
          .filter { dstFullName =>
            !srcNode.propertyDefaultValue(dstFullNameKey).equals(dstFullName)
          }
          .ifPresent { dstFullName =>
            // for `UNKNOWN` this is not always set, so we're using an Option here
            val srcStoredNode = srcNode.asInstanceOf[StoredNode]
            dstNodeMap(dstFullName) match {
              case Some(dstNode) =>
                dstGraph.addEdgeInOriginal(srcStoredNode, dstNode, edgeType)
              case None if dstNotExistsHandler.isDefined =>
                dstNotExistsHandler.get(srcStoredNode, dstFullName)
              case _ =>
                logFailedDstLookup(edgeType, srcNode.label, srcNode.id.toString, dstNodeLabel, dstFullName)
            }
          }
      } else {
        srcNode.out(edgeType).property(Properties.FULL_NAME).nextOption() match {
          case Some(dstFullName) => srcNode.setProperty(dstFullNameKey, dstFullName)
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

  def linkToMultiple[SRC_NODE_TYPE <: StoredNode](cpg: Cpg,
                                                  srcLabels: List[String],
                                                  dstNodeLabel: String,
                                                  edgeType: String,
                                                  dstNodeMap: String => Option[StoredNode],
                                                  getDstFullNames: SRC_NODE_TYPE => Iterable[String],
                                                  dstFullNameKey: String,
                                                  dstGraph: DiffGraph.Builder): Unit = {
    var loggedDeprecationWarning = false
    Traversal(cpg.graph.nodes(srcLabels: _*)).cast[SRC_NODE_TYPE].foreach { srcNode =>
      if (!srcNode.outE(edgeType).hasNext) {
        getDstFullNames(srcNode).foreach { dstFullName =>
          dstNodeMap(dstFullName) match {
            case Some(dstNode) => dstGraph.addEdgeInOriginal(srcNode, dstNode, edgeType)
            case None          => logFailedDstLookup(edgeType, srcNode.label, srcNode.id.toString, dstNodeLabel, dstFullName)
          }
        }
      } else {
        val dstFullNames = srcNode.out(edgeType).property(Properties.FULL_NAME).l
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

  @inline
  def logFailedDstLookup(edgeType: String,
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
  def logFailedSrcLookup(edgeType: String,
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

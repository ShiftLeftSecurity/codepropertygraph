package io.shiftleft.semanticcpg.passes.linking.linker

import gremlin.scala._
import io.shiftleft.codepropertygraph.generated.{nodes, _}
import io.shiftleft.passes.{CpgPass, DiffGraph, ParallelIteratorExecutor}
import io.shiftleft.semanticcpg.language.Steps
import io.shiftleft.Implicits.JavaIteratorDeco
import io.shiftleft.codepropertygraph.Cpg
import org.apache.tinkerpop.gremlin.structure.Direction
import org.apache.tinkerpop.gremlin.structure.VertexProperty.Cardinality
import org.apache.logging.log4j.{LogManager, Logger}

import scala.collection.mutable
import scala.jdk.CollectionConverters._

/**
  * This pass has MethodStubCreator and TypeDeclStubCreator as prerequisite for
  * language frontends which do not provide method stubs and type decl stubs.
  */
class Linker(cpg: Cpg) extends CpgPass(cpg) {
  import Linker.logger

  private val typeDeclFullNameToNode = mutable.Map.empty[String, nodes.StoredNode]
  private val typeFullNameToNode = mutable.Map.empty[String, nodes.StoredNode]
  private val methodFullNameToNode = mutable.Map.empty[String, nodes.StoredNode]
  private val namespaceBlockFullNameToNode = mutable.Map.empty[String, nodes.StoredNode]

  override def run(): Iterator[DiffGraph] = {

    initMaps()

    linkAstChildToParent() ++
      linkToSingle(
        srcLabels = List(NodeTypes.TYPE),
        dstNodeLabel = NodeTypes.TYPE_DECL,
        edgeType = EdgeTypes.REF,
        dstNodeMap = typeDeclFullNameToNode,
        dstFullNameKey = nodes.Type.PropertyNames.TypeDeclFullName
      ) ++
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
          NodeTypes.UNKNOWN
        ),
        dstNodeLabel = NodeTypes.TYPE,
        edgeType = EdgeTypes.EVAL_TYPE,
        dstNodeMap = typeFullNameToNode,
        dstFullNameKey = "TYPE_FULL_NAME"
      ) ++
      linkToSingle(
        srcLabels = List(NodeTypes.METHOD_REF),
        dstNodeLabel = NodeTypes.METHOD,
        edgeType = EdgeTypes.REF,
        dstNodeMap = methodFullNameToNode,
        dstFullNameKey = nodes.MethodRef.PropertyNames.MethodFullName,
      ) ++
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
        dstFullNameKey = nodes.TypeDecl.PropertyNames.InheritsFromTypeFullName
      ) ++
      linkToMultiple(
        srcLabels = List(NodeTypes.TYPE_DECL),
        dstNodeLabel = NodeTypes.TYPE,
        edgeType = EdgeTypes.ALIAS_OF,
        dstNodeMap = typeFullNameToNode,
        getDstFullNames = (srcNode: nodes.TypeDecl) => {
          srcNode.aliasTypeFullName
        },
        dstFullNameKey = NodeKeyNames.ALIAS_TYPE_FULL_NAME
      )
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

  /**
    * For all nodes `n` with a label in `srcLabels`, determine
    * the value of `n.$dstFullNameKey`, use that to lookup the
    * destination node in `dstNodeMap`, and create an edge of type
    * `edgeType` between `n` and the destination node.
    * */
  private def linkToSingle(srcLabels: List[String],
                           dstNodeLabel: String,
                           edgeType: String,
                           dstNodeMap: mutable.Map[String, nodes.StoredNode],
                           dstFullNameKey: String): Iterator[DiffGraph] = {
    var loggedDeprecationWarning = false
    val sourceTraversal = cpg.graph.V.hasLabel(srcLabels.head, srcLabels.tail: _*)
    val sourceIterator = new Steps(sourceTraversal).toIterator()

    val chunkSize = 2048
    new ParallelIteratorExecutor[Iterator[Vertex]](sourceIterator.grouped(chunkSize)).map { chunk =>
      implicit val dstGraph: DiffGraph.Builder = DiffGraph.newBuilder

      chunk.foreach { srcNode =>
        // If the source node does not have any outgoing edges of this type
        // This check is just required for backward compatibility
        if (!srcNode.edges(Direction.OUT, edgeType).hasNext) {
          srcNode.valueOption[String](dstFullNameKey).foreach { dstFullName =>
            // for `UNKNOWN` this is not always set, so we're using an Option here
            val dstNode: Option[nodes.StoredNode] = dstNodeMap.get(dstFullName)
            dstNode match {
              case Some(dstNodeInner) =>
                dstGraph
                  .addEdgeInOriginal(srcNode.asInstanceOf[nodes.StoredNode], dstNodeInner, edgeType)
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
      dstGraph.build()
    }
  }

  private def linkToMultiple[SRC_NODE_TYPE <: nodes.StoredNode](srcLabels: List[String],
                                                                dstNodeLabel: String,
                                                                edgeType: String,
                                                                dstNodeMap: mutable.Map[String, nodes.StoredNode],
                                                                getDstFullNames: SRC_NODE_TYPE => Iterable[String],
                                                                dstFullNameKey: String): Iterator[DiffGraph] = {
    var loggedDeprecationWarning = false
    new ParallelIteratorExecutor[Vertex](
      cpg.graph.V
        .hasLabel(srcLabels.head, srcLabels.tail: _*)
        .toIterator()).map {
      case srcNode: SRC_NODE_TYPE @unchecked =>
        implicit val dstGraph: DiffGraph.Builder = DiffGraph.newBuilder
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
            .iterator
            .to(Iterable)
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
        dstGraph.build()
    }
  }

  private def linkAstChildToParent(): Iterator[DiffGraph] = {
    val chunkSize = 2048
    val chunk = cpg.graph.V
      .hasLabel(NodeTypes.METHOD, NodeTypes.TYPE_DECL)
      .l
      .grouped(chunkSize)

    new ParallelIteratorExecutor[List[Vertex]](chunk).map { vertices: List[Vertex] =>
      implicit val dstGraph: DiffGraph.Builder = DiffGraph.newBuilder
      vertices.foreach {
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
            case _ =>
          }
      }
      dstGraph.build()
    }
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

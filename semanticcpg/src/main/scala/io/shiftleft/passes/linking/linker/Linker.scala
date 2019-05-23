package io.shiftleft.passes.linking.linker

import gremlin.scala._
import io.shiftleft.codepropertygraph.generated._
import io.shiftleft.passes.CpgPass
import io.shiftleft.queryprimitives.steps.Implicits.JavaIteratorDeco
import java.lang.{Long => JLong}

import io.shiftleft.diffgraph.DiffGraph
import org.apache.tinkerpop.gremlin.structure.Direction
import org.apache.tinkerpop.gremlin.structure.VertexProperty.Cardinality

import scala.collection.JavaConverters._

class Linker(graph: ScalaGraph) extends CpgPass(graph) {
  private var typeDeclFullNameToNodeId = Map[String, JLong]()
  private var typeFullNameToNodeId = Map[String, JLong]()
  private var methodFullNameToNodeId = Map[String, JLong]()
  private var methodInstFullNameToNodeId = Map[String, JLong]()
  private var namespaceBlockFullNameToNodeId = Map[String, JLong]()

  override def run(): Iterator[DiffGraph] = {
    val dstGraph = new DiffGraph

    initMaps()

    linkAstChildToParent(dstGraph)

    linkToSingle(
      srcLabels = Set(NodeTypes.TYPE),
      dstNodeLabel = NodeTypes.TYPE_DECL,
      edgeType = EdgeTypes.REF,
      dstNodeIdMap = typeDeclFullNameToNodeId,
      dstFullNameKey = nodes.Type.Keys.TypeDeclFullName,
      dstGraph
    )

    linkToSingle(
      srcLabels = Set(NodeTypes.CALL),
      dstNodeLabel = NodeTypes.METHOD_INST,
      edgeType = EdgeTypes.CALL,
      dstNodeIdMap = methodInstFullNameToNodeId,
      dstFullNameKey = nodes.Call.Keys.MethodInstFullName,
      dstGraph
    )

    linkToSingle(
      srcLabels = Set(NodeTypes.METHOD_INST),
      dstNodeLabel = NodeTypes.METHOD,
      edgeType = EdgeTypes.REF,
      dstNodeIdMap = methodFullNameToNodeId,
      dstFullNameKey = nodes.MethodInst.Keys.MethodFullName,
      dstGraph
    )

    linkToSingle(
      srcLabels = Set(
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
      dstNodeIdMap = typeFullNameToNodeId,
      dstFullNameKey = "TYPE_FULL_NAME",
      dstGraph
    )

    linkToSingle(
      srcLabels = Set(NodeTypes.METHOD_REF),
      dstNodeLabel = NodeTypes.METHOD_INST,
      edgeType = EdgeTypes.REF,
      dstNodeIdMap = methodInstFullNameToNodeId,
      dstFullNameKey = nodes.MethodRef.Keys.MethodInstFullName,
      dstGraph
    )

    linkToMultiple(
      srcLabels = List(NodeTypes.TYPE_DECL),
      dstNodeLabel = NodeTypes.TYPE,
      edgeType = EdgeTypes.INHERITS_FROM,
      dstNodeIdMap = typeFullNameToNodeId,
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

    Iterator(dstGraph)
  }

  private def initMaps(): Unit = {
    graph.graph.vertices().asScala.foreach {
      case node: nodes.TypeDecl   => typeDeclFullNameToNodeId += node.fullName -> node.getId
      case node: nodes.Type       => typeFullNameToNodeId += node.fullName -> node.getId
      case node: nodes.Method     => methodFullNameToNodeId += node.fullName -> node.getId
      case node: nodes.MethodInst => methodInstFullNameToNodeId += node.fullName -> node.getId
      case node: nodes.NamespaceBlock =>
        namespaceBlockFullNameToNodeId += node.fullName -> node.getId
      case _ => // ignore
    }
  }

  private def linkToSingle[SRC_NODE_TYPE <: nodes.StoredNode](srcLabels: Set[String],
                                                              dstNodeLabel: String,
                                                              edgeType: String,
                                                              dstNodeIdMap: Map[String, JLong],
                                                              dstFullNameKey: String,
                                                              dstGraph: DiffGraph): Unit = {
    var loggedDeprecationWarning = false
    graph.graph
      .vertices()
      .asScala
      .filter(srcNode => srcLabels.contains(srcNode.label()))
      .foreach { srcNode =>
        if (!srcNode.edges(Direction.OUT, edgeType).hasNext) {
          // for `UNKNOWN` this is not always set, so we're using an Option here
          srcNode.valueOption[String](dstFullNameKey).map { dstFullName =>
            val dstNode: Option[nodes.StoredNode] =
              dstNodeIdMap.get(dstFullName).flatMap(lookupNode(_))
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
                                                                dstNodeIdMap: Map[String, JLong],
                                                                getDstFullNames: SRC_NODE_TYPE => Iterable[String],
                                                                dstFullNameKey: String,
                                                                dstGraph: DiffGraph): Unit = {
    var loggedDeprecationWarning = false
    graph.V
      .hasLabel(srcLabels.head, srcLabels.tail: _*)
      .sideEffect {
        case srcNode: SRC_NODE_TYPE @unchecked =>
          if (!srcNode.edges(Direction.OUT, edgeType).hasNext) {
            getDstFullNames(srcNode).foreach { dstFullName =>
              val node = dstNodeIdMap.get(dstFullName).flatMap(lookupNode(_))
              node match {
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

    graph.V
      .hasLabel(NodeTypes.METHOD, NodeTypes.TYPE_DECL)
      .sideEffect {
        case astChild: nodes.HasAstParentType with nodes.HasAstParentFullName with nodes.StoredNode =>
          astChild.edges(Direction.IN, EdgeTypes.AST).nextOption match {
            case None =>
              val astParentOption: Option[nodes.StoredNode] =
                astChild.astParentType match {
                  case NodeTypes.METHOD =>
                    methodFullNameToNodeId.get(astChild.astParentFullName).flatMap(lookupNode(_))
                  case NodeTypes.TYPE_DECL =>
                    typeDeclFullNameToNodeId.get(astChild.astParentFullName).flatMap(lookupNode(_))
                  case NodeTypes.NAMESPACE_BLOCK =>
                    namespaceBlockFullNameToNodeId
                      .get(astChild.astParentFullName)
                      .flatMap(lookupNode(_))
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

  private def lookupNode(nodeId: JLong): Option[nodes.StoredNode] =
    graph.graph.vertices(nodeId).nextOption.map(_.asInstanceOf[nodes.StoredNode])

}

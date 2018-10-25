package io.shiftleft.cpgenhancements.generic.linker
import gremlin.scala._
import io.shiftleft.cpgenhancements.CpgEnhancement
import io.shiftleft.codepropertygraph.generated._
import io.shiftleft.queryprimitives.steps.Implicits._
import io.shiftleft.queryprimitives.steps.starters.Cpg
import org.apache.tinkerpop.gremlin.structure.Direction
import org.apache.tinkerpop.gremlin.structure.VertexProperty.Cardinality

import scala.collection.JavaConverters._

class Linker(graph: ScalaGraph) extends CpgEnhancement(graph) {
  private var typeDeclFullNameToNode       = Map.empty[String, Vertex]
  private var typeFullNameToNode           = Map.empty[String, Vertex]
  private var methodFullNameToNode         = Map.empty[String, Vertex]
  private var methodInstFullNameToNode     = Map.empty[String, Vertex]
  private var namespaceBlockFullNameToNode = Map.empty[String, Vertex]

  override def run(): Unit = {
    initMaps()

    linkAstChildToParent()

    linkToSingle(
      srcLabels = List(NodeTypes.TYPE),
      dstNodeLabel = NodeTypes.TYPE_DECL,
      edgeType = EdgeTypes.REF,
      dstNodeMap = typeDeclFullNameToNode,
      getDstFullName = (srcNode: Vertex) => srcNode.value2(NodeKeys.TYPE_DECL_FULL_NAME),
      setDstFullName =
        (srcNode: Vertex, fullName: String) =>
          srcNode.property(NodeKeyNames.TYPE_DECL_FULL_NAME, fullName)
    )

    linkToSingle(
      srcLabels = List(NodeTypes.METHOD_INST),
      dstNodeLabel = NodeTypes.METHOD,
      edgeType = EdgeTypes.REF,
      dstNodeMap = methodFullNameToNode,
      getDstFullName = (srcNode: Vertex) => srcNode.value2(NodeKeys.METHOD_FULL_NAME),
      setDstFullName =
        (srcNode: Vertex, fullName: String) =>
          srcNode.property(NodeKeyNames.METHOD_FULL_NAME, fullName)
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
      ),
      dstNodeLabel = NodeTypes.TYPE,
      edgeType = EdgeTypes.EVAL_TYPE,
      dstNodeMap = typeFullNameToNode,
      getDstFullName = (srcNode: Vertex) => srcNode.value2(NodeKeys.TYPE_FULL_NAME),
      setDstFullName = (srcNode: Vertex, fullName: String) =>
        srcNode.property(NodeKeyNames.TYPE_FULL_NAME, fullName)
    )

    linkToSingle(
      srcLabels = List(NodeTypes.METHOD_REF),
      dstNodeLabel = NodeTypes.METHOD_INST,
      edgeType = EdgeTypes.REF,
      dstNodeMap = methodInstFullNameToNode,
      getDstFullName = (srcNode: Vertex) => srcNode.value2(NodeKeys.METHOD_INST_FULL_NAME),
      setDstFullName =
        (srcNode: Vertex, fullName: String) =>
      srcNode.property(NodeKeyNames.METHOD_INST_FULL_NAME, fullName)
    )

    linkToMultiple(
      srcLabels = List(NodeTypes.TYPE_DECL),
      dstNodeLabel = NodeTypes.TYPE,
      edgeType = EdgeTypes.INHERITS_FROM,
      dstNodeMap = typeFullNameToNode,
      getDstFullNames = (srcNode: Vertex) => {
        Seq(srcNode.valueOption(NodeKeys.INHERITS_FROM_TYPE_FULL_NAME)).flatten
      },
      setDstFullNames = (srcNode: Vertex, fullNames: Iterable[String]) => {
        fullNames.foreach { fullName =>
          srcNode.property(Cardinality.list, NodeKeyNames.INHERITS_FROM_TYPE_FULL_NAME, fullName)
        }
      }
    )

  }

  private def initMaps(): Unit = {
    val cpg = Cpg(graph.graph)

    cpg.typeDecl
      .sideEffect { typeDecl =>
        typeDeclFullNameToNode += typeDecl.fullName -> typeDecl.underlying
      }
      .iterate()

    cpg.types
      .sideEffect { typ =>
        typeFullNameToNode += typ.fullName -> typ.underlying
      }
      .iterate()

    cpg.method
      .sideEffect { method =>
        methodFullNameToNode += method.fullName -> method.underlying
      }
      .iterate()

    cpg.methodInst
      .sideEffect { methodInst =>
        methodInstFullNameToNode += methodInst.fullName -> methodInst.underlying
      }
      .iterate()

    cpg.namespaceBlock
      .sideEffect { namespaceBlock =>
        namespaceBlockFullNameToNode += namespaceBlock.fullName -> namespaceBlock.underlying
      }
      .iterate()
  }

  private def linkToSingle(
      srcLabels: List[String],
      dstNodeLabel: String,
      edgeType: String,
      dstNodeMap: Map[String, Vertex],
      getDstFullName: Vertex => String,
      setDstFullName: (Vertex, String) => Unit): Unit = {
    var loggedDeprecationWarning = false
    graph.V
      .hasLabel(srcLabels.head, srcLabels.tail: _*)
      .sideEffect { srcNode: Vertex =>
        if (!srcNode.edges(Direction.OUT, edgeType).hasNext) {
          dstNodeMap.get(getDstFullName(srcNode)) match {
            case Some(dstNode) =>
              dstGraph.addEdgeInOriginal(srcNode, dstNode, edgeType)
            case None =>
              logFailedDstLookup(edgeType,
                                  srcNode.label,
                                  srcNode.id.toString,
                                  dstNodeLabel,
                                  getDstFullName(srcNode))
          }
        } else {
          val dstFullName =
            srcNode.vertices(Direction.OUT, edgeType).next.value2(NodeKeys.FULL_NAME)
          setDstFullName(srcNode, dstFullName)
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

  private def linkToMultiple(
      srcLabels: List[String],
      dstNodeLabel: String,
      edgeType: String,
      dstNodeMap: Map[String, Vertex],
      getDstFullNames: Vertex => Iterable[String],
      setDstFullNames: (Vertex, Iterable[String]) => Unit): Unit = {
    var loggedDeprecationWarning = false
    graph.V
      .hasLabel(srcLabels.head, srcLabels.tail: _*)
      .sideEffect { srcNode: Vertex => 
        if (!srcNode.edges(Direction.OUT, edgeType).hasNext) {
          getDstFullNames(srcNode).foreach { dstFullName =>
            dstNodeMap.get(dstFullName) match {
              case Some(dstNode) =>
                dstGraph.addEdgeInOriginal(srcNode, dstNode, edgeType)
              case None =>
                logFailedDstLookup(edgeType,
                                    srcNode.label,
                                    srcNode.id.toString,
                                    dstNodeLabel,
                                    dstFullName)

            }
          }
        } else {
          val dstFullNames = srcNode
            .vertices(Direction.OUT, edgeType)
            .asScala
            .map(_.value2(NodeKeys.FULL_NAME))
            .toIterable
          setDstFullNames(srcNode, dstFullNames)
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

  private def linkAstChildToParent(): Unit = {
    var loggedDeprecationWarning = false

    graph.V
      .hasLabel(NodeTypes.METHOD, NodeTypes.TYPE_DECL)
      .sideEffect { astChild =>
        astChild.edges(Direction.IN, EdgeTypes.AST).nextOption match {
          case None =>
            val astParentOption =
              astChild.value2(NodeKeys.AST_PARENT_TYPE) match {
                case NodeTypes.METHOD =>
                  methodFullNameToNode.get(astChild.value2(NodeKeys.AST_PARENT_FULL_NAME))
                case NodeTypes.TYPE_DECL =>
                  typeDeclFullNameToNode.get(astChild.value2(NodeKeys.AST_PARENT_FULL_NAME))
                case NodeTypes.NAMESPACE_BLOCK =>
                  namespaceBlockFullNameToNode.get(astChild.value2(NodeKeys.AST_PARENT_FULL_NAME))
                case _ =>
                  logger.error("Invalid AST_PARENT_TYPE=${astChild.value2(NodeKeys.AST_PARENT_FULL_NAME)}")
                  None
              }

            astParentOption match {
              case Some(astParent) =>
                dstGraph.addEdgeInOriginal(astParent, astChild, EdgeTypes.AST)
              case None =>
                logFailedSrcLookup(EdgeTypes.AST,
                                    astChild.value2(NodeKeys.AST_PARENT_TYPE),
                                    astChild.value2(NodeKeys.AST_PARENT_FULL_NAME),
                                    astChild.label,
                                    astChild.id.toString)
            }
          case Some(astEdge) if !loggedDeprecationWarning =>
            logger.warn(
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
        "edgeType=$edgeType, srcNodeType=$srcNodeType, srcNodeId=$srcNodeId, " +
        "dstNodeType=$dstNodeType, dstFullName=$dstFullName")
  }

  @inline
  private def logFailedSrcLookup(edgeType: String,
                                 srcNodeType: String,
                                 srcFullName: String,
                                 dstNodeType: String,
                                 dstNodeId: String): Unit = {
    logger.error(
      "Could not create edge. Destination lookup failed. " +
        "edgeType=$edgeType, srcNodeType=$srcNodeType, srcFullName=$srcFullName, " +
        "dstNodeType=$dstNodeType, dstNodeId=$dstNodeId")
  }
}

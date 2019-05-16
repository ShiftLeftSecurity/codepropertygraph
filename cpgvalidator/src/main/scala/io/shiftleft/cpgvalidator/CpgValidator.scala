package io.shiftleft.cpgvalidator

import io.shiftleft.codepropertygraph.Cpg
import gremlin.scala.{Edge, Vertex}
import io.shiftleft.cpgvalidator.FactConstructionClasses.{InFact, OutFact}
import org.apache.logging.log4j.LogManager
import org.apache.tinkerpop.gremlin.structure.Direction

import scala.collection.JavaConverters._

class CpgValidator(notEnhancedCpg: Cpg) {
  private var validationErrors = List.empty[ValidationError]
  private val logger = LogManager.getLogger(getClass)

  def validate(): Boolean = {
    validateOutFacts()
    validateInFacts()

    validationErrors.isEmpty
  }

  def getValidationErrors: List[ValidationError] = {
    validationErrors
  }

  def logValidationErrors(): Unit = {
    validationErrors.foreach { error =>
      logger.error(s"Validation error: $error")
    }

    if (validationErrors.isEmpty) {
      logger.info("Found 0 errors.")
    } else {
      logger.error(s"Found ${validationErrors.size} errors.")
    }
  }

  private def validateOutFacts(): Unit = {
    val outFactsByEdgeTypeBySrcType = getOutFactByEdgeTypeBySrcType

    outFactsByEdgeTypeBySrcType.foreach { case (srcType, outFactsByEdgeType) =>
      notEnhancedCpg.scalaGraph.V.hasLabel(srcType).sideEffectWithTraverser { traverser =>
        val srcNode = traverser.get
        val outEdges = srcNode.edges(Direction.OUT).asScala.toList
        outFactsByEdgeType.foreach { case (edgeType, outFacts) =>
          val actualDstNodes = outEdges.filter(_.label == edgeType).map(_.inVertex)
          outFacts.foreach { outFact =>
            validateOutDegree(srcNode, actualDstNodes, outFact)
          }

          validateAllDstNodeTypes(srcNode, edgeType, actualDstNodes, outFacts)
        }

        val allowedEdgeTypes = outFactsByEdgeType.map(_._1)
        validateAllOutEdgesTypes(srcNode, outEdges, allowedEdgeTypes)

      }.iterate()
    }
  }

  private def validateInFacts(): Unit = {
    val inFactsByEdgeTypeByDstType = getInFactByEdgeTypeByDstType

    inFactsByEdgeTypeByDstType.foreach { case (srcType, inFactsByEdgeType) =>
      notEnhancedCpg.scalaGraph.V.hasLabel(srcType).sideEffectWithTraverser { traverser =>
        val dstNode = traverser.get
        val inEdges = dstNode.edges(Direction.IN).asScala.toList
        inFactsByEdgeType.foreach { case (edgeType, inFacts) =>
          val actualSrcNodes = inEdges.filter(_.label == edgeType).map(_.outVertex)
          inFacts.foreach { inFact =>
            validateInDegree(dstNode, actualSrcNodes, inFact)
          }

          validateAllSrcNodeTypes(dstNode, edgeType, actualSrcNodes, inFacts)
        }

        val allowedEdgeTypes = inFactsByEdgeType.map(_._1)
        validateAllInEdgesTypes(dstNode, inEdges, allowedEdgeTypes)

      }.iterate()
    }
  }


  private def validateOutDegree(srcNode: Vertex, actualDstNodes: List[Vertex], outFact: OutFact): Unit = {
    val actualOutDegree =
      actualDstNodes.count(actualDstNode => outFact.dstNodeTypes.contains(actualDstNode.label))

    if (!outFact.outDegreeRange.contains(actualOutDegree)) {
      validationErrors =
        EdgeDegreeError(
          srcNode,
          outFact.edgeType,
          Direction.OUT,
          actualOutDegree,
          outFact.outDegreeRange,
          outFact.dstNodeTypes
        ) :: validationErrors
    }
  }

  private def validateInDegree(dstNode: Vertex, actualSrcNode: List[Vertex], inFact: InFact): Unit = {
    val actualInDegree =
      actualSrcNode.count(actualSrcNode => inFact.srcNodeTypes.contains(actualSrcNode.label))

    if (!inFact.inDegreeRange.contains(actualInDegree)) {
      validationErrors =
        EdgeDegreeError(
          dstNode,
          inFact.edgeType,
          Direction.IN,
          actualInDegree,
          inFact.inDegreeRange,
          inFact.srcNodeTypes
        ) :: validationErrors
    }
  }

  private def validateAllDstNodeTypes(srcNode: Vertex,
                                      edgeType: String,
                                      actualDstNodes: List[Vertex],
                                      outFacts: List[OutFact]): Unit = {
    val allAllowedDstTypes = outFacts.flatMap(_.dstNodeTypes).distinct

    val invalidDstNodes = actualDstNodes.filter(actualDstNode => !allAllowedDstTypes.contains(actualDstNode.label))

    if (invalidDstNodes.nonEmpty) {
      validationErrors = NodeTypeError(srcNode, edgeType, Direction.OUT, invalidDstNodes) :: validationErrors
    }
  }

  private def validateAllSrcNodeTypes(dstNode: Vertex,
                                      edgeType: String,
                                      actualSrcNodes: List[Vertex],
                                      inFacts: List[InFact]): Unit = {
    val allAllowedSrcTypes = inFacts.flatMap(_.srcNodeTypes).distinct

    val invalidSrcNodes = actualSrcNodes.filter(actualSrcNode => !allAllowedSrcTypes.contains(actualSrcNode.label))

    if (invalidSrcNodes.nonEmpty) {
      validationErrors = NodeTypeError(dstNode, edgeType, Direction.IN, invalidSrcNodes) :: validationErrors
    }
  }

  private def validateAllOutEdgesTypes(srcNode: Vertex,
                                       actualEdges: List[Edge],
                                       allowedEdgeTypes: List[String]): Unit = {
    val invalidEdges = actualEdges.filter(actualEdge => !allowedEdgeTypes.contains(actualEdge.label))
    if (invalidEdges.nonEmpty) {
      validationErrors = EdgeTypeError(srcNode, Direction.OUT, invalidEdges) :: validationErrors
    }
  }

  private def validateAllInEdgesTypes(dstNode: Vertex,
                                      actualEdges: List[Edge],
                                      allowedEdgeTypes: List[String]): Unit = {
    val invalidEdges = actualEdges.filter(actualEdge => !allowedEdgeTypes.contains(actualEdge.label))
    if (invalidEdges.nonEmpty) {
      validationErrors = EdgeTypeError(dstNode, Direction.IN, invalidEdges) :: validationErrors
    }
  }

  private def getOutFactByEdgeTypeBySrcType = {
    val outFactsBySrcAndEdgeType = Facts.nodeOutFacts.groupBy(outFact => (outFact.srcNodeType, outFact.edgeType)).toList
    val outFactsBySrcAndEdgeTypeBySrcType = outFactsBySrcAndEdgeType.groupBy(z => z._1._1).toList
    outFactsBySrcAndEdgeTypeBySrcType.map { case (srcType, list) =>
      (srcType, list.map { case ((_, edgeType), outFacts) =>
        (edgeType, outFacts)
      }.sortBy(_._1)) // Sort by edgeType
    }.sortBy(_._1) // Sort by srcType
  }

  private def getInFactByEdgeTypeByDstType = {
    val inFactsByDstAndEdgeType = Facts.nodeInFacts.groupBy(inFact => (inFact.dstNodeType, inFact.edgeType)).toList
    val inFactsByDstAndEdgeTypeByDstType = inFactsByDstAndEdgeType.groupBy(z => z._1._1).toList
    inFactsByDstAndEdgeTypeByDstType.map { case (dstType, list) =>
      (dstType, list.map { case ((_, edgeType), inFacts) =>
        (edgeType, inFacts)
      }.sortBy(_._1)) // Sort by edgeType
    }.sortBy(_._1) // Sort by srcType
  }

}

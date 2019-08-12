package io.shiftleft.cpgvalidator.validators

import gremlin.scala.{Edge, Vertex}
import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.cpgvalidator.facts.FactConstructionClasses.InFact
import io.shiftleft.cpgvalidator._
import io.shiftleft.cpgvalidator.facts.InFactsImporter
import org.apache.tinkerpop.gremlin.structure.Direction

import scala.collection.JavaConverters._

class InFactsValidator extends Validator {

  override def validate(notEnhancedCpg: Cpg): Boolean = {
    validateInFacts(notEnhancedCpg)
    errorRegistry.getErrorCount == 0
  }

  private def validateInFacts(notEnhancedCpg: Cpg): Unit = {
    val inFactsByEdgeTypeByDstType = getInFactByEdgeTypeByDstType

    inFactsByEdgeTypeByDstType.foreach {
      case (srcType, inFactsByEdgeType) =>
        notEnhancedCpg.scalaGraph.V
          .hasLabel(srcType)
          .sideEffectWithTraverser { traverser =>
            val dstNode = traverser.get
            val inEdges = dstNode.edges(Direction.IN).asScala.toList
            inFactsByEdgeType.foreach {
              case (edgeType, inFacts) =>
                val actualSrcNodes =
                  inEdges.filter(_.label == edgeType).map(_.outVertex)
                inFacts.foreach { inFact =>
                  validateInDegree(dstNode, actualSrcNodes, inFact)
                }

                validateAllSrcNodeTypes(
                  dstNode,
                  edgeType,
                  actualSrcNodes,
                  inFacts
                )
            }

            val allowedEdgeTypes = inFactsByEdgeType.map(_._1)
            validateAllInEdgesTypes(dstNode, inEdges, allowedEdgeTypes)

          }
          .iterate()
    }
  }

  private def validateInDegree(dstNode: Vertex,
                               actualSrcNode: List[Vertex],
                               inFact: InFact): Unit = {
    val actualInDegree =
      actualSrcNode.count(
        actualSrcNode => inFact.srcNodeTypes.contains(actualSrcNode.label)
      )

    if (!inFact.inDegreeRange.contains(actualInDegree)) {
      errorRegistry.addError(
        EdgeDegreeError(
          dstNode,
          inFact.edgeType,
          Direction.IN,
          actualInDegree,
          inFact.inDegreeRange,
          inFact.srcNodeTypes
        )
      )
    }
  }

  private def validateAllSrcNodeTypes(dstNode: Vertex,
                                      edgeType: String,
                                      actualSrcNodes: List[Vertex],
                                      inFacts: List[InFact]): Unit = {
    val allAllowedSrcTypes = inFacts.flatMap(_.srcNodeTypes).distinct

    val invalidSrcNodes = actualSrcNodes.filter(
      actualSrcNode => !allAllowedSrcTypes.contains(actualSrcNode.label)
    )

    if (invalidSrcNodes.nonEmpty) {
      errorRegistry.addError(
        NodeTypeError(dstNode, edgeType, Direction.IN, invalidSrcNodes)
      )
    }
  }

  private def validateAllInEdgesTypes(dstNode: Vertex,
                                      actualEdges: List[Edge],
                                      allowedEdgeTypes: List[String]): Unit = {
    val invalidEdges = actualEdges.filter(
      actualEdge => !allowedEdgeTypes.contains(actualEdge.label)
    )
    if (invalidEdges.nonEmpty) {
      errorRegistry.addError(EdgeTypeError(dstNode, Direction.IN, invalidEdges))
    }
  }

  private def getInFactByEdgeTypeByDstType
    : List[(String, List[(String, List[InFact])])] = {
    val inFactsByDstAndEdgeType = new InFactsImporter().loadFacts
      .groupBy(inFact => (inFact.dstNodeType, inFact.edgeType))
      .toList
    val inFactsByDstAndEdgeTypeByDstType =
      inFactsByDstAndEdgeType.groupBy(z => z._1._1).toList
    inFactsByDstAndEdgeTypeByDstType
      .map {
        case (dstType, list) =>
          (
            dstType,
            list
              .map {
                case ((_, edgeType), inFacts) =>
                  (edgeType, inFacts)
              }
              .sortBy(_._1)
          ) // Sort by edgeType
      }
      .sortBy(_._1) // Sort by srcType
  }

}

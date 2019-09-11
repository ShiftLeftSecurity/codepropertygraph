package io.shiftleft.cpgvalidator.validators

import gremlin.scala.{Edge, Vertex}
import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.cpgvalidator.facts.FactConstructionClasses.OutFact
import io.shiftleft.cpgvalidator._
import io.shiftleft.cpgvalidator.facts.OutFactsImporter
import org.apache.tinkerpop.gremlin.structure.Direction

import scala.collection.JavaConverters._

class OutFactsValidator(errorRegistry: ValidationErrorRegistry) extends Validator {

  override def validate(notEnhancedCpg: Cpg): Boolean = {
    validateOutFacts(notEnhancedCpg)
    errorRegistry.getErrorCount == 0
  }

  private def validateOutFacts(notEnhancedCpg: Cpg): Unit = {
    val outFactsByEdgeTypeBySrcType = getOutFactByEdgeTypeBySrcType

    outFactsByEdgeTypeBySrcType.foreach {
      case (srcType, outFactsByEdgeType) =>
        notEnhancedCpg.scalaGraph.V
          .hasLabel(srcType)
          .sideEffectWithTraverser { traverser =>
            val srcNode = traverser.get
            val outEdges = srcNode.edges(Direction.OUT).asScala.toList
            outFactsByEdgeType.foreach {
              case (edgeType, outFacts) =>
                val actualDstNodes =
                  outEdges.filter(_.label == edgeType).map(_.inVertex)
                outFacts.foreach { outFact =>
                  validateOutDegree(srcNode, actualDstNodes, outFact)
                }

                validateAllDstNodeTypes(
                  srcNode,
                  edgeType,
                  actualDstNodes,
                  outFacts
                )
            }

            val allowedEdgeTypes = outFactsByEdgeType.map(_._1)
            validateAllOutEdgesTypes(srcNode, outEdges, allowedEdgeTypes)

          }
          .iterate()
    }
  }

  private def getOutFactByEdgeTypeBySrcType: List[(String, List[(String, List[OutFact])])] = {
    val outFactsBySrcAndEdgeType = new OutFactsImporter().loadFacts
      .groupBy(outFact => (outFact.srcNodeType, outFact.edgeType))
      .toList
    val outFactsBySrcAndEdgeTypeBySrcType =
      outFactsBySrcAndEdgeType.groupBy(z => z._1._1).toList
    outFactsBySrcAndEdgeTypeBySrcType
      .map {
        case (srcType, list) =>
          (
            srcType,
            list
              .map {
                case ((_, edgeType), outFacts) =>
                  (edgeType, outFacts)
              }
              .sortBy(_._1)
          ) // Sort by edgeType
      }
      .sortBy(_._1) // Sort by srcType
  }

  private def validateOutDegree(srcNode: Vertex, actualDstNodes: List[Vertex], outFact: OutFact): Unit = {
    val actualOutDegree =
      actualDstNodes.count(
        actualDstNode => outFact.dstNodeTypes.contains(actualDstNode.label)
      )

    if (!outFact.outDegreeRange.contains(actualOutDegree)) {
      errorRegistry.addError(
        EdgeDegreeError(
          srcNode,
          outFact.edgeType,
          Direction.OUT,
          actualOutDegree,
          outFact.outDegreeRange,
          outFact.dstNodeTypes
        )
      )
    }
  }

  private def validateAllDstNodeTypes(srcNode: Vertex,
                                      edgeType: String,
                                      actualDstNodes: List[Vertex],
                                      outFacts: List[OutFact]): Unit = {
    val allAllowedDstTypes = outFacts.flatMap(_.dstNodeTypes).distinct

    val invalidDstNodes = actualDstNodes.filter(
      actualDstNode => !allAllowedDstTypes.contains(actualDstNode.label)
    )

    if (invalidDstNodes.nonEmpty) {
      errorRegistry.addError(
        NodeTypeError(srcNode, edgeType, Direction.OUT, invalidDstNodes)
      )
    }
  }

  private def validateAllOutEdgesTypes(srcNode: Vertex,
                                       actualEdges: List[Edge],
                                       allowedEdgeTypes: List[String]): Unit = {
    val invalidEdges = actualEdges.filter(
      actualEdge => !allowedEdgeTypes.contains(actualEdge.label)
    )
    if (invalidEdges.nonEmpty) {
      errorRegistry.addError(
        EdgeTypeError(srcNode, Direction.OUT, invalidEdges)
      )
    }
  }

}

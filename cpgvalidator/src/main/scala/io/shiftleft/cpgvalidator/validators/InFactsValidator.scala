package io.shiftleft.cpgvalidator.validators

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.NodeTypes
import io.shiftleft.cpgvalidator.facts.FactConstructionClasses.InFact
import io.shiftleft.cpgvalidator._
import io.shiftleft.cpgvalidator.facts.InFactsImporter
import overflowdb.{Direction, Node, OdbEdge}

import scala.jdk.CollectionConverters._

class InFactsValidator(errorRegistry: ValidationErrorRegistry) extends Validator {

  override def validate(notEnhancedCpg: Cpg): Boolean = {
    validateInFacts(notEnhancedCpg)
    errorRegistry.getErrorCount == 0
  }

  private def validateInFacts(notEnhancedCpg: Cpg): Unit = {
    getInFactByEdgeTypeByDstType.foreach {
      case (srcType, inFactsByEdgeType) =>
        notEnhancedCpg.graph.nodes(srcType).asScala.foreach {
          case dstNode if dstNode.label != NodeTypes.UNKNOWN =>
            val inEdges = dstNode.inE.asScala.toList
            inFactsByEdgeType.foreach {
              case (edgeType, inFacts) =>
                val actualSrcNodes = inEdges.filter(_.label == edgeType).map(_.outNode)
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
            val allowedEdgeTypes = inFactsByEdgeType.map(_._1) :+ NodeTypes.UNKNOWN
            validateAllInEdgesTypes(dstNode, inEdges, allowedEdgeTypes)
          case _ => // Do nothing. Hence, we skip UNKNOWN nodes
        }
    }
  }

  private def validateInDegree(dstNode: Node, actualSrcNode: List[Node], inFact: InFact): Unit = {
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

  private def validateAllSrcNodeTypes(dstNode: Node,
                                      edgeType: String,
                                      actualSrcNodes: List[Node],
                                      inFacts: List[InFact]): Unit = {
    val allAllowedSrcTypes = inFacts.flatMap(_.srcNodeTypes).distinct :+ NodeTypes.UNKNOWN

    val invalidSrcNodes = actualSrcNodes.filter(
      actualSrcNode => !allAllowedSrcTypes.contains(actualSrcNode.label)
    )

    if (invalidSrcNodes.nonEmpty) {
      errorRegistry.addError(
        NodeTypeError(dstNode, edgeType, Direction.IN, invalidSrcNodes)
      )
    }
  }

  private def validateAllInEdgesTypes(dstNode: Node,
                                      actualEdges: List[OdbEdge],
                                      allowedEdgeTypes: List[String]): Unit = {
    val invalidEdges = actualEdges.filter(
      actualEdge => !allowedEdgeTypes.contains(actualEdge.label)
    )
    if (invalidEdges.nonEmpty) {
      errorRegistry.addError(EdgeTypeError(dstNode, Direction.IN, invalidEdges))
    }
  }

  private def getInFactByEdgeTypeByDstType: List[(String, List[(String, List[InFact])])] = {
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

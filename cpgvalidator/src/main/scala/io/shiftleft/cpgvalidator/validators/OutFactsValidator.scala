package io.shiftleft.cpgvalidator.validators

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.NodeTypes
import io.shiftleft.cpgvalidator.facts.FactConstructionClasses.OutFact
import io.shiftleft.cpgvalidator._
import io.shiftleft.cpgvalidator.facts.OutFactsImporter
import org.apache.tinkerpop.gremlin.structure.Direction
import overflowdb.{Node, OdbEdge}

import scala.jdk.CollectionConverters._

class OutFactsValidator(errorRegistry: ValidationErrorRegistry) extends Validator {

  override def validate(notEnhancedCpg: Cpg): Boolean = {
    validateOutFacts(notEnhancedCpg)
    errorRegistry.getErrorCount == 0
  }

  private def validateOutFacts(notEnhancedCpg: Cpg): Unit = {
    getOutFactByEdgeTypeBySrcType.foreach {
      case (srcType, outFactsByEdgeType) =>
        notEnhancedCpg.graph.nodesByLabel(srcType).asScala.foreach {
          case srcNode if srcNode.label != NodeTypes.UNKNOWN =>
            val outEdges = srcNode.outE.asScala.toList
            outFactsByEdgeType.foreach {
              case (edgeType, outFacts) =>
                val actualDstNodes = outEdges.filter(_.label == edgeType).map(_.inNode)
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
            val allowedEdgeTypes = outFactsByEdgeType.map(_._1) :+ NodeTypes.UNKNOWN
            validateAllOutEdgesTypes(srcNode, outEdges, allowedEdgeTypes)
          case _ => // Do nothing. Hence, we skip UNKNOWN nodes
        }
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

  private def validateOutDegree(srcNode: Node, actualDstNodes: List[Node], outFact: OutFact): Unit = {
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

  private def validateAllDstNodeTypes(srcNode: Node,
                                      edgeType: String,
                                      actualDstNodes: List[Node],
                                      outFacts: List[OutFact]): Unit = {
    val allAllowedDstTypes = outFacts.flatMap(_.dstNodeTypes).distinct :+ NodeTypes.UNKNOWN

    val invalidDstNodes = actualDstNodes.filter(
      actualDstNode => !allAllowedDstTypes.contains(actualDstNode.label)
    )

    if (invalidDstNodes.nonEmpty) {
      errorRegistry.addError(
        NodeTypeError(srcNode, edgeType, Direction.OUT, invalidDstNodes)
      )
    }
  }

  private def validateAllOutEdgesTypes(srcNode: Node,
                                       actualEdges: List[OdbEdge],
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

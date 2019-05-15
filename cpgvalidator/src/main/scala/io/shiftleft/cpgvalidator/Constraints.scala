package io.shiftleft.cpgvalidator

import gremlin.scala.Vertex
import io.shiftleft.cpgvalidator.FactConstructionClasses.{InFact, OutFact}
import org.apache.tinkerpop.gremlin.structure.Direction

import scala.collection.JavaConverters._

object Constraints {

  sealed trait ValidationResult
  object ValidationSuccess extends ValidationResult
  sealed trait ValidationError extends ValidationResult

  case class ValidationOutDegreeError(srcNode: Vertex,
                                      outConstraint: OutFact,
                                      invalidDegree: Int) extends ValidationError
  case class ValidationInDegreeError(dstNode: Vertex,
                                     inConstraint: InFact,
                                     invalidDegree: Int) extends ValidationError
  case class ValidationDstNodeError(srcNode: Vertex,
                                    outConstraint: OutFact,
                                    invalidDstNodes: List[Vertex]) extends ValidationError
  case class ValidationSrcNodeError(dstNode: Vertex,
                                    inConstraint: InFact,
                                    invalidSrcNodes: List[Vertex]) extends ValidationError

  trait Constraint {
    def validate(node: Vertex): ValidationResult
  }

  class OutDegreeConstraint(outFact: OutFact) extends Constraint {
    def validate(node: Vertex): ValidationResult = {
      val actualDstNodes = node.vertices(Direction.OUT, outFact.edgeType).asScala
        .filter(dstNode => outFact.dstNodeTypes.contains(dstNode.label))
        .toList

      if (actualDstNodes.size < outFact.outDegreeRange.start ||
        actualDstNodes.size > outFact.outDegreeRange.end) {
        ValidationOutDegreeError(node, outFact, actualDstNodes.size)
      } else {
        ValidationSuccess
      }
    }
  }

  class InDegreeConstraint(inConstraint: InFact) extends Constraint {
    def validate(node: Vertex): ValidationResult = {
      val actualSrcNodes = node.vertices(Direction.IN, inConstraint.edgeType).asScala
        .filter(srcNode => inConstraint.srcNodeTypes.contains(srcNode.label))
        .toList

      if (actualSrcNodes.size < inConstraint.inDegreeRange.start ||
        actualSrcNodes.size > inConstraint.inDegreeRange.end) {
        ValidationInDegreeError(node, inConstraint, actualSrcNodes.size)
      } else {
        ValidationSuccess
      }
    }
  }
}

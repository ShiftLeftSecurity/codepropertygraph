package io.shiftleft.cpgvalidator

import gremlin.scala.Vertex
import io.shiftleft.cpgvalidator.ConstraintClasses.{InConstraint, OutConstraint}
import org.apache.tinkerpop.gremlin.structure.Direction

import scala.collection.JavaConverters._

object ConstraintValidators {

  sealed trait ValidationResult
  object ValidationSuccess extends ValidationResult
  sealed trait ValidationError extends ValidationResult

  case class ValidationOutDegreeError(srcNode: Vertex,
                                      outConstraint: OutConstraint,
                                      invalidDegree: Int) extends ValidationError
  case class ValidationInDegreeError(dstNode: Vertex,
                                     inConstraint: InConstraint,
                                     invalidDegree: Int) extends ValidationError
  case class ValidationDstNodeError(srcNode: Vertex,
                                    outConstraint: OutConstraint,
                                    invalidDstNodes: List[Vertex]) extends ValidationError
  case class ValidationSrcNodeError(dstNode: Vertex,
                                    inConstraint: InConstraint,
                                    invalidSrcNodes: List[Vertex]) extends ValidationError

  trait ConstraintValidator {
    def validate(node: Vertex): ValidationResult
  }

  class OutConstraintValidator(outConstraint: OutConstraint) extends ConstraintValidator {
    def validate(node: Vertex): ValidationResult = {
      val actualDstNodes = node.vertices(Direction.OUT, outConstraint.edgeType).asScala
        .filter(dstNode => outConstraint.dstNodeTypes.contains(dstNode.label))
        .toList

      if (actualDstNodes.size < outConstraint.outDegreeRange.start ||
        actualDstNodes.size > outConstraint.outDegreeRange.end) {
        ValidationOutDegreeError(node, outConstraint, actualDstNodes.size)
      } else {
        ValidationSuccess
      }
    }
  }

  class InConstraintValidator(inConstraint: InConstraint) extends ConstraintValidator {
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

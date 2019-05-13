package io.shiftleft.cpgvalidator

import gremlin.scala.Vertex
import io.shiftleft.cpgvalidator.ConstraintClasses.{InConstraint, OutConstraint}
import org.apache.tinkerpop.gremlin.structure.Direction

import scala.collection.JavaConverters._

object ConstraintValidators {

  sealed trait ValidationResult
  object ValidationSuccess extends ValidationResult
  sealed trait ValidationError extends ValidationResult
  case class ValidationOutDegreeError(outConstraint: OutConstraint,
                                      invalidDegree: Int) extends ValidationError
  case class ValidationInDegreeError(inConstraint: InConstraint,
                                     invalidDegree: Int) extends ValidationError
  case class ValidationDstNodeError(outConstraint: OutConstraint,
                                    invalidDstNodes: List[Vertex]) extends ValidationError
  case class ValidationSrcNodeError(inConstraint: InConstraint,
                                    invalidSrcNodes: List[Vertex]) extends ValidationError

  trait ConstraintValidator {
    def validate(node: Vertex): ValidationResult
  }

  class OutConstraintValidator(outConstraint: OutConstraint) extends ConstraintValidator {
    def validate(node: Vertex): ValidationResult = {
      val actualDstNodes = node.vertices(Direction.OUT, outConstraint.edgeType).asScala.toList

      if (actualDstNodes.size < outConstraint.outDegreeRange.start ||
        actualDstNodes.size > outConstraint.outDegreeRange.end) {
        ValidationOutDegreeError(outConstraint, actualDstNodes.size)
      } else {
        val invalidDstNodes = getInvalidDstNodes(actualDstNodes)

        if (invalidDstNodes.nonEmpty) {
          ValidationDstNodeError(outConstraint, invalidDstNodes)
        } else {
          ValidationSuccess
        }
      }
    }

    private def getInvalidDstNodes(actualDstNodes: List[Vertex]): List[Vertex] = {
      actualDstNodes.filter(actualDstNode => !outConstraint.dstNodeTypes.contains(actualDstNode.label))
    }
  }

  class InConstraintValidator(inConstraint: InConstraint) extends ConstraintValidator {
    def validate(node: Vertex): ValidationResult = {
      val actualSrcNodes = node.vertices(Direction.IN, inConstraint.edgeType).asScala.toList

      if (actualSrcNodes.size < inConstraint.inDegreeRange.start ||
        actualSrcNodes.size > inConstraint.inDegreeRange.end) {
        ValidationInDegreeError(inConstraint, actualSrcNodes.size)
      } else {
        val invalidDstNodes = getInvalidSrcNodes(actualSrcNodes)

        if (invalidDstNodes.nonEmpty) {
          ValidationSrcNodeError(inConstraint, invalidDstNodes)
        } else {
          ValidationSuccess
        }
      }
    }

    private def getInvalidSrcNodes(actualSrcNodes: List[Vertex]): List[Vertex] = {
      actualSrcNodes.filter(actualSrcNode => !inConstraint.srcNodeTypes.contains(actualSrcNode.label))
    }
  }
}

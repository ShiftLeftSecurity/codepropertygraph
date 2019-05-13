package io.shiftleft.cpgvalidator

import io.shiftleft.codepropertygraph.Cpg
import ConstraintValidators._
import org.apache.logging.log4j.LogManager

class CpgValidator(notEnhancedCpg: Cpg) {
  private var validationErrors = List.empty[ValidationError]
  private val logger = LogManager.getLogger(getClass)

  private def validate(nodeType: String, validators: List[ConstraintValidator]): Unit = {
    notEnhancedCpg.scalaGraph.V().hasLabel(nodeType).sideEffectWithTraverser { traverser =>
      val node = traverser.get
      val valErrors =
        validators.flatMap { validator =>
          val validationResult = validator.validate(node)
          validationResult match {
            case ValidationSuccess => None
            case error: ValidationError => Some(error)
          }
        }

      validationErrors = validationErrors ++ valErrors
    }.iterate()
  }

  def validate(): Boolean = {
    val outConstraintsBySrcType = Constraints.outConstraints.groupBy(_.srcNodeType)
    val outConstraintSrcTypesSorted = Constraints.outConstraints.map(_.srcNodeType).distinct.sorted

    outConstraintSrcTypesSorted.foreach { srcType =>
      validate(srcType, outConstraintsBySrcType(srcType).map(constraint => new OutConstraintValidator(constraint)))
    }

    val inConstraintsByDstType = Constraints.inConstraints.groupBy(_.dstNodeType)
    val inConstraintDstTypesSorted = Constraints.outConstraints.map(_.srcNodeType).distinct.sorted

    inConstraintDstTypesSorted.foreach { dstType =>
      validate(dstType, inConstraintsByDstType(dstType).map(constraint => new InConstraintValidator(constraint)))
    }

    validationErrors.isEmpty
  }

  def getValidationErrors: List[ValidationError] = {
    validationErrors
  }

  def logValidationErrors(): Unit = {
    validationErrors.foreach { error =>
      logger.error(s"Validation error: $error")
    }
  }

}

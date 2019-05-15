package io.shiftleft.cpgvalidator

import io.shiftleft.codepropertygraph.Cpg
import Constraints._
import org.apache.logging.log4j.LogManager

class CpgValidator(notEnhancedCpg: Cpg) {
  private var validationErrors = List.empty[ValidationError]
  private val logger = LogManager.getLogger(getClass)

  private def validate(nodeType: String, validators: List[Constraint]): Unit = {
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
    val outConstraintsBySrcType = Facts.nodeOutFacts.groupBy(_.srcNodeType)
    val outConstraintSrcTypesSorted = Facts.nodeOutFacts.map(_.srcNodeType).distinct.sorted

    outConstraintSrcTypesSorted.foreach { srcType =>
      validate(srcType, outConstraintsBySrcType(srcType).map(constraint => new OutDegreeConstraint(constraint)))
    }

    val inConstraintsByDstType = Facts.nodeInFacts.groupBy(_.dstNodeType)
    val inConstraintDstTypesSorted = Facts.nodeInFacts.map(_.dstNodeType).distinct.sorted

    inConstraintDstTypesSorted.foreach { dstType =>
      validate(dstType, inConstraintsByDstType(dstType).map(constraint => new InDegreeConstraint(constraint)))
    }






    val outConstraintsBySrcNodeAndEdgeType =
      Facts.nodeOutFacts.groupBy(outConstraint => (outConstraint.srcNodeType, outConstraint.edgeType))
        .toList

    val allowedDstNodesForSrcEdgeTypePair =
      outConstraintsBySrcNodeAndEdgeType.map { case ((srcType, edgeType), outConstraints) =>
        (srcType, edgeType, outConstraints.flatMap(_.dstNodeTypes).distinct)
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

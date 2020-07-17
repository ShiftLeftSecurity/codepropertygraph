package io.shiftleft.cpgvalidator

import org.slf4j.Logger
import org.slf4j.LoggerFactory

import scala.collection.mutable

class ValidationErrorRegistry {
  import ValidationErrorRegistry.logger

  protected val validationErrors: mutable.Map[ValidationErrorCategory, List[ValidationError]] =
    mutable.Map.empty

  def addError(error: ValidationError): Unit = {
    val category = error.getCategory
    val errorsForCategory = validationErrors.getOrElse(category, Nil)
    val updatedErrorsForCategory = error :: errorsForCategory
    validationErrors.put(category, updatedErrorsForCategory)
  }

  def logValidationErrors(): Unit = {
    // We only log one error per category in order to not spam the user
    // because errors are usually quite repetitive.
    validationErrors.foreach {
      case (_, errors) =>
        logger.error(s"Validation error: ${errors.head}")
    }

    if (validationErrors.isEmpty) {
      logger.info("Found 0 errors.")
    } else {
      logger.error(s"Found $getErrorCount errors.")
    }
  }

  def getErrorCount: Int = {
    validationErrors.keys.size
  }

}

object ValidationErrorRegistry {
  private val logger: Logger = LoggerFactory.getLogger(classOf[ValidationErrorRegistry])
}

package io.shiftleft.cpgvalidator

import org.apache.logging.log4j.LogManager

import scala.collection.mutable

class ValidationErrorRegistry {
  private var validationErrors = mutable.Map[ValidationErrorCategory, List[ValidationError]]()
  private val logger = LogManager.getLogger(getClass)

  def addError(error: ValidationError): Unit = {
    val category = error.getCategory
    val errorsForCategory = validationErrors.getOrElse(category, Nil)
    val updatedErrorsForCategory = error :: errorsForCategory
    validationErrors.put(category, updatedErrorsForCategory)
  }

  def logValidationErrors(): Unit = {
    // We only log one error per category in order to not spam the user
    // because errors are usually quite repetitive.
    validationErrors.toList.foreach {
      case (categery, errors) =>
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

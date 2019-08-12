package io.shiftleft.cpgvalidator.validators

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.cpgvalidator.ValidationErrorRegistry

abstract class Validator {
  protected val errorRegistry = new ValidationErrorRegistry()

  def validate(notEnhancedCpg: Cpg): Boolean

  def logValidationErrors(): Unit =
    errorRegistry.logValidationErrors()

}

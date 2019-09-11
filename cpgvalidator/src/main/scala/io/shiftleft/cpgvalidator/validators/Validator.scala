package io.shiftleft.cpgvalidator.validators

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.cpgvalidator.ValidationErrorRegistry

abstract class Validator() {
  def validate(notEnhancedCpg: Cpg): Boolean
}

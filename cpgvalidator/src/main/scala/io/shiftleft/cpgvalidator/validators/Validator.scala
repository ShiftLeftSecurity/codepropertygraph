package io.shiftleft.cpgvalidator.validators

import io.shiftleft.codepropertygraph.Cpg

abstract class Validator() {
  def validate(notEnhancedCpg: Cpg): Boolean
}

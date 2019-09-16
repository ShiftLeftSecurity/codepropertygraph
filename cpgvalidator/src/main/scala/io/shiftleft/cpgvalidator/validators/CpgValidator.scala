package io.shiftleft.cpgvalidator.validators

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.cpgvalidator.ValidationErrorRegistry

class CpgValidator(errorRegistry: ValidationErrorRegistry) {
  private val validators =
    Seq(new OutFactsValidator(errorRegistry), new InFactsValidator(errorRegistry), new KeysValidator(errorRegistry))

  def validate(notEnhancedCpg: Cpg): Boolean =
    validators.forall(_.validate(notEnhancedCpg))
}

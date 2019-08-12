package io.shiftleft.cpgvalidator.validators

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.cpgvalidator.ValidationErrorRegistry

class CpgValidator extends Validator {
  private val validators =
    Seq(new OutFactsValidator(), new InFactsValidator(), new KeysValidator())

  override def validate(notEnhancedCpg: Cpg): Boolean =
    validators.forall(_.validate(notEnhancedCpg))

}

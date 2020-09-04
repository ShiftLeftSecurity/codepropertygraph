package io.shiftleft.cpgvalidator.validators

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.cpgvalidator.ValidationErrorRegistry
import io.shiftleft.cpgvalidator.validators.cfg.NoLongJumpValidator

class CpgValidator(errorRegistry: ValidationErrorRegistry) {
  private val validators = Seq(
    new OutFactsValidator(errorRegistry),
    new InFactsValidator(errorRegistry),
    new KeysValidator(errorRegistry),
    new NoLongJumpValidator(errorRegistry),
    new CallReceiverValidator(errorRegistry)
  )

  def validate(notEnhancedCpg: Cpg): Boolean = {
    var validationOk = true
    validators.foreach { validator =>
      val result = validator.validate(notEnhancedCpg)
      if (!result) {
        validationOk = false
      }
    }
    validationOk
  }
}

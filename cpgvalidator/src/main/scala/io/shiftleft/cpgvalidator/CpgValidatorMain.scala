package io.shiftleft.cpgvalidator

import io.shiftleft.codepropertygraph.cpgloading.{CpgLoader, CpgLoaderConfig, OverflowDbConfig}
import io.shiftleft.cpgvalidator.validators.CpgValidator

object CpgValidatorMain extends App {
  val cpgFileName = args(0)

  val loaderConfig = CpgLoaderConfig().withOverflowConfig(OverflowDbConfig.disabled)
  val cpg = CpgLoader.load(cpgFileName, loaderConfig)

  val validator = new CpgValidator()
  val cpgValid = validator.validate(cpg)
  validator.logValidationErrors()

  if (cpgValid) {
    System.exit(0)
  } else {
    System.exit(1)
  }
}

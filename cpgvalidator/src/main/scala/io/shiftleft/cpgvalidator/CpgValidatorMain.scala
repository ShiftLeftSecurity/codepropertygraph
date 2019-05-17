package io.shiftleft.cpgvalidator

import io.shiftleft.codepropertygraph.cpgloading.{CpgLoader, CpgLoaderConfig}

object CpgValidatorMain extends App {
  val cpgFileName = args(0)

  val loaderConfig = new CpgLoaderConfig(createIndices = true, onDiskOverflowConfig = None, ignoredProtoEntries = None)
  val cpg = CpgLoader.load(cpgFileName, loaderConfig)

  val validator = new CpgValidator(cpg)
  val cpgValid = validator.validate()
  validator.logValidationErrors()

  if (cpgValid) {
    System.exit(0)
  } else {
    System.exit(1)
  }
}

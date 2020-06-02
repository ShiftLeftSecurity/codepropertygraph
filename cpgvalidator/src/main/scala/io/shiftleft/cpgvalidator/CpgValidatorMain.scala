package io.shiftleft.cpgvalidator

import java.io.File

import io.shiftleft.codepropertygraph.cpgloading.{CpgLoader, CpgLoaderConfig}
import io.shiftleft.cpgvalidator.validators.CpgValidator
import io.shiftleft.overflowdb.OdbConfig

object CpgValidatorMain extends App {
  case class Config(cpgPath: String, isOldProtoCpg: Boolean = false)

  def parseConfig: Option[Config] =
    new scopt.OptionParser[Config](getClass.getSimpleName) {
      opt[String]("cpg").required
        .action((x, c) => c.copy(cpgPath = x))
        .text("path to cpg")
        .validate { cpgPath =>
          if (new File(cpgPath).exists) success
          else failure(s"$cpgPath does not exist")
        }
      opt[Unit]("oldProtoCpg").optional
        .action((x, c) => c.copy(isOldProtoCpg = true))
        .text("set flag if input is an old cpg proto (usually cpg.bin.zip)")
      help("help").text("prints this usage text")
    }.parse(args, Config(""))

  parseConfig.foreach { config =>
    val cpg =
      if (config.isOldProtoCpg) CpgLoader.load(config.cpgPath, CpgLoaderConfig.withoutOverflow)
      else
        CpgLoader.loadFromOverflowDb(
          CpgLoaderConfig.withoutOverflow.doNotCreateIndexesOnLoad
            .withOverflowConfig(OdbConfig.withoutOverflow.withStorageLocation(config.cpgPath)))

    val errorRegistry = new ValidationErrorRegistry
    val validator = new CpgValidator(errorRegistry)
    val cpgValid = validator.validate(cpg)
    errorRegistry.logValidationErrors()

    if (cpgValid) {
      System.exit(0)
    } else {
      System.exit(1)
    }
  }
}

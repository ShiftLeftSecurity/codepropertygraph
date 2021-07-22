package io.shiftleft.fuzzyc2cpg

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.Languages
import io.shiftleft.fuzzyc2cpg.passes.{AstCreationPass, StubRemovalPass}
import io.shiftleft.passes.{CpgPassRunner, IntervalKeyPool}
import io.shiftleft.semanticcpg.passes.CfgCreationPass
import io.shiftleft.semanticcpg.passes.metadata.MetaDataPass
import io.shiftleft.semanticcpg.passes.typenodes.TypeNodePass
import io.shiftleft.x2cpg.X2Cpg.newEmptyCpg
import io.shiftleft.x2cpg.{SourceFiles, X2Cpg, X2CpgConfig}
import org.slf4j.LoggerFactory
import scopt.OParser

import java.nio.file.Files
import java.util.concurrent.ConcurrentHashMap
import scala.collection.mutable.ListBuffer
import scala.jdk.CollectionConverters._
import scala.util.control.NonFatal

case class Global(usedTypes: ConcurrentHashMap[String, Boolean] = new ConcurrentHashMap[String, Boolean]())

class FuzzyC2Cpg() {
  import FuzzyC2Cpg.logger

  def runWithPreprocessorAndOutput(sourcePaths: Set[String],
                                   sourceFileExtensions: Set[String],
                                   includeFiles: Set[String],
                                   includePaths: Set[String],
                                   defines: Set[String],
                                   undefines: Set[String],
                                   preprocessorExecutable: String,
                                   optionalOutputPath: Option[String] = None): Unit = {
    // Create temp dir to store preprocessed source.
    val preprocessedPath = Files.createTempDirectory("fuzzyc2cpg_preprocessed_")
    logger.info(s"Writing preprocessed files to [$preprocessedPath]")

    val preprocessorLogFile = Files.createTempFile("fuzzyc2cpg_preprocessor_log", ".txt").toFile
    logger.info(s"Writing preprocessor logs to [$preprocessorLogFile]")

    val sourceFileNames = SourceFiles.determine(sourcePaths, sourceFileExtensions)

    val commandBuffer = new ListBuffer[String]()
    commandBuffer.appendAll(List(preprocessorExecutable, "--verbose", "-o", preprocessedPath.toString))
    if (sourceFileNames.nonEmpty) commandBuffer.appendAll(List("-f", sourceFileNames.mkString(",")))
    if (includeFiles.nonEmpty) commandBuffer.appendAll(List("--include", includeFiles.mkString(",")))
    if (includePaths.nonEmpty) commandBuffer.appendAll(List("-I", includePaths.mkString(",")))
    if (defines.nonEmpty) commandBuffer.appendAll(List("-D", defines.mkString(",")))
    if (undefines.nonEmpty) commandBuffer.appendAll(List("-U", defines.mkString(",")))

    val cmd = commandBuffer.toList

    // Run preprocessor
    logger.info("Running preprocessor...")
    val process = new ProcessBuilder()
      .redirectOutput(preprocessorLogFile)
      .redirectError(preprocessorLogFile)
      .command(cmd: _*)
      .start()
    val exitCode = process.waitFor()

    if (exitCode == 0) {
      logger.info(s"Preprocessing complete, files written to [$preprocessedPath], starting CPG generation...")
      val cpg = runAndOutput(Set(preprocessedPath.toString), sourceFileExtensions, optionalOutputPath)
      cpg.close()
    } else {
      logger.error(
        s"Error occurred whilst running preprocessor. Log written to [$preprocessorLogFile]. Exit code [$exitCode].")
    }
  }

  def runAndOutput(sourcePaths: Set[String],
                   sourceFileExtensions: Set[String],
                   optionalOutputPath: Option[String] = None): Cpg = {
    val metaDataKeyPool = new IntervalKeyPool(1, 100)
    val typesKeyPool = new IntervalKeyPool(100, 1000100)
    val functionKeyPool = new IntervalKeyPool(1000100, Long.MaxValue)

    val cpg = newEmptyCpg(optionalOutputPath)
    val sourceFileNames = SourceFiles.determine(sourcePaths, sourceFileExtensions)

    val passRunner = new CpgPassRunner(cpg, outputDir = None, inverse = false)
    passRunner.addPass(new MetaDataPass(Languages.C, Some(metaDataKeyPool)))
    val astCreator = new AstCreationPass(sourceFileNames, cpg, functionKeyPool)
    passRunner.addPass(astCreator)
    passRunner.addPass(new CfgCreationPass(cpg))
    passRunner.addPass(new StubRemovalPass(cpg))
    passRunner.addPass(new TypeNodePass(() => astCreator.global.usedTypes.keys().asScala.toList, Some(typesKeyPool)))
    passRunner.run()

    cpg
  }

}

object FuzzyC2Cpg {

  private val logger = LoggerFactory.getLogger(classOf[FuzzyC2Cpg])

  final case class Config(inputPaths: Set[String] = Set.empty,
                          outputPath: String = X2CpgConfig.defaultOutputPath,
                          sourceFileExtensions: Set[String] = Set(".c", ".cc", ".cpp", ".h", ".hpp"),
                          includeFiles: Set[String] = Set.empty,
                          includePaths: Set[String] = Set.empty,
                          defines: Set[String] = Set.empty,
                          undefines: Set[String] = Set.empty,
                          preprocessorExecutable: String = "./fuzzypp/bin/fuzzyppcli")
      extends X2CpgConfig[Config] {
    lazy val usePreprocessor: Boolean =
      includeFiles.nonEmpty || includePaths.nonEmpty || defines.nonEmpty || undefines.nonEmpty

    override def withAdditionalInputPath(inputPath: String): Config = copy(inputPaths = inputPaths + inputPath)
    override def withOutputPath(x: String): Config = copy(outputPath = x)
  }

  def main(args: Array[String]): Unit = {

    val frontendSpecificOptions = {
      val builder = OParser.builder[Config]
      import builder._
      OParser.sequence(
        programName(classOf[FuzzyC2Cpg].getSimpleName),
        opt[String]("out")
          .text("(DEPRECATED use `output`) output filename")
          .action { (x, c) =>
            logger.warn("`--out` is DEPRECATED. Use `--output` instead")
            c.withOutputPath(x)
          },
        opt[String]("source-file-ext")
          .unbounded()
          .text(
            "source file extensions to include when gathering source files. Defaults are .c, .cc, .cpp, .h and .hpp")
          .action((pat, cfg) => cfg.copy(sourceFileExtensions = cfg.sourceFileExtensions + pat)),
        opt[String]("include")
          .unbounded()
          .text("header include files")
          .action((incl, cfg) => cfg.copy(includeFiles = cfg.includeFiles + incl)),
        opt[String]('I', "")
          .unbounded()
          .text("header include paths")
          .action((incl, cfg) => cfg.copy(includePaths = cfg.includePaths + incl)),
        opt[String]('D', "define")
          .unbounded()
          .text("define a name")
          .action((d, cfg) => cfg.copy(defines = cfg.defines + d)),
        opt[String]('U', "undefine")
          .unbounded()
          .text("undefine a name")
          .action((u, cfg) => cfg.copy(undefines = cfg.undefines + u)),
        opt[String]("preprocessor-executable")
          .text("path to the preprocessor executable")
          .action((s, cfg) => cfg.copy(preprocessorExecutable = s)),
      )
    }

    X2Cpg.parseCommandLine(args, frontendSpecificOptions, Config()) match {
      case Some(config) =>
        try {
          val fuzzyc = new FuzzyC2Cpg()
          if (config.usePreprocessor) {
            fuzzyc.runWithPreprocessorAndOutput(
              config.inputPaths,
              config.sourceFileExtensions,
              config.includeFiles,
              config.includePaths,
              config.defines,
              config.undefines,
              config.preprocessorExecutable,
              Some(config.outputPath)
            )
          } else {
            val cpg = fuzzyc.runAndOutput(config.inputPaths, config.sourceFileExtensions, Some(config.outputPath))
            cpg.close()
          }
        } catch {
          case NonFatal(ex) =>
            logger.error("Failed to generate CPG.", ex)
            System.exit(1)
        }
      case None =>
        System.exit(1)
    }
  }

}

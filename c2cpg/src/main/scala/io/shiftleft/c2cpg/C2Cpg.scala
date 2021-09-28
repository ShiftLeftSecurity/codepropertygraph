package io.shiftleft.c2cpg

import io.shiftleft.c2cpg.C2Cpg.Config
import io.shiftleft.c2cpg.parser.{FileDefaults, HeaderFileFinder, ParseConfig}
import io.shiftleft.c2cpg.passes.{AstCreationPass, PreprocessorPass}
import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.Languages
import io.shiftleft.passes.IntervalKeyPool
import io.shiftleft.semanticcpg.passes.CfgCreationPass
import io.shiftleft.semanticcpg.passes.metadata.MetaDataPass
import io.shiftleft.semanticcpg.passes.typenodes.TypeNodePass
import io.shiftleft.x2cpg.X2Cpg.newEmptyCpg
import io.shiftleft.x2cpg.{SourceFiles, X2Cpg, X2CpgConfig}
import org.slf4j.LoggerFactory
import scopt.OParser

import java.nio.file.Paths
import scala.util.control.NonFatal

class C2Cpg {

  private def createParseConfig(config: Config): ParseConfig = {
    ParseConfig(
      config.includePaths.map(Paths.get(_)).toList,
      config.defines.map {
        case define if define.contains("=") =>
          val s = define.split("=")
          s.head -> s(1)
        case define => define -> "true"
      }.toMap,
      config.logProblems,
      config.logPreprocessor
    )
  }

  def runAndOutput(config: Config): Cpg = {
    val metaDataKeyPool = new IntervalKeyPool(1, 100)
    val typesKeyPool = new IntervalKeyPool(100, 1000100)
    val functionKeyPool = new IntervalKeyPool(1000100, Long.MaxValue)

    val cpg = newEmptyCpg(Some(config.outputPath))
    val sourceFileNames = SourceFiles.determine(config.inputPaths, config.sourceFileExtensions)

    new MetaDataPass(cpg, Languages.NEWC, Some(metaDataKeyPool)).createAndApply()
    val headerFileFinder = new HeaderFileFinder(config.inputPaths.toList)
    val astCreationPass =
      new AstCreationPass(sourceFileNames, cpg, functionKeyPool, config, createParseConfig(config), headerFileFinder)
    astCreationPass.createAndApply()
    new CfgCreationPass(cpg).createAndApply()
    new TypeNodePass(astCreationPass.usedTypes(), cpg, Some(typesKeyPool)).createAndApply()
    cpg
  }

  def printIfDefsOnly(config: Config): Unit = {
    val sourceFileNames = SourceFiles.determine(config.inputPaths, config.sourceFileExtensions)
    val headerFileFinder = new HeaderFileFinder(config.inputPaths.toList)
    val stmts = new PreprocessorPass(sourceFileNames, createParseConfig(config), headerFileFinder).run().mkString(",")
    println(stmts)
  }

}

object C2Cpg {

  private val logger = LoggerFactory.getLogger(classOf[C2Cpg])

  final case class Config(inputPaths: Set[String] = Set.empty,
                          outputPath: String = X2CpgConfig.defaultOutputPath,
                          sourceFileExtensions: Set[String] = FileDefaults.SOURCE_FILE_EXTENSIONS,
                          includePaths: Set[String] = Set.empty,
                          defines: Set[String] = Set.empty,
                          includeComments: Boolean = false,
                          logProblems: Boolean = false,
                          logPreprocessor: Boolean = false,
                          printIfDefsOnly: Boolean = false)
      extends X2CpgConfig[Config] {

    override def withAdditionalInputPath(inputPath: String): Config = copy(inputPaths = inputPaths + inputPath)
    override def withOutputPath(x: String): Config = copy(outputPath = x)
  }

  def main(args: Array[String]): Unit = {

    val frontendSpecificOptions = {
      val builder = OParser.builder[Config]
      import builder._
      OParser.sequence(
        programName(classOf[C2Cpg].getSimpleName),
        opt[Unit]("include-comments")
          .text(s"includes all comments into the CPG")
          .action((_, c) => c.copy(includeComments = true)),
        opt[Unit]("log-problems")
          .text(s"enables logging of all parse problems while generating the CPG")
          .action((_, c) => c.copy(logProblems = true)),
        opt[Unit]("log-preprocessor")
          .text(s"enables logging of all preprocessor statements while generating the CPG")
          .action((_, c) => c.copy(logPreprocessor = true)),
        opt[Unit]("print-ifdef-only")
          .text(s"prints a comma-separated list of all preprocessor ifdef and if statements; does not create a CPG")
          .action((_, c) => c.copy(printIfDefsOnly = true)),
        opt[String]("include")
          .unbounded()
          .text("header include paths")
          .action((incl, cfg) => cfg.copy(includePaths = cfg.includePaths + incl)),
        opt[String]("define")
          .unbounded()
          .text("define a name")
          .action((d, cfg) => cfg.copy(defines = cfg.defines + d))
      )
    }

    X2Cpg.parseCommandLine(args, frontendSpecificOptions, Config()) match {
      case Some(config) if config.printIfDefsOnly =>
        try {
          new C2Cpg().printIfDefsOnly(config)
        } catch {
          case NonFatal(ex) =>
            logger.error("Failed to print preprocessor statements.", ex)
            System.exit(1)
        }
      case Some(config) =>
        try {
          val cpg = new C2Cpg().runAndOutput(config)
          cpg.close()
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

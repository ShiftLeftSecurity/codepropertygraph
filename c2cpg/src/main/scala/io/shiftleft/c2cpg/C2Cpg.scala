package io.shiftleft.c2cpg

import io.shiftleft.c2cpg.C2Cpg.Config
import io.shiftleft.c2cpg.parser.ParseConfig
import io.shiftleft.codepropertygraph.generated.Languages
import io.shiftleft.c2cpg.passes.{AstCreationPass, StubRemovalPass, TypeNodePass}
import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.passes.IntervalKeyPool
import io.shiftleft.semanticcpg.passes.CfgCreationPass
import io.shiftleft.semanticcpg.passes.metadata.MetaDataPass
import io.shiftleft.x2cpg.X2Cpg.newEmptyCpg
import io.shiftleft.x2cpg.{SourceFiles, X2Cpg, X2CpgConfig}
import org.slf4j.LoggerFactory
import scopt.OParser

import java.nio.file.Paths
import java.util.concurrent.ConcurrentHashMap
import scala.util.control.NonFatal

case class Global(usedTypes: ConcurrentHashMap[String, Boolean] = new ConcurrentHashMap[String, Boolean]())

class C2Cpg() {

  private def createParseConfig(config: Config): ParseConfig = {
    ParseConfig(
      config.includePaths.map(Paths.get(_)).toList,
      config.defines.map {
        case define if define.contains("=") =>
          val s = define.split("=")
          s.head -> s(1)
        case define => define -> "true"
      }.toMap
    )
  }

  def runAndOutput(config: Config): Cpg = {
    val metaDataKeyPool = new IntervalKeyPool(1, 100)
    val typesKeyPool = new IntervalKeyPool(100, 1000100)
    val functionKeyPool = new IntervalKeyPool(1000100, Long.MaxValue)

    val cpg = newEmptyCpg(Some(config.outputPath))
    val sourceFileNames = SourceFiles.determine(config.inputPaths, config.sourceFileExtensions)

    new MetaDataPass(cpg, Languages.C, Some(metaDataKeyPool)).createAndApply()
    val astCreationPass = new AstCreationPass(sourceFileNames, cpg, functionKeyPool, createParseConfig(config))
    astCreationPass.createAndApply()
    new TypeNodePass(astCreationPass.usedTypes(), cpg, Some(typesKeyPool)).createAndApply()
    new CfgCreationPass(cpg).createAndApply()
    new StubRemovalPass(cpg).createAndApply()
    cpg
  }

}

object C2Cpg {

  private val logger = LoggerFactory.getLogger(classOf[C2Cpg])

  final case class Config(inputPaths: Set[String] = Set.empty,
                          outputPath: String = X2CpgConfig.defaultOutputPath,
                          sourceFileExtensions: Set[String] = Set(".c", ".cc", ".cpp", ".h", ".hpp"),
                          includePaths: Set[String] = Set.empty,
                          defines: Set[String] = Set.empty)
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
        opt[String]("include")
          .unbounded()
          .text("header include paths")
          .action((incl, cfg) => cfg.copy(includePaths = cfg.includePaths + incl)),
        opt[String]('D', "define")
          .unbounded()
          .text("define a name")
          .action((d, cfg) => cfg.copy(defines = cfg.defines + d))
      )
    }

    X2Cpg.parseCommandLine(args, frontendSpecificOptions, Config()) match {
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

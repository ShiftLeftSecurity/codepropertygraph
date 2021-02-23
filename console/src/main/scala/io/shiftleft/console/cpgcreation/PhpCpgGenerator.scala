package io.shiftleft.console.cpgcreation

import java.nio.file.Path

import io.shiftleft.console.PhpFrontendConfig

case class PhpCpgGenerator(config: PhpFrontendConfig, rootPath: Path) extends CpgGenerator {

  override def generate(inputPath: String, outputPath: String, namespaces: List[String]): Option[String] = {
    val command = rootPath.resolve("php2cp").toString
    val arguments = Seq("create") ++ List(inputPath) ++ Seq("-o", outputPath) ++ config.cmdLineParams
    runShellCommand(command, arguments).map(_ => outputPath)
  }

  override def isAvailable: Boolean = rootPath.resolve("php2cpg").toFile.exists()
}

package io.shiftleft.console.cpgcreation

import io.shiftleft.console.{FrontendConfig, InstallConfig}

import java.nio.file.{Path, Paths}

case class JsCpgGenerator(config: FrontendConfig, installConfig: InstallConfig) extends CpgGenerator {

  val binaryPath: Path = {
    val rootPath = installConfig.rootPath.path
    val relativeBinPath = Paths.get(installConfig.cpgGeneratorsDir, "js2cpg.sh")
    rootPath.resolve(relativeBinPath)
  }

  /**
    * Generate a CPG for the given input path.
    * Returns the output path, or None, if no
    * CPG was generated.
    **/
  override def generate(inputPath: String,
                        outputPath: String = "cpg.bin.zip",
                        namespaces: List[String] = List()): Option[String] = {
    val js2cpgsh = binaryPath.toString
    val arguments = Seq(inputPath, "--output", outputPath) ++ config.cmdLineParams
    runShellCommand(js2cpgsh, arguments).map(_ => outputPath)
  }

  override def isAvailable: Boolean = binaryPath.toFile.exists()
}

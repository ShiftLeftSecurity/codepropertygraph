package io.shiftleft.console.cpgcreation

import io.shiftleft.console.{FrontendConfig, InstallConfig}

import java.nio.file.{Path, Paths}

/**
  * Language frontend for Ghidra - translates compiled binaries into Code Property Graphs.
  */
case class GhidraCpgGenerator(config: FrontendConfig, installConfig: InstallConfig) extends CpgGenerator {

  val binaryPath: Path = {
    val rootPath = installConfig.rootPath.path
    val relativeBinPath = Paths.get(installConfig.cpgGeneratorsDir, "ghidra2cpg")
    rootPath.resolve(relativeBinPath)
  }

  /**
    * Generate a CPG for the given input path.
    * Returns the output path, or None, if no
    * CPG was generated.
    **/
  override def generate(inputPath: String,
                        outputPath: String = "cpg.bin",
                        namespaces: List[String] = List()): Option[String] = {
    val arguments = config.cmdLineParams.toSeq ++ Seq(inputPath, "--output", outputPath)
    runShellCommand(binaryPath.toString, arguments).map(_ => outputPath)
  }

  override def isAvailable: Boolean = binaryPath.toFile.exists()
}

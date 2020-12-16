package io.shiftleft.console.cpgcreation

import java.nio.file.Path

import io.shiftleft.console.FuzzyCFrontendConfig

/**
  * Fuzzy C/C++ language frontend. Translates C/C++ source files
  * into code property graphs via fuzzy parsing.
  * */
case class FuzzyCCpgGenerator(config: FuzzyCFrontendConfig, rootPath: Path) extends CpgGenerator {

  /**
    * Generate a CPG for the given input path.
    * Returns the output path, or None, if no
    * CPG was generated.
    **/
  override def generate(inputPath: String,
                        outputPath: String = "cpg.bin.zip",
                        namespaces: List[String] = List()): Option[String] = {
    val fuzzyc2cpgsh = rootPath.resolve("fuzzyc2cpg.sh").toString
    val arguments = Seq(inputPath, "--output", outputPath) ++ config.cmdLineParams
    runShellCommand(fuzzyc2cpgsh, arguments).map(_ => outputPath)
  }

  override def isAvailable: Boolean = rootPath.resolve("fuzzyc2cpg.sh").toFile.exists
}

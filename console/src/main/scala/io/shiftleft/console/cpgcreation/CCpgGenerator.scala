package io.shiftleft.console.cpgcreation

import io.shiftleft.c2cpg.C2Cpg
import io.shiftleft.c2cpg.C2Cpg.Config
import io.shiftleft.console.CFrontendConfig

import java.nio.file.Path

/**
  * Fuzzy C/C++ language frontend. Translates C/C++ source files
  * into code property graphs via fuzzy parsing.
  * */
case class CCpgGenerator(config: CFrontendConfig, rootPath: Path) extends CpgGenerator {

  /**
    * Generate a CPG for the given input path.
    * Returns the output path, or None, if no
    * CPG was generated.
    **/
  override def generate(inputPath: String,
                        outputPath: String = "cpg.bin.zip",
                        namespaces: List[String] = List()): Option[String] = {
    val c = new C2Cpg()
    val config = Config(
      inputPaths = Set(inputPath),
      outputPath = outputPath,
      sourceFileExtensions = Set(".c", ".cc", ".cpp", ".h", ".hpp")
    )
    val cpg = c.runAndOutput(config)
    cpg.close()
    Some(outputPath)
  }

  override def isAvailable: Boolean = true
}

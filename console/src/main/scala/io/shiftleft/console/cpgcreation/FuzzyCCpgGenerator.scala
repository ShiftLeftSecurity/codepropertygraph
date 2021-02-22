package io.shiftleft.console.cpgcreation

import java.nio.file.Path

import io.shiftleft.console.FuzzyCFrontendConfig
import io.shiftleft.fuzzyc2cpg.FuzzyC2Cpg

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
    val fuzzyc = new FuzzyC2Cpg()
    val cpg = fuzzyc.runAndOutput(Set(inputPath), Set(".c"), Some(outputPath))
    cpg.close()
    Some(outputPath)
  }

  override def isAvailable: Boolean = true
}

package io.shiftleft.console.cpgcreation

import java.nio.file.Path

import io.shiftleft.console.LlvmFrontendConfig

/**
  * Language frontend for LLVM.  Translates LLVM bitcode into Code Property Graphs.
  */
case class LlvmLanguageFrontend(config: LlvmFrontendConfig, rootPath: Path) extends LanguageFrontend {

  /**
    * Generate a CPG for the given input path.
    * Returns the output path, or None, if no
    * CPG was generated.
    **/
  override def generate(inputPath: String,
                        outputPath: String = "cpg.bin.zip",
                        namespaces: List[String] = List()): Option[String] = {
    val command = rootPath.resolve("llvm2cpg.sh").toString
    val arguments = Seq("--output", outputPath) ++ config.cmdLineParams ++ List(inputPath)
    runShellCommand(command, arguments).map(_ => outputPath)
  }

  override def isAvailable: Boolean = rootPath.resolve("llvm2cpg.sh").toFile.exists()
}

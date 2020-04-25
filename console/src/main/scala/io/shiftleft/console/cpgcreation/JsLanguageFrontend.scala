package io.shiftleft.console.cpgcreation

import java.nio.file.Path

import io.shiftleft.console.JsFrontendConfig

case class JsLanguageFrontend(config: JsFrontendConfig, rootPath: Path) extends LanguageFrontend {

  /**
    * Generate a CPG for the given input path.
    * Returns the output path, or None, if no
    * CPG was generated.
    **/
  override def generate(inputPath: String,
                        outputPath: String = "cpg.bin.zip",
                        namespaces: List[String] = List()): Option[String] = {
    val js2cpgsh = rootPath.resolve("js2cpg.sh").toString
    val arguments = Seq(inputPath, "--output", outputPath) ++ config.cmdLineParams
    runShellCommand(js2cpgsh, arguments).map(_ => outputPath)
  }

  override def isAvailable: Boolean = rootPath.resolve("js2cpg.sh").toFile.exists()
}

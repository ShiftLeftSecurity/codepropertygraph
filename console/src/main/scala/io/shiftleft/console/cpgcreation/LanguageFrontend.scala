package io.shiftleft.console.cpgcreation

import better.files.File

import scala.sys.process._

/**
  * A language frontend translates code into code property graphs. Each
  * supported language implements a LanguageFrontend, e.g., [[JavaLanguageFrontend]]
  * implements Java Archive to CPG conversion, while [[CSharpLanguageFrontend]]
  * translates C# projects into code property graphs.
  * */
abstract class LanguageFrontend() {

  def isAvailable: Boolean

  /**
    * Generate a CPG for the given input path.
    * Returns the output path, or None, if no
    * CPG was generated.
    *
    * This method appends command line options
    * in config.frontend.cmdLineParams to the
    * shell command.
    * */
  def generate(inputPath: String, outputPath: String = "cpg.bin.zip", namespaces: List[String] = List()): Option[String]

  protected def runShellCommand(program: String, arguments: Seq[String]): Option[String] = {
    if (!File(program).exists) {
      System.err.println("Support for this language is only available in ShiftLeft Ocular with an appropriate license")
      return None
    }
    val cmd = Seq[String](program) ++ arguments
    val exitValue = cmd.run.exitValue()
    if (exitValue == 0) {
      Some(cmd.toString)
    } else {
      System.err.println(s"Error running shell command: $cmd")
      None
    }
  }

}

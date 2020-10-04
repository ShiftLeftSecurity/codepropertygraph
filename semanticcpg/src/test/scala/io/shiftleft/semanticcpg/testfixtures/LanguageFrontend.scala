package io.shiftleft.semanticcpg.testfixtures

import java.io.File

import io.shiftleft.codepropertygraph.Cpg

/**
  * LanguageFrontend encapsulates the logic that translates the source code directory
  * into CPGs
  */
abstract class LanguageFrontend {

  /**
    * A standard file extension for the source code files of the given language.
    * E.g. `.c` for C language
    */
  val fileSuffix: String

  /**
    * Generate CPG for the given source code directory
    * @param sourceCodeFile directory where source code is located
    * @return CPG representation stored in a file
    */
  def execute(sourceCodeFile: File): Cpg
}

object LanguageFrontend {

  class FuzzycFrontend extends LanguageFrontend {
    def execute(sourceCodePath: File): Cpg = {
      val cpgFile = File.createTempFile("fuzzyc", ".zip")
      cpgFile.deleteOnExit()
      // val fuzzyc2Cpg = new FuzzyC2Cpg()
      // fuzzyc2Cpg.runAndOutput(Set(sourceCodePath.getAbsolutePath), Set(fileSuffix), Some(cpgFile.getAbsolutePath))
      ???
    }
    override val fileSuffix: String = ".c"
  }

  def Fuzzyc: LanguageFrontend = new FuzzycFrontend
}

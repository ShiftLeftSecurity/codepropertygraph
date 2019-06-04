package io.shiftleft.cpgqueryingtests.codepropertygraph

import java.io.File

import io.shiftleft.fuzzyc2cpg.FuzzyC2Cpg
import io.shiftleft.fuzzyc2cpg.output.protobuf.OutputModuleFactory

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
  def execute(sourceCodeFile: File): File
}

object LanguageFrontend {

  class FuzzycFrontend extends LanguageFrontend {
    def execute(sourceCodePath: File): File = {
      val cpgFile = File.createTempFile("fuzzyc", ".zip")
      cpgFile.deleteOnExit()
      val fuzzyc2Cpg = new FuzzyC2Cpg(new OutputModuleFactory(cpgFile.getAbsolutePath, true, false))
      fuzzyc2Cpg.runAndOutput(Seq(sourceCodePath.getAbsolutePath).toArray)
      cpgFile
    }
    override val fileSuffix: String = ".c"
  }

  val Fuzzyc: LanguageFrontend = new FuzzycFrontend
}

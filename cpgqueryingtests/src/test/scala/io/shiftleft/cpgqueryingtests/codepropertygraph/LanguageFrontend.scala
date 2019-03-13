package io.shiftleft.cpgqueryingtests.codepropertygraph

import java.io.File

import io.shiftleft.fuzzyc2cpg.Fuzzyc2Cpg
import io.shiftleft.fuzzyc2cpg.output.protobuf.OutputModuleFactory

abstract class LanguageFrontend {
  val fileSuffix: String
  def execute(sourceCodeFile: File): File
}

object LanguageFrontend {

  class FuzzycFrontend extends LanguageFrontend {
    def execute(sourceCodePath: File): File = {
      val cpgFile = File.createTempFile("fuzzyc", ".zip")
      cpgFile.deleteOnExit()
      val fuzzyc2Cpg = new Fuzzyc2Cpg(new OutputModuleFactory(cpgFile.getAbsolutePath, true, false))
      fuzzyc2Cpg.runAndOutput(Seq(sourceCodePath.getAbsolutePath).toArray)
      cpgFile
    }
    override val fileSuffix: String = ".c"
  }

  val Fuzzyc   = new FuzzycFrontend
}

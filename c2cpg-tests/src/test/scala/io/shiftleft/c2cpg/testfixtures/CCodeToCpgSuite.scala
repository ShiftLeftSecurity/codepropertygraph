package io.shiftleft.c2cpg.testfixtures

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.c2cpg.C2Cpg
import io.shiftleft.c2cpg.C2Cpg.Config
import io.shiftleft.semanticcpg.testfixtures.{CodeToCpgFixture, LanguageFrontend}

import java.io.File

class C2CpgFrontend extends LanguageFrontend {
  def execute(sourceCodePath: File): Cpg = {
    val cpgFile = File.createTempFile("c2cpg", ".zip")
    cpgFile.deleteOnExit()
    val c2cpg = new C2Cpg()
    val config = Config(inputPaths = Set(sourceCodePath.getAbsolutePath),
                        outputPath = cpgFile.getAbsolutePath,
                        sourceFileExtensions = Set(fileSuffix))
    c2cpg.runAndOutput(config)
  }
  override val fileSuffix: String = ".c"
}

class CCodeToCpgSuite extends CodeToCpgFixture(new C2CpgFrontend) {}

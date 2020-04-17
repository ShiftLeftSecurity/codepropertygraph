package io.shiftleft.semanticcpg.testfixtures

import java.io.{File, PrintWriter}
import java.nio.file.{Files, Path}

import io.shiftleft.SerializedCpg
import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.cpgloading.{CpgLoader, CpgLoaderConfig}
import io.shiftleft.semanticcpg.layers.EnhancementRunner

object CodeToCpgFixture {

  def apply[T](): CodeToCpgFixture =
    new CodeToCpgFixture(LanguageFrontend.Fuzzyc)

  def apply[T](sourceCode: String,
               passes: (Cpg => Unit) = createEnhancements,
               frontend: LanguageFrontend = LanguageFrontend.Fuzzyc)(fun: Cpg => T): T =
    new CodeToCpgFixture(frontend).buildCpg(sourceCode, passes)(fun)

  private def createEnhancements(cpg: Cpg): Unit = {
    new EnhancementRunner().run(cpg, new SerializedCpg())
  }

}

class CodeToCpgFixture(frontend: LanguageFrontend) {

  /**
    * Build a CPG for the provided code snippet.
    *
    * @param sourceCode code for which one wants to generate cpg
    */
  def buildCpg[T](sourceCode: String, passes: (Cpg => Unit) = CodeToCpgFixture.createEnhancements)(fun: Cpg => T): T = {
    val tmpDir = better.files.File.newTemporaryDirectory("test")
    (tmpDir / "test.c").write(sourceCode)
    try {
      buildCpgForFile[T](tmpDir.toJava, passes)(fun)
    } finally {
      tmpDir.delete()
    }
  }

  def buildCpgForFile[T](file: File, passes: (Cpg => Unit) = CodeToCpgFixture.createEnhancements)(fun: Cpg => T): T = {
    val cpgFile = frontend.execute(file)
    val config = CpgLoaderConfig.withoutOverflow
    val cpg = CpgLoader.load(cpgFile.getAbsolutePath, config)

    passes(cpg)

    try fun(cpg)
    finally { cpg.close() }
  }

}

package io.shiftleft.semanticcpg.testfixtures

import java.io.{File, PrintWriter}
import java.nio.file.Files

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.cpgloading.{CpgLoader, CpgLoaderConfig}
import io.shiftleft.semanticcpg.layers.{LayerCreatorContext, Scpg}
import io.shiftleft.semanticcpg.testfixtures.CodeToCpgFixture.createEnhancements

object CodeToCpgFixture {

  def apply[T](): CodeToCpgFixture =
    new CodeToCpgFixture(LanguageFrontend.Fuzzyc)

  def apply[T](sourceCode: String,
               passes: (Cpg => Unit) = createEnhancements,
               frontend: LanguageFrontend = LanguageFrontend.Fuzzyc)(fun: Cpg => T): T =
    new CodeToCpgFixture(frontend).buildCpg(sourceCode, passes)(fun)

  def createEnhancements(cpg: Cpg): Unit = {
    val context = new LayerCreatorContext(cpg)
    new Scpg().run(context)
  }

}

object CodeDirToCpgFixture {

  def apply[T](dir: File,
               passes: (Cpg => Unit) = createEnhancements,
               frontend: LanguageFrontend = LanguageFrontend.Fuzzyc)(fun: Cpg => T): T =
    new CodeToCpgFixture(frontend).buildCpgForDir(dir, passes)(fun)

}

class CodeToCpgFixture(frontend: LanguageFrontend) {

  /**
    * Build a CPG for the provided code snippet.
    *
    * @param sourceCode code for which one wants to generate cpg
    */
  def buildCpg[T](sourceCode: String, passes: (Cpg => Unit) = CodeToCpgFixture.createEnhancements)(fun: Cpg => T): T = {
    val tmpDir = writeCodeToFile(sourceCode)
    buildCpgForDir(tmpDir, passes)(fun)
  }

  def buildCpgForDir[T](dir: File, passes: (Cpg => Unit) = CodeToCpgFixture.createEnhancements)(fun: Cpg => T): T = {
    val cpgFile = frontend.execute(dir)
    val config = CpgLoaderConfig.withoutOverflow
    val cpg = CpgLoader.load(cpgFile.getAbsolutePath, config)

    passes(cpg)

    try fun(cpg)
    finally { cpg.close() }
  }

  private def writeCodeToFile(sourceCode: String): File = {
    val tmpDir = Files.createTempDirectory("semanticcpgtest").toFile
    tmpDir.deleteOnExit()
    val codeFile = File.createTempFile("Test", frontend.fileSuffix, tmpDir)
    codeFile.deleteOnExit()
    new PrintWriter(codeFile) { write(sourceCode); close() }
    tmpDir
  }

}

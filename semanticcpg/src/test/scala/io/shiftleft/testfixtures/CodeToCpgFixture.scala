package io.shiftleft.testfixtures

import java.io.{File, PrintWriter}
import java.nio.file.Files

import io.shiftleft.SerializedCpg
import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.cpgloading.{CpgLoader, CpgLoaderConfig}
import io.shiftleft.semanticcpg.layers.EnhancementRunner

object CodeToCpgFixture {
  def apply(frontend: LanguageFrontend = LanguageFrontend.Fuzzyc): CodeToCpgFixture = new CodeToCpgFixture(frontend)
}

class CodeToCpgFixture(frontend: LanguageFrontend) {

  /**
    * Build a CPG for the provided code snippet.
    *
    * @param sourceCode code for which one wants to generate cpg
    */
  def buildCpg[T](sourceCode: String, passes: (Cpg => Unit) = createEnhancements)(fun: Cpg => T): T = {
    val tmpDir = writeCodeToFile(sourceCode)
    val cpgFile = frontend.execute(tmpDir)
    val config = CpgLoaderConfig.withoutOverflow
    val cpg = CpgLoader.load(cpgFile.getAbsolutePath, config)

    passes(cpg)

    try fun(cpg)
    finally { cpg.close() }
  }

  private def createEnhancements(cpg: Cpg): Unit = {
    new EnhancementRunner().run(cpg, new SerializedCpg())
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

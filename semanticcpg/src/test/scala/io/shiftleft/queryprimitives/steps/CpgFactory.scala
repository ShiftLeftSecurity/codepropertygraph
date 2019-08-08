package io.shiftleft.queryprimitives.steps

import java.io.{File, PrintWriter}
import java.nio.file.Files

import io.shiftleft.SerializedCpg
import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.cpgloading.{CpgLoader, CpgLoaderConfig, OverflowDbConfig}
import io.shiftleft.layers.EnhancementRunner

object CpgFactory {
  def apply(frontend: LanguageFrontend = LanguageFrontend.Fuzzyc): CpgFactory = new CpgFactory(frontend)
}

class CpgFactory(frontend: LanguageFrontend) {

  /**
    * Build a CPG for the provided code snippet.
    *
    * @param sourceCode code for which one wants to generate cpg
    */
  def buildCpg[T](sourceCode: String)(fun: Cpg => T): T = {
    val tmpDir = Files.createTempDirectory("dflowtest").toFile
    tmpDir.deleteOnExit()

    val codeFile = File.createTempFile("Test", frontend.fileSuffix, tmpDir)
    codeFile.deleteOnExit()

    new PrintWriter(codeFile) { write(sourceCode); close() }

    val cpgFile = frontend.execute(tmpDir)

    val config = CpgLoaderConfig.withoutOverflow
    val cpg = CpgLoader.load(cpgFile.getAbsolutePath, config)

    new EnhancementRunner().run(cpg, new SerializedCpg())

    try fun(cpg)
    finally { cpg.close() }
  }

}

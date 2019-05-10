package io.shiftleft.cpgqueryingtests.codepropertygraph

import java.io.{File, PrintWriter}
import java.nio.file.Files

import io.shiftleft.SerializedCpg
import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.cpgloading.{CpgLoader, CpgLoaderConfig}
import io.shiftleft.layers.EnhancementRunner
import io.shiftleft.semanticsloader.SemanticsLoader

class CpgFactory(frontend: LanguageFrontend, semanticsFilename: String) {

  /**
    * Build a CPG for the provided C/C++ code snippet.
    * */
  def buildCpg(sourceCode: String): Cpg = {
    val tmpDir = Files.createTempDirectory("dflowtest").toFile
    tmpDir.deleteOnExit()

    val codeFile = File.createTempFile("Test", frontend.fileSuffix, tmpDir)
    codeFile.deleteOnExit()

    new PrintWriter(codeFile) { write(sourceCode); close() }

    val cpgFile = frontend.execute(tmpDir)

    val config = CpgLoaderConfig.default
    val cpg = CpgLoader.load(cpgFile.getAbsolutePath, config)

    val semantics = new SemanticsLoader(semanticsFilename).load
    new EnhancementRunner(semantics).run(cpg, new SerializedCpg())

    cpg
  }

}

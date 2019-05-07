package io.shiftleft.cpgqueryingtests.codepropertygraph

import java.io.{File, PrintWriter}
import java.nio.file.Files

import io.shiftleft.cpgloading.{CpgLoader, CpgLoaderConfig}
import io.shiftleft.queryprimitives.steps.starters.Cpg

class CpgFactory(frontend: LanguageFrontend,
                 semanticsFilename : String) {

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
    config.semanticsFilename = Some(semanticsFilename)
    val graph = CpgLoader.load(cpgFile.getAbsolutePath, config)

    graph
  }

}

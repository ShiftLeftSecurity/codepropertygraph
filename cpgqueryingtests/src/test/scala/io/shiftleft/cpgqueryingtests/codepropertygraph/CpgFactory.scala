package io.shiftleft.cpgqueryingtests.codepropertygraph

import java.io.{File, PrintWriter}
import java.nio.file.Files

import io.shiftleft.cpgloading.tinkergraph.CpgLoader
import io.shiftleft.queryprimitives.steps.starters.Cpg

class CpgFactory(frontend: LanguageFrontend) {

  def buildCpg(sourceCode: String): Cpg = {

    val tmpDir = Files.createTempDirectory("dflowtest").toFile
    tmpDir.deleteOnExit()

    val codeFile = File.createTempFile("Test", frontend.fileSuffix, tmpDir)
    codeFile.deleteOnExit()

    new PrintWriter(codeFile) { write(sourceCode); close() }

    val cpgFile = frontend.execute(tmpDir)

    val graph = CpgLoader.loadCodePropertyGraph(cpgFile.getAbsolutePath, runEnhancements = true)

    graph
  }

}

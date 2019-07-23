package io.shiftleft.cpgqueryingtests.codepropertygraph

import java.io.{File, PrintWriter}
import java.nio.file.Files

import io.shiftleft.SerializedCpg
import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.cpgloading.{CpgLoader, CpgLoaderConfig}
import io.shiftleft.layers.{DataFlowRunner, EnhancementRunner}
import io.shiftleft.semanticsloader.SemanticsLoader

class CpgFactory(frontend: LanguageFrontend, semanticsFilename: String) {

  /**
    * Build a CPG for the provided C/C++ code snippet.
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

    val config = CpgLoaderConfig.default.withoutOverflow
    val cpg = CpgLoader.load(cpgFile.getAbsolutePath, config)

    val semantics = new SemanticsLoader(semanticsFilename).load()
    new EnhancementRunner().run(cpg, new SerializedCpg())
    new DataFlowRunner(semantics).run(cpg, new SerializedCpg())

    try fun(cpg)
    finally { cpg.close() }
  }

}

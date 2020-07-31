package io.shiftleft.semanticcpg.testfixtures

import java.io.{File, PrintWriter}
import java.nio.file.Files

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.cpgloading.{CpgLoader, CpgLoaderConfig}
import io.shiftleft.semanticcpg.layers.{LayerCreatorContext, Scpg}
import io.shiftleft.semanticcpg.testfixtures.LanguageFrontend.FuzzycFrontend
import org.scalatest.{BeforeAndAfterAll, Matchers, WordSpec}

class CodeToCpgSuite extends WordSpec with Matchers with BeforeAndAfterAll {

  val code = ""
  var cpg: Cpg = _
  val frontend: LanguageFrontend = new FuzzycFrontend
  def passes(cpg: Cpg): Unit = createEnhancements(cpg)

  override def beforeAll(): Unit = {
    val tmpDir = writeCodeToFile(code)
    buildCpgForDir(tmpDir)
  }

  def createEnhancements(cpg: Cpg): Unit = {
    val context = new LayerCreatorContext(cpg)
    new Scpg().run(context)
  }

  private def buildCpgForDir[T](dir: File): Unit = {
    val cpgFile = frontend.execute(dir)
    val config = CpgLoaderConfig.withoutOverflow
    cpg = CpgLoader.load(cpgFile.getAbsolutePath, config)
    passes(cpg)
  }

  private def writeCodeToFile(sourceCode: String): File = {
    val tmpDir = Files.createTempDirectory("semanticcpgtest").toFile
    tmpDir.deleteOnExit()
    val codeFile = File.createTempFile("Test", frontend.fileSuffix, tmpDir)
    codeFile.deleteOnExit()
    new PrintWriter(codeFile) { write(sourceCode); close() }
    tmpDir
  }

  override def afterAll(): Unit = {
    cpg.close()
  }

}

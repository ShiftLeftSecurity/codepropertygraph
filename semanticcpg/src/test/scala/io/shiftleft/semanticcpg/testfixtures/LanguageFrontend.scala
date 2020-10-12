package io.shiftleft.semanticcpg.testfixtures

import io.shiftleft.codepropertygraph.Cpg
import java.io.File

import io.shiftleft.codepropertygraph.cpgloading.{CpgLoader, CpgLoaderConfig}
import overflowdb.Config

import scala.sys.process.Process

/**
  * LanguageFrontend encapsulates the logic that translates the source code directory
  * into CPGs
  */
abstract class LanguageFrontend {

  /**
    * A standard file extension for the source code files of the given language.
    * E.g. `.c` for C language
    */
  val fileSuffix: String

  /**
    * Generate CPG for the given source code directory
    * @param sourceCodeFile directory where source code is located
    * @return CPG representation stored in a file
    */
  def execute(sourceCodeFile: File): Cpg
}

object LanguageFrontend {

  class FuzzycFrontend extends LanguageFrontend {
    def execute(sourceCodePath: File): Cpg = {
      val cpgFile = File.createTempFile("fuzzyc", ".zip")
      cpgFile.deleteOnExit()

      val p = Process(
        List(
          "./fuzzyc2cpg.sh",
          sourceCodePath.toString,
          "--output",
          cpgFile.getAbsolutePath,
        )
      ).run()
      assert(p.exitValue() == 0, s"fuzzyc exited with code ${p.exitValue}")

      val overflowConfig = new Config().withStorageLocation(cpgFile.getAbsolutePath)
      val loaderConfig = CpgLoaderConfig().withOverflowConfig(overflowConfig)
      CpgLoader.loadFromOverflowDb(loaderConfig)
    }
    override val fileSuffix: String = ".c"
  }

  def Fuzzyc: LanguageFrontend = new FuzzycFrontend
}

package io.shiftleft.console.fixtures

import java.util.concurrent.LinkedBlockingQueue

import better.files.File
import io.shiftleft.console.ConsoleConfig
import io.shiftleft.console.cpgcreation.{CpgGenerator, LanguageFrontend}
import io.shiftleft.fuzzyc2cpg.FuzzyC2Cpg
import io.shiftleft.proto.cpg.Cpg.CpgStruct

class TestCpgGenerator(config: ConsoleConfig) extends CpgGenerator(config) {
  override def createFrontendByPath(
      inputPath: String,
  ): Option[LanguageFrontend] = {
    Some(new FuzzyCTestingFrontend)
  }

  override def createFrontendByLanguage(language: String): Option[LanguageFrontend] = {
    Some(new FuzzyCTestingFrontend)
  }

  private class FuzzyCTestingFrontend extends LanguageFrontend {

    override def generate(inputPath: String, outputPath: String, namespaces: List[String]): Option[String] = {
      val queue = new LinkedBlockingQueue[CpgStruct.Builder]()
      val factory =
        new io.shiftleft.fuzzyc2cpg.output.overflowdb.OutputModuleFactory(outputPath, queue)
      val fuzzyc = new FuzzyC2Cpg(factory)
      File(inputPath).list.foreach(println(_))
      fuzzyc.runAndOutput(Set(inputPath), Set(".c"))
      Some(outputPath)
    }

    def isAvailable: Boolean = true

  }

}

package io.shiftleft.console

import better.files.{File, FileExtensions}
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class ConsoleConfigTest extends AnyWordSpec with Matchers {
  "An InstallConfig" should {
    "set the rootPath to parent of directory containing jar by default" in {
      val config = new InstallConfig(environment = Map.empty)
      val uriToLibDir = classOf[io.shiftleft.console.InstallConfig].getProtectionDomain.getCodeSource.getLocation.toURI
      val pathToLibDir = new java.io.File(uriToLibDir).toPath
      config.rootPath shouldBe pathToLibDir.getParent.getParent.toFile.toScala
    }

    "set the rootPath to SHIFTLEFT_OCULAR_INSTALL_DIR if it is defined" in {
      val config = new InstallConfig(environment = Map("SHIFTLEFT_OCULAR_INSTALL_DIR" -> "/tmp"))
      config.rootPath shouldBe File("/tmp")
    }

    "copy config with params correctly" in {
      val initialParamList = List("param1", "param2")
      val additionalParamList = List("param3", "param4", "param5")

      val config = new FrontendConfig(initialParamList)
      val copyConfig = config.withArgs(additionalParamList)

      withClue("should be able to copy config without mutating original") {
        copyConfig.cmdLineParams shouldBe (initialParamList ++ additionalParamList)
      }

      withClue("should be able to mutate config") {
        val moreAdditionalParams = List("param5", "param6")
        config.cmdLineParams ++= moreAdditionalParams
        config.cmdLineParams shouldBe (initialParamList ++ moreAdditionalParams)
      }
    }
  }
}

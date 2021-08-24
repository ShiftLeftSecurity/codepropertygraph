package io.shiftleft.console

import better.files.File
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class ConsoleConfigTest extends AnyWordSpec with Matchers {
  "An InstallConfig" should {
    "set the rootPath to the current working directory by default" in {
      val config = new InstallConfig(environment = Map.empty)
      config.rootPath shouldBe File(".")
    }

    "set the rootPath to SHIFTLEFT_OCULAR_INSTALL_DIR if it is defined" in {
      val config = new InstallConfig(environment = Map("SHIFTLEFT_OCULAR_INSTALL_DIR" -> "/tmp"))
      config.rootPath shouldBe File("/tmp")
    }

    "copy config with params correctly" in {
      val initialParamList = List("param1", "param2")
      val additionalParamList = List("param3", "param4", "param5")

      val config = FrontendConfig(initialParamList)
      val newConfig = config.withAdditionalArgs(additionalParamList)

      withClue("New config should have the full param list") {
        newConfig.cmdLineParams shouldBe (initialParamList ++ additionalParamList)
      }

      withClue("Old config should not be mutated") {
        config.cmdLineParams shouldBe initialParamList
      }
    }
  }
}

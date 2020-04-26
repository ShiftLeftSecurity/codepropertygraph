package io.shiftleft.console

import better.files.File
import org.scalatest.{Matchers, WordSpec}

class ConsoleConfigTest extends WordSpec with Matchers {
  "An InstallConfig" should {
    "set the rootPath to the current working directory by default" in {
      val config = new InstallConfig(environment = Map.empty)
      config.rootPath shouldBe File(".")
    }

    "set the rootPath to SHIFTLEFT_CONSOLE_INSTALL_DIR if it is defined" in {
      val config = new InstallConfig(environment = Map("SHIFTLEFT_CONSOLE_INSTALL_DIR" -> "/tmp"))
      config.rootPath shouldBe File("/tmp")
    }
  }
}

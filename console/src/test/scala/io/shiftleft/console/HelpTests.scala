package io.shiftleft.console

import io.shiftleft.console.workspacehandling.Project
import org.scalatest.{Matchers, WordSpec}

class HelpTests extends WordSpec with Matchers {

  "Help" should {
    "provide overview of commands as table" in {
      Help.overview[io.shiftleft.console.Console[Project]].contains("| CPG") shouldBe true
    }
  }

}

package io.shiftleft.console

import better.files.File
import io.shiftleft.console.ScriptManager.ScriptDescription
import org.scalatest.{Inside, Matchers, WordSpec}

class ScriptManagerTest extends WordSpec with Matchers with Inside {

  private class TestScriptExecutor extends ScriptExecutor {
    override def run(script: String): String = script
  }

  private class TestScriptManager extends ScriptManager(new TestScriptExecutor) {

    override val DEFAULT_SCRIPTS_FOLDER: File = File("resources") / "testcode" / "scripts"

  }

  "listing scripts" should {
    "be correct" in {
      val sut = new TestScriptManager
      val scripts = sut.scripts()
      scripts shouldBe List(ScriptDescription("list-funcs", "Lists all functions."))
    }
  }

  "running scripts" should {
    "be correct" in {
      val expected = """loadCpg("cpg.bin.zip")
                        |cpg.method.name.l""".stripMargin
      val sut = new TestScriptManager
      inside(sut.scripts()) {
        case ScriptDescription("list-funcs", _) :: _ =>
          val actualT: String = sut.runScriptT("list-funcs")
          actualT shouldBe expected
          val actual = sut.runScript("list-funcs")
          actual shouldBe expected
      }

    }
  }

}

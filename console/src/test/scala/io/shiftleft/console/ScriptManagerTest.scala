package io.shiftleft.console

import org.scalatest.{Matchers, WordSpec}

class ScriptManagerTest extends WordSpec with Matchers {

  private class TestScriptExecutor extends ScriptExecutor {
    override def run(script: String): AnyRef = script
  }

  private class TestScriptManager extends ScriptManager(new TestScriptExecutor) {

    override val DEFAULT_SCRIPTS_FOLDER: String = "resources/testcode/scripts/"

  }

  "listing scripts" should {
    "be correct" in {
      val sut = new TestScriptManager
      val scripts = sut.scripts()
      scripts shouldBe List(sut.ScriptDescription("list-funcs", "Lists all functions."))
    }
  }

  "running scripts" should {
    "be correct" in {
      val expected = """loadCpg("cpg.bin.zip")
                        |cpg.method.name.l""".stripMargin

      val sut = new TestScriptManager
      sut.scripts() match {
        case Nil => fail("There should be a script named 'list-funcs'!")
        case ::(sut.ScriptDescription("list-funcs", _), _) =>
          sut.runScript("list-funcs") shouldBe expected
      }

    }
  }

}

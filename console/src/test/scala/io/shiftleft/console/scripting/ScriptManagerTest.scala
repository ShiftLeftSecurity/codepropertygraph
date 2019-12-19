package io.shiftleft.console.scripting

import better.files.File
import cats.effect.IO
import org.scalatest.{Inside, Matchers, WordSpec}

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.console.scripting.ScriptManager.{ScriptCollections, ScriptDescription, ScriptDescriptions}

import java.nio.file.{FileSystemNotFoundException, NoSuchFileException, Path}

import scala.io.Source
import scala.util.Try

class ScriptManagerTest extends WordSpec with Matchers with Inside {

  private object TestScriptExecutor extends AmmoniteExecutor {
    override protected def predef: String = ""
    override def runScript(scriptPath: Path, parameters: Map[String, String], cpg: Cpg): IO[Any] = IO.fromTry(
      Try {
        val source = Source.fromFile(scriptPath.toFile)
        val result = source.getLines.mkString(System.lineSeparator())
        source.close()
        result
      }
    )
  }

  private object TestScriptManager extends ScriptManager(TestScriptExecutor)

  protected val DEFAULT_CPG_NAME: String =
    (File("resources") / "testcode" / "cpgs" / "method" / "cpg.bin.zip").pathAsString

  def withScriptManager(f: ScriptManager => Unit): Unit = {
    f(TestScriptManager)
  }

  "listing scripts" should {
    "be correct" in withScriptManager { scriptManager =>
      val scripts = scriptManager.scripts()
      val expected = List(
        ScriptCollections("general",
                          ScriptDescriptions(
                            "A collection of general purpose scripts.",
                            List(ScriptDescription("list-funcs", "Lists all functions."))
                          )),
        ScriptCollections("java",
                          ScriptDescriptions(
                            "A collection of java-specific scripts.",
                            List(ScriptDescription("list-sl-ns", "Lists all shiftleft namespaces."))
                          )),
        ScriptCollections("general/general_plus",
                          ScriptDescriptions(
                            "Even more general purpose scripts.",
                            List.empty
                          ))
      )

      scripts should contain theSameElementsAs expected
    }
  }

  "running scripts" should {
    "be correct when explicitly specifying a CPG" in withScriptManager { scriptManager =>
      val expected =
        """|@main def main() = {
           |  cpg.method.name.l
           |}""".stripMargin

      scriptManager.runScript("general/list-funcs", Map.empty, Cpg.emptyCpg) shouldBe expected
    }

    "be correct when specifying a CPG filename" in withScriptManager { scriptManager =>
      val expected =
        """|@main def main() = {
           |  cpg.method.name.l
           |}""".stripMargin

      scriptManager.runScript("general/list-funcs", Map.empty, DEFAULT_CPG_NAME) shouldBe expected
    }

    "throw an exception if the specified CPG can not be found" in withScriptManager { scriptManager =>
      intercept[FileSystemNotFoundException] {
        scriptManager.runScript("general/list-funcs", Map.empty, "cake.bin.zip")
      }
    }

    "throw an exception if the specified script can not be found" in withScriptManager { scriptManager =>
      intercept[NoSuchFileException] {
        scriptManager.runScript("list-funcs", Map.empty, Cpg.emptyCpg)
      }
    }
  }

}

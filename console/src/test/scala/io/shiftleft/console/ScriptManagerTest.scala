package io.shiftleft.console

import java.util.UUID
import better.files.File
import cats.data.OptionT
import cats.effect.IO

import io.shiftleft.codepropertygraph.Cpg
import org.scalatest.{Inside, Matchers, WordSpec}

import io.shiftleft.console.ScriptManager.{ScriptCollections, ScriptDescription, ScriptDescriptions}
import io.shiftleft.console.query.{CpgOperationResult, CpgOperationSuccess, CpgQueryExecutor}

import java.nio.file.NoSuchFileException

class ScriptManagerTest extends WordSpec with Matchers with Inside {

  private object TestScriptExecutor extends CpgQueryExecutor[AnyRef] {
    override def executeQuery(cpg: Cpg, query: String): IO[UUID] = ???

    override def executeQuerySync(cpg: Cpg, query: String): IO[CpgOperationResult[AnyRef]] =
      // Faking the actual execution by just returning the query itself
      IO(CpgOperationSuccess(query))

    override def retrieveQueryResult(queryId: UUID): OptionT[IO, CpgOperationResult[AnyRef]] = ???
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
      val expected = "cpg.method.name.l"
      scriptManager.runScript("general/list-funcs", Cpg.emptyCpg) shouldBe expected
    }

    "be correctly when specifying a CPG filename" in withScriptManager { scriptManager =>
      val expected = "cpg.method.name.l"
      scriptManager.runScript("general/list-funcs", DEFAULT_CPG_NAME) shouldBe expected
    }

    "throw an exception if the specified script can not be found" in withScriptManager { scriptManager =>
      intercept[NoSuchFileException] {
        scriptManager.runScript("list-funcs", Cpg.emptyCpg)
      }
    }
  }

}

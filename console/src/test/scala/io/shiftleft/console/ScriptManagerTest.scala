package io.shiftleft.console

import java.util.UUID

import better.files.File
import cats.data.OptionT
import cats.effect.IO
import io.shiftleft.codepropertygraph.Cpg
import org.scalatest.{Inside, Matchers, WordSpec}
import io.shiftleft.console.ScriptManager.ScriptDescription
import io.shiftleft.console.query.{CpgOperationResult, CpgOperationSuccess, CpgQueryExecutor}

class ScriptManagerTest extends WordSpec with Matchers with Inside {

  private object TestScriptExecutor extends CpgQueryExecutor[AnyRef] {
    override def executeQuery(cpg: Cpg, query: String): IO[UUID] = ???

    override def executeQuerySync(cpg: Cpg, query: String): IO[CpgOperationResult[AnyRef]] =
      // Faking the actual execution by just returning the query itself
      IO(CpgOperationSuccess(query))

    override def retrieveQueryResult(queryId: UUID): OptionT[IO, CpgOperationResult[AnyRef]] = ???
  }

  private object TestScriptManager extends ScriptManager(TestScriptExecutor) {
    override protected val DEFAULT_CPG_NAME: String =
      (File("resources") / "testcode" / "cpgs" / "method" / "cpg.bin.zip").pathAsString
  }

  def withScriptManager(f: ScriptManager => Unit): Unit = {
    f(TestScriptManager)
  }

  "listing scripts" should {
    "be correct" in withScriptManager { scriptManager =>
      val scripts = scriptManager.scripts()
      scripts shouldBe List(ScriptDescription("list-funcs", "Lists all functions."))
    }
  }

  "running scripts" should {
    "be correct" in withScriptManager { scriptManager =>
      val expected = "cpg.method.name.l"
      inside(scriptManager.scripts()) {
        case ScriptDescription("list-funcs", _) :: _ =>
          scriptManager.runScript("list-funcs", Cpg.emptyCpg) shouldBe expected
      }

    }
  }

}

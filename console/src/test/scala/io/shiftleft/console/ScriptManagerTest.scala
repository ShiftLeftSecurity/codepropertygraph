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

  private class TestScriptExecutor extends CpgQueryExecutor[AnyRef] {
    override def executeQuery(cpg: Cpg, query: String): IO[UUID] = ???

    override def executeQuerySync(cpg: Cpg, query: String): IO[CpgOperationResult[AnyRef]] =
      // Faking the actual execution by just returning the query itself
      IO(CpgOperationSuccess(query))

    override def retrieveQueryResult(queryId: UUID): OptionT[IO, CpgOperationResult[AnyRef]] = ???
  }

  private class TestScriptManager extends ScriptManager(new TestScriptExecutor) {

    override val DEFAULT_SCRIPTS_FOLDER: File = File("resources") / "testcode" / "scripts"
    override val DEFAULT_CPG_NAME: String =
      (File("resources") / "testcode" / "cpgs" / "method" / "cpg.bin.zip").pathAsString

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
      val expected = "cpg.method.name.l"
      val sut = new TestScriptManager
      inside(sut.scripts()) {
        case ScriptDescription("list-funcs", _) :: _ =>
          sut.runScript("list-funcs") shouldBe expected
      }

    }
  }

}

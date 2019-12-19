package io.shiftleft.cpgserver.query

import cats.effect.{ContextShift, IO}
import org.scalatest.{Matchers, WordSpec}

import io.shiftleft.codepropertygraph.Cpg

class ServerAmmoniteExecutorSpec extends WordSpec with Matchers {

  private implicit val cs: ContextShift[IO] = IO.contextShift(scala.concurrent.ExecutionContext.global)

  private class DummyServerAmmoniteExecutor extends ServerAmmoniteExecutor {
    override protected def predef: String = "import io.shiftleft.semanticcpg.language._"
  }

  private def withServerExecutor[T](f: ServerAmmoniteExecutor => T): T = {
    f(new DummyServerAmmoniteExecutor)
  }

  "A ServerAmmoniteExecutor" should {
    "run a query synchronously" in withServerExecutor { executor =>
      executor.executeQuerySync(Cpg.emptyCpg, "cpg.method.l").unsafeRunSync() should matchPattern {
        case CpgOperationSuccess("List()") =>
      }
    }
  }
}

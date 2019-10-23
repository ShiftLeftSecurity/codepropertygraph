package io.shiftleft.console.query

import java.io.Reader
import java.util.UUID

import scala.concurrent.ExecutionContext
import scala.concurrent.duration._
import scala.language.postfixOps
import cats.data.OptionT
import cats.effect.{ContextShift, IO}
import javax.script._
import org.scalatest.{Matchers, WordSpec}
import org.scalatest.concurrent.Eventually

import io.shiftleft.codepropertygraph.Cpg

class DefaultCpgQueryExecutorSpec extends WordSpec with Matchers with Eventually {

  private val queryResult = "a result"
  private val queryException = new RuntimeException("Oh noes!")

  private implicit val cs: ContextShift[IO] = IO.contextShift(ExecutionContext.global)

  private object UndefinedScriptEngineManager extends ScriptEngineManager {
    override def getEngineByName(shortName: String): ScriptEngine = null
  }

  private class DefinedScriptEnginerManager(queryError: Boolean) extends ScriptEngineManager {
    override def getEngineByName(shortName: String): ScriptEngine = new ScriptEngine {
      def eval(script: String): AnyRef =
        if (queryError) throw queryException
        else queryResult

      def eval(script: String, context: ScriptContext): AnyRef = ???
      def eval(reader: Reader, context: ScriptContext): AnyRef = ???
      def eval(reader: Reader): AnyRef = ???
      def eval(script: String, n: Bindings): AnyRef = ???
      def eval(reader: Reader, n: Bindings): AnyRef = ???
      def put(key: String, value: Any): Unit = ()
      def get(key: String): AnyRef = ???
      def getBindings(scope: Int): Bindings = ???
      def setBindings(bindings: Bindings, scope: Int): Unit = ???
      def createBindings(): Bindings = ???
      def getContext: ScriptContext = ???
      def setContext(context: ScriptContext): Unit = ???
      def getFactory: ScriptEngineFactory = ???
    }
  }

  private def withNewQueryExecutor[T](scripManagerIsDefined: Boolean = true, queryError: Boolean = false)(
      f: DefaultCpgQueryExecutor[String] => T): T = {
    val manager =
      if (scripManagerIsDefined) new DefinedScriptEnginerManager(queryError)
      else UndefinedScriptEngineManager

    f(new DefaultCpgQueryExecutor(manager))
  }

  "Executing a query" should {
    "return a UUID referencing the eventual result of the query" in withNewQueryExecutor() { executor =>
      noException should be thrownBy executor.executeQuery(Cpg.emptyCpg, "cpg.method.l").unsafeRunSync()
    }

    "raise an error if there was an issue initialising the script engine" in withNewQueryExecutor(
      scripManagerIsDefined = false
    ) { executor =>
      intercept[RuntimeException] {
        executor.executeQuery(Cpg.emptyCpg, "cpg.method.l").unsafeRunSync()
      }
    }
  }

  "Retrieving a query" should {
    "return a success if the query was completed successfully" in withNewQueryExecutor() { executor =>
      val queryId = executor.executeQuery(Cpg.emptyCpg, "cpg.method.l").unsafeRunSync()

      eventually(timeout(2 seconds), interval(500 millis)) {
        executor.retrieveQueryResult(queryId) shouldBe OptionT.pure[IO](CpgOperationSuccess(queryResult))
      }
    }

    "return a failure if there was an issue running the query" in withNewQueryExecutor(queryError = true) { executor =>
      val queryId = executor.executeQuery(Cpg.emptyCpg, "cpg.method.l").unsafeRunSync()

      eventually(timeout(2 seconds), interval(500 millis)) {
        executor.retrieveQueryResult(queryId) shouldBe OptionT.pure[IO](CpgOperationFailure(queryException))
      }
    }

    "return an empty OptionT if the query does not yet exist" in withNewQueryExecutor() { executor =>
      executor.retrieveQueryResult(UUID.randomUUID) shouldBe OptionT.none[IO, CpgOperationResult[String]]
    }
  }
}

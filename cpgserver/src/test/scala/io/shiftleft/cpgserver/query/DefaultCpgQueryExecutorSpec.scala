package io.shiftleft.cpgserver.query

import java.io.Reader
import java.util.UUID

import cats.data.OptionT
import cats.effect.IO
import javax.script.{Bindings, ScriptContext, ScriptEngine, ScriptEngineFactory, ScriptEngineManager}

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.cpgserver.BaseSpec
import io.shiftleft.cpgserver.model.{CpgOperationFailure, CpgOperationResult, CpgOperationSuccess}

class DefaultCpgQueryExecutorSpec extends BaseSpec {

  private val queryResult = "a result"
  private val queryException = new RuntimeException("Oh noes!")

  private object UndefinedScriptEngineManager extends ScriptEngineManager {
    override def getEngineByName(shortName: String): ScriptEngine = null
  }

  private class DefinedScriptEnginerManager(queryError: Boolean) extends ScriptEngineManager {
    override def getEngineByName(shortName: String): ScriptEngine = new ScriptEngine {
      def eval(script: String): AnyRef = if (queryError) throw queryException
                                         else queryResult

      def eval(script: String, context: ScriptContext): AnyRef = ???
      def eval(reader: Reader, context: ScriptContext): AnyRef = ???
      def eval(reader: Reader): AnyRef = ???
      def eval(script: String, n: Bindings): AnyRef = ???
      def eval(reader: Reader, n: Bindings): AnyRef = ???
      def put(key: String, value: Any): Unit = ???
      def get(key: String): AnyRef = ???
      def getBindings(scope: Int): Bindings = ???
      def setBindings(bindings: Bindings, scope: Int): Unit = ???
      def createBindings(): Bindings = ???
      def getContext: ScriptContext = ???
      def setContext(context: ScriptContext): Unit = ???
      def getFactory: ScriptEngineFactory = ???
    }
  }

  private def withNewQueryExecutor[T](scripManagerIsDefined: Boolean = true,
                                      queryError: Boolean = false)
                                     (f: DefaultCpgQueryExecutor => T): T = {
    val manager = if (scripManagerIsDefined) new DefinedScriptEnginerManager(queryError)
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

      executor.retrieveQueryResult(queryId) shouldBe OptionT.pure[IO](CpgOperationSuccess(queryResult))
    }

    "return a failure if there was an issue running the query" in withNewQueryExecutor(queryError = true) { executor =>
      val queryId = executor.executeQuery(Cpg.emptyCpg, "cpg.method.l").unsafeRunSync()

      executor.retrieveQueryResult(queryId) shouldBe OptionT.pure[IO](CpgOperationFailure(queryException))

    }

    "return an empty OptionT if the query does not yet exist" in withNewQueryExecutor() { executor =>
      executor.retrieveQueryResult(UUID.randomUUID) shouldBe OptionT.none[IO, CpgOperationResult[String]]
    }
  }
}

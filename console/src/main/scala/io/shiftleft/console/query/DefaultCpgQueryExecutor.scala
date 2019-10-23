package io.shiftleft.console.query

import cats.data.OptionT
import cats.effect.{Blocker, ContextShift, IO}
import javax.script.{ScriptEngine, ScriptEngineManager}
import io.shiftleft.codepropertygraph.Cpg
import java.util.UUID
import java.util.concurrent.{ConcurrentHashMap, Executors}

import scala.collection.JavaConverters._
import scala.collection.concurrent.Map
import scala.concurrent.ExecutionContext

/**
  * This class executes a query on a given CPG.
  */
class DefaultCpgQueryExecutor(scriptEngineManager: ScriptEngineManager)(implicit val cs: ContextShift[IO])
    extends CpgQueryExecutor[AnyRef] {

  private val ENGINE_NAME = "scala"

  private lazy val engine: IO[ScriptEngine] = OptionT
    .fromOption[IO](Option(scriptEngineManager.getEngineByName(ENGINE_NAME)))
    .getOrElseF(IO.raiseError(new RuntimeException("Engine could not be instantiated.")))

  private val blocker: Blocker =
    Blocker.liftExecutionContext(ExecutionContext.fromExecutor(Executors.newFixedThreadPool(2)))

  private val queryResultMap: Map[UUID, CpgOperationResult[AnyRef]] =
    new ConcurrentHashMap[UUID, CpgOperationResult[AnyRef]].asScala

  private val uuidProvider = IO { UUID.randomUUID }

  protected def buildQuery(query: String): String =
    s"""
      |import io.shiftleft.codepropertygraph.Cpg
      |import io.shiftleft.semanticcpg.language._
      |import io.shiftleft.semanticcpg.language.NoResolve
      |implicit val resolver = NoResolve
      |val cpg = aCpg.asInstanceOf[io.shiftleft.codepropertygraph.Cpg]
      |$query
      |""".stripMargin

  override def executeQuery(cpg: Cpg, query: String): IO[UUID] = {
    val completeQuery = buildQuery(query)
    for {
      e <- engine
      resultUuid <- uuidProvider
      _ <- IO(e.put("aCpg", cpg))
      _ <- blocker
        .blockOn(IO(e.eval(completeQuery)))
        .runAsync {
          case Right(result) => IO(queryResultMap.put(resultUuid, CpgOperationSuccess(result))).map(_ => ())
          case Left(ex)      => IO(queryResultMap.put(resultUuid, CpgOperationFailure(ex))).map(_ => ())
        }
        .toIO
    } yield resultUuid
  }

  override def retrieveQueryResult(queryId: UUID): OptionT[IO, CpgOperationResult[AnyRef]] = {
    OptionT.fromOption(queryResultMap.get(queryId))
  }

  override def executeQuerySync(cpg: Cpg, query: String): IO[CpgOperationResult[AnyRef]] = {
    val completeQuery = buildQuery(query)
    for {
      e <- engine
      _ <- IO(e.put("aCpg", cpg))
      result <- IO(e.eval(completeQuery))
        .handleErrorWith(err => IO(CpgOperationFailure(err)))
        .map(CpgOperationSuccess(_))
    } yield result
  }
}

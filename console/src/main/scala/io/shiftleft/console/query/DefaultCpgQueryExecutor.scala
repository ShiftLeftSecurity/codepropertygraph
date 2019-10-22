package io.shiftleft.console.query

import cats.data.OptionT
import cats.effect.{Blocker, ContextShift, IO}
import javax.script.ScriptEngineManager

import io.shiftleft.codepropertygraph.Cpg

import java.util.UUID
import java.util.concurrent.{ConcurrentHashMap, Executors}

import scala.collection.JavaConverters._
import scala.collection.concurrent.Map
import scala.concurrent.ExecutionContext

class DefaultCpgQueryExecutor(scriptEngineManager: ScriptEngineManager)(implicit val cs: ContextShift[IO])
    extends CpgQueryExecutor[String] {

  private val engineType = "scala"

  private val blocker: Blocker =
    Blocker.liftExecutionContext(ExecutionContext.fromExecutor(Executors.newFixedThreadPool(2)))

  private val queryResultMap: Map[UUID, CpgOperationResult[String]] =
    new ConcurrentHashMap[UUID, CpgOperationResult[String]].asScala

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
    val engine = OptionT
      .fromOption[IO](Option(scriptEngineManager.getEngineByName(engineType)))
      .getOrElseF(IO.raiseError(new RuntimeException("Engine could not be instantiated.")))

    val completeQuery = buildQuery(query)

    for {
      e <- engine
      resultUuid <- uuidProvider
      _ <- IO(e.put("aCpg", cpg))
      _ <- blocker
        .blockOn(IO(e.eval(completeQuery).toString))
        .runAsync {
          case Right(result) => IO(queryResultMap.put(resultUuid, CpgOperationSuccess(result))).map(_ => ())
          case Left(ex)      => IO(queryResultMap.put(resultUuid, CpgOperationFailure(ex))).map(_ => ())
        }
        .toIO
    } yield resultUuid
  }

  override def retrieveQueryResult(queryId: UUID): OptionT[IO, CpgOperationResult[String]] = {
    OptionT.fromOption(queryResultMap.get(queryId))
  }
}

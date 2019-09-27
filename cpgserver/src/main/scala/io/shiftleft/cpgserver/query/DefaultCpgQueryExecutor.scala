package io.shiftleft.cpgserver.query

import java.util.UUID
import java.util.concurrent.ConcurrentHashMap

import scala.collection.JavaConverters._
import scala.collection.concurrent.Map

import cats.data.OptionT
import cats.effect.IO
import javax.script.ScriptEngineManager

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.cpgserver.model.{CpgOperationFailure, CpgOperationResult, CpgOperationSuccess}

class DefaultCpgQueryExecutor(scriptEngineManager: ScriptEngineManager) extends CpgQueryExecutor[String] {

  private val engineType = "scala"

  private val queryResultMap: Map[UUID, CpgOperationResult[String]] =
    new ConcurrentHashMap[UUID, CpgOperationResult[String]].asScala

  private val uuidProvider = IO { UUID.randomUUID }

  private def buildQuery(query: String) =
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
      _ <- IO(e.eval(completeQuery).toString).runAsync {
        case Right(result) => IO(queryResultMap.put(resultUuid, CpgOperationSuccess(result))).map(_ => ())
        case Left(ex)      => IO(queryResultMap.put(resultUuid, CpgOperationFailure(ex))).map(_ => ())
      }.toIO
    } yield resultUuid
  }

  override def retrieveQueryResult(queryId: UUID): OptionT[IO, CpgOperationResult[String]] = {
    OptionT.fromOption(queryResultMap.get(queryId))
  }
}

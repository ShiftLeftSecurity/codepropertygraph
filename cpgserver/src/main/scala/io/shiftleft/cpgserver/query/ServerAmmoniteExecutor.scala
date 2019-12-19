package io.shiftleft.cpgserver.query

import cats.data.OptionT
import cats.effect.{Blocker, ContextShift, IO}

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.console.scripting.AmmoniteExecutor

import java.util.UUID
import java.util.concurrent.{ConcurrentHashMap, Executors}

import scala.collection.concurrent.Map
import scala.concurrent.ExecutionContext
import scala.jdk.CollectionConverters._

abstract class ServerAmmoniteExecutor(implicit cs: ContextShift[IO]) extends AmmoniteExecutor {

  private val blocker: Blocker =
    Blocker.liftExecutionContext(ExecutionContext.fromExecutor(Executors.newFixedThreadPool(2)))

  private val queryResultMap: Map[UUID, CpgOperationResult[String]] =
    new ConcurrentHashMap[UUID, CpgOperationResult[String]].asScala

  private val uuidProvider = IO { UUID.randomUUID }

  def executeQuery(cpg: Cpg, query: String): IO[UUID] = {
    for {
      resultUuid <- uuidProvider
      _ <- blocker
        .blockOn(runQuery(query, cpg))
        .runAsync {
          case Right(result) => IO(queryResultMap.put(resultUuid, CpgOperationSuccess(result.toString))).map(_ => ())
          case Left(ex)      => IO(queryResultMap.put(resultUuid, CpgOperationFailure(ex))).map(_ => ())
        }
        .toIO
    } yield resultUuid
  }

  def retrieveQueryResult(queryId: UUID): OptionT[IO, CpgOperationResult[String]] = {
    OptionT.fromOption(queryResultMap.get(queryId))
  }

  def executeQuerySync(cpg: Cpg, query: String): IO[CpgOperationResult[String]] = {
    for {
      result <- runQuery(query, cpg)
        .map(v => CpgOperationSuccess(v.toString))
        .handleErrorWith(err => IO(CpgOperationFailure(err)))
    } yield result
  }
}

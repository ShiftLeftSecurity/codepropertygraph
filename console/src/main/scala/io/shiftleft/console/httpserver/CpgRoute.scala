package io.shiftleft.console.httpserver

import java.util.UUID
import java.util.concurrent.{ConcurrentHashMap, Executors}

import cats.data.OptionT
import cats.effect.{Blocker, ContextShift, IO}
import org.http4s.{EntityDecoder, HttpRoutes, MessageBodyFailure, Response}
import org.http4s.circe.jsonOf
import org.http4s.dsl.io.{BadRequest, InternalServerError}
import org.slf4j.LoggerFactory

import scala.util.control.NonFatal
import io.circe.syntax._
import io.circe.generic.auto._
import org.http4s.circe._
import org.http4s.dsl.io._

import scala.collection.concurrent.Map
import scala.concurrent.ExecutionContext
import scala.jdk.CollectionConverters._

final class CpgRoute()(implicit httpErrorHandler: HttpErrorHandler, cs: ContextShift[IO]) {

  import CpgRoute._

  private val blocker: Blocker =
    Blocker.liftExecutionContext(ExecutionContext.fromExecutor(Executors.newFixedThreadPool(2)))

  private val queryResultMap: Map[UUID, CpgOperationResult[String]] =
    new ConcurrentHashMap[UUID, CpgOperationResult[String]].asScala

  private object QueryExecutor {
    private val uuidProvider = IO { UUID.randomUUID }

    def executeQuery(query: String): IO[UUID] = {
      for {
        resultUuid <- uuidProvider
        _ <- blocker
          .blockOn(runQuery(query))
          .runAsync {
            case Right(result) => IO(queryResultMap.put(resultUuid, CpgOperationSuccess(result.toString))).map(_ => ())
            case Left(ex)      => IO(queryResultMap.put(resultUuid, CpgOperationFailure(ex))).map(_ => ())
          }
          .toIO
      } yield resultUuid
    }

    private def runQuery(query : String) : IO[Any] = {
      // TODO send query to queue and fetch result
      ???
    }

    def retrieveQueryResult(queryId: UUID): OptionT[IO, CpgOperationResult[String]] = {
      OptionT.fromOption(queryResultMap.get(queryId))
    }

  }

  private def createCpgQuery(queryRequest: CreateCpgQueryRequest): IO[Response[IO]] = {
    QueryExecutor.executeQuery(query = queryRequest.query)
    .flatMap(queryId => Accepted(CreateCpgQueryResponse(queryId).asJson))
  }

  private def retrieveQuery(queryId: UUID): IO[Response[IO]] = {
    QueryExecutor
      .retrieveQueryResult(queryId)
      .semiflatMap {
        case CpgOperationSuccess(queryResult) =>
          Ok(CpgOperationResponse(ready = true, result = Some(queryResult)).asJson)
        case CpgOperationFailure(ex) =>
          Ok(CpgOperationResponse(ready = true, error = Some(ex.getMessage)).asJson)
      }
      .getOrElseF(Ok(CpgOperationResponse(ready = false).asJson))
  }

  private val unhandledCpgRoutes: HttpRoutes[IO] = HttpRoutes.of {

    case req @ POST -> Root / "v1" / "cpg" / "query" =>
      req
        .as[CreateCpgQueryRequest]
        .flatMap(createCpgQuery)

    case GET -> Root / "v1" / "query" / UUIDVar(queryId) =>
      retrieveQuery(queryId)
  }

  val routes: HttpRoutes[IO] = httpErrorHandler.handle(unhandledCpgRoutes)

}

object CpgRoute {

  /* Response types */
  final case class ApiError(error: String)
  final case class CreateCpgQueryRequest(query: String)
  final case class CreateCpgQueryResponse(uuid: UUID)
  final case class CpgOperationResponse(ready: Boolean, result: Option[String] = None, error: Option[String] = None)

  /* Error handling types */
  final case class FileNameMissingException(message: String) extends RuntimeException(message)
  final case class FileSizeException(message: String) extends RuntimeException(message)

  /* Entity decoders. */
  private[route] implicit val decodeCpgQueryRequest: EntityDecoder[IO, CreateCpgQueryRequest] = jsonOf

  def apply()(
    implicit httpErrorHandler: HttpErrorHandler,
    cs: ContextShift[IO]): CpgRoute = {
    new CpgRoute()
  }

  object CpgHttpErrorHandler extends HttpErrorHandler {
    private val logger = LoggerFactory.getLogger(this.getClass)

    override def handle(routes: HttpRoutes[IO]): HttpRoutes[IO] =
      HttpErrorHandler(routes) {
        case _: MessageBodyFailure =>
          BadRequest(ApiError("Invalid payload. Please check that the payload is formatted correctly.").asJson)
        case NonFatal(ex) =>
          logger.error(s"Unhandled error: {}", ex)
          InternalServerError(ApiError("An unknown error has occurred. Please try again later.").asJson)
      }
  }
}

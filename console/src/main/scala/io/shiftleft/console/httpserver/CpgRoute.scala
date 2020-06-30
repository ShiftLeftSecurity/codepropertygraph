package io.shiftleft.console.httpserver

import java.util.UUID

import cats.effect.{ContextShift, IO}
import org.http4s.{EntityDecoder, HttpRoutes, MessageBodyFailure, Response}
import org.http4s.circe.jsonOf
import org.http4s.dsl.io.{BadRequest, InternalServerError}
import org.slf4j.LoggerFactory

import scala.util.control.NonFatal
import io.circe.generic.auto._
import io.circe.syntax._
import io.shiftleft.console.embammonite.EmbeddedAmmonite
import org.http4s.circe._
import org.http4s.dsl.io._

final class CpgRoute(executor: EmbeddedAmmonite)(implicit httpErrorHandler: HttpErrorHandler, cs: ContextShift[IO]) {

  println(executor)
  import CpgRoute._

  private def submitCpgQuery(queryRequest: CreateCpgQueryRequest): IO[Response[IO]] = {
    ???
    // executeQuery(query = queryRequest.query)
    // .flatMap(queryId => Accepted(CreateCpgQueryResponse(queryId).asJson))
  }

  private def retrieveQuery(queryId: UUID): IO[Response[IO]] = {
    ???
    //    retrieveQueryResult(queryId)
//      .semiflatMap {
//        case CpgOperationSuccess(queryResult) =>
//          Ok(CpgOperationResponse(ready = true, result = Some(queryResult)).asJson)
//        case CpgOperationFailure(ex) =>
//          Ok(CpgOperationResponse(ready = true, error = Some(ex.getMessage)).asJson)
//      }
//      .getOrElseF(Ok(CpgOperationResponse(ready = false).asJson))
  }

  private val unhandledCpgRoutes: HttpRoutes[IO] = HttpRoutes.of {

    case req @ POST -> Root / "v1" / "query" =>
      req
        .as[CreateCpgQueryRequest]
        .flatMap(submitCpgQuery)

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
  implicit val decodeCpgQueryRequest: EntityDecoder[IO, CreateCpgQueryRequest] = jsonOf

  def apply(executor: EmbeddedAmmonite)(implicit httpErrorHandler: HttpErrorHandler, cs: ContextShift[IO]): CpgRoute = {
    new CpgRoute(executor)
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

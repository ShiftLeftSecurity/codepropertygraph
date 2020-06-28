package io.shiftleft.console.httpserver

import cats.data.{Kleisli, OptionT}
import cats.effect.IO
import io.circe.generic.auto._
import io.circe.syntax._
import org.http4s._
import org.http4s.circe._
import org.http4s.dsl.io._
import org.slf4j.LoggerFactory

import scala.util.control.NonFatal

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

trait HttpErrorHandler {
  def handle(routes: HttpRoutes[IO]): HttpRoutes[IO]
}

object HttpErrorHandler {

  def apply(routes: HttpRoutes[IO])(handler: PartialFunction[Throwable, IO[Response[IO]]]): HttpRoutes[IO] = {
    Kleisli { req: Request[IO] =>
      OptionT {
        routes.run(req).value.handleErrorWith { e =>
          if (handler.isDefinedAt(e)) handler(e).map(Option(_))
          else IO.raiseError(e)
        }
      }
    }
  }
}

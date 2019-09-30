package io.shiftleft.cpgserver.route

import cats.data.{Kleisli, OptionT}
import cats.effect.IO
import org.http4s.{HttpRoutes, Request, Response}

trait HttpErrorHandler {
  def handle(routes: HttpRoutes[IO]): HttpRoutes[IO]
}

object HttpErrorHandler {

  def apply(routes: HttpRoutes[IO])(handler: Throwable => IO[Response[IO]]): HttpRoutes[IO] = {
    Kleisli { req: Request[IO] =>
      OptionT {
        routes.run(req).value.handleErrorWith { e =>
          handler(e).map(Option(_))
        }
      }
    }
  }
}

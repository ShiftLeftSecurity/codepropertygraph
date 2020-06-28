package io.shiftleft.console.httpserver

import cats.effect.{ExitCode, IO, IOApp}
import org.http4s.server.blaze.BlazeServerBuilder

import cats.implicits._
import org.http4s.implicits._

object Server extends IOApp {

  private val banner: String =
    """| ██████╗██████╗  ██████╗     ███████╗███████╗██████╗ ██╗   ██╗███████╗██████╗
       |██╔════╝██╔══██╗██╔════╝     ██╔════╝██╔════╝██╔══██╗██║   ██║██╔════╝██╔══██╗
       |██║     ██████╔╝██║  ███╗    ███████╗█████╗  ██████╔╝██║   ██║█████╗  ██████╔╝
       |██║     ██╔═══╝ ██║   ██║    ╚════██║██╔══╝  ██╔══██╗╚██╗ ██╔╝██╔══╝  ██╔══██╗
       |╚██████╗██║     ╚██████╔╝    ███████║███████╗██║  ██║ ╚████╔╝ ███████╗██║  ██║
       | ╚═════╝╚═╝      ╚═════╝     ╚══════╝╚══════╝╚═╝  ╚═╝  ╚═══╝  ╚══════╝╚═╝  ╚═╝
       |""".stripMargin

  private val serverConfig: ServerConfiguration =
    ServerConfiguration.config.getOrElse(ServerConfiguration.default)

  private val httpRoutes = SwaggerRoute().routes

  def run(args: List[String]): IO[ExitCode] = {

    BlazeServerBuilder[IO]
      .withBanner(List(banner))
      .bindHttp(serverConfig.port, serverConfig.host)
      .withHttpApp(httpRoutes.orNotFound)
      .serve
      .compile
      .drain
      .as(ExitCode.Success)
  }

}

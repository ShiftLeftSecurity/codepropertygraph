package io.shiftleft.console.httpserver

import cats.implicits._
import cats.effect.{IO, _}
import io.shiftleft.console.embammonite.EmbeddedAmmonite
import org.http4s.server.blaze.BlazeServerBuilder
import org.http4s.implicits._

class Server(executor: EmbeddedAmmonite) extends IOApp {

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

  private implicit val httpErrorHandler: HttpErrorHandler =
    CpgRoute.CpgHttpErrorHandler

  private val httpRoutes = CpgRoute(executor).routes <+> SwaggerRoute().routes

  override def run(args: List[String]): IO[ExitCode] = {

    BlazeServerBuilder[IO]
      .withBanner(List(banner))
      .bindHttp(serverConfig.port, serverConfig.host)
      .withHttpApp(httpRoutes.orNotFound)
      .serve
      .onFinalize(IO(println("Server terminated gracefully")))
      .compile
      .drain
      .as(ExitCode.Success)
  }

}

package io.shiftleft.console.httpserver

import java.io.{PipedInputStream, PipedOutputStream}

import cats.implicits._
import cats.effect.{IO, _}
import org.http4s.server.blaze.BlazeServerBuilder
import org.http4s.implicits._

class Server(toStdin: PipedOutputStream, fromStdout: PipedInputStream, fromStderr: PipedInputStream) extends IOApp {

  println(toStdin)
  println(fromStdout)
  println(fromStderr)

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

  private val httpRoutes = CpgRoute().routes <+> SwaggerRoute().routes

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

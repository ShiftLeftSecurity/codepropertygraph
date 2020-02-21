package io.shiftleft.cpgserver

import cats.effect.{ExitCode, IO, IOApp}
import cats.implicits._
import org.http4s.implicits._
import org.http4s.server.blaze.BlazeServerBuilder

import io.shiftleft.cpgserver.config.ServerConfiguration
import io.shiftleft.cpgserver.cpg.DummyCpgProvider
import io.shiftleft.cpgserver.query.{DefaultAmmoniteExecutor, ServerAmmoniteExecutor}
import io.shiftleft.cpgserver.route.{CpgRoute, HttpErrorHandler, SwaggerRoute}

object CpgServerMain extends IOApp {

  private val banner: String =
    """| ██████╗██████╗  ██████╗     ███████╗███████╗██████╗ ██╗   ██╗███████╗██████╗
       |██╔════╝██╔══██╗██╔════╝     ██╔════╝██╔════╝██╔══██╗██║   ██║██╔════╝██╔══██╗
       |██║     ██████╔╝██║  ███╗    ███████╗█████╗  ██████╔╝██║   ██║█████╗  ██████╔╝
       |██║     ██╔═══╝ ██║   ██║    ╚════██║██╔══╝  ██╔══██╗╚██╗ ██╔╝██╔══╝  ██╔══██╗
       |╚██████╗██║     ╚██████╔╝    ███████║███████╗██║  ██║ ╚████╔╝ ███████╗██║  ██║
       | ╚═════╝╚═╝      ╚═════╝     ╚══════╝╚══════╝╚═╝  ╚═╝  ╚═══╝  ╚══════╝╚═╝  ╚═╝
       |""".stripMargin

  private val cpgProvider: DummyCpgProvider =
    new DummyCpgProvider

  private val ammoniteExecutor: ServerAmmoniteExecutor =
    new DefaultAmmoniteExecutor

  private implicit val httpErrorHandler: HttpErrorHandler =
    CpgRoute.CpgHttpErrorHandler

  private val serverConfig: ServerConfiguration =
    ServerConfiguration.config.getOrElse(ServerConfiguration.default)

  private val httpRoutes =
    CpgRoute(cpgProvider, ammoniteExecutor, serverConfig.files).routes <+> SwaggerRoute().routes

  override def run(args: List[String]): IO[ExitCode] = {
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

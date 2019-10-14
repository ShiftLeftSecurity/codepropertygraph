package io.shiftleft.cpgserver.route

import java.util.concurrent.Executors

import scala.concurrent.ExecutionContext

import cats.data.OptionT
import cats.effect.{ContextShift, IO}
import io.circe.generic.auto._
import io.circe.syntax._
import org.http4s._
import org.http4s.circe._
import org.http4s.dsl.io._
import org.http4s.headers.Location
import org.webjars.WebJarAssetLocator

import io.shiftleft.cpgserver.route.CpgRoute.ApiError

final class SwaggerRoute {

  private val blockingEc = ExecutionContext.fromExecutor(Executors.newSingleThreadExecutor)
  private implicit val blockingCs: ContextShift[IO] = IO.contextShift(blockingEc)

  private val swaggerUiVersion = IO { new WebJarAssetLocator().getWebJars.get("swagger-ui") }
  private val swaggerUiResources = swaggerUiVersion.map { ver =>
    s"/META-INF/resources/webjars/swagger-ui/$ver"
  }
  private val swaggerUiPath = Path("swagger-ui")

  val routes: HttpRoutes[IO] = HttpRoutes.of {
    case GET -> Root / ("swagger-ui" | "docs") =>
      PermanentRedirect(Location(uri"swagger-ui/index.html"))

    case req @ GET -> (Root | `swaggerUiPath`) / "swagger.yaml" =>
      StaticFile
        .fromResource("/swagger.yaml", blockingEc, Some(req))
        .getOrElseF(InternalServerError(ApiError("Swagger documentation is missing.").asJson))

    case req @ GET -> path if path.startsWith(swaggerUiPath) => {
      val file = path.toList.tail.mkString("/", "/", "") match {
        case f if f == "/index.html" =>
          StaticFile.fromResource[IO]("/swagger-ui/index.html", blockingEc, Some(req))
        case f =>
          OptionT.liftF(swaggerUiResources).flatMap { resources =>
            StaticFile.fromResource[IO](resources + f, blockingEc, Some(req))
          }
      }
      file.getOrElseF(InternalServerError(ApiError(s"Requested file [$file] is missing.").asJson))
    }
  }
}

object SwaggerRoute {
  def apply(): SwaggerRoute =
    new SwaggerRoute
}

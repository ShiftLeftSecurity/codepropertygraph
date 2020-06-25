package io.shiftleft.cpgserver.route

import cats.effect.{ContextShift, IO}
import cats.implicits.catsStdInstancesForList
import cats.syntax.foldable._
import io.circe.generic.auto._
import io.circe.syntax._
import org.http4s._
import org.http4s.circe._
import org.http4s.dsl.io._
import org.slf4j.LoggerFactory

import io.shiftleft.cpgserver.cpg.CpgProvider
import io.shiftleft.cpgserver.query.{CpgOperationFailure, CpgOperationSuccess, ServerAmmoniteExecutor}

import java.nio.file.{Files, Paths}
import java.util.UUID

import scala.util.control.NonFatal

final class CpgRoute(cpgProvider: CpgProvider, cpgQueryExecutor: ServerAmmoniteExecutor)(
    implicit httpErrorHandler: HttpErrorHandler,
    cs: ContextShift[IO]) {

  import CpgRoute._

  private def allFilesExist(fileNames: List[String]): IO[Boolean] = fileNames.forallM { fileName =>
    IO {
      Files.exists(Paths.get(fileName))
    }
  }

  private def createCpg(createRequest: CreateCpgRequest): IO[Response[IO]] = createRequest match {
    case CreateCpgRequest(files) if files.isEmpty =>
      BadRequest(ApiError("At least one or more files must be specified.").asJson)
    case CreateCpgRequest(files) =>
      allFilesExist(files).flatMap {
        case true =>
          cpgProvider
            .createCpg(files.toSet)
            .flatMap(cpgId => Accepted(CreateCpgResponse(cpgId).asJson))
        case false =>
          BadRequest(ApiError("One or more of the specified files do not exist.").asJson)
      }
  }

  private def createCpgQuery(cpgId: UUID, queryRequest: CreateCpgQueryRequest): IO[Response[IO]] = {
    cpgProvider
      .retrieveCpg(cpgId)
      .semiflatMap {
        case CpgOperationSuccess(cpg) =>
          cpgQueryExecutor
            .executeQuery(cpg, queryRequest.query)
            .flatMap(queryId => Accepted(CreateCpgQueryResponse(queryId).asJson))
        case CpgOperationFailure(_) =>
          UnprocessableEntity(
            ApiError("The creation of the referenced CPG failed, therefore the query can not be run.").asJson)
      }
      .getOrElseF(NotFound(ApiError(s"CPG referenced by [$cpgId] does not exist.").asJson))
  }

  private def retrieveCpgStatus(cpgId: UUID): IO[Response[IO]] = {
    cpgProvider
      .retrieveCpg(cpgId)
      .semiflatMap {
        case CpgOperationSuccess(_) =>
          Ok(CpgOperationResponse(ready = true).asJson)
        case CpgOperationFailure(ex) =>
          Ok(CpgOperationResponse(ready = true, error = Some(ex.getMessage)).asJson)
      }
      .getOrElseF(Ok(CpgOperationResponse(ready = false).asJson))
  }

  private def retrieveQuery(queryId: UUID): IO[Response[IO]] = {
    cpgQueryExecutor
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
    case req @ POST -> Root / "v1" / "create" =>
      req
        .as[CreateCpgRequest]
        .flatMap(createCpg)

    case req @ POST -> Root / "v1" / "cpg" / UUIDVar(cpgId) / "query" =>
      req
        .as[CreateCpgQueryRequest]
        .flatMap(createCpgQuery(cpgId, _))

    case GET -> Root / "v1" / "cpg" / UUIDVar(cpgId) =>
      retrieveCpgStatus(cpgId)

    case GET -> Root / "v1" / "query" / UUIDVar(queryId) =>
      retrieveQuery(queryId)
  }

  val routes: HttpRoutes[IO] = httpErrorHandler.handle(unhandledCpgRoutes)
}

object CpgRoute {

  /* Response types */
  final case class ApiError(error: String)
  final case class CreateCpgRequest(files: List[String])
  final case class CreateCpgResponse(uuid: UUID)
  final case class CreateCpgQueryRequest(query: String)
  final case class CreateCpgQueryResponse(uuid: UUID)
  final case class CpgOperationResponse(ready: Boolean, result: Option[String] = None, error: Option[String] = None)

  /* Error handling types */
  final case class FileNameMissingException(message: String) extends RuntimeException(message)
  final case class FileSizeException(message: String) extends RuntimeException(message)

  /* Entity decoders. */
  private[route] implicit val decodeCpgRequest: EntityDecoder[IO, CreateCpgRequest] = jsonOf
  private[route] implicit val decodeCpgQueryRequest: EntityDecoder[IO, CreateCpgQueryRequest] = jsonOf

  def apply(cpgProvider: CpgProvider, ammoniteExecutor: ServerAmmoniteExecutor)(
      implicit httpErrorHandler: HttpErrorHandler,
      cs: ContextShift[IO]): CpgRoute = {
    new CpgRoute(cpgProvider, ammoniteExecutor)
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

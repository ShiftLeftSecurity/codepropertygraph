package io.shiftleft.cpgserver.route

import java.nio.file.{Files, Paths}
import java.util.UUID
import cats.effect.IO
import cats.implicits.catsStdInstancesForList
import cats.syntax.foldable._
import io.circe.Encoder
import io.circe.generic.auto._
import io.circe.syntax._
import org.http4s.circe._
import org.http4s.dsl.io._
import org.http4s.{EntityDecoder, HttpRoutes, MessageBodyFailure, Response}
import org.slf4j.LoggerFactory

import io.shiftleft.console.query.{CpgOperationFailure, CpgOperationSuccess, CpgQueryExecutor}
import io.shiftleft.cpgserver.cpg.CpgProvider

final class CpgRoute[T: Encoder](cpgProvider: CpgProvider, cpgQueryExecutor: CpgQueryExecutor[T])(
    implicit httpErrorHandler: HttpErrorHandler) {

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

  // TODO discuss with jacob: according to scalac this is unreachable... commenting for now since it probably never worked anyway
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
          Ok(CpgOperationResponse[T](ready = true).asJson)
        case CpgOperationFailure(ex) =>
          Ok(CpgOperationResponse[T](ready = true, error = Some(ex.getMessage)).asJson)
      }
      .getOrElseF(Ok(CpgOperationResponse[T](ready = false).asJson))
  }

  private def retrieveQuery(queryId: UUID): IO[Response[IO]] = {
    cpgQueryExecutor
      .retrieveQueryResult(queryId)
      .semiflatMap {
        case CpgOperationSuccess(queryResult) =>
          Ok(CpgOperationResponse[T](ready = true, result = Some(queryResult)).asJson)
        case CpgOperationFailure(ex) =>
          Ok(CpgOperationResponse[T](ready = true, error = Some(ex.getMessage)).asJson)
      }
      .getOrElseF(Ok(CpgOperationResponse[T](ready = false).asJson))
  }

  private val unhandledCpgRoutes: HttpRoutes[IO] = HttpRoutes.of {
    case req @ POST -> Root / "v1" / "create" =>
      req
        .as[CreateCpgRequest]
        .flatMap(createCpg)

    // TODO discuss with jacob: according to scalac this is unreachable... commenting for now since it probably never worked anyway
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

  def apply[T: Encoder](cpgProvider: CpgProvider, cpgQueryExecutor: CpgQueryExecutor[T])(
      implicit httpErrorHandler: HttpErrorHandler): CpgRoute[T] = {
    new CpgRoute[T](cpgProvider, cpgQueryExecutor)
  }

  final case class ApiError(error: String)
  final case class CreateCpgRequest(files: List[String])
  final case class CreateCpgResponse(uuid: UUID)
  final case class CreateCpgQueryRequest(query: String)
  final case class CreateCpgQueryResponse(uuid: UUID)
  final case class CpgOperationResponse[T](ready: Boolean, result: Option[T] = None, error: Option[String] = None)

  private[route] implicit val decodeCpgRequest: EntityDecoder[IO, CreateCpgRequest] = jsonOf
  private[route] implicit val decodeCpgQueryRequest: EntityDecoder[IO, CreateCpgQueryRequest] = jsonOf

  object CpgHttpErrorHandler extends HttpErrorHandler {
    private val logger = LoggerFactory.getLogger(this.getClass)

    override def handle(routes: HttpRoutes[IO]): HttpRoutes[IO] =
      HttpErrorHandler(routes) {
        case _: MessageBodyFailure =>
          BadRequest(ApiError("Invalid payload. Please check that the payload is formatted correctly.").asJson)
        case ex =>
          logger.error(s"Unhandled error: {}", ex)
          InternalServerError(ApiError("An unknown error has occurred. Please try again later.").asJson)
      }
  }
}

package io.shiftleft.cpgserver.route

import java.nio.file.Files
import java.util.UUID
import cats.data.{Kleisli, OptionT}
import cats.effect.IO
import org.http4s.implicits._
import org.http4s._

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.console.query.{CpgOperationFailure, CpgOperationResult, CpgOperationSuccess, CpgQueryExecutor}
import io.shiftleft.cpgserver.cpg.CpgProvider
import io.shiftleft.cpgserver.route.CpgRoute.{ApiError, CpgOperationResponse, CreateCpgQueryResponse, CreateCpgResponse}

class CpgRouteSpec extends Http4sSpec {
  import CpgRouteSpec._

  private class DummyCpgProvider(uuid: UUID, cpg: OptionT[IO, CpgOperationResult[Cpg]]) extends CpgProvider {
    def createCpg(fileNames: Set[String]): IO[UUID] = IO.pure(uuid)
    def retrieveCpg(cpgId: UUID): OptionT[IO, CpgOperationResult[Cpg]] = cpg
  }

  private class DummyCpgQueryExecutor(uuid: UUID, queryResult: OptionT[IO, CpgOperationResult[String]])
      extends CpgQueryExecutor[String] {
    def executeQuery(cpg: Cpg, query: String): IO[UUID] = IO.pure(uuid)
    def retrieveQueryResult(queryId: UUID): OptionT[IO, CpgOperationResult[String]] = queryResult
  }

  private def withRoute[T](
      cpgUuid: UUID = UUID.randomUUID(),
      cpg: OptionT[IO, CpgOperationResult[Cpg]] = OptionT.pure(CpgOperationSuccess(Cpg.emptyCpg)),
      queryUuid: UUID = UUID.randomUUID(),
      queryResult: OptionT[IO, CpgOperationResult[String]] = OptionT.pure(CpgOperationSuccess("")))(
      f: Kleisli[IO, Request[IO], Response[IO]] => T): T = {
    val cpgProvider = new DummyCpgProvider(cpgUuid, cpg)
    val cpgQueryExecutor = new DummyCpgQueryExecutor(queryUuid, queryResult)
    implicit val errorHandler: HttpErrorHandler = CpgRoute.CpgHttpErrorHandler
    f(new CpgRoute(cpgProvider, cpgQueryExecutor).routes.orNotFound)
  }

  "Creating a CPG" should {
    "succeed with 201 ACCEPTED if all of the specified files exist" in withRoute(cpgUuid = fixedCpgUuid) { route =>
      val file = Files.createTempFile("cpgserver_test", ".java")

      val requestBody =
        s"""
          |{
          |  "files": ["$file"]
          |}
          |""".stripMargin
      val request = Request[IO](method = Method.POST, uri = uri"/v1/create").withEntity(requestBody)
      val response = route.run(request)

      check(response, Status.Accepted, Some(CreateCpgResponse(fixedCpgUuid))) shouldBe true
    }

    "fail with 400 BAD REQUEST if one or more of the specified files do not exist" in withRoute() { route =>
      val requestBody =
        s"""
           |{
           |  "files": ["/tmp/definitelynothere.java"]
           |}
           |""".stripMargin
      val request = Request[IO](method = Method.POST, uri = uri"/v1/create").withEntity(requestBody)
      val response = route.run(request)

      check(response, Status.BadRequest, Some(ApiError("One or more of the specified files do not exist."))) shouldBe true
    }

    "fail with 400 BAD REQUEST if no files are specified" in withRoute() { route =>
      val requestBody =
        s"""
           |{
           |  "files": []
           |}
           |""".stripMargin
      val request = Request[IO](method = Method.POST, uri = uri"/v1/create").withEntity(requestBody)
      val response = route.run(request)

      check(response, Status.BadRequest, Some(ApiError("At least one or more files must be specified."))) shouldBe true
    }

    "fail with 400 BAD REQUEST if the JSON payload is not correct" in withRoute(
      cpgUuid = fixedCpgUuid
    ) { route =>
      val requestBody =
        """
          |{
          |  "more": "cake"
          |}
          |""".stripMargin

      val request = Request[IO](method = Method.POST, uri = uri"/v1/create").withEntity(requestBody)
      val response = route.run(request)

      check(response,
            Status.BadRequest,
            Some(ApiError("Invalid payload. Please check that the payload is formatted correctly.")))
    }
  }

  "Checking the status of a CPG" should {
    "succeed with 200 OK if the CPG is ready" in withRoute(cpgUuid = fixedCpgUuid) { route =>
      val request = Request[IO](method = Method.GET, uri = uri"/v1/cpg" / fixedCpgUuid.toString)
      val response = route.run(request)

      check(response, Status.Ok, Some(CpgOperationResponse[String](ready = true))) shouldBe true
    }

    "succeed with 200 OK if the CPG was unable to be created" in withRoute(
      cpgUuid = fixedCpgUuid,
      cpg = OptionT.pure(CpgOperationFailure(new RuntimeException("Oh no!")))) { route =>
      val request = Request[IO](method = Method.GET, uri = uri"/v1/cpg" / fixedCpgUuid.toString)
      val response = route.run(request)

      check(response, Status.Ok, Some(CpgOperationResponse[String](ready = true, error = Some("Oh no!")))) shouldBe true
    }

    "succeed with 200 OK if the CPG is not ready" in withRoute(
      cpgUuid = fixedCpgUuid,
      cpg = OptionT.none
    ) { route =>
      val request = Request[IO](method = Method.GET, uri = uri"/v1/cpg" / fixedCpgUuid.toString)
      val response = route.run(request)

      check(response, Status.Ok, Some(CpgOperationResponse[String](ready = false))) shouldBe true
    }

    "succeed with 200 OK if the referenced CPG does not exist" in withRoute(
      cpg = OptionT.none
    ) { route =>
      val request = Request[IO](method = Method.GET, uri = uri"/v1/cpg" / UUID.randomUUID.toString)
      val response = route.run(request)

      check(response, Status.Ok, Some(CpgOperationResponse[String](ready = false))) shouldBe true
    }
  }

  "Creating a CPG query" should {
    val query =
      """
        |{
        |  "query": "cpg.method.l"
        |}
        |""".stripMargin

    "succeed with 201 ACCEPTED if the CPG query was successfully started" in withRoute(
      cpgUuid = fixedCpgUuid,
      queryUuid = fixedQueryUuid
    ) { route =>
      val request =
        Request[IO](method = Method.POST, uri = uri"/v1/cpg" / fixedCpgUuid.toString / "query").withEntity(query)
      val response = route.run(request)

      check(response, Status.Accepted, Some(CreateCpgQueryResponse(fixedQueryUuid))) shouldBe true
    }

    "fail with 422 UNPROCESSABLE ENTITY if the target CPG was not created due to some error" in withRoute(
      cpgUuid = fixedCpgUuid,
      cpg = OptionT.pure(CpgOperationFailure(new RuntimeException("Oh noes!")))
    ) { route =>
      val request =
        Request[IO](method = Method.POST, uri = uri"/v1/cpg" / fixedCpgUuid.toString / "query").withEntity(query)
      val response = route.run(request)

      check(
        response,
        Status.UnprocessableEntity,
        Some(ApiError("The creation of the referenced CPG failed, therefore the query can not be run."))) shouldBe true
    }

    "fail with 404 NOT FOUND if the target CPG does not exist" in withRoute(
      cpg = OptionT.none
    ) { route =>
      val request =
        Request[IO](method = Method.POST, uri = uri"/v1/cpg" / fixedCpgUuid.toString / "query").withEntity(query)
      val response = route.run(request)

      check(response, Status.NotFound, Some(ApiError(s"CPG referenced by [$fixedCpgUuid] does not exist.")))
    }

    "fail with 400 BAD REQUEST if the JSON payload is not correct" in withRoute(
      cpgUuid = fixedCpgUuid
    ) { route =>
      val requestBody =
        """
          |{
          |  "more": "cake"
          |}
          |""".stripMargin

      val request =
        Request[IO](method = Method.POST, uri = uri"/v1/cpg" / fixedCpgUuid.toString / "query").withEntity(requestBody)
      val response = route.run(request)

      check(response,
            Status.BadRequest,
            Some(ApiError("Invalid payload. Please check that the payload is formatted correctly.")))
    }
  }

  "Checking the status of a CPG query" should {

    "succeed with 200 OK with the query result if the CPG query is ready" in withRoute(
      queryUuid = fixedQueryUuid,
      queryResult = OptionT.pure(CpgOperationSuccess("result"))
    ) { route =>
      val request = Request[IO](method = Method.GET, uri = uri"/v1/query" / fixedQueryUuid.toString)
      val response = route.run(request)

      check(response, Status.Ok, Some(CpgOperationResponse[String](ready = true, result = Some("result")))) shouldBe true
    }

    "succeed with 200 OK with an error message if the CPG query failed" in withRoute(
      queryUuid = fixedQueryUuid,
      queryResult = OptionT.pure(CpgOperationFailure(new RuntimeException("Oh noes!")))
    ) { route =>
      val request = Request[IO](method = Method.GET, uri = uri"/v1/query" / fixedQueryUuid.toString)
      val response = route.run(request)

      check(response, Status.Ok, Some(CpgOperationResponse[String](ready = true, error = Some("Oh noes!")))) shouldBe true
    }

    "succeed with 200 OK without the query result if the CPG query is not ready" in withRoute(
      queryUuid = fixedQueryUuid,
      queryResult = OptionT.none
    ) { route =>
      val request = Request[IO](method = Method.GET, uri = uri"/v1/query" / fixedQueryUuid.toString)
      val response = route.run(request)

      check(response, Status.Ok, Some(CpgOperationResponse[String](ready = false))) shouldBe true
    }

    "succeed with 200 OK if the referenced query does not exist" in withRoute(
      queryResult = OptionT.none
    ) { route =>
      val request = Request[IO](method = Method.GET, uri = uri"/v1/query" / UUID.randomUUID.toString)
      val response = route.run(request)

      check(response, Status.Ok, Some(CpgOperationResponse[String](ready = false))) shouldBe true
    }
  }

  "Hitting an unknown route" should {
    "return 400 NOT FOUND" in withRoute() { route =>
      val request = Request[IO](method = Method.POST, uri = uri"/v1/eat-more-cake")
      val response = route.run(request)

      check(response, Status.NotFound, Some("Not found")) shouldBe true
    }
  }
}

object CpgRouteSpec {
  import io.circe.generic.auto._
  import org.http4s.circe._

  implicit val createResponseDecoder: EntityDecoder[IO, CreateCpgResponse] = jsonOf
  implicit val apiErrorDecoder: EntityDecoder[IO, ApiError] = jsonOf
  implicit val cpgOpResponseDecoder: EntityDecoder[IO, CpgOperationResponse[String]] = jsonOf
  implicit val cpgCreateQueryResponse: EntityDecoder[IO, CreateCpgQueryResponse] = jsonOf

  val fixedCpgUuid: UUID = UUID.randomUUID
  val fixedQueryUuid: UUID = UUID.randomUUID
}

package io.shiftleft.cpgserver.route

import java.nio.file.Files
import java.util.UUID
import cats.data.{Kleisli, OptionT}
import cats.effect.{Blocker, ContextShift, IO}
import org.http4s.implicits._
import org.http4s._
import org.http4s.headers.{`Content-Disposition`, `Content-Type`}
import org.http4s.multipart.{Multipart, Part}

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.cpgserver.config.{ServerConfiguration, ServerFilesConfiguration}
import io.shiftleft.cpgserver.query.{
  CpgOperationFailure,
  CpgOperationResult,
  CpgOperationSuccess,
  ServerAmmoniteExecutor
}
import io.shiftleft.cpgserver.cpg.CpgProvider
import io.shiftleft.cpgserver.route.CpgRoute.{ApiError, CpgOperationResponse, CreateCpgQueryResponse, CreateCpgResponse}

import java.util.concurrent.Executors

class CpgRouteSpec extends Http4sSpec {
  import CpgRouteSpec._

  private implicit val cs: ContextShift[IO] = IO.contextShift(scala.concurrent.ExecutionContext.global)
  private val blocker: Blocker = Blocker.liftExecutorService(Executors.newFixedThreadPool(1))

  private class DummyCpgProvider(uuid: UUID, cpg: OptionT[IO, CpgOperationResult[Cpg]]) extends CpgProvider {
    override def createCpg(fileNames: Set[String]): IO[UUID] = IO.pure(uuid)
    override def retrieveCpg(cpgId: UUID): OptionT[IO, CpgOperationResult[Cpg]] = cpg
  }

  private class DummyServerAmmoniteExecutor(uuid: UUID, queryResult: OptionT[IO, CpgOperationResult[String]])
      extends ServerAmmoniteExecutor {

    override protected lazy val predef: String = ""
    override def executeQuery(cpg: Cpg, query: String): IO[UUID] = IO.pure(uuid)
    override def retrieveQueryResult(queryId: UUID): OptionT[IO, CpgOperationResult[String]] = queryResult
    override def executeQuerySync(cpg: Cpg, query: String): IO[CpgOperationResult[String]] = ???
  }

  private def withRoute[T](cpgUuid: UUID = UUID.randomUUID(),
                           cpg: OptionT[IO, CpgOperationResult[Cpg]] = OptionT.pure(CpgOperationSuccess(Cpg.emptyCpg)),
                           queryUuid: UUID = UUID.randomUUID(),
                           queryResult: OptionT[IO, CpgOperationResult[String]] = OptionT.pure(CpgOperationSuccess("")),
                           config: ServerFilesConfiguration = ServerConfiguration.default.files)(
      f: Kleisli[IO, Request[IO], Response[IO]] => T): T = {
    val cpgProvider = new DummyCpgProvider(cpgUuid, cpg)
    val cpgQueryExecutor = new DummyServerAmmoniteExecutor(queryUuid, queryResult)
    implicit val errorHandler: HttpErrorHandler = CpgRoute.CpgHttpErrorHandler
    f(new CpgRoute(cpgProvider, cpgQueryExecutor, config).routes.orNotFound)
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

  "Creating a CPG from a set of uploaded files" should {

    "succeed with 201 ACCEPTED if all files were uploaded successfully for processing" in withRoute(
      cpgUuid = fixedCpgUuid) { route =>
      val tempFile = Files.createTempFile("cpgserver_test", ".c").toUri.toURL

      val requestContent =
        Multipart[IO](Vector(Part.fileData("file", tempFile, blocker, `Content-Type`(MediaType.text.plain))))
      val request = Request[IO](method = Method.POST, uri = uri"/v1/upload", headers = requestContent.headers)
        .withEntity(requestContent)
      val response = route.run(request)

      check(response, Status.Accepted, Some(CreateCpgResponse(fixedCpgUuid))) shouldBe true
    }

    "fail with 413 PAYLOAD TOO LARGE if any file exceeds the specified size limit of 10MB" in withRoute(
      config = ServerFilesConfiguration(42, 42) // Size file size limit to 42 bytes.
    ) { route =>
      val tempFile = Files.createTempFile("cpgserver_test", ".c")
      Files.write(tempFile, Array.fill(42)(1: Byte)) // Size check is inclusive of the limit.

      val requestContent = Multipart[IO](
        Vector(Part.fileData("file", tempFile.toUri.toURL, blocker, `Content-Type`(MediaType.text.plain))))
      val request = Request[IO](method = Method.POST, uri = uri"/v1/upload", headers = requestContent.headers)
        .withEntity(requestContent)
      val response = route.run(request)

      check(response, Status.PayloadTooLarge, Some(ApiError("A provided file is larger than [42] bytes."))) shouldBe true
    }

    "fail with 400 BAD REQUEST if no 'file' entries were specified" in withRoute() { route =>
      val tempFile = Files.createTempFile("cpgserver_test", ".c").toUri.toURL

      val requestContent =
        Multipart[IO](Vector(Part.fileData("cake", tempFile, blocker, `Content-Type`(MediaType.text.plain))))
      val request = Request[IO](method = Method.POST, uri = uri"/v1/upload", headers = requestContent.headers)
        .withEntity(requestContent)
      val response = route.run(request)

      check(response,
            Status.BadRequest,
            Some(ApiError("At least one 'file' must be specified for the CPG to be created"))) shouldBe true
    }

    "fail with 400 BAD REQUEST if a 'file' is missing a 'filename'" in withRoute() { route =>
      val tempFile = Files.createTempFile("cpgserver_test", ".c").toUri.toURL

      val part = Part.fileData[IO]("file", tempFile, blocker, `Content-Type`(MediaType.text.plain))
      val dispositionHeader = part.headers.get(`Content-Disposition`).get
      val headersWithoutFilename =
        part.headers.put(dispositionHeader.copy(parameters = dispositionHeader.parameters.removed("filename")))

      val requestContent = Multipart[IO](Vector(part.copy(headers = headersWithoutFilename)))
      val request = Request[IO](method = Method.POST, uri = uri"/v1/upload", headers = requestContent.headers)
        .withEntity(requestContent)
      val response = route.run(request)

      check(response, Status.BadRequest, Some(ApiError("All parts must specify a filename."))) shouldBe true
    }

    "fail with 422 UNPROCESSABLE ENTITY if no multipart entries were specified" in withRoute() { route =>
      val requestContent = Multipart[IO](Vector.empty)
      val request = Request[IO](method = Method.POST, uri = uri"/v1/upload", headers = requestContent.headers)
        .withEntity(requestContent)
      val response = route.run(request)

      check(response, Status.UnprocessableEntity, Some(ApiError("Invalid Multipart body provided."))) shouldBe true
    }
  }

  "Checking the status of a CPG" should {
    "succeed with 200 OK if the CPG is ready" in withRoute(cpgUuid = fixedCpgUuid) { route =>
      val request = Request[IO](method = Method.GET, uri = uri"/v1/cpg" / fixedCpgUuid.toString)
      val response = route.run(request)

      check(response, Status.Ok, Some(CpgOperationResponse(ready = true))) shouldBe true
    }

    "succeed with 200 OK if the CPG was unable to be created" in withRoute(
      cpgUuid = fixedCpgUuid,
      cpg = OptionT.pure(CpgOperationFailure(new RuntimeException("Oh no!")))) { route =>
      val request = Request[IO](method = Method.GET, uri = uri"/v1/cpg" / fixedCpgUuid.toString)
      val response = route.run(request)

      check(response, Status.Ok, Some(CpgOperationResponse(ready = true, error = Some("Oh no!")))) shouldBe true
    }

    "succeed with 200 OK if the CPG is not ready" in withRoute(
      cpgUuid = fixedCpgUuid,
      cpg = OptionT.none
    ) { route =>
      val request = Request[IO](method = Method.GET, uri = uri"/v1/cpg" / fixedCpgUuid.toString)
      val response = route.run(request)

      check(response, Status.Ok, Some(CpgOperationResponse(ready = false))) shouldBe true
    }

    "succeed with 200 OK if the referenced CPG does not exist" in withRoute(
      cpg = OptionT.none
    ) { route =>
      val request = Request[IO](method = Method.GET, uri = uri"/v1/cpg" / UUID.randomUUID.toString)
      val response = route.run(request)

      check(response, Status.Ok, Some(CpgOperationResponse(ready = false))) shouldBe true
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

      check(response, Status.Ok, Some(CpgOperationResponse(ready = true, result = Some("result")))) shouldBe true
    }

    "succeed with 200 OK with an error message if the CPG query failed" in withRoute(
      queryUuid = fixedQueryUuid,
      queryResult = OptionT.pure(CpgOperationFailure(new RuntimeException("Oh noes!")))
    ) { route =>
      val request = Request[IO](method = Method.GET, uri = uri"/v1/query" / fixedQueryUuid.toString)
      val response = route.run(request)

      check(response, Status.Ok, Some(CpgOperationResponse(ready = true, error = Some("Oh noes!")))) shouldBe true
    }

    "succeed with 200 OK without the query result if the CPG query is not ready" in withRoute(
      queryUuid = fixedQueryUuid,
      queryResult = OptionT.none
    ) { route =>
      val request = Request[IO](method = Method.GET, uri = uri"/v1/query" / fixedQueryUuid.toString)
      val response = route.run(request)

      check(response, Status.Ok, Some(CpgOperationResponse(ready = false))) shouldBe true
    }

    "succeed with 200 OK if the referenced query does not exist" in withRoute(
      queryResult = OptionT.none
    ) { route =>
      val request = Request[IO](method = Method.GET, uri = uri"/v1/query" / UUID.randomUUID.toString)
      val response = route.run(request)

      check(response, Status.Ok, Some(CpgOperationResponse(ready = false))) shouldBe true
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
  implicit val cpgOpResponseDecoder: EntityDecoder[IO, CpgOperationResponse] = jsonOf
  implicit val cpgCreateQueryResponse: EntityDecoder[IO, CreateCpgQueryResponse] = jsonOf

  val fixedCpgUuid: UUID = UUID.randomUUID
  val fixedQueryUuid: UUID = UUID.randomUUID
}

package io.shiftleft.cpgserver.route

import cats.effect.IO
import org.http4s.{EntityDecoder, Response, Status}

import io.shiftleft.cpgserver.BaseSpec

trait Http4sSpec extends BaseSpec {

  // Helpfully lifted from https://http4s.org/v0.20/testing/
  def check[A](actual: IO[Response[IO]], expectedStatus: Status, expectedBody: Option[A] = None)(
      implicit ev: EntityDecoder[IO, A]
  ): Boolean = {
    val actualResp = actual.unsafeRunSync
    val statusCheck = actualResp.status == expectedStatus
    val bodyCheck =
      expectedBody.fold[Boolean](actualResp.body.compile.toVector.unsafeRunSync.isEmpty)( // Verify Response's body is empty.
        expected => actualResp.as[A].unsafeRunSync == expected)
    statusCheck && bodyCheck
  }
}

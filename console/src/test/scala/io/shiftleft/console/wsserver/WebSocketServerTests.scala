package io.shiftleft.console.wsserver

import java.util.UUID

import org.scalatest.{Matchers, WordSpec}

import scala.concurrent._
import duration.Duration.Inf
import castor.Context.Simple.global
import cask.util.Logger.Console._
import io.shiftleft.console.embammonite.EmbeddedAmmonite

import scala.util.Try

class WebSocketServerTests extends WordSpec with Matchers {
  "WebsocketServer" should {

    "allow connecting, posting a query and getting back a uuid" in Fixture() { host =>
      var wsPromise = scala.concurrent.Promise[String]
      cask.util.WsClient.connect(s"$host/connect") {
        case cask.Ws.Text(msg) => wsPromise.success(msg)
      }

      val wsMsg = Await.result(wsPromise.future, Inf)
      wsMsg shouldBe "connected"

      wsPromise = scala.concurrent.Promise[String]
      val response = requests.post(s"$host/query", data = ujson.Obj("query" -> "1").toString)
      val jsonResponse = ujson.read(response.contents)
      Try { UUID.fromString(jsonResponse("uuid").str) }.toOption shouldBe defined
      jsonResponse("success").bool shouldBe true

      val wsMsg2 = Await.result(wsPromise.future, Inf)
      Try { UUID.fromString(wsMsg2) }.toOption shouldBe defined
    }

  }
}

object Fixture {

  def apply[T]()(f: String => T): T = {
    val ammonite = new EmbeddedAmmonite()
    ammonite.start()
    val ammServer = new WebsocketServer(ammonite)
    val server = io.undertow.Undertow.builder
      .addHttpListener(8081, "localhost")
      .setHandler(ammServer.defaultHandler)
      .build
    server.start()
    val res =
      try { f("http://localhost:8081") } finally {
        server.stop()
        ammonite.shutdown()
      }
    res
  }
}

package io.shiftleft.console.wsserver

import java.net.URLEncoder
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

    "allow connecting, posting a query, and fetching result" in Fixture() { host =>
      var wsPromise = scala.concurrent.Promise[String]
      cask.util.WsClient.connect(s"$host/connect") {
        case cask.Ws.Text(msg) => wsPromise.success(msg)
      }

      // Connect to websocket endpoint

      val wsMsg = Await.result(wsPromise.future, Inf)
      wsMsg shouldBe "connected"

      // Post a query and make sure to get back a uuid

      wsPromise = scala.concurrent.Promise[String]
      val postResponse = requests.post(s"$host/query", data = ujson.Obj("query" -> "1").toString)
      val jsonResponse = ujson.read(postResponse.contents)
      val uuidFromPost = Try { UUID.fromString(jsonResponse("uuid").str) }.toOption match {
        case Some(num) => num
        case None      => fail
      }
      jsonResponse("success").bool shouldBe true

      // Wait until receiving the same uuid on the websocket

      val wsMsg2 = Await.result(wsPromise.future, Inf)
      Try { UUID.fromString(wsMsg2) }.toOption match {
        case Some(num) => num shouldBe uuidFromPost
        case None      => fail
      }

      // Fetch result using get

      val uri = s"$host/result/${URLEncoder.encode(uuidFromPost.toString, "utf-8")}"
      val getResponse = requests.get(uri)
      val jsonGetResponse = ujson.read(getResponse.contents)
      jsonGetResponse("out").str shouldBe "res0: Int = 1\n"
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

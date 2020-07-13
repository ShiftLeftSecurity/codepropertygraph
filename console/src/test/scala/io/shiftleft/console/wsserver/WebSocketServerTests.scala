package io.shiftleft.console.wsserver

import java.net.URLEncoder
import java.util.UUID

import org.scalatest.{Matchers, WordSpec}

import scala.concurrent._
import castor.Context.Simple.global
import cask.util.Logger.Console._
import io.shiftleft.console.embammonite.EmbeddedAmmonite
import ujson.Value.Value

import scala.concurrent.duration._
import scala.util.Try

class WebSocketServerTests extends WordSpec with Matchers {

  def postQuery(host: String): Value = {
    val postResponse = requests.post(s"$host/query", data = ujson.Obj("query" -> "1").toString)
    ujson.read(postResponse.contents)
  }

  def getResponse(host: String, uuidParam: String): Value = {
    val uri = s"$host/result/${URLEncoder.encode(uuidParam, "utf-8")}"
    val getResponse = requests.get(uri)
    ujson.read(getResponse.contents)
  }

  "WebsocketServer" should {

    "allow connecting, posting a query, and fetching result" in Fixture() { host =>
      var wsPromise = scala.concurrent.Promise[String]
      cask.util.WsClient.connect(s"$host/connect") {
        case cask.Ws.Text(msg) => wsPromise.success(msg)
      }
      val wsMsg = Await.result(wsPromise.future, Duration(10, SECONDS))
      wsMsg shouldBe "connected"

      // Post a query and make sure to get back a uuid

      val jsonResponse = postQuery(host)
      val uuidFromPost = Try { UUID.fromString(jsonResponse("uuid").str) }.toOption match {
        case Some(num) => num
        case None      => fail
      }
      jsonResponse("success").bool shouldBe true

      // Wait until receiving the same uuid on the websocket

      wsPromise = scala.concurrent.Promise[String]
      val wsMsg2 = Await.result(wsPromise.future, Duration(100, SECONDS))
      Try { UUID.fromString(wsMsg2) }.toOption match {
        case Some(num) => num shouldBe uuidFromPost
        case None      => fail
      }

      val jsonGetResponse = getResponse(host, uuidFromPost.toString)
      jsonGetResponse("out").str shouldBe "res0: Int = 1\n"
    }
  }

  "receive error when attempting to retrieve result with invalid uuid" in Fixture() { host =>
    val wsPromise = scala.concurrent.Promise[String]
    cask.util.WsClient.connect(s"$host/connect") {
      case cask.Ws.Text(msg) => wsPromise.success(msg)
    }
    Await.result(wsPromise.future, Duration(100, SECONDS))
    val jsonGetResponse = getResponse(host, UUID.randomUUID().toString)
    jsonGetResponse("success").bool shouldBe false
  }

  "return a valid JSON response when calling /result with incorrectly-formatted UUID parameter" in Fixture() { host =>
    val wsPromise = scala.concurrent.Promise[String]
    cask.util.WsClient.connect(s"$host/connect") {
      case cask.Ws.Text(msg) => wsPromise.success(msg)
    }
    Await.result(wsPromise.future, Duration(100, SECONDS))
    val jsonGetResponse = getResponse(host, "INCORRECTLY_FORMATTED_UUID_PARAM")
    jsonGetResponse("success").bool shouldBe false
    jsonGetResponse("err").str.length should not equal(0)
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

package io.shiftleft.console.cpgqlserver

import java.net.URLEncoder
import java.util.UUID

import org.scalatest.{Matchers, WordSpec}

import scala.concurrent._
import castor.Context.Simple.global
import cask.util.Logger.Console._
import io.shiftleft.console.embammonite.EmbeddedAmmonite
import ujson.Value.Value

import scala.concurrent.duration._

class CPGQLServerTests extends WordSpec with Matchers {

  val DefaultPromiseAwaitTimeout = Duration(10, SECONDS)

  def postQuery(host: String, query: String): Value = {
    val postResponse = requests.post(s"$host/query", data = ujson.Obj("query" -> query).toString)
    ujson.read(postResponse.contents)
  }

  def getResponse(host: String, uuidParam: String): Value = {
    val uri = s"$host/result/${URLEncoder.encode(uuidParam, "utf-8")}"
    val getResponse = requests.get(uri)
    ujson.read(getResponse.contents)
  }

  "CPGQLServer" should {

    "allow websocket connections to the `/connect` endpoint" in Fixture() { host =>
      val webSocketTextMsg = scala.concurrent.Promise[String]
      cask.util.WsClient.connect(s"$host/connect") {
        case cask.Ws.Text(msg) => webSocketTextMsg.success(msg)
      }
      val wsMsg = Await.result(webSocketTextMsg.future, DefaultPromiseAwaitTimeout)
      wsMsg shouldBe "connected"
    }

    "allow posting a simple query without any websocket connections established" in Fixture() { host =>
      val postQueryResponse = postQuery(host, "1")
      postQueryResponse.obj.keySet should contain("success")
      val UUIDResponse = postQueryResponse("uuid").str
      UUIDResponse should not be empty
      postQueryResponse("success").bool shouldBe true
    }

    "return a valid JSON response when trying to retrieve the result of a query without a connection" in Fixture() {
      host =>
        val postQueryResponse = postQuery(host, "1")
        postQueryResponse.obj.keySet should contain("uuid")
        val UUIDResponse = postQueryResponse("uuid").str
        val getResultResponse = getResponse(host, UUIDResponse)
        getResultResponse.obj.keySet should contain("success")
        getResultResponse.obj.keySet should contain("stderr")
        getResultResponse("success").bool shouldBe false
        getResultResponse("stderr").str.length should not be (0)
    }

    "allow fetching the result of a completed query using its UUID" in Fixture() { host =>
      var webSocketTextMsg = scala.concurrent.Promise[String]
      cask.util.WsClient.connect(s"$host/connect") {
        case cask.Ws.Text(msg) => webSocketTextMsg.success(msg)
      }
      Await.result(webSocketTextMsg.future, DefaultPromiseAwaitTimeout)
      val postQueryResponse = postQuery(host, "1")
      val queryUUID = postQueryResponse("uuid").str
      queryUUID.length should not be (0)

      webSocketTextMsg = scala.concurrent.Promise[String]
      val queryResultWSMessage = Await.result(webSocketTextMsg.future, DefaultPromiseAwaitTimeout)
      queryResultWSMessage.length should not be (0)

      val getResultResponse = getResponse(host, queryUUID)
      getResultResponse.obj.keySet should contain("success")
      getResultResponse("uuid").str shouldBe queryResultWSMessage
      getResultResponse("stdout").str shouldBe "res0: Int = 1\n"
      getResultResponse("stderr").str shouldBe ""
    }

    "write a well-formatted message to a websocket connection when a query has finished evaluation" in Fixture() {
      host =>
        var webSocketTextMsg = scala.concurrent.Promise[String]
        cask.util.WsClient.connect(s"$host/connect") {
          case cask.Ws.Text(msg) => webSocketTextMsg.success(msg)
        }
        Await.result(webSocketTextMsg.future, DefaultPromiseAwaitTimeout)

        val postQueryResponse = postQuery(host, "1")
        val queryUUID = postQueryResponse("uuid").str
        queryUUID.length should not be (0)

        webSocketTextMsg = scala.concurrent.Promise[String]
        val queryResultWSMessage = Await.result(webSocketTextMsg.future, DefaultPromiseAwaitTimeout)
        queryResultWSMessage.length should not be (0)

        val getResultResponse = getResponse(host, queryUUID)
        getResultResponse.obj.keySet should contain("success")
        getResultResponse.obj.keySet should contain("stdout")
        getResultResponse.obj.keySet should contain("stderr")
        getResultResponse("uuid").str shouldBe queryResultWSMessage
        getResultResponse("stdout").str shouldBe "res0: Int = 1\n"
        getResultResponse("stderr").str shouldBe ""
    }

    "write a well-formatted message to a websocket connection when a query failed evaluation" in Fixture() { host =>
      var webSocketTextMsg = scala.concurrent.Promise[String]
      cask.util.WsClient.connect(s"$host/connect") {
        case cask.Ws.Text(msg) => webSocketTextMsg.success(msg)
      }
      Await.result(webSocketTextMsg.future, DefaultPromiseAwaitTimeout)

      val postQueryResponse = postQuery(host, "if else for loop soup // i.e., an invalid Ammonite query")
      val queryUUID = postQueryResponse("uuid").str
      queryUUID.length should not be (0)

      webSocketTextMsg = scala.concurrent.Promise[String]
      val queryResultWSMessage = Await.result(webSocketTextMsg.future, DefaultPromiseAwaitTimeout)
      queryResultWSMessage.length should not be (0)

      val getResultResponse = getResponse(host, queryUUID)
      getResultResponse.obj.keySet should contain("success")
      getResultResponse.obj.keySet should contain("stdout")
      getResultResponse.obj.keySet should contain("stderr")
      getResultResponse("success").bool shouldBe true
      getResultResponse("uuid").str shouldBe queryResultWSMessage
      getResultResponse("stdout").str shouldBe ""
      getResultResponse("stderr").str.length should not be(0)
    }
  }

  "receive error when attempting to retrieve result with invalid uuid" in Fixture() { host =>
    val webSocketTextMsg = scala.concurrent.Promise[String]
    cask.util.WsClient.connect(s"$host/connect") {
      case cask.Ws.Text(msg) => webSocketTextMsg.success(msg)
    }
    Await.result(webSocketTextMsg.future, Duration(100, SECONDS))
    val getResultResponse = getResponse(host, UUID.randomUUID().toString)
    getResultResponse.obj.keySet should contain("success")
    getResultResponse.obj.keySet should contain("stderr")
    getResultResponse("success").bool shouldBe false
  }

  "return a valid JSON response when calling /result with incorrectly-formatted UUID parameter" in Fixture() { host =>
    val webSocketTextMsg = scala.concurrent.Promise[String]
    cask.util.WsClient.connect(s"$host/connect") {
      case cask.Ws.Text(msg) => webSocketTextMsg.success(msg)
    }
    Await.result(webSocketTextMsg.future, Duration(100, SECONDS))
    val getResultResponse = getResponse(host, "INCORRECTLY_FORMATTED_UUID_PARAM")
    getResultResponse.obj.keySet should contain("success")
    getResultResponse.obj.keySet should contain("stderr")
    getResultResponse("success").bool shouldBe false
    getResultResponse("stderr").str.length should not equal (0)
  }
}

object Fixture {

  def apply[T]()(f: String => T): T = {
    val ammonite = new EmbeddedAmmonite()
    ammonite.start()

    val host = "localhost"
    val port = 8081
    val httpEndpoint = "http://" + host + ":" + port.toString()
    val ammServer = new CPGQLServer(ammonite, host, port)
    val server = io.undertow.Undertow.builder
      .addHttpListener(ammServer.port, ammServer.host)
      .setHandler(ammServer.defaultHandler)
      .build
    server.start()
    val res =
      try { f(httpEndpoint) } finally {
        server.stop()
        ammonite.shutdown()
      }
    res
  }
}

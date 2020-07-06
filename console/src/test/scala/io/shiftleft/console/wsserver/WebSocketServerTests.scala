package io.shiftleft.console.wsserver

import org.scalatest.{Matchers, WordSpec}
import scala.concurrent._, duration.Duration.Inf
import castor.Context.Simple.global, cask.util.Logger.Console._

class WebSocketServerTests extends WordSpec with Matchers {
  "WebsocketServer" should {
    "allow connecting via /connect" in Fixture(new WebsocketServer()) { host =>
      val wsPromise = scala.concurrent.Promise[String]
      cask.util.WsClient.connect(s"$host/connect") {
        case cask.Ws.Text(msg) => wsPromise.success(msg)
      }
      val wsMsg = Await.result(wsPromise.future, Inf)
      wsMsg shouldBe "connected"
    }
  }
}

object Fixture {

  def apply[T](example: cask.main.Main)(f: String => T): T = {
    val server = io.undertow.Undertow.builder
      .addHttpListener(8081, "localhost")
      .setHandler(example.defaultHandler)
      .build
    server.start()
    val res =
      try { f("http://localhost:8081") } finally server.stop()
    res
  }
}

package io.shiftleft.console.wsserver
import java.util.concurrent.atomic.AtomicInteger

import org.asynchttpclient.ws.{WebSocket, WebSocketListener, WebSocketUpgradeHandler}
import org.scalatest.{Matchers, WordSpec}

object Fixture {
  def apply[T](example: cask.main.Main)(f: String => T): T = {
    val server = io.undertow.Undertow.builder
      .addHttpListener(8081, "localhost")
      .setHandler(example.defaultHandler)
      .build
    server.start()
    val res =
      try f("http://localhost:8081")
      finally server.stop()
    res
  }
}

class WebSocketServerTests extends WordSpec with Matchers {
  "WebsocketServer" should {
    "allow connecting" in Fixture(new WebsocketServer()) { _ =>
      @volatile var out = List.empty[String]
      val closed = new AtomicInteger(0)
      val client = org.asynchttpclient.Dsl.asyncHttpClient()
      val ws = Seq.fill(2)(
        client
          .prepareGet("ws://localhost:8081/connect/haoyi")
          .execute(new WebSocketUpgradeHandler.Builder()
            .addWebSocketListener(new WebSocketListener() {
              override def onTextFrame(payload: String, finalFragment: Boolean, rsv: Int): Unit = {
                out.synchronized {
                  out = payload :: out
                }
              }
              def onOpen(websocket: WebSocket): Unit = ()

              def onClose(websocket: WebSocket, code: Int, reason: String): Unit = {
                closed.incrementAndGet()
              }
              def onError(t: Throwable): Unit = ()
            })
            .build())
          .get())
      try {
        ws.foreach(_.sendTextFrame("hello"))
        Thread.sleep(1500)
        out.length shouldBe 2

        ws.foreach(_.sendTextFrame("world"))

        Thread.sleep(1500)
        out.length shouldBe 4
        closed.get() shouldBe 0

        ws.foreach(_.sendTextFrame(""))

        Thread.sleep(1500)
        closed.get() shouldBe 2

      } finally {
        client.close()
      }
    }
  }
}

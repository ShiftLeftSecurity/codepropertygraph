package io.shiftleft.console.wsserver

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
  "foo" should {
    "bar" in Fixture(new WebsocketServer()) { _ =>
      }
  }
}

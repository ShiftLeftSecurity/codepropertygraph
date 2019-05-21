package io.shiftleft.cpgserver

object TestServerMain extends App {
  val port = 8080
  JettyLauncher.startServer(port)
}

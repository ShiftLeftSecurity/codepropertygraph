package io.shiftleft.cpgserver

object TestServerMain extends App {
  val port = 1337
  JettyLauncher.startServer(port)
}

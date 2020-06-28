package io.shiftleft.console.httpserver

import pureconfig.ConfigSource
import pureconfig.ConfigReader.Result
import pureconfig.generic.auto._

final case class ServerConfiguration(host: String, port: Int) {
  lazy val hostUrl = s"$host:$port"
}

object ServerConfiguration {
  lazy val config: Result[ServerConfiguration] = ConfigSource.default.load[ServerConfiguration]

  lazy val default: ServerConfiguration =
    ServerConfiguration(
      "127.0.0.1",
      8080
    )
}

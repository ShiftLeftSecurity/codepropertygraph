package io.shiftleft.cpgserver.config

import pureconfig.ConfigReader.Result
import pureconfig._
import pureconfig.generic.auto._

case class ServerConfiguration(host: String, port: Int) {
  lazy val hostUrl = s"$host:$port"
}

object ServerConfiguration {
  lazy val config: Result[ServerConfiguration] = ConfigSource.default.load[ServerConfiguration]
}

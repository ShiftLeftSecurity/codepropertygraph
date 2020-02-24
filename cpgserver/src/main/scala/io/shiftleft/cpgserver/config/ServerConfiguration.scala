package io.shiftleft.cpgserver.config

import pureconfig.ConfigReader.Result
import pureconfig._
import pureconfig.generic.auto._

final case class ServerConfiguration(host: String, port: Int, files: ServerFilesConfiguration) {
  lazy val hostUrl = s"$host:$port"
}

final case class ServerFilesConfiguration(uploadFileSizeLimit: Long, uploadCpgSizeLimit: Long)

object ServerConfiguration {
  lazy val config: Result[ServerConfiguration] = ConfigSource.default.load[ServerConfiguration]

  lazy val default: ServerConfiguration =
    ServerConfiguration(
      "127.0.0.1",
      8080,
      ServerFilesConfiguration(
        1024 * 1024 * 10, // 10MB
        1024 * 1024 * 200 // 200MB
      )
    )
}

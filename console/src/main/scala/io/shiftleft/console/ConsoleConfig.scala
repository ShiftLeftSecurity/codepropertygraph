package io.shiftleft.console

import better.files._

/**
  * Installation configuration of Console
  *
  * @param environment A map of system environment variables.
  * */
class InstallConfig(environment: Map[String, String] = sys.env) {
  var rootPath: File = environment
    .getOrElse("SHIFTLEFT_CONSOLE_INSTALL_DIR", ".")
    .toFile
}

object InstallConfig {
  def apply(): InstallConfig = new InstallConfig()
}

class ConsoleConfig(val install: InstallConfig = InstallConfig()) {

}

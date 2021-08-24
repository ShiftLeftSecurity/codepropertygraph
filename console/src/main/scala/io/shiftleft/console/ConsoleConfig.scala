package io.shiftleft.console

import better.files._

import scala.collection.mutable

/**
  * Installation configuration of Console
  *
  * @param environment A map of system environment variables.
  * */
class InstallConfig(environment: Map[String, String] = sys.env) {
  var rootPath: File = environment
    .getOrElse("SHIFTLEFT_OCULAR_INSTALL_DIR", ".")
    .toFile
}

object InstallConfig {
  def apply(): InstallConfig = new InstallConfig()
}

class ConsoleConfig(val install: InstallConfig = InstallConfig(),
                    val frontend: FrontendConfig = FrontendConfig(),
                    val tools: ToolsConfig = ToolsConfig()) {}

object ToolsConfig {
  def apply(): ToolsConfig = new ToolsConfig()
}

class ToolsConfig(var imageViewer: String = "xdg-open")

case class FrontendConfig(cmdLineParams: Iterable[String] = Nil) {
  def withAdditionalArgs(additionalArgs: Iterable[String]) = copy(cmdLineParams ++ additionalArgs)
}

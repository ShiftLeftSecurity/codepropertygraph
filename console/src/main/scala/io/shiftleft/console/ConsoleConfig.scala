package io.shiftleft.console

import better.files._

import scala.collection.mutable

/**
  * Installation configuration of Console
  *
  * @param environment A map of system environment variables.
  * */
class InstallConfig(environment: Map[String, String] = sys.env) {
  lazy val rootPath: File = {
    if (environment.contains("SHIFTLEFT_OCULAR_INSTALL_DIR")) {
      environment("SHIFTLEFT_OCULAR_INSTALL_DIR").toFile
    } else {
      val uriToLibDir = classOf[io.shiftleft.console.InstallConfig].getProtectionDomain.getCodeSource.getLocation.toURI
      val pathToLibDir = File(uriToLibDir)
      findRootDirectory(pathToLibDir).getOrElse(throw new AssertionError(
        s"""unable to find root installation directory
           | context: tried to find marker file `$rootDirectoryMarkerFilename`
           | started search in $pathToLibDir and searched $maxSearchDepth directories upwards""".stripMargin))
    }
  }

  private val rootDirectoryMarkerFilename = ".installation_root"
  private val maxSearchDepth = 10
  private def findRootDirectory(currentSearchDir: File, currentSearchDepth: Int = 0): Option[File] = {
    if (currentSearchDir.list.map(_.name).contains(rootDirectoryMarkerFilename))
      Some(currentSearchDir)
    else if (currentSearchDepth < maxSearchDepth && currentSearchDir.parentOption.isDefined)
      findRootDirectory(currentSearchDir.parent)
    else
      None
  }
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

class FrontendConfig(var cmdLineParams: Iterable[String] = mutable.Buffer()) {
  def withArgs(args: Iterable[String]): FrontendConfig = {
    new FrontendConfig(cmdLineParams ++ args)
  }
}

object FrontendConfig {
  def apply(): FrontendConfig = new FrontendConfig()
}

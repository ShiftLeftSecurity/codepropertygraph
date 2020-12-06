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
                    val frontend: LanguageFrontendConfig = LanguageFrontendConfig(),
                    val tools: ToolsConfig = ToolsConfig()) {}

object ToolsConfig {
  def apply(): ToolsConfig = new ToolsConfig()
}

class ToolsConfig(var imageViewer: String = "xdg-open")

object LanguageFrontendConfig {
  def apply(): LanguageFrontendConfig = new LanguageFrontendConfig()
}

class LanguageFrontendConfig(var csharp: CSharpFrontendConfig = CSharpFrontendConfig(),
                             var fuzzyc: FuzzyCFrontendConfig = FuzzyCFrontendConfig(),
                             var go: GoFrontendConfig = GoFrontendConfig(),
                             var java: JavaFrontendConfig = JavaFrontendConfig(),
                             var js: JsFrontendConfig = JsFrontendConfig(),
                             var llvm: LlvmFrontendConfig = LlvmFrontendConfig(),
                             var python: PythonFrontendConfig = PythonFrontendConfig())

class CSharpFrontendConfig(var cmdLineParams: Iterable[String] = mutable.Buffer())
class FuzzyCFrontendConfig(var cmdLineParams: Iterable[String] = mutable.Buffer())
class GoFrontendConfig(var cmdLineParams: Iterable[String] = mutable.Buffer())
class JavaFrontendConfig(var cmdLineParams: Iterable[String] = mutable.Buffer())
class JsFrontendConfig(var cmdLineParams: Iterable[String] = mutable.Buffer())
class LlvmFrontendConfig(var cmdLineParams: Iterable[String] = mutable.Buffer())
class PythonFrontendConfig(var cmdLineParams: Iterable[String] = mutable.Buffer())

object CSharpFrontendConfig {
  def apply(): CSharpFrontendConfig = new CSharpFrontendConfig()
}

object FuzzyCFrontendConfig {
  def apply(): FuzzyCFrontendConfig = new FuzzyCFrontendConfig()
}

object GoFrontendConfig {
  def apply(): GoFrontendConfig = new GoFrontendConfig()
}

object JavaFrontendConfig {
  def apply(): JavaFrontendConfig = new JavaFrontendConfig()
}

object JsFrontendConfig {
  def apply(): JsFrontendConfig = new JsFrontendConfig()
}

object LlvmFrontendConfig {
  def apply(): LlvmFrontendConfig = new LlvmFrontendConfig()
}

object PythonFrontendConfig {
  def apply(): PythonFrontendConfig = new PythonFrontendConfig()
}

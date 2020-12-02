package io.shiftleft.console

import java.nio.file.Path

import io.shiftleft.codepropertygraph.generated.Languages
import io.shiftleft.console.cpgcreation.{
  CSharpLanguageFrontend,
  FuzzyCLanguageFrontend,
  GoLanguageFrontend,
  JavaLanguageFrontend,
  JsLanguageFrontend,
  LanguageFrontend,
  LlvmLanguageFrontend,
  PythonLanguageFrontend
}

object LanguageHelper {

  /**
    * For a given language, return CPG generator script
    * */
  def cpgGeneratorForLanguage(language: String,
                              config: LanguageFrontendConfig,
                              rootPath: Path): Option[LanguageFrontend] = {
    language match {
      case Languages.CSHARP => Some(CSharpLanguageFrontend(config.csharp, rootPath))
      case Languages.C      => Some(FuzzyCLanguageFrontend(config.fuzzyc, rootPath))
      case Languages.LLVM   => Some(LlvmLanguageFrontend(config.llvm, rootPath))
      case Languages.GOLANG => Some(GoLanguageFrontend(config.go, rootPath))
      // fallback for existing Go CPG's
      case "GO"                 => Some(GoLanguageFrontend(config.go, rootPath))
      case Languages.JAVA       => Some(JavaLanguageFrontend(config.java, rootPath))
      case Languages.JAVASCRIPT => Some(JsLanguageFrontend(config.js, rootPath))
      case Languages.PYTHON     => Some(PythonLanguageFrontend(config.python, rootPath))
      case _                    => None
    }
  }

  def languageIsKnown(language: String): Boolean = {
    Set(Languages.C,
        Languages.CSHARP,
        Languages.GOLANG,
        "GO",
        Languages.JAVA,
        Languages.JAVASCRIPT,
        Languages.PYTHON,
        Languages.LLVM).contains(language)
  }

}

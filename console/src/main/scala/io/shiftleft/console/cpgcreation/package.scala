package io.shiftleft.console

import java.io.File
import java.nio.file.Path

import io.shiftleft.codepropertygraph.generated.Languages

package object cpgcreation {

  /**
    * For a given language, return CPG generator script
    * */
  def cpgGeneratorForLanguage(language: String,
                              config: LanguageFrontendConfig,
                              rootPath: Path): Option[CpgGenerator] = {
    language match {
      case Languages.CSHARP     => Some(CSharpCpgGenerator(config.csharp, rootPath))
      case Languages.C          => Some(FuzzyCCpgGenerator(config.fuzzyc, rootPath))
      case Languages.LLVM       => Some(LlvmCpgGenerator(config.llvm, rootPath))
      case Languages.GOLANG     => Some(GoCpgGenerator(config.go, rootPath))
      case Languages.JAVA       => Some(JavaCpgGenerator(config.java, rootPath))
      case Languages.JAVASCRIPT => Some(JsCpgGenerator(config.js, rootPath))
      case Languages.PYTHON     => Some(PythonCpgGenerator(config.python, rootPath))
      case _                    => None
    }
  }

  /**
    * Heuristically determines language by inspecting
    * file/dir at path.
    * */
  def guessLanguage(path: String): Option[String] = {
    val lowerCasePath = path.toLowerCase
    if (lowerCasePath.endsWith(".jar") ||
        lowerCasePath.endsWith(".war") ||
        lowerCasePath.endsWith(".ear") ||
        lowerCasePath.endsWith(".apk")) {
      Some(Languages.JAVA)
    } else if (lowerCasePath.endsWith("csproj")) {
      Some(Languages.CSHARP)
    } else if (lowerCasePath.endsWith(".go")) {
      Some(Languages.GOLANG)
    } else {
      val file = new File(path)
      if (file.isDirectory) {
        val files = file.list()
        if (files.exists(f =>
              f.endsWith(".go") || Set("Gopkg.lock", "Gopkg.toml", "go.mod", "go.sum")
                .contains(f))) {
          Some(Languages.GOLANG)
        } else if (files.exists(f => f.endsWith(".js") || Set("package.json").contains(f))) {
          Some(Languages.JAVASCRIPT)
        } else if (files.exists(f => isLlvmSrcFile(new File(f).toPath))) {
          Some(Languages.LLVM)
        } else {
          Some(Languages.C)
        }
      } else if (file.isFile) {
        if (isLlvmSrcFile(file.toPath)) {
          Some(Languages.LLVM)
        } else {
          None
        }
      } else {
        None
      }
    }
  }

  private def isLlvmSrcFile(path: Path): Boolean = {
    val filename = path.getFileName.toString
    filename.endsWith(".bc") || filename.endsWith(".ll")
  }

}

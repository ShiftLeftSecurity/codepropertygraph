package io.shiftleft.console

import io.shiftleft.codepropertygraph.generated.Languages

import java.io.File
import java.nio.file.Path

package object cpgcreation {

  /**
    * For a given language, return CPG generator script
    * */
  def cpgGeneratorForLanguage(language: String,
                              config: FrontendConfig,
                              rootPath: Path,
                              additionalArgs: List[String]): Option[CpgGenerator] = {
    language match {
      case Languages.CSHARP     => Some(CSharpCpgGenerator(config.withAdditionalArgs(additionalArgs), rootPath))
      case Languages.C          => Some(FuzzyCCpgGenerator(config.withAdditionalArgs(additionalArgs), rootPath))
      case Languages.LLVM       => Some(LlvmCpgGenerator(config.withAdditionalArgs(additionalArgs), rootPath))
      case Languages.GOLANG     => Some(GoCpgGenerator(config.withAdditionalArgs(additionalArgs), rootPath))
      case Languages.JAVA       => Some(JavaCpgGenerator(config.withAdditionalArgs(additionalArgs), rootPath))
      case Languages.JAVASCRIPT => Some(JsCpgGenerator(config.withAdditionalArgs(additionalArgs), rootPath))
      case Languages.PYTHON     => Some(PythonCpgGenerator(config.withAdditionalArgs(additionalArgs), rootPath))
      case Languages.PHP        => Some(PhpCpgGenerator(config.withAdditionalArgs(additionalArgs), rootPath))
      case Languages.GHIDRA     => Some(GhidraCpgGenerator(config.withAdditionalArgs(additionalArgs), rootPath))
      case Languages.NEWC       => Some(CCpgGenerator(config.withAdditionalArgs(additionalArgs), rootPath))
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
        } else if (files.exists(f => f.endsWith(".java") || f.endsWith(".class"))) {
          Some(Languages.JAVA)
        } else if (files.exists(f => f.endsWith(".php"))) {
          Some(Languages.PHP)
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

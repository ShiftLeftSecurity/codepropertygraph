package io.shiftleft.console.cpgcreation

import better.files.File
import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.Languages
import io.shiftleft.console.workspacehandling.Project
import overflowdb.traversal.help.Table

import scala.util.Try

class ImportCode[T <: Project](console: io.shiftleft.console.Console[T]) {

  import io.shiftleft.console.Console._

  def c: CFrontend = new CFrontend()
  def llvm: Frontend = new Frontend(Languages.LLVM, "LLVM Bitcode Frontend")
  def java: Frontend = new Frontend(Languages.JAVA, "Java/Dalvik Bytecode Frontend")
  def golang: Frontend = new Frontend(Languages.GOLANG, "Golang Source Frontend")
  def javascript: Frontend = new Frontend(Languages.JAVASCRIPT, "Javascript Source Frontend")
  def csharp: Frontend = new Frontend(Languages.CSHARP, "C# Source Frontend (Roslyn)")
  def python: Frontend = new Frontend(Languages.PYTHON, "Python Source Frontend")

  private val config = console.config
  private val workspace = console.workspace
  protected val generatorFactory = new CpgGeneratorFactory(config)

  private def allFrontends: List[Frontend] = List(
    c,
    csharp,
    golang,
    java,
    javascript,
    llvm,
    python
  )

  override def toString: String = {
    val cols = List("name", "description", "available")
    val rows = allFrontends.map { frontend =>
      List(frontend.language.toLowerCase, frontend.description, frontend.isAvailable.toString)
    }
    "Type `importCode.<language>` to run a specific language frontend\n" +
      "\n" + Table(cols, rows).render
  }

  class Frontend(val language: String, val description: String = "") {

    def isAvailable: Boolean = {
      cpgGeneratorForLanguage(language, config.frontend, config.install.rootPath.path).get.isAvailable
    }

    def apply(inputPath: String, projectName: String = "", namespaces: List[String] = List()): Option[Cpg] = {
      val frontend =
        cpgGeneratorForLanguage(language, config.frontend, config.install.rootPath.path)
      new ImportCode(console)(frontend.get, inputPath, projectName, namespaces)
    }
  }

  class CFrontend extends Frontend(Languages.C, "Fuzzy Parser for C/C++") {
    def fromString(str: String): Option[Cpg] = {
      val dir = File.newTemporaryDirectory("console")
      val result = Try {
        (dir / "tmp.c").write(str)
        apply(dir.path.toString)
      }.toOption.flatten
      dir.delete()
      result
    }
  }

  private def apply(frontend: CpgGenerator,
                    inputPath: String,
                    projectName: String,
                    namespaces: List[String]): Option[Cpg] = {
    val name =
      Option(projectName).filter(_.nonEmpty).getOrElse(deriveNameFromInputPath(inputPath, workspace))
    report(s"Creating project `$name` for code at `$inputPath`")
    val pathToProject = workspace.createProject(inputPath, name)
    val frontendCpgOutFileOpt = pathToProject.map(_.resolve(nameOfLegacyCpgInProject))

    if (frontendCpgOutFileOpt.isEmpty) {
      report(s"Error creating project for input path: `$inputPath`")
    }

    val result = frontendCpgOutFileOpt.flatMap { frontendCpgOutFile =>
      Some(frontend).flatMap { frontend =>
        generatorFactory
          .runGenerator(
            frontend,
            inputPath,
            frontendCpgOutFile.toString,
            namespaces
          )
          .flatMap(_ => console.open(name).flatMap(_.cpg))
          .map { c =>
            console.applyDefaultOverlays(c)
          }
      }
    }
    if (result.isDefined) {
      report(
        """|Code successfully imported. You can now query it using `cpg`.
           |For an overview of all imported code, type `workspace`.""".stripMargin
      )
    }
    result
  }

  /**
    * This is the `importCode(...)` method exposed on the console.
    * */
  def apply(
      inputPath: String,
      projectName: String = "",
      namespaces: List[String] = List(),
      language: String = ""
  ): Option[Cpg] = {

    var frontendOpt = generatorFactory.createGeneratorByLanguage(language)
    if (frontendOpt.isEmpty) {
      frontendOpt = generatorFactory.createGeneratordByPath(inputPath)
    }
    frontendOpt.flatMap { frontend =>
      apply(frontend, inputPath, projectName, namespaces)
    }
  }
}

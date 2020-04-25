package io.shiftleft.console

import better.files.Dsl.{cp, rm}
import better.files.File
import gremlin.scala.ScalaGraph
import io.shiftleft.SerializedCpg
import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.cpgloading.CpgLoader
import io.shiftleft.codepropertygraph.generated.Languages
import io.shiftleft.console.LanguageHelper.cpgGeneratorForLanguage
import io.shiftleft.console.cpgcreation.{CpgGenerator, LanguageFrontend}
import io.shiftleft.console.scripting.{AmmoniteExecutor, ScriptManager}
import io.shiftleft.console.workspacehandling.{Project, WorkspaceLoader, WorkspaceManager}
import io.shiftleft.overflowdb.traversal.help.{Doc, Table}
import io.shiftleft.semanticcpg.Overlays
import io.shiftleft.semanticcpg.layers.{LayerCreator, LayerCreatorContext, Scpg}
import io.shiftleft.semanticcpg.language._

import scala.util.Try

class Console[T <: Project](executor: AmmoniteExecutor, loader: WorkspaceLoader[T]) extends ScriptManager(executor) {

  private val _config = new ConsoleConfig()
  def config: ConsoleConfig = _config

  protected val workspacePathName: String = config.install.rootPath.path.resolve("workspace").toString
  protected val workspaceManager = new WorkspaceManager[T](workspacePathName, loader)
  private val nameOfCpgInProject = "cpg.bin"

  @Doc(
    "Access to the workspace directory",
    """
      |All auditing projects are stored in a workspace directory, and `workspace`
      |provides programmatic access to this directory. Entering `workspace` provides
      |a list of all projects, indicating which code the project makes accessible,
      |whether the project is open, and which analyzers have been run to produce it.
      |Multiple projects can be open at any given time, however, only one project
      |can be active. Queries and edit-operations are executed on the active project
      |only.
      |
      |Operations
      |
      |----------
      |
      |`workspace` provides low-level access to the workspace directory. In most cases,
      |it is a better idea to use higher-level operations such as `importCode`, `open`,
      |`close`, and `delete`, which make use of workspace operations internally.
      |
      |* workspace.open([name]): open project by name and make it the active project.
      | If `name` is omitted, the last project in the workspace list is opened. If
      | the project is already open, this has the same effect as `workspace.setActiveProject([name])`
      |
      |* workspace.close([name]): close project by name. Does not remove the project.
      |
      |* workspace.remove([name]): close and remove project by name.
      |
      |* workspace.reset: create a fresh workspace directory, deleting the current
      |workspace directory
      |
      |""".stripMargin,
    "workspace"
  )
  def workspace: WorkspaceManager[T] = workspaceManager

  @Doc("Currently active project", "", "project")
  def project: T =
    workspace.projectByCpg(cpg).getOrElse(throw new RuntimeException("No active project"))

  @Doc(
    "CPG of the active project",
    """
      |Upon importing code into Ocular, a project is created that holds
      |an intermediate representation called `Code Property Graph`. This
      |graph is a composition of low-level program representations such
      |as abstract syntax trees and control flow graphs, but it can be arbitrarily
      |extended to hold any information relevant in your audit, information
      |about HTTP entry points, IO routines, information flows, or locations
      |of vulnerable code. Think of Ocular as a CPG editor.
      |
      |In practice, `cpg` is the root object of the query language, that is, all
      |query language constructs can be invoked starting from `cpg`. For exanple,
      |`cpg.method.l` lists all methods, while `cpg.finding.l` lists all findings
      |of potentially vulnerable code.
      |""".stripMargin,
    "cpg.method.l"
  )
  def cpg: Cpg = workspace.cpg

  /**
    * All cpgs loaded in the workspace
    * */
  def cpgs: Iterator[Cpg] = {
    if (workspace.projects.lastOption.isEmpty) {
      Iterator()
    } else {
      val activeProjectName = project.name
      (workspace.projects.filter(_.cpg.isDefined).iterator.flatMap { project =>
        open(project.name)
        Some(project.cpg)
      } ++ Iterator({ open(activeProjectName); None })).flatten
    }
  }

  implicit def graph: ScalaGraph = cpg.scalaGraph

  @Doc(
    "Open project",
    """
      |open([projectName])
      |
      |Opens the project named `name` and make it the active project.
      |If `name` is not provided, the active project is opened. If `name`
      |is a path, the project name is derived from and a deprecation
      |warning is printed.
      |
      |Upon completion of this operation, the CPG stored in this project
      |can be queried via `cpg`. Returns an optional reference to the
      |project, which is empty on error.
      |""".stripMargin,
    """open("projectName")"""
  )
  def open(name: String): Option[Project] = {
    val projectName = fixProjectNameAndComplainOnFix(name)
    workspace.openProject(projectName).map { project =>
      project
    }
  }

  /**
    * Open the active project
    * */
  def open: Option[Project] = {
    workspace.projects.lastOption.flatMap { p =>
      open(p.name)
    }
  }

  // We still tie the project name to the input path here
  // if no project name has been provided.

  protected def fixProjectNameAndComplainOnFix(name: String): String = {
    val projectName = Some(name)
      .filter(_.contains(java.io.File.pathSeparator))
      .map(deriveNameFromInputPath)
      .getOrElse(name)
    if (name != projectName) {
      System.err.println("Passing paths to `loadCpg` is deprecated, please use a project name")
    }
    projectName
  }

  protected def deriveNameFromInputPath(inputPath: String): String = {
    val name = File(inputPath).name
    val project = workspace.project(name)
    if (project.isDefined && project.exists(_.inputPath != inputPath)) {
      var i = 1
      while (workspace.project(name + i).isDefined) {
        i += 1
      }
      name + i
    } else {
      name
    }
  }

  /**
    * Delete project from disk and remove it from
    * the workspace manager. Returns the (now invalid)
    * project.
    * @param name the name of the project
    * */
  @Doc("Close and remove project from disk", "", "delete(projectName)")
  def delete(name: String): Option[Unit] = {
    defaultProjectNameIfEmpty(name).flatMap(workspace.deleteProject)
  }

  /**
    * Delete the active project
    * */
  def delete: Option[Unit] = delete("")

  protected def defaultProjectNameIfEmpty(name: String): Option[String] = {
    if (name.isEmpty) {
      val projectNameOpt = workspace.projectByCpg(cpg).map(_.name)
      if (projectNameOpt.isEmpty) {
        report("Fatal: cannot find project for active CPG")
      }
      projectNameOpt
    } else {
      Some(fixProjectNameAndComplainOnFix(name))
    }
  }

  @Doc(
    "undo effects of analyzer",
    """|Undo the last change, that is, unapply the last
       |overlay applied to the active project.
       |""".stripMargin,
    "undo"
  )
  def undo: File = {
    project.overlayFiles.lastOption
      .map { file =>
        CpgLoader.addDiffGraphs(List(file.path.toString), cpg)
        Overlays.removeLastOverlayName(cpg)
        rm(file)
      }
      .getOrElse(throw new RuntimeException("No overlays present"))
  }

  @Doc(
    "Write all changes to disk",
    """
      |Close and reopen all loaded CPGs. This ensures
      |that changes have been flushed to disk.
      |
      |Returns list of affected projects
      |""".stripMargin,
    "save"
  )
  def save: List[Project] = {
    report("Saving graphs on disk. This may take a while.")
    workspace.projects.collect {
      case p: Project if p.cpg.isDefined =>
        p.close
        workspace.openProject(p.name)
    }.flatten
  }

  protected val cpgGenerator = new CpgGenerator(config)
  private val nameOfLegacyCpgInProject = "cpg.bin.zip"

  @Doc(
    "Create new project from code",
    """
      |importCode(<inputPath>, [projectName], [namespaces], [language])
      |
      |Import code at `inputPath` into Ocular. Creates a new project, generates a CPG,
      |and opens the project. Upon success, the CPG can be queried via the `cpg`
      |object. Default overlays are already applied to the newly created CPG.
      |Returns new CPG and ensures that `cpg` now refers to this new CPG.
      |
      |Parameters:
      |
      |-----------
      |
      |inputPath: location on disk of the code to analyze. e.g., a directory
      |containing source code or a Java archive (JAR).
      |
      |projectName: a unique name used for project management. If this parameter
      |is omitted, the name will be derived from `inputPath`
      |
      |namespaces: the whitelist of namespaces to analyse. Specifying this
      |parameter is only effective if the language frontend supports it.
      |If the list is omitted or empty, namespace selection is performed
      |automatically via heuristics.
      |
      |language: the programming language which the code at `inputPath` is written in.
      |If `language` is empty, the language used is guessed by inspecting
      |the filename found and possibly by looking into the file/directory.
      |
      |""".stripMargin,
    """importCode("example.jar")"""
  )
  def importCode = new ImportCode()

  class ImportCode {

    private def allFrontends: List[Frontend] = List(
      c,
      csharp,
      golang,
      jar,
      javascript,
      llvm,
    )

    override def toString: String = {
      val cols = List("name", "description", "available")
      val rows = allFrontends.map { frontend =>
        List(frontend.language, frontend.description, frontend.isAvailable.toString)
      }
      "\n" + Table(cols, rows).render
    }

    class Frontend(val language: String, val description: String = "") {

      def isAvailable: Boolean = {
        cpgGeneratorForLanguage(language, config.frontend, config.install.rootPath.path).get.isAvailable
      }

      def apply(inputPath: String, projectName: String = "", namespaces: List[String] = List()): Option[Cpg] = {
        val frontend =
          cpgGeneratorForLanguage(language, config.frontend, config.install.rootPath.path)
        new ImportCode()(frontend.get, inputPath, projectName, namespaces, defaultOverlays)
      }
    }

    def c: Frontend = new Frontend(Languages.C, "Fuzzy Parser for C/C++")
    def llvm: Frontend = new Frontend(Languages.LLVM, "LLVM Bitcode Frontend")
    def jar: Frontend = new Frontend(Languages.JAVA, "JVM/Dalvik Bytecode Frontend")
    def golang: Frontend = new Frontend(Languages.GOLANG, "Golang Source Frontend")
    def javascript: Frontend = new Frontend(Languages.JAVASCRIPT, "Javascript Source Frontend")
    def csharp: Frontend = new Frontend(Languages.CSHARP, "C# Source Frontend (Roslyn)")

    // TODO
    // def python: Frontend     = new Frontend(Languages.PYTHON)

    def apply(inputPath: String,
              projectName: String = "",
              namespaces: List[String] = List(),
              language: String = ""): Option[Cpg] = apply(
      inputPath,
      projectName,
      namespaces,
      defaultOverlays,
      language
    )

    private def apply(frontend: LanguageFrontend,
                      inputPath: String,
                      projectName: String,
                      namespaces: List[String],
                      overlayCreators: List[String]): Option[Cpg] = {
      val name =
        Option(projectName).filter(_.nonEmpty).getOrElse(deriveNameFromInputPath(inputPath))
      report(s"Creating project `$name` for code at `$inputPath`")
      val pathToProject = workspace.createProject(inputPath, name)
      val frontendCpgOutFileOpt = pathToProject.map(_.resolve(nameOfLegacyCpgInProject))

      if (frontendCpgOutFileOpt.isEmpty) {
        report(s"Error creating project for input path: `$inputPath`")
      }

      frontendCpgOutFileOpt.flatMap { frontendCpgOutFile =>
        Some(frontend).flatMap { frontend =>
          cpgGenerator
            .runLanguageFrontend(
              frontend,
              inputPath,
              frontendCpgOutFile.toString,
              namespaces
            )
            .flatMap(_ => open(name).flatMap(_.cpg))
            .map(_ => _runAnalyzer(overlayCreators.flatMap(overlayCreatorByName): _*))
        }
      }
    }

    def apply(
        inputPath: String,
        projectName: String,
        namespaces: List[String],
        overlayCreators: List[String],
        language: String
    ): Option[Cpg] = {

      var frontendOpt = cpgGenerator.createFrontendByLanguage(language)
      if (frontendOpt.isEmpty) {
        frontendOpt = cpgGenerator.createFrontendByPath(inputPath)
      }
      frontendOpt.flatMap { frontend =>
        apply(frontend, inputPath, projectName, namespaces, overlayCreators)
      }
    }
  }

  def defaultOverlays: List[String] = List(Scpg.overlayName)

  protected def overlayCreatorByName(name: String): Option[LayerCreator] = {
    if (name == Scpg.overlayName) {
      Some(new Scpg())
    } else {
      None
    }
  }

  @Doc(
    "Create new project from existing CPG",
    """
      |importCpg(<inputPath>, [projectName])
      |
      |Import an existing CPG into Ocular. The CPG is stored as part
      |of a new project and blanks are filled in by analyzing the CPG.
      |If we find that default overlays have not been applied, these
      |are applied to the CPG after loading it.
      |
      |Parameters:
      |
      |inputPath: path where the existing CPG (in overflowdb format)
      |is stored
      |
      |projectName: name of the new project. If this parameter
      |is omitted, the path is derived from `inputPath`
      |""".stripMargin,
    """importCpg("cpg.bin.zip")"""
  )
  def importCpg(inputPath: String, projectName: String = ""): Option[Cpg] = {
    val name = Option(projectName).filter(_.nonEmpty).getOrElse(deriveNameFromInputPath(inputPath))
    val cpgFile = File(inputPath)

    if (!cpgFile.exists) {
      report(s"CPG at $inputPath does not exist. Bailing out.")
      return None
    }

    System.err.println(s"Creating project `$name` for CPG at `$inputPath`")
    val pathToProject = workspace.createProject(inputPath, name)
    val cpgDestinationPathOpt = pathToProject.map(_.resolve(nameOfCpgInProject))

    if (cpgDestinationPathOpt.isEmpty) {
      report(s"Error creating project for input path: `$inputPath`")
      return None
    }

    val cpgDestinationPath = cpgDestinationPathOpt.get

    if (isZipFile(cpgFile)) {
      report("You have provided a legacy proto CPG. Attempting conversion.")
      try {
        CpgConverter.convertProtoCpgToOverflowDb(cpgFile.path.toString, cpgDestinationPath.toString)
      } catch {
        case exc: Exception =>
          report("Error converting legacy CPG: " + exc.getMessage)
          return None
      }
    } else {
      cp(cpgFile, cpgDestinationPath)
    }

    val cpgOpt = open(name).flatMap(_.cpg)

    if (cpgOpt.isEmpty) {
      workspace.deleteProject(name)
    }

    cpgOpt
      .filter(_.metaData.headOption().isDefined)
      .foreach(applyDefaultOverlays)

    cpgOpt
  }

  private def isZipFile(file: File): Boolean = {
    val bytes = file.bytes
    Try {
      bytes.next() == 'P' && bytes.next() == 'K'
    }.getOrElse(false)
  }
  @Doc(
    "Close project by name",
    """|Close project. Resources are freed but the project remains on disk.
       |The project remains active, that is, calling `cpg` now raises an
       |exception. A different project can now be activated using `open`.
       |""".stripMargin,
    "close(projectName)"
  )
  def close(name: String): Option[Project] = {
    defaultProjectNameIfEmpty(name).flatMap(workspace.closeProject)
  }

  def close: Option[Project] = close("")

  /**
    * Close the project and open it again.
    *
    * @param name the name of the project
    * */
  def reload(name: String): Option[Project] = {
    close(name).flatMap(p => open(p.name))
  }

  def applyDefaultOverlays(cpg: Cpg): Unit = {
    val appliedOverlays = io.shiftleft.semanticcpg.Overlays.appliedOverlays(cpg)
    if (appliedOverlays.isEmpty && !(new Scpg().probe(cpg))) {
      report("Adding default overlays to base CPG")
      val overlayCreators = List(new Scpg)
      _runAnalyzer(overlayCreators: _*)
    }
  }

  protected def report(string: String): Unit = System.err.println(string)

  // This is only public because we can't use the dynamically
  // created `run` in unit tests.
  def _runAnalyzer(overlayCreators: LayerCreator*): Cpg = {

    overlayCreators.foreach { creator =>
      val overlayOutFilename =
        workspace.getNextOverlayFilename(cpg, creator.overlayName)

      val projectOpt = workspace.projectByCpg(cpg)
      if (projectOpt.isEmpty) {
        throw new RuntimeException("No record for CPG. Please use `importCode`/`importCpg/open`")
      }

      if (projectOpt.get.appliedOverlays.contains(creator.overlayName)) {
        report(s"Overlay ${creator.overlayName} already exists - skipping")
      } else {
        val serializedCpg = new SerializedCpg(overlayOutFilename)
        runCreator(creator, serializedCpg)
        serializedCpg.close()
      }
    }
    report(
      "The graph has been modified. You may want to use the `save` command to persist changes to disk. Ocular will also auto-save all changes collectively when you exit")
    cpg
  }

  protected def runCreator(creator: LayerCreator, serializedCpg: SerializedCpg): Unit = {
    val context = new LayerCreatorContext(cpg, serializedCpg)
    creator.run(context, serializeInverse = true)
  }

}

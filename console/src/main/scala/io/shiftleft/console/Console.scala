package io.shiftleft.console

import better.files.Dsl.{cp, rm}
import better.files.File
import gremlin.scala.ScalaGraph
import io.shiftleft.SerializedCpg
import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.cpgloading.CpgLoader
import io.shiftleft.console.scripting.{AmmoniteExecutor, ScriptManager}
import io.shiftleft.console.workspacehandling.{Project, WorkspaceLoader, WorkspaceManager}
import io.shiftleft.semanticcpg.Overlays
import io.shiftleft.semanticcpg.layers.LayerCreator
import io.shiftleft.semanticcpg.language._

import scala.util.Try

abstract class Console[T <: Project](executor: AmmoniteExecutor, loader: WorkspaceLoader[T])
    extends ScriptManager(executor) {

  def config: ConsoleConfig

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

  def applyDefaultOverlays(cpg: Cpg): Unit

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
    cpg
  }

  protected def runCreator(creator: LayerCreator, serializedCpg: SerializedCpg): Unit

}

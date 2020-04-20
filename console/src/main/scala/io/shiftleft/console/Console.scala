package io.shiftleft.console

import better.files.Dsl.rm
import better.files.File
import gremlin.scala.ScalaGraph
import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.cpgloading.CpgLoader
import io.shiftleft.console.scripting.{AmmoniteExecutor, ScriptManager}
import io.shiftleft.console.workspacehandling.{Project, WorkspaceLoader, WorkspaceManager}
import io.shiftleft.semanticcpg.Overlays
import io.shiftleft.semanticcpg.layers.LayerCreator

abstract class Console(executor: AmmoniteExecutor, loader : WorkspaceLoader) extends ScriptManager(executor) {

  def _runAnalyzer(overlayCreators: LayerCreator*): Cpg

  def config : ConsoleConfig

  protected val workspacePathName: String = config.install.rootPath.path.resolve("workspace").toString
  protected val workspaceManager        = new WorkspaceManager(workspacePathName, loader)

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
  def workspace: WorkspaceManager      = workspaceManager

  @Doc("Currently active project", "", "project")
  def project: Project =
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
    val name    = File(inputPath).name
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
  def undo: Unit = {
    project.overlayFiles.lastOption
      .map { file =>
        CpgLoader.addDiffGraphs(List(file.path.toString), cpg)
        Overlays.removeLastOverlayName(cpg)
        rm(file)
      }
      .getOrElse(throw new RuntimeException("No overlays present"))
  }

  protected def report(string: String): Unit = System.err.println(string)

}

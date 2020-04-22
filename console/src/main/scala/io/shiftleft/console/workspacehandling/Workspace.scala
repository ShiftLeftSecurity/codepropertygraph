package io.shiftleft.console.workspacehandling

import io.shiftleft.semanticcpg.utils.Table

import scala.collection.mutable.ListBuffer

/**
  * Create a workspace from a list of projects. Workspace is
  * a passive object that is managed by WorkspaceManager
  * @param projects list of projects present in this workspace
  * */
class Workspace[ProjectType <: Project](var projects: ListBuffer[ProjectType]) {

  /**
    * Returns total number of projects in this workspace
    * */
  def numberOfProjects: Int = projects.size

  /**
    * Provide a human-readable overview of the workspace
    * */
  override def toString: String = {
    if (projects.isEmpty) {
      "empty"
    } else {
      "\n" + Table(
        columnNames = List("name", "overlays", "inputPath", "open"),
        rows = projects.map(_.toString).toList
      ).render
    }

  }

}

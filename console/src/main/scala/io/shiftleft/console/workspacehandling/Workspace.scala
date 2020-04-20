package io.shiftleft.console.workspacehandling

import io.shiftleft.console.Table

import scala.collection.mutable.ListBuffer

/**
  * Create a workspace from a list of projects. Workspace is
  * a passive object that is managed by WorkspaceManager
  * @param projects list of projects present in this workspace
  * */
class Workspace(var projects: ListBuffer[Project]) {

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
      val rows = projects.map(_.toString).toList
      val columnNames = List("name", "overlays", "inputPath", "open")
      Table.create(columnNames, rows)
    }

  }

}

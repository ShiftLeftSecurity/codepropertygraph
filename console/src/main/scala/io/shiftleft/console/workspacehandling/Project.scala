package io.shiftleft.console.workspacehandling

import java.nio.file.Path

import better.files.File
import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.semanticcpg.Overlays

case class ProjectFile(inputPath: String, name: String)

/**
  * @param path absolute path to directory holding the project
  * @param cpg reference to loaded CPG or None, if the CPG is not loaded
  * */
case class Project(projectFile: ProjectFile, var path: Path, var cpg: Option[Cpg] = None) {

  def name: String = projectFile.name

  def inputPath: String = projectFile.inputPath

  def appliedOverlays: List[String] = {
    cpg.map(Overlays.appliedOverlays).getOrElse(List())
  }

  def overlayFiles: List[File] = {
    val overlayDir = File(path.resolve("overlays"))
    appliedOverlays.map(o => overlayDir / o)
  }

  override def toString: String = {
    val cpgLoaded = cpg.isDefined
    val overlays = appliedOverlays
      .mkString(",")
    val inputPath = projectFile.inputPath
    s"$name\t$overlays\t$inputPath\t$cpgLoaded"
  }

  /**
    * Close project if it is open and do nothing otherwise.
    * */
  def close: Project = {
    cpg.foreach(_.close)
    cpg = None
    this
  }

}

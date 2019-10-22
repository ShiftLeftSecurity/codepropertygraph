package io.shiftleft.console

import better.files._
import io.circe.generic.auto._, io.circe.parser._

object ScriptManager {

  final case class ScriptDescription(name: String, description: String)

}

abstract class ScriptManager(executor: ScriptExecutor) {

  import ScriptManager._

  protected val DEFAULT_SCRIPTS_FOLDER: File = File("scripts")

  private val SCRIPT_DESC: String = "description.json"

  private def folders() = DEFAULT_SCRIPTS_FOLDER.children.filter(_.isDirectory)

  private def scriptFileContent(file: File): String = file.lines.mkString(System.lineSeparator())

  def scripts(): List[ScriptDescription] =
    folders().flatMap { folder =>
      decode[ScriptDescription](scriptFileContent(folder / SCRIPT_DESC)).toOption
    }.toList

  def runScript(name: String): Object =
    executor.run(scriptFileContent(DEFAULT_SCRIPTS_FOLDER / name / s"$name.scala"))

  def runScriptT[T <: AnyRef](name: String): T = {
    runScript(name).asInstanceOf[T]
  }

}

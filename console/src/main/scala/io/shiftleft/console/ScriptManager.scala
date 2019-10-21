package io.shiftleft.console

import java.io.FileInputStream
import better.files._
import play.api.libs.json.{Json, Reads}

abstract class ScriptManager(executor: ScriptExecutor) {

  protected val DEFAULT_SCRIPTS_FOLDER: String = "scripts/"

  private val SCRIPT_DESC: String = "description.json"

  case class ScriptDescription(name: String, description: String)

  private implicit val scriptDescriptionRead: Reads[ScriptDescription] = Json.reads[ScriptDescription]

  private def folders() = File(DEFAULT_SCRIPTS_FOLDER).children.filter(_.isDirectory)

  def scripts(): List[ScriptDescription] = {
    val allScriptDescriptions = folders().map { folder =>
      Json.parse(new FileInputStream(folder.pathAsString + "/" + SCRIPT_DESC)).as[ScriptDescription]
    }
    allScriptDescriptions.toList
  }

  def runScript(name: String): Object = {
    val script = File(s"$DEFAULT_SCRIPTS_FOLDER$name/$name.scala")
    val content = script.lines.mkString("\n")
    executor.run(content)
  }

}

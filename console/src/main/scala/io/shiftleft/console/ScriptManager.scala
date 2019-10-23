package io.shiftleft.console

import better.files._
import cats.effect.IO
import io.circe.generic.auto._
import io.circe.parser._
import io.shiftleft.codepropertygraph.cpgloading.CpgLoader
import io.shiftleft.console.query.{CpgOperationFailure, CpgOperationSuccess, CpgQueryExecutor}

object ScriptManager {

  final case class ScriptDescription(name: String, description: String)

}

abstract class ScriptManager(executor: CpgQueryExecutor[AnyRef]) {

  import ScriptManager._

  protected val DEFAULT_SCRIPTS_FOLDER: File = File("scripts")

  private val SCRIPT_DESC: String = "description.json"

  protected val DEFAULT_CPG_NAME: String = "cpg.bin.zip"

  private def folders(): Iterator[File] = DEFAULT_SCRIPTS_FOLDER.children.filter(_.isDirectory)

  private def scriptFileContent(file: File): String = file.lines.mkString(System.lineSeparator())

  def scripts(): List[ScriptDescription] =
    folders().flatMap { folder =>
      decode[ScriptDescription](scriptFileContent(folder / SCRIPT_DESC)).toOption
    }.toList

  def runScript(name: String): AnyRef = {
    val scriptExecutionResult = for {
      queryResult <- executor.executeQuerySync(CpgLoader.load(DEFAULT_CPG_NAME),
                                               scriptFileContent(DEFAULT_SCRIPTS_FOLDER / name / s"$name.scala"))
      result <- queryResult match {
        case CpgOperationSuccess(result) => IO(result)
        case CpgOperationFailure(ex)     => IO.raiseError[AnyRef](ex)
      }
    } yield result
    scriptExecutionResult.handleErrorWith(IO(_)).unsafeRunSync()
  }

}

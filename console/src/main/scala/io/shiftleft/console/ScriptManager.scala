package io.shiftleft.console

import better.files._
import cats.effect.IO
import io.circe.generic.auto._
import io.circe.parser._

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.cpgloading.CpgLoader
import io.shiftleft.console.query.{CpgOperationFailure, CpgOperationResult, CpgOperationSuccess, CpgQueryExecutor}

import java.nio.file.{FileSystems, Paths}

object ScriptManager {

  final case class ScriptDescriptions(scripts: List[ScriptDescription])
  final case class ScriptDescription(name: String, description: String)
}

abstract class ScriptManager(executor: CpgQueryExecutor[AnyRef]) {

  import ScriptManager._

  protected lazy val DEFAULT_SCRIPTS_FOLDER: File = {
    import scala.collection.JavaConverters.mapAsJavaMapConverter

    val scriptsPath = getClass.getClassLoader.getResource("scripts").toURI
    if (scriptsPath.getScheme.contains("jar")) {
      FileSystems.newFileSystem(scriptsPath, Map("create" -> "false").asJava)
    }

    File(Paths.get(scriptsPath))
  }

  private val SCRIPT_DESCS: String = "scripts.json"

  private def scriptContent(file: File): String = file.lines.mkString(System.lineSeparator())

  def scripts(): List[ScriptDescription] =
    decode[ScriptDescriptions] {
      (DEFAULT_SCRIPTS_FOLDER / SCRIPT_DESCS).lines.mkString(System.lineSeparator())
    }.toOption.map(_.scripts).getOrElse(List.empty)

  private def handleQueryResult(result: IO[CpgOperationResult[AnyRef]]): AnyRef = {
    val scriptExecutionResult = for {
      queryResult <- result
      result <- queryResult match {
        case CpgOperationSuccess(result) => IO(result)
        case CpgOperationFailure(ex)     => IO.raiseError(ex)
      }
    } yield result
    scriptExecutionResult.handleErrorWith(IO(_)).unsafeRunSync()
  }

  def runScript(name: String, cpgFilename: String): AnyRef =
    handleQueryResult(
      executor.executeQuerySync(CpgLoader.load(cpgFilename), scriptContent(DEFAULT_SCRIPTS_FOLDER / s"$name.sc")))

  def runScript(name: String, cpg: Cpg): AnyRef =
    handleQueryResult(executor.executeQuerySync(cpg, scriptContent(DEFAULT_SCRIPTS_FOLDER / s"$name.sc")))

}

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
  final case class ScriptCollections(collection: String, scripts: ScriptDescriptions)
  final case class ScriptDescriptions(description: String, scripts: List[ScriptDescription])
  final case class ScriptDescription(name: String, description: String)
}

/**
  * This class manages a hierarchy of scripts, and provides an interface
  * that allows users to easily discover and run scripts on their CPGs.
  *
  * Scripts should be grouped inside folders placed within the application's
  * `resource` directory, for example:
  *
  *  resources
  *    |-- java
  *        |-- my-java-script.sc
  *    |-- go
  *    |-- csharp
  *
  * To run `my-java-script.sc` you would run:
  * `runScript("java/my-java-script", cpg)`
  *
  * All scripts *must* have the `.sc` extension to be picked up by the script manager.
  *
  * @param executor A CPG executor that is used to run the managed scripts.
  */
abstract class ScriptManager(executor: CpgQueryExecutor[AnyRef]) {

  import ScriptManager._

  protected lazy val DEFAULT_SCRIPTS_FOLDER: File = {
    import scala.jdk.CollectionConverters._

    val scriptsPath = this.getClass.getClassLoader.getResource("scripts").toURI
    if (scriptsPath.getScheme.contains("jar")) {
      FileSystems.newFileSystem(scriptsPath, Map("create" -> "false").asJava)
    }

    File(Paths.get(scriptsPath))
  }

  private val SCRIPT_DESCS: String = "scripts.json"

  private def scriptContent(file: File): String = file.lines.mkString(System.lineSeparator())

  def scripts(): List[ScriptCollections] = {
    DEFAULT_SCRIPTS_FOLDER
      .collectChildren(_.isDirectory)
      .collect {
        case dir if dir != DEFAULT_SCRIPTS_FOLDER =>
          val relativeDir = DEFAULT_SCRIPTS_FOLDER.relativize(dir)

          val scriptDescs = decode[ScriptDescriptions] {
            (dir / SCRIPT_DESCS).lines.mkString(System.lineSeparator())
          }.toOption.getOrElse(ScriptDescriptions("", List.empty))

          ScriptCollections(relativeDir.toString, scriptDescs)
      }
      .toList
  }

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

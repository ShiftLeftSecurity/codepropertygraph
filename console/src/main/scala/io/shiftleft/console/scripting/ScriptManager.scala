package io.shiftleft.console.scripting

import better.files._
import cats.effect.IO
import io.circe.generic.auto._
import io.circe.parser._
import org.zeroturnaround.zip.{NameMapper, ZipUtil}

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.cpgloading.CpgLoader

import java.nio.file.{Files, NoSuchFileException}

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
  * `resources/scripts` directory, for example:
  *
  *  resources
  *    |-- scripts
  *       |-- java
  *          |-- my-java-script.sc
  *       |-- go
  *       |-- csharp
  *
  * To run `my-java-script.sc` you would run:
  * `runScript("java/my-java-script", cpg)`
  *
  * Scripts *must* end with the `.sc` extension to be picked up by the script
  * manager.
  *
  * @param executor An executor that is used to run the managed scripts.
  */
abstract class ScriptManager(executor: AmmoniteExecutor) {

  import ScriptManager._

  private val absoluteJarPathRegex = """jar:file:(.*)!/scripts""".r
  private val scriptFileRegex = """(scripts/.*)""".r
  private val scriptDir = "scripts"

  // This is to work around Ammonite failing to read resource files on the classpath.
  // We simply copy the files into a temporary directory and read from there.
  private lazy val scriptsTempDir: File = {
    val newScriptsDir = File(Files.createTempDirectory("sl_scripts"))

    val scriptsPath = this.getClass.getClassLoader.getResource(scriptDir).toURI
    if (scriptsPath.getScheme.contains("jar")) {
      // get absolute jar path from classpath URI
      scriptsPath.toString match {
        case absoluteJarPathRegex(jarPath) =>
          ZipUtil.unpack(
            new java.io.File(jarPath),
            newScriptsDir.toJava,
            new NameMapper {
              override def map(name: String): String = name match {
                case scriptFileRegex(scriptFile) => scriptFile
                case _                           => null
              }
            }
          )
      }
    } else {
      File(scriptsPath).copyToDirectory(newScriptsDir)
    }

    newScriptsDir / scriptDir
  }

  private val SCRIPT_DESCS: String = "scripts.json"

  def scripts(): List[ScriptCollections] = {
    scriptsTempDir
      .collectChildren(f => f.isDirectory && f != scriptsTempDir)
      .map { dir =>
        val relativeDir = scriptsTempDir.relativize(dir)

        val scriptDescs = decode[ScriptDescriptions] {
          (dir / SCRIPT_DESCS).lines.mkString(System.lineSeparator())
        }.toOption.getOrElse(ScriptDescriptions("", List.empty))

        ScriptCollections(relativeDir.toString, scriptDescs)
      }
      .toList
  }

  protected def withScriptFile[T](scriptName: String)(f: File => IO[T]): IO[T] = {
    val scriptPath = scriptsTempDir / s"$scriptName.sc"
    if (scriptPath.exists) {
      f(scriptPath)
    } else {
      IO.raiseError(new NoSuchFileException(s"Script [$scriptPath] was not found."))
    }
  }

  def runScript(scriptName: String, parameters: Map[String, String], cpgFileName: String): Any = {
    runScript(scriptName, parameters, CpgLoader.load(cpgFileName))
  }

  def runScript(scriptName: String, parameters: Map[String, String], cpg: Cpg): Any = {
    withScriptFile(scriptName) { script =>
      executor.runScript(script.path, parameters, cpg)
    }.unsafeRunSync()
  }
}

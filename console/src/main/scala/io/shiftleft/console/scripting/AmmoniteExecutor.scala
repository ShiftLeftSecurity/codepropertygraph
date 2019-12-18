package io.shiftleft.console.scripting

import ammonite.runtime.Storage
import ammonite.util.Res
import cats.effect.IO
import cats.instances.list._
import cats.syntax.traverse._

import io.shiftleft.codepropertygraph.Cpg

import java.nio.file.Path

/**
  * Provides an interface for the execution of scripts using the
  * Ammonite interpreter.
  *
  * All scripts are compiled in-memory and no caching is performed.
  */
trait AmmoniteExecutor {
  protected def predef: String

  private lazy val ammoniteMain = ammonite.Main(predefCode = predef,
    remoteLogging = false,
    verboseOutput = false,
    welcomeBanner = None,
    storageBackend = Storage.InMemory())

  /**
    * Runs the given script, passing any defined parameters in addition to bringing the target CPG
    * in to scope.
    *
    * @param scriptPath A path pointing to the Ammonite script to be executed.
    * @param parameters A map of parameters to be passed to the script, useful if you have a @main method in the script.
    * @param cpg        The CPG made implicitly available in the script.
    * @return The result of running the script.
    */
  def runScript(scriptPath: Path, parameters: Map[String, String], cpg: Cpg): IO[Any] = {
    for {
      replInstance <- IO(ammoniteMain.instantiateRepl(IndexedSeq("cpg" -> cpg)))
      repl <- IO.fromEither(replInstance.left.map { case (err, _) => new RuntimeException(err.msg) })
      ammoniteResult <- IO {
        repl.interp.initializePredef()
        ammonite.main.Scripts.runScript(ammoniteMain.wd,
          os.Path(scriptPath),
          repl.interp,
          parameters.map { case (k, v) => k -> Some(v) }.toSeq)
      }
      result <- ammoniteResult match {
        case Res.Success(res) => IO.pure(res)
        case Res.Exception(ex, _) => IO.raiseError(ex)
        case Res.Failure(msg) => IO.raiseError(new RuntimeException(msg))
        case _ => IO.pure(())
      }
    } yield result
  }

  /**
    * Runs multiple scripts in the order they are specified in `scriptPaths`.
    *
    * @param scriptPaths A list of paths pointing to Ammonite scripts to be executed.
    * @param parameters  A map from script path to a set of parameter key/values.
    *                    If no entry is found for a script, an empty set of params
    *                    will be passed to the interpreter.
    * @param cpg         The CPG made implicitly available in the script.
    * @return A list containing the results of running each script, in order.
    */
  def runScripts(scriptPaths: List[Path], parameters: Map[Path, Map[String, String]], cpg: Cpg): IO[List[Any]] = {
    scriptPaths.map { scriptPath =>
      val scriptParams = parameters.getOrElse(scriptPath, Map.empty)
      runScript(scriptPath, scriptParams, cpg)
    }.sequence
  }
}

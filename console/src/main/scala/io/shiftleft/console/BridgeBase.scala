package io.shiftleft.console

import ammonite.ops.pwd
import ammonite.ops.Path
import ammonite.util.{Colors, Res}
import better.files.FileOps
import better.files.File

case class Config(
    scriptFile: Option[Path] = None,
    params: Map[String, String] = Map.empty,
    additionalImports: List[Path] = Nil,
    colors: Option[Colors] = None
)

/**
  * Base class for Ammonite Bridge. Nothing to see here, move along.
  * */
trait BridgeBase {

  protected def parseConfig(args: Array[String]): Config = {
    implicit def pathRead: scopt.Read[Path] =
      scopt.Read.stringRead
        .map(Path(_, pwd)) //support both relative and absolute paths

    val parser = new scopt.OptionParser[Config]("scopt") {
      override def errorOnUnknownArgument = false

      head("cpg scripting")

      opt[Path]("script")
        .action((x, c) => c.copy(scriptFile = Some(x)))
        .text("path to script file: will execute and exit")

      opt[Map[String, String]]('p', "params")
        .valueName("k1=v1,k2=v2")
        .action((x, c) => c.copy(params = x))
        .text("key values for script")

      opt[Seq[Path]]("import")
        .valueName("script1.sc,script2.sc,...")
        .action((x, c) => c.copy(additionalImports = x.toList))
        .text("import additional additional script(s): will execute and keep console open")

      opt[Unit]("nocolors")
        .action((x, c) => c.copy(colors = Some(Colors.BlackWhite)))
        .text("turn off colors")
    }

    // note: if config is really `None` an error message would have been displayed earlier
    parser.parse(args, Config()).get
  }

  protected def runAmmonite(config: Config): Unit = {
    val additionalImportCode: List[String] =
      config.additionalImports.flatMap { importScript =>
        val file = importScript.toIO
        assert(file.canRead, s"unable to read $file")
        readScript(file.toScala)
      }

    config.scriptFile match {
      case None =>
        val replConfig = List(
          "repl.prompt() = \"" + promptStr() + "\"",
          "repl.pprinter() = repl.pprinter().copy(defaultHeight = 99999)",
          "banner()"
        )
        ammonite
          .Main(
            predefCode = predefPlus(additionalImportCode ++ replConfig ++ shutdownHooks),
            welcomeBanner = Some("Welcome to ShiftLeft Ocular/Joern"),
            storageBackend = new StorageBackend,
            remoteLogging = false,
            colors = config.colors.getOrElse(Colors.Default)
          )
          .run()

      case Some(scriptFile) =>
        val isEncryptedScript = scriptFile.ext == "enc"
        println(s"executing $scriptFile with params=${config.params}")
        val scriptArgs: Seq[(String, Option[String])] = config.params.mapValues(Option.apply).toSeq
        val actualScriptFile =
          if (isEncryptedScript) decryptedScript(scriptFile)
          else scriptFile
        ammonite
          .Main(
            predefCode = predefPlus(additionalImportCode ++ shutdownHooks),
            remoteLogging = false,
            colors = config.colors.getOrElse(Colors.Default)
          )
          .runScript(actualScriptFile, scriptArgs)
          ._1 match {
          case Res.Success(r) =>
            println(s"script finished successfully")
            println(r)
          case Res.Failure(msg) =>
            throw new AssertionError(s"script failed: $msg")
          case Res.Exception(e, msg) =>
            throw new AssertionError(s"script errored: $msg", e)
          case _ => ???
        }
        /* minimizing exposure time by deleting the decrypted script straight away */
        if (isEncryptedScript) actualScriptFile.toIO.delete
    }
  }

  /**
    * Override this method to implement script decryption
    * */
  protected def decryptedScript(scriptFile: Path): Path = {
    scriptFile
  }

  private def readScript(scriptFile: File): List[String] = {
    val code = scriptFile.lines.toList
    println(s"importing $scriptFile (${code.size} lines)")
    code
  }

  protected def predefPlus(lines: List[String]): String

  protected def shutdownHooks: List[String]

  protected def promptStr(): String

}

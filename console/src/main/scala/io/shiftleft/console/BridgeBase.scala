package io.shiftleft.console

import ammonite.ops.pwd
import ammonite.ops.Path
import ammonite.util.{Colors, Res}
import better.files._
import io.shiftleft.console.embammonite.EmbeddedAmmonite
import io.shiftleft.console.cpgqlserver.CPGQLServer

case class Config(
    scriptFile: Option[Path] = None,
    params: Map[String, String] = Map.empty,
    additionalImports: List[Path] = Nil,
    nocolors: Boolean = false,
    server: Boolean = false,
    serverHost: String = "localhost",
    serverPort: Int = 8080,
    command: Option[String] = None
)

/**
  * Base class for Ammonite Bridge. Nothing to see here, move along.
  * */
trait BridgeBase {

  protected def parseConfig(args: Array[String]): Config = {
    implicit def pathRead: scopt.Read[Path] =
      scopt.Read.stringRead
        .map(Path(_, pwd)) //support both relative and absolute paths

    val parser = new scopt.OptionParser[Config]("(joern|ocular)") {
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
        .action((_, c) => c.copy(nocolors = true))
        .text("turn off colors")

      opt[Unit]("server")
        .action((_, c) => c.copy(server = true))
        .text("run as HTTP server")

      opt[String]("server-host")
        .action((x, c) => c.copy(serverHost = x))
        .text("Hostname on which to expose the CPGQL server")

      opt[Int]("server-port")
        .action((x, c) => c.copy(serverPort = x))
        .text("Port on which to expose the CPGQL server")

      opt[String]("command")
        .action((x, c) => c.copy(command = Some(x)))
        .text("select one of multiple @main methods")

      help("help")
    }

    // note: if config is really `None` an error message would have been displayed earlier
    parser.parse(args, Config()).get
  }

  protected def runAmmonite(config: Config, slProduct: SLProduct = OcularProduct): Unit = {
    config.scriptFile match {
      case None =>
        if (config.server) {
          startHttpServer(config)
        } else {
          startInteractiveShell(config, slProduct)
        }
      case Some(scriptFile) =>
        runScript(scriptFile, config)
    }
  }

  private def startInteractiveShell(config: Config, slProduct: SLProduct) = {
    val configurePPrinterMaybe =
      if (config.nocolors) ""
      else """val originalPPrinter = repl.pprinter()
             |repl.pprinter.update(io.shiftleft.console.pprinter.create(originalPPrinter))
             |""".stripMargin

    val replConfig = List(
      "repl.prompt() = \"" + promptStr() + "\"",
      configurePPrinterMaybe,
      "banner()"
    )
    ammonite
      .Main(
        predefCode = predefPlus(additionalImportCode(config) ++ replConfig ++ shutdownHooks),
        welcomeBanner = None,
        storageBackend = new StorageBackend(slProduct),
        remoteLogging = false,
        colors = ammoniteColors(config)
      )
      .run()
  }

  private def startHttpServer(config: Config): Unit = {
    val predef = predefPlus(additionalImportCode(config))
    val ammonite = new EmbeddedAmmonite(predef)
    ammonite.start()
    Runtime.getRuntime.addShutdownHook(new Thread(() => {
      ammonite.shutdown()
    }))
    val server = new CPGQLServer(ammonite, config.serverHost, config.serverPort)
    server.main(Array.empty)
  }

  private def runScript(scriptFile: Path, config: Config) = {
    val isEncryptedScript = scriptFile.ext == "enc"
    println(s"executing $scriptFile with params=${config.params}")
    val scriptArgs: Seq[(String, Option[String])] = {
      val commandArgs = config.command match {
        case Some(command) => Seq(command -> None)
        case _             => Nil
      }
      commandArgs ++ config.params.view.mapValues(Option.apply).toSeq
    }
    val actualScriptFile =
      if (isEncryptedScript) decryptedScript(scriptFile)
      else scriptFile
    ammonite
      .Main(
        predefCode = predefPlus(additionalImportCode(config) ++ shutdownHooks),
        remoteLogging = false,
        colors = ammoniteColors(config)
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

  private def additionalImportCode(config: Config): List[String] =
    config.additionalImports.flatMap { importScript =>
      val file = importScript.toIO
      assert(file.canRead, s"unable to read $file")
      readScript(file.toScala)
    }

  private def ammoniteColors(config: Config) =
    if (config.nocolors) Colors.BlackWhite
    else Colors.Default

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

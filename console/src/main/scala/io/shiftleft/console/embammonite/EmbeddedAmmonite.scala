package io.shiftleft.console.embammonite

import java.io.{BufferedReader, InputStreamReader, PipedInputStream, PipedOutputStream, PrintWriter}
import java.util.UUID
import java.util.concurrent.{BlockingQueue, ConcurrentHashMap, LinkedBlockingQueue, Semaphore}

import ammonite.util.Colors
import org.slf4j.LoggerFactory

case class Job(uuid: UUID, query: String, observer: Result => Unit)
class Result(val out: String, val err: String, val uuid: UUID)

class EmbeddedAmmonite(predef: String = "") {

  private val logger = LoggerFactory.getLogger(this.getClass)

  // The standard frontend attempts to query /dev/tty
  // in multiple places, e.g., to query terminal dimensions.
  // This does not work in intellij tests
  // (see https://github.com/lihaoyi/Ammonite/issues/276)
  // The below hack overrides the default frontend with
  // a custom frontend that does not require /dev/tty.
  // This also enables us to disable terminal echo
  // by passing a `displayTransform` that returns
  // an empty string on all input.

  private val embeddedAmmonitePredef =
    """
      | class CustomFrontend extends ammonite.repl.AmmoniteFrontEnd() {
      |   override def width = 100
      |   override def height = 100
      |
      |  override def readLine(reader: java.io.Reader,
      |                        output: java.io.OutputStream,
      |                        prompt: String,
      |                        colors: ammonite.util.Colors,
      |                        compilerComplete: (Int, String) => (Int, Seq[String], Seq[String]),
      |                        history: IndexedSeq[String]) = {
      |
      |  val writer = new java.io.OutputStreamWriter(output)
      |
      | val multilineFilter = ammonite.terminal.Filter.action(
      |   ammonite.terminal.SpecialKeys.NewLine,
      |   ti => ammonite.interp.Parsers.split(ti.ts.buffer.mkString).isEmpty
      | ){
      |   case ammonite.terminal.TermState(rest, b, c, _) => ammonite.terminal.filters.BasicFilters.injectNewLine(b, c, rest)
      | }
      |
      |  val allFilters = ammonite.terminal.Filter.merge(extraFilters, multilineFilter, ammonite.terminal.filters.BasicFilters.all)
      |
      |  new ammonite.terminal.LineReader(width, prompt, reader, writer, allFilters, displayTransform = { (x: Vector[Char], i: Int) => (fansi.Str(""), i) } )
      |  .readChar(ammonite.terminal.TermState(ammonite.terminal.LazyList.continually(reader.read()), Vector.empty, 0, ""), 0)
      | }
      |}
      | repl.frontEnd() = new CustomFrontend()
      |
      |""".stripMargin

  val jobQueue: BlockingQueue[Job] = new LinkedBlockingQueue[Job]()
  val jobMap = new ConcurrentHashMap[UUID, Job]()
  val results = new ConcurrentHashMap[UUID, Result]

  val toStdin = new PipedOutputStream()
  val inStream = new PipedInputStream()
  inStream.connect(toStdin)
  val outStream = new PipedOutputStream()
  val fromStdout = new PipedInputStream()
  fromStdout.connect(outStream)
  val errStream = new PipedOutputStream()
  val fromStderr = new PipedInputStream()
  fromStderr.connect(errStream)

  val writer = new PrintWriter(toStdin)
  val reader = new BufferedReader(new InputStreamReader(fromStdout))
  val errReader = new BufferedReader(new InputStreamReader(fromStderr))

  val writerThread = new Thread(new WriterRunnable(jobQueue, writer, jobMap))
  val readerThread = new Thread(new ReaderRunnable(reader, errReader, jobMap))

  val shellThread = new Thread(() => {
    val ammoniteShell =
      ammonite
        .Main(
          predefCode = embeddedAmmonitePredef + predef,
          welcomeBanner = None,
          remoteLogging = false,
          colors = Colors.BlackWhite,
          inputStream = inStream,
          outputStream = outStream,
          errorStream = errStream
        )
    ammoniteShell.run()
  })

  def start(): Unit = {
    shellThread.start()
    writerThread.start()
    readerThread.start()
  }

  /**
    * Ask for query to be run. Returns a query
    * id (given by a uuid) and eventually calls
    * observer.
    * */
  def queryAsync(q: String)(observer: Result => Unit): UUID = {
    val uuid = UUID.randomUUID()
    jobQueue.add(Job(uuid, q, observer))
    uuid
  }

  def query(q: String): String = {
    val mutex = new Semaphore(0)
    var stdoutResult = ""
    queryAsync(q) { result =>
      stdoutResult = result.out
      mutex.release()
    }
    mutex.acquire()
    stdoutResult
  }

  def shutdown(): Unit = {
    shutdownWriterThread()
    shutdownShellThread()
    logger.info("Shell terminated gracefully")
    outStream.close()
    readerThread.join()

    def shutdownWriterThread(): Unit = {
      jobQueue.add(Job(null, null, null))
      writerThread.join()
    }
    def shutdownShellThread(): Unit = {
      writer.println("exit")
      writer.close()
      shellThread.join()
    }
  }

}

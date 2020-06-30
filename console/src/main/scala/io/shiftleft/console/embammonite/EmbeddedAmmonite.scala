package io.shiftleft.console.embammonite

import java.io.{BufferedReader, InputStreamReader, PipedInputStream, PipedOutputStream, PrintWriter}
import java.util.UUID
import java.util.concurrent.{BlockingQueue, ConcurrentHashMap, LinkedBlockingQueue}

import ammonite.util.Colors

case class Job(uuid: UUID, query: String)
case class InProgress() extends Result
case class Succeeded(out: String, err: String) extends Result
case class Failed(out: String, err: String) extends Result
class Result()

class EmbeddedAmmonite(predef: String = "") {

  val jobQueue: BlockingQueue[Job] = new LinkedBlockingQueue[Job]()
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

  val shellThread = new Thread(() => {
    // The standard frontend attempts to query /dev/tty
    // for terminal dimensions, which fails when running
    // tests in intellij and in container environments
    // (see https://github.com/lihaoyi/Ammonite/issues/276)
    // Since terminal dimensions do not matter to us, we
    // just set them manually.
    val completePredef: String =
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
      |  // Enter
      | val multilineFilter = ammonite.terminal.Filter.action(
      | ammonite.terminal.SpecialKeys.NewLine,
      | ti => ammonite.interp.Parsers.split(ti.ts.buffer.mkString).isEmpty
      | ){
      | case ammonite.terminal.TermState(rest, b, c, _) => ammonite.terminal.filters.BasicFilters.injectNewLine(b, c, rest)
      |}
      |
      |  val allFilters = ammonite.terminal.filters.BasicFilters.all
      |
      |  val res = new ammonite.terminal.LineReader(width, prompt, reader, writer, allFilters)
      |  .readChar(ammonite.terminal.TermState(ammonite.terminal.LazyList.continually(reader.read()), Vector.empty, 0, ""), 0)
      |  res
      | }
      |}
      | repl.frontEnd() = new CustomFrontend()
      |
      |""".stripMargin + predef

    val ammoniteShell =
      ammonite
        .Main(
          predefCode = completePredef,
          welcomeBanner = None,
          remoteLogging = false,
          colors = Colors.BlackWhite,
          inputStream = inStream,
          // outputStream = outStream,
          // errorStream = errStream
        )
    ammoniteShell.run()
  })

  class UserRunnable(queue: BlockingQueue[Job]) extends Runnable {
    override def run(): Unit = {
      try {
        var terminate = false;
        while (!terminate && !queue.isEmpty) {
          val job = queue.take()
          if (job.uuid == null && job.query == null) {
            terminate = true
          } else {
            println(job.query.trim)
            writer.println(job.query.trim)
            writer.flush()
          }
        }
      } catch {
        case _: InterruptedException =>
          println("Interrupted AmmoniteUserThread")
      }
      println("AmmoniteUserThread exited gracefully")
    }
  }

  val userRunnable = new UserRunnable(jobQueue)
  val userThread = new Thread(userRunnable)

  def start(): Unit = {
    shellThread.start()
    userThread.start()
  }

  def enqueue(uuid: UUID, query: String): Unit = {
    jobQueue.add(Job(uuid, query))
  }

  def result(uuid: UUID): Option[Result] = {
    Option(results.get(uuid))
  }

  def shutdown(): Unit = {
    shutdownShell()
    // Terminate user thread
    jobQueue.add(Job(null, null))
    shellThread.join()
    userThread.join()
    println("Shell terminated gracefully")
  }

  def shutdownShell(): Unit = {
    writer.println("exit")
    writer.close()
    reader.close()
  }

}

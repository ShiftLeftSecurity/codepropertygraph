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

  val writerThread = new Thread(new WriterRunnable(jobQueue, writer))
  val readerThread = new Thread(new ReaderRunnable(reader))

  val shellThread = new Thread(() => {
    val ammoniteShell =
      ammonite
        .Main(
          predefCode = predef,
          welcomeBanner = None,
          remoteLogging = false,
          colors = Colors.BlackWhite,
          inputStream = inStream,
          outputStream = outStream,
          // errorStream = errStream
        )
    ammoniteShell.run()
  })

  def start(): Unit = {
    shellThread.start()
    writerThread.start()
    readerThread.start()
  }

  def enqueue(uuid: UUID, query: String): Unit = {
    jobQueue.add(Job(uuid, query))
  }

  def result(uuid: UUID): Option[Result] = {
    Option(results.get(uuid))
  }

  def shutdown(): Unit = {
    shutdownWriterThread()
    shutdownShellThread()
    println("Shell terminated gracefully")
    outStream.close()
    readerThread.join()

    def shutdownWriterThread(): Unit = {
      jobQueue.add(Job(null, null))
      writerThread.join()
    }
    def shutdownShellThread(): Unit = {
      writer.println("exit")
      writer.close()
      shellThread.join()
    }
  }

}

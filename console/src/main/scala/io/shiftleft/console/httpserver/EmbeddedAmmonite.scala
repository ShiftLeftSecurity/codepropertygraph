package io.shiftleft.console.httpserver

import java.io.{PipedInputStream, PipedOutputStream, PrintWriter}
import java.util.UUID
import java.util.concurrent.{BlockingQueue, LinkedBlockingQueue}

import ammonite.util.Colors

class EmbeddedAmmonite(predef: String) {

  val queue: BlockingQueue[Job] = new LinkedBlockingQueue[Job]()

  val toStdin = new PipedOutputStream()
  val inStream = new PipedInputStream()
  inStream.connect(toStdin)

  val outStream = new PipedOutputStream()
  val fromStdout = new PipedInputStream()
  fromStdout.connect(outStream)

  val errStream = new PipedOutputStream()
  val fromStderr = new PipedInputStream()
  fromStderr.connect(errStream)

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
          errorStream = errStream
        )
    ammoniteShell.run()
  })

  val userThread = new Thread(new AmmoniteUserThread(queue))

  def start(): Unit = {
    shellThread.start()
    userThread.start()
  }

  def shutdown(): Unit = {
    val writer = new PrintWriter(toStdin)
    // Send an `exit` to ammonite and wait
    // until it terminates. This will restore
    // terminal settings
    writer.println("exit")
    writer.close()
    // Terminate user thread
    queue.add(new Job(null, null))
    shellThread.join()
    userThread.join()
    println("Shell terminated gracefully")
  }

}

case class Job(uuid: UUID, query: String)

class AmmoniteUserThread(queue: BlockingQueue[Job]) extends Runnable {
  override def run(): Unit = {
    try {
      var terminate = false;
      while (!terminate) {
        val job = queue.take()
        if (job.uuid == null && job.query == null) {
          terminate = true
        } else {
          // carry out job
        }
      }
    } catch {
      case _: InterruptedException =>
        println("Interrupted AmmoniteUserThread")
    }
    println("Shutting down AmmoniteUserThread")
  }
}

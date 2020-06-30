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
    val ammoniteShell =
      ammonite
        .Main(
          predefCode = predef,
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

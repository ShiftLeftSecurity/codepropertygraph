package io.shiftleft.console.embammonite

import java.io.PrintWriter
import java.util.concurrent.BlockingQueue

class WriterRunnable(queue: BlockingQueue[Job], writer: PrintWriter) extends Runnable {
  override def run(): Unit = {
    try {
      var terminate = false;
      while (!(terminate && queue.isEmpty)) {
        val job = queue.take()
        if (job.uuid == null && job.query == null) {
          terminate = true
        } else {
          writer.println(job.query.trim)
          writer.println(s"""println("${job.uuid}")""")
          writer.flush()
        }
      }
    } catch {
      case _: InterruptedException =>
        println("Interrupted WriterThread")
    }
    println("WriterThread terminated gracefully")
  }
}

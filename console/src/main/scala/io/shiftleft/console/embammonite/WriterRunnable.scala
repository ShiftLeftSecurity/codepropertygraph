package io.shiftleft.console.embammonite

import java.io.PrintWriter
import java.util.UUID
import java.util.concurrent.{BlockingQueue, ConcurrentHashMap}

import org.slf4j.LoggerFactory

class WriterRunnable(queue: BlockingQueue[Job], writer: PrintWriter, jobMap: ConcurrentHashMap[UUID, Job])
    extends Runnable {

  private val logger = LoggerFactory.getLogger(this.getClass)

  override def run(): Unit = {
    try {
      var terminate = false;
      while (!(terminate && queue.isEmpty)) {
        val job = queue.take()
        if (job.uuid == null && job.query == null) {
          terminate = true
        } else {
          jobMap.put(job.uuid, job)
          writer.println(job.query.trim)
          writer.println(s""""END: ${job.uuid}"""")
          writer.println(s"""throw new RuntimeException("END: ${job.uuid}")""")
          writer.flush()
        }
      }
    } catch {
      case _: InterruptedException =>
        logger.info("Interrupted WriterThread")
    }
    logger.info("WriterThread terminated gracefully")
  }
}

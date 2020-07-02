package io.shiftleft.console.embammonite

import java.io.{BufferedReader, IOException}
import java.util.UUID
import java.util.concurrent.ConcurrentHashMap

import org.slf4j.LoggerFactory

import scala.util.Try

class ReaderRunnable(reader: BufferedReader, jobMap: ConcurrentHashMap[UUID, Job]) extends Runnable {

  private val logger = LoggerFactory.getLogger(this.getClass)

  private val magicEchoSeq: Seq[Char] = List(27, 91, 57, 57, 57, 57, 68, 27, 91, 48, 74, 64, 32).map(_.toChar)
  private val endMarker = """.*END: ([0-9a-f\-]+)""".r

  override def run(): Unit = {
    try {
      logger.info("Starting reader")
      var currentOutput = ""
      var line = reader.readLine()
      while (line != null) {
        if (!line.startsWith(magicEchoSeq) && !line.isEmpty) {
          val uuid = endMarker.findAllIn(line).matchData.flatMap { m =>
            Try { UUID.fromString(m.group(1)) }.toOption
          }
          if (uuid.isEmpty) {
            currentOutput += line + "\n"
          } else {
            val actualUuid = uuid.next
            Option(jobMap.get(actualUuid)).foreach { job =>
              job.observer(new Result(currentOutput, "", actualUuid))
            }
            currentOutput = ""
          }
        }
        line = reader.readLine()
      }
      logger.info("ReaderThread terminated gracefully")
    } catch {
      case exc: IOException =>
        logger.info("Exception in reader. Terminating: " + exc)
    }
  }
}

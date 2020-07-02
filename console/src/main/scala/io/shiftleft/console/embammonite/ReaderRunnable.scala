package io.shiftleft.console.embammonite

import java.io.{BufferedReader, IOException}
import java.util.UUID
import java.util.concurrent.ConcurrentHashMap

import org.slf4j.LoggerFactory

import scala.util.Try

class ReaderRunnable(reader: BufferedReader, errReader: BufferedReader, jobMap: ConcurrentHashMap[UUID, Job])
    extends Runnable {

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
          val uuid = uuidFromLine(line)
          if (uuid.isEmpty) {
            currentOutput += line + "\n"
          } else {
            val errOutput = exhaustStderr()
            val actualUuid = uuid.next
            Option(jobMap.get(actualUuid)).foreach { job =>
              job.observer(new Result(currentOutput, errOutput, actualUuid))
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

  private def uuidFromLine(line: String): Iterator[UUID] = {
    endMarker.findAllIn(line).matchData.flatMap { m =>
      Try { UUID.fromString(m.group(1)) }.toOption
    }
  }

  private def exhaustStderr(): String = {
    var currentOutput = ""
    var line = errReader.readLine()
    while (line != null) {
      val uuid = uuidFromLine(line)
      if (uuid.isEmpty) {
        currentOutput += line
      } else {
        return currentOutput
      }
      line = errReader.readLine()
    }
    currentOutput
  }

}

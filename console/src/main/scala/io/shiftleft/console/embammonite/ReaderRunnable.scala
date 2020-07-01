package io.shiftleft.console.embammonite

import java.io.{BufferedReader, IOException}
import java.util.UUID

import scala.util.Try

class ReaderRunnable(reader: BufferedReader) extends Runnable {

  val magicEchoSeq: Seq[Char] = List(27, 91, 57, 57, 57, 57, 68, 27, 91, 48, 74, 64, 32).map(_.toChar)
  val endMarker = """.*END: ([0-9a-f\-]+)""".r

  override def run(): Unit = {
    try {
      println("Starting reader")
      var line = reader.readLine()
      while (line != null) {
        if (!line.startsWith(magicEchoSeq) && !line.isEmpty) {
          println("line: " + line)
          endMarker.findAllIn(line).matchData.foreach { m =>
            Try { UUID.fromString(m.group(1)) }.toOption.foreach {
              println(_)
            }
          }
        }
        line = reader.readLine()
      }
      println("ReaderThread terminated gracefully")
    } catch {
      case exc: IOException =>
        println("Exception in reader. Terminating: " + exc)
    }
  }
}

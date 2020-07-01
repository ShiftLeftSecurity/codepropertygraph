package io.shiftleft.console.embammonite

import java.io.{BufferedReader, IOException}

class ReaderRunnable(reader: BufferedReader) extends Runnable {

  override def run(): Unit = {
    var line = reader.readLine()
    try {
      while (line != null) {
        println(line)
        line = reader.readLine()
      }
      println("ReaderThread terminated gracefully")
    } catch {
      case exc: IOException =>
        println("Exception in reader. Terminating: " + exc)
    }
  }
}

package io.shiftleft.argdefloader

import java.nio.file.{Files, Paths}

import org.apache.logging.log4j.LogManager

import scala.io.Source

case class ArgumentDef(methodFullName: String, parameterIndex: Int)
case class ArgumentDefs(elements: List[ArgumentDef])

class ArgumentDefLoader(filename: String) {
  private val logger = LogManager.getLogger(getClass)

  def load(): ArgumentDefs = {
    val bufferedReader = Source.fromFile(filename)
    var lineNumber = 0

    try {
      val argumentDefElements =
        bufferedReader
          .getLines()
          .flatMap { line =>
            val parts = line.split(",")

            if (parts.size == 2) {
              try {
                val methodFullName = parts(0).trim
                val parameterIndex = parts(1).trim.toInt
                lineNumber += 1
                Some(ArgumentDef(methodFullName, parameterIndex))
              } catch {
                case _: NumberFormatException =>
                  logFormatError("Argument index is not convertable to Int.", lineNumber)
                  None
              }

            } else {
              logFormatError("Invalid number of elements per line. Expected method name followed by argument index.",
                             lineNumber)
              None
            }
          }
          .toList

      ArgumentDefs(argumentDefElements)
    } finally {
      bufferedReader.close()
    }

  }

  private def logFormatError(msg: String, lineNumber: Int): Unit = {
    logger.warn(s"$msg In $filename on line $lineNumber")
  }

}

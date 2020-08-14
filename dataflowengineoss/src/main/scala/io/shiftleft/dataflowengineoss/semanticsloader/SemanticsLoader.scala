package io.shiftleft.dataflowengineoss.semanticsloader

import org.slf4j.Logger
import org.slf4j.LoggerFactory

import scala.io.Source

case class Semantic(methodFullName: String, parameterIndex: Int)
case class Semantics(elements: List[Semantic])

object SemanticsLoader {
  private val logger: Logger = LoggerFactory.getLogger(classOf[SemanticsLoader])

  def emptySemantics: Semantics = {
    Semantics(Nil)
  }
}

class SemanticsLoader(filename: String) {
  import SemanticsLoader.logger

  def load(): Semantics = {
    val bufferedReader = Source.fromFile(filename)
    var lineNumber = 0

    try {
      val semanticElements =
        bufferedReader
          .getLines()
          .flatMap { line =>
            val parts = line.split(",")

            if (parts.size == 2) {
              try {
                val methodFullName = parts(0).trim
                val parameterIndex = parts(1).trim.toInt
                lineNumber += 1
                Some(Semantic(methodFullName, parameterIndex))
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

      Semantics(semanticElements)
    } finally {
      bufferedReader.close()
    }

  }

  private def logFormatError(msg: String, lineNumber: Int): Unit = {
    logger.warn(s"$msg In $filename on line $lineNumber")
  }

}

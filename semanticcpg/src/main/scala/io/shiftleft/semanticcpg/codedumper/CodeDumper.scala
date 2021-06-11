package io.shiftleft.semanticcpg.codedumper

import better.files.File
import io.shiftleft.codepropertygraph.generated.nodes.{Expression, Method, NewLocation}
import io.shiftleft.codepropertygraph.generated.Languages
import io.shiftleft.semanticcpg.language._
import org.slf4j.{Logger, LoggerFactory}

import scala.util.Try

object CodeDumper {

  private val logger: Logger = LoggerFactory.getLogger(getClass)

  val arrow: CharSequence = "/* <=== */ "

  /**
    * Dump string representation of code at given `location`.
    * */
  def dump(location: NewLocation, language: Option[String], highlight: Boolean): String = {
    val filename = location.filename

    if (location.node.isEmpty) {
      logger.warn("Empty `location.node` encountered")
      return ""
    }

    val node = location.node.get
    if (language.isEmpty || !Set(Languages.C).contains(language.get)) {
      logger.info("dump not supported for this language or language not set in CPG")
      return ""
    }

    val method = node match {
      case n: Method     => Some(n)
      case n: Expression => Some(n.method)
      case _                   => None
    }

    val lineToHighlight = location.lineNumber
    method
      .collect {
        case m: Method if m.lineNumber.isDefined && m.lineNumberEnd.isDefined =>
          val rawCode = code(filename, m.lineNumber.get, m.lineNumberEnd.get, lineToHighlight)
          if (highlight) {
            SourceHighlighter.highlight(Source(rawCode, language.get))
          } else {
            Some(rawCode)
          }
      }
      .flatten
      .getOrElse("")
  }

  /**
    * For a given `filename`, `startLine`, and `endLine`, return the corresponding code
    * by reading it from the file. If `lineToHighlight` is defined, then a line containing
    * an arrow (as a source code comment) is included right before that line.
    * */
  def code(filename: String, startLine: Integer, endLine: Integer, lineToHighlight: Option[Integer] = None): String = {
    val lines = Try(File(filename).lines.toList).getOrElse {
      logger.warn("error reading from: " + filename);
      List()
    }
    lines
      .slice(startLine - 1, endLine)
      .zipWithIndex
      .map {
        case (line, lineNo) =>
          if (lineToHighlight.isDefined && lineNo == lineToHighlight.get - startLine) {
            line + " " + arrow
          } else {
            line
          }
      }
      .mkString("\n")
  }

}

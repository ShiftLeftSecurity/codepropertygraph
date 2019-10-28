package io.shiftleft.semanticcpg.codedumper

import io.shiftleft.codepropertygraph.generated.{NodeTypes, nodes}
import io.shiftleft.semanticcpg.language._
import io.shiftleft.semanticcpg.language.NodeSteps
import better.files._
import org.apache.logging.log4j.LogManager

import scala.util.Try

object CodeDumper {

  private val logger = LogManager.getLogger(CodeDumper)
  val arrow: CharSequence = "// ===>\n"

  /**
    * Evaluate the `step` and determine associated locations.
    * Dump code at those locations
    * */
  def dump[NodeType <: nodes.StoredNode](step: NodeSteps[NodeType]): String =
    step.location.l.map(dump).mkString("\n")

  /**
    * Dump string representation of code at given `location`.
    * */
  def dump(location: nodes.NewLocation): String = {
    val filename = location.filename

    if (location.node.isEmpty) {
      logger.warn("Empty `location.node` encountered")
      return ""
    }

    val node = location.node.get
    val method = node match {
      case n: nodes.Method     => Some(n)
      case n: nodes.Expression => Some(n.method)
      case _                   => None
    }

    val lineToHighlight = location.lineNumber
    method
      .collect {
        case m: nodes.Method if m.lineNumber.isDefined && m.lineNumberEnd.isDefined =>
          code(filename, m.lineNumber.get, m.lineNumberEnd.get, lineToHighlight)
      }
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
            arrow + " " + line
          } else {
            line
          }
      }
      .mkString("\n")
  }

}

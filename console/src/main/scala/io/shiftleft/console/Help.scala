package io.shiftleft.console

import io.shiftleft.overflowdb.traversal.Doc
import io.shiftleft.semanticcpg.utils.Table
import org.apache.commons.lang.WordUtils

import scala.reflect.runtime.universe.{TypeTag, typeOf}

object Help {

  private val width = 80

  def overview[C: TypeTag]: String = {
    val columnNames = List("command", "description", "example")
    val rows = Doc
      .docByMethodName(typeOf[C])
      .map {
        case (name, doc) => List(name, doc.short, doc.example)
      }
      .toList ++ List(runRow)
    "\n" + Table(columnNames, rows.sortBy(_.head)).render
  }

  def format(text: String): String = {
    "\"\"\"" + "\n" +
      text.stripMargin
        .split("\n\n")
        .map(x => WordUtils.wrap(x.replace("\n", " "), width))
        .mkString("\n\n")
        .trim + "\"\"\""
  }

  private def runRow: List[String] =
    List("run", "Run analyzer on active CPG", "run.securityprofile")

  // Since `run` is generated dynamically, it's not picked up when looking
  // through methods via reflection, and therefore, we are adding
  // it manually.
  def runLongHelp: String =
    Help.format(
      """
        |
        |""".stripMargin
    )

  def codeForHelpCommand[C: TypeTag]: String = {
    val membersCode = Doc
      .docByMethodName(typeOf[C])
      .map {
        case (funcName, doc) =>
          s"val $funcName : String = ${Help.format(doc.long)}"
      }
      .mkString("\n")

    val overview = Help.overview[C]
    s"""
       | class Helper() {
       |
       | $membersCode
       |
       | def run : String = Help.runLongHelp
       |
       |  override def toString : String = \"\"\"${overview}\"\"\"
       | }
       |
       | val help = new Helper
       |""".stripMargin
  }

}

package io.shiftleft.console

import io.shiftleft.semanticcpg.utils.Table
import org.apache.commons.lang.WordUtils

import scala.reflect.runtime.universe._
import scala.tools.reflect.ToolBox

object Help {

  private val width = 80

  def overview[C](implicit tag: TypeTag[C]): String = {
    val columnNames = List("command", "description", "example")
    val rows = funcNameDocPairs[C].map {
      case (name, doc) => List(name, doc.short, doc.example)
    } ++ List(runRow)
    "\n" + Table(columnNames, rows.sortBy(_.head)).render
  }

  def funcNameDocPairs[C](implicit tag: TypeTag[C]): List[(String, Doc)] = {
    val tb = runtimeMirror(this.getClass.getClassLoader).mkToolBox()
    typeOf[C].members
      .filter(_.isPublic)
      .map(
        x =>
          (x.name.toString,
           x.annotations
             .filter(a => a.tree.tpe =:= typeOf[Doc])
             .map(a => tb.eval(tb.untypecheck(a.tree)).asInstanceOf[Doc])
             .headOption
             .orNull))
      .filter(_._2 != null)
      .toList
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

  def codeForHelpCommand[C](implicit tag: TypeTag[C]): String = {
    val membersCode = Help
      .funcNameDocPairs[C]
      .map {
        case (funcName, doc) =>
          s"val $funcName : String = ${Help.format(doc.long)}"
      }
      .mkString("\n")

    val overview = Help.overview[C](tag)
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

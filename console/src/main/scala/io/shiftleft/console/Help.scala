package io.shiftleft.console

import org.apache.commons.lang.WordUtils

import scala.reflect.runtime.universe._
import scala.tools.reflect.ToolBox

object Help {

  private val width = 80

  def overview[C](implicit tag: TypeTag[C]): String = {
    val columnNames = List("command", "description", "example")
    val rows = funcNameDocPairs[C].map {
      case (name, doc) => s"$name\t${doc.short}\t${doc.example}"
    } ++ List(runRow)
    Table.create(columnNames, rows.sorted)
  }

  def funcNameDocPairs[C](implicit tag: TypeTag[C]): List[(String, Doc)] = {
    val tb = runtimeMirror(this.getClass.getClassLoader).mkToolBox()
    typeOf[C].decls
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

  private def runRow: String = {
    "run\tRun analyzer on active CPG\trun.securityprofile"
  }

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

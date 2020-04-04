package io.shiftleft.semanticcpg.language

import java.io.{ByteArrayOutputStream, PrintStream}
import java.nio.charset.StandardCharsets
import dnl.utils.text.table.TextTable
import scala.util.Using

/** Typeclass for getting help/documentation about a type - useful for REPL users */
trait Help[A] {
  def toText: String
}

object Help {
  val ApiDocRoot = "https://ocular.shiftleft.io/api"
  val ColumnNames = Array("step", "description")

  class ForNode[A](description: String,
                   entries: List[Entry],
                   apiDocPath: String = "/io/shiftleft/queryprimitives/steps/NodeSteps.html") extends Help[A] {

    override def toText: String = {
      val entriesTable = Using.Manager { use =>
        val baos = use(new ByteArrayOutputStream)
        val ps = use(new PrintStream(baos, true, "utf-8"))
        val entriesData: Array[Array[Object]] = entries.toArray.map(entry => Array(entry.step, entry.description))
        new TextTable(ColumnNames, entriesData).printTable(ps, 0)
        new String(baos.toByteArray, StandardCharsets.UTF_8)
      }.get

      s"""$description
         |$entriesTable
         |$ApiDocRoot/$apiDocPath
         |""".stripMargin
    }
  }

  case class Entry(step: String, description: String)

  val genericHelp = new ForNode("generic NodeStep", List(
    Entry(".l", "execute this traversal and return a List"),
    Entry(".p", "pretty print"),
    Entry(".toJson", ""),
    Entry(".toJsonPretty", ""),
    Entry(".map", "transform the traversal by a given function, e.g. `.map(_.toString)`"),
  ))

  def default[A]: Help[A] = genericHelp.asInstanceOf[Help[A]]
}


package io.shiftleft.semanticcpg.language

import java.io.{ByteArrayOutputStream, PrintStream}
import java.nio.charset.StandardCharsets

import dnl.utils.text.table.TextTable
import io.shiftleft.semanticcpg.{Doc, Traversal}
import org.reflections.Reflections
import scala.jdk.CollectionConverters._

import scala.util.Using

object Help {
  /**
    * The base package that we scan for @Traversal annotations.
    * Note that this restricts `.help` to only find @Doc annotations in classes in that namespace and it's children.
    * The only reason for doing so is to speed up the scanning for @Traversal annotations.
    */
  val StepsBasePackage = "io.shiftleft"

  val ColumnNames = Array("step", "description")

  case class StepDoc(traversalClassName: String, methodName: String, msg: String)

  // TODO refactor
  lazy val stepDocsByElementType: Map[Class[_], List[StepDoc]] =
    new Reflections(StepsBasePackage).getTypesAnnotatedWith(classOf[Traversal]).iterator.asScala.toList.flatMap { traversal =>
      val elementClass = traversal.getAnnotation(classOf[Traversal]).elementType
      traversal.getMethods.filterNot(m => java.lang.reflect.Modifier.isStatic(m.getModifiers)).flatMap { method =>
        Option(method.getAnnotation(classOf[Doc])).map { doc =>
          (elementClass, StepDoc(traversal.getName, method.getName, doc.msg))
        }
      }
    }.groupMap(_._1)(_._2)

  def renderTable(elementClass: Class[_]): String = {
    // TODO generate table
    stepDocsByElementType.get(elementClass).getOrElse(Nil).mkString("\n")
  }

//  class ForNode[A](description: String, entries: List[Entry]) extends Help[A] {
//    override def toText: String = {
//      val entriesTable = Using.Manager { use =>
//        val baos = use(new ByteArrayOutputStream)
//        val ps = use(new PrintStream(baos, true, "utf-8"))
//        val entriesData: Array[Array[Object]] = entries.toArray.map(entry => Array(entry.step, entry.description))
//        new TextTable(ColumnNames, entriesData).printTable(ps, 0)
//        new String(baos.toByteArray, StandardCharsets.UTF_8)
//      }.get
//
//      s"""$description
//         |$entriesTable
//         |""".stripMargin
//    }
//  }
//
//  case class Entry(step: String, description: String)

//  val genericHelp = new ForNode(
//    "generic NodeStep",
//    List(
//      Entry(".l", "execute this traversal and return a List"),
//      Entry(".p", "pretty print"),
//      Entry(".toJson", ""),
//      Entry(".toJsonPretty", ""),
//      Entry(".map", "transform the traversal by a given function, e.g. `.map(_.toString)`"),
//    )
//  )
}

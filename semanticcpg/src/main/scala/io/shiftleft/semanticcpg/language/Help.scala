package io.shiftleft.semanticcpg.language

import java.io.{ByteArrayOutputStream, PrintStream}
import java.lang.reflect.Modifier.isStatic
import java.nio.charset.StandardCharsets

import dnl.utils.text.table.TextTable
import io.shiftleft.semanticcpg.{Doc, Traversal}
import org.reflections.Reflections
import scala.jdk.CollectionConverters._
import scala.util.Using

object Foo extends App {
  import io.shiftleft.codepropertygraph.generated.nodes
  println(new Steps[nodes.Method](null).help)
}

object Help {
  /**
    * The base package that we scan for @Traversal annotations.
    * Note that this restricts `.help` to only find @Doc annotations in classes in that namespace and it's children.
    * The only reason for doing so is to speed up the scanning for @Traversal annotations.
    */
  val StepsBasePackage = "io.shiftleft"
  val ColumnNames = Array("step", "description")

  /**
    * Scans the entire classpath for classes annotated with @Traversal (using java reflection),
    * to then extract the @Doc annotations for all steps, and group them by the elementType (e.g. node.Method).
    * Results are cached (hence lazy val).
    */
  lazy val stepDocsByElementType: Map[Class[_], List[StepDoc]] = {
    for {
      traversal <- new Reflections(StepsBasePackage).getTypesAnnotatedWith(classOf[Traversal]).iterator.asScala
      elementClass = traversal.getAnnotation(classOf[Traversal]).elementType
      method <- traversal.getMethods.filterNot(m => isStatic(m.getModifiers))
      doc <- Option(method.getAnnotation(classOf[Doc]))
    } yield (elementClass, StepDoc(traversal.getName, method.getName, doc.msg))
  }.toList.groupMap(_._1)(_._2)

  def renderTable(elementClass: Class[_]): String = {
    val stepDocs = stepDocsByElementType.get(elementClass).getOrElse(Nil)
    val rowData: Array[Array[Object]] = stepDocs.toArray.map(stepDoc => Array(s".${stepDoc.methodName}", stepDoc.msg))
    val entriesTable = Using.Manager { use =>
      val baos = use(new ByteArrayOutputStream)
      val ps = use(new PrintStream(baos, true, "utf-8"))
      new TextTable(ColumnNames, rowData).printTable(ps, 0)
      new String(baos.toByteArray, StandardCharsets.UTF_8)
    }.get

      s"""Available steps for ${elementClass.getSimpleName}:
         |$entriesTable
         |""".stripMargin
  }

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

  case class StepDoc(traversalClassName: String, methodName: String, msg: String)
}

package io.shiftleft.semanticcpg.language

import java.io.{ByteArrayOutputStream, PrintStream}
import java.lang.reflect.Modifier.isStatic
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
  val ColumnNamesVerbose = ColumnNames :+ "traversal name"

  def renderTable(elementClass: Class[_], verbose: Boolean): String = {
    val stepDocs = stepDocsByElementType.get(elementClass).getOrElse(Nil)

    val columnNames = if (verbose) ColumnNamesVerbose else ColumnNames
    val rowData = stepDocs.toArray.map { stepDoc =>
      val baseColumns: Array[Object] = Array(s".${stepDoc.methodName}", stepDoc.msg)
      if (verbose) baseColumns :+ stepDoc.traversalClassName
      else baseColumns
    }

    val entriesTable = Using.Manager { use =>
      val baos = use(new ByteArrayOutputStream)
      val ps = use(new PrintStream(baos, true, "utf-8"))
      new TextTable(columnNames, rowData).printTable(ps, 0)
      new String(baos.toByteArray, StandardCharsets.UTF_8)
    }.get

    s"""Available steps for ${elementClass.getSimpleName}:
         |$entriesTable
         |""".stripMargin
  }

  /**
    * Scans the entire classpath for classes annotated with @Traversal (using java reflection),
    * to then extract the @Doc annotations for all steps, and group them by the elementType (e.g. node.Method).
    */
  lazy val stepDocsByElementType: Map[Class[_], List[StepDoc]] = {
    for {
      traversal <- new Reflections(StepsBasePackage).getTypesAnnotatedWith(classOf[Traversal]).iterator.asScala
      stepDoc <- readDocAnnotations(traversal)
      elementType = traversal.getAnnotation(classOf[Traversal]).elementType
    } yield (elementType, stepDoc)
  }.toList.groupMap(_._1)(_._2)

  lazy val genericStepDocs: List[StepDoc] =
    readDocAnnotations(classOf[Steps[_]])

  lazy val genericNodeStepDocs: List[StepDoc] =
    readDocAnnotations(classOf[NodeSteps[_]])

  private def readDocAnnotations(traversal: Class[_]): List[StepDoc] =
    for {
      method <- traversal.getMethods.toList if !isStatic(method.getModifiers)
      doc <- Option(method.getAnnotation(classOf[Doc]))
    } yield StepDoc(traversal.getName, method.getName, doc.msg.stripMargin)

  case class StepDoc(traversalClassName: String, methodName: String, msg: String)
}

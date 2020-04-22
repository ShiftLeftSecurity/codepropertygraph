package io.shiftleft.semanticcpg.language

import java.io.{ByteArrayOutputStream, PrintStream}
import java.nio.charset.StandardCharsets

import dnl.utils.text.table.TextTable
import io.shiftleft.codepropertygraph.generated.nodes.Node
import io.shiftleft.semanticcpg.{Doc, Traversal}
import org.reflections.Reflections

import scala.jdk.CollectionConverters._
import scala.reflect.runtime.universe._
import scala.tools.reflect.ToolBox
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
    val isNode = classOf[Node].isAssignableFrom(elementClass)

    val stepDocs = {
      val base = stepDocsByElementType.get(elementClass).getOrElse(Nil)
      if (!verbose) base
      else {
        if (isNode) base ++ genericNodeStepDocs
        else base ++ genericStepDocs
      }
    }

    val columnNames = if (verbose) ColumnNamesVerbose else ColumnNames
    val rowData = stepDocs.sortBy(_.methodName).toArray.map { stepDoc =>
      val baseColumns: Array[Object] = Array(s".${stepDoc.methodName}", stepDoc.doc.short)
      if (verbose) baseColumns :+ stepDoc.traversalClassName
      else baseColumns
    }

    val entriesTable =
      Using.Manager { use =>
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
      elementType = traversal.getAnnotation(classOf[Traversal]).elementType
      stepDoc <- readDocAnnotations(traversal)
    } yield (elementType, stepDoc)
  }.toList.groupMap(_._1)(_._2)

  lazy val genericStepDocs: Iterable[StepDoc] =
    readDocAnnotations(classOf[Steps[_]])

  lazy val genericNodeStepDocs: Iterable[StepDoc] =
    readDocAnnotations(classOf[NodeSteps[_]])

  private val mirror = runtimeMirror(this.getClass.getClassLoader)
  private val mirrorToolbox = mirror.mkToolBox()

  private def readDocAnnotations(traversal: Class[_]): Iterable[StepDoc] = {
    val traversalTpe = mirror.classSymbol(traversal).toType
    def toDoc(annotation: Annotation): Doc =
      mirrorToolbox.eval(mirrorToolbox.untypecheck(annotation.tree)).asInstanceOf[Doc]

    traversalTpe.members
      .filter(_.isPublic)
      .map { member =>
        (member.name.toString, member.annotations.filter(_.tree.tpe =:= typeOf[Doc]).map(toDoc).headOption)
      }
      .collect { case (methodName, Some(doc)) => StepDoc(traversal.getName, methodName, doc) }
  }

  case class StepDoc(traversalClassName: String, methodName: String, doc: Doc)
}

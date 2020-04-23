package io.shiftleft.semanticcpg.language

import io.shiftleft.codepropertygraph.generated.nodes.Node
import io.shiftleft.overflowdb.traversal.Doc
import io.shiftleft.semanticcpg.utils.Table
import io.shiftleft.semanticcpg.Traversal
import org.reflections.Reflections

import scala.reflect.runtime.universe.runtimeMirror
import scala.jdk.CollectionConverters._

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

    val table = Table(
      columnNames = if (verbose) ColumnNamesVerbose else ColumnNames,
      rows = stepDocs.sortBy(_.methodName).map { stepDoc =>
        val baseColumns = List(s".${stepDoc.methodName}", stepDoc.doc.short)
        if (verbose) baseColumns :+ stepDoc.traversalClassName
        else baseColumns
      }
    )

    s"""Available steps for ${elementClass.getSimpleName}:
         |${table.render}
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
      stepDoc <- findStepDocs(traversal)
    } yield (elementType, stepDoc)
  }.toList.groupMap(_._1)(_._2)

  lazy val genericStepDocs: Iterable[StepDoc] =
    findStepDocs(classOf[Steps[_]])

  lazy val genericNodeStepDocs: Iterable[StepDoc] =
    findStepDocs(classOf[NodeSteps[_]])

  private lazy val mirror = runtimeMirror(this.getClass.getClassLoader)

  private def findStepDocs(traversal: Class[_]): Iterable[StepDoc] = {
    val traversalTpe = mirror.classSymbol(traversal).toType
    Doc.docByMethodName(traversalTpe).map {
      case (methodName, doc) =>
        StepDoc(traversal.getName, methodName, doc)
    }
  }

  case class StepDoc(traversalClassName: String, methodName: String, doc: Doc)
}

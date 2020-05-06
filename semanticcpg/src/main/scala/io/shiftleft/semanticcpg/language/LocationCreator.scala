package io.shiftleft.semanticcpg.language

import gremlin.scala.Vertex
import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.codepropertygraph.generated.nodes.NewLocation
import io.shiftleft.semanticcpg.utils.ExpandTo
import org.apache.logging.log4j.LogManager

import scala.jdk.CollectionConverters._

/* TODO MP: this should be part of the normal steps, rather than matching on the type at runtime
 * all (and only) steps extending DataFlowObject should/must have `newSink`, `newSource` and `newLocation` */
object LocationCreator {

  private val logger = LogManager.getLogger(getClass)

  def apply(vertex: Vertex): nodes.NewLocation = {
    try {
      location(vertex)
    } catch {
      case exc @ (_: NoSuchElementException | _: ClassCastException) => {
        logger.error(s"Cannot determine location for ${vertex.label} due to broken CPG", exc)
        emptyLocation(vertex.label, Some(vertex.asInstanceOf[nodes.Node]))
      }
    }
  }

  private def location(vertex: Vertex): NewLocation = {
    vertex match {
      case paramIn: nodes.MethodParameterIn =>
        apply(
          paramIn,
          paramIn.name,
          paramIn.label,
          paramIn.lineNumber,
          paramIn.method
        )
      case paramOut: nodes.MethodParameterOut =>
        apply(
          paramOut,
          paramOut.name,
          paramOut.label,
          paramOut.lineNumber,
          paramOut.method
        )
      case methodReturn: nodes.MethodReturn =>
        apply(
          methodReturn,
          "$ret",
          methodReturn.label,
          methodReturn.lineNumber,
          methodReturn.method
        )
      case call: nodes.Call =>
        apply(
          call,
          call.code,
          call.label,
          call.lineNumber,
          call.method
        )
      case implicitCall: nodes.ImplicitCall =>
        apply(
          implicitCall,
          implicitCall.code,
          implicitCall.label,
          implicitCall.lineNumber,
          implicitCall.method
        )
      case method: nodes.Method =>
        apply(
          method,
          method.name,
          method.label,
          method.lineNumber,
          method
        )
      case identifier: nodes.Identifier =>
        apply(
          identifier,
          identifier.name,
          identifier.label,
          identifier.lineNumber,
          identifier.method
        )
      case literal: nodes.Literal =>
        apply(
          literal,
          literal.code,
          literal.label,
          literal.lineNumber,
          literal.method
        )
      case local: nodes.Local =>
        apply(
          local,
          local.name,
          local.label,
          local.lineNumber,
          local.start.method.head
        )
      case methodRef: nodes.MethodRef =>
        apply(
          methodRef,
          methodRef.code,
          methodRef.label,
          methodRef.lineNumber,
          methodRef.start.method.head
        )
      case source: nodes.Source =>
        apply(source.node)
      case vertex: Vertex =>
        emptyLocation(vertex.label, Some(vertex.asInstanceOf[nodes.Node]))
    }
  }

  def apply(node: nodes.Node,
            symbol: String,
            label: String,
            lineNumber: Option[Integer],
            method: nodes.Method): nodes.NewLocation = {

    if (method == null) {
      nodes.NewLocation("", "", "", "", None, "", "", "", "", Some(node))
    } else {
      val typeOption = ExpandTo.methodToTypeDecl(method)
      val typeName = typeOption.map(_.fullName).getOrElse("")
      val typeShortName = typeOption.map(_.name).getOrElse("")

      val namespaceOption = typeOption.flatMap { tpe =>
        tpe._namespaceBlockViaAstIn
          .flatMap(_._namespaceViaRefOut)
          .nextOption
      }
      val namespaceName = namespaceOption.map(_.name).getOrElse("")
      val fileOption = ExpandTo.methodToFile(method)
      val fileName = fileOption.map(_.name).getOrElse("N/A")

      nodes.NewLocation(
        symbol = symbol,
        methodFullName = method.fullName,
        methodShortName = method.name,
        packageName = namespaceName,
        lineNumber = lineNumber,
        className = typeName,
        classShortName = typeShortName,
        nodeLabel = label,
        filename = fileName,
        node = Some(node)
      )
    }
  }

  def emptyLocation(label: String, node: Option[nodes.Node]): nodes.NewLocation =
    nodes.NewLocation("", "", "", "", None, "", "", label, "", node)
}

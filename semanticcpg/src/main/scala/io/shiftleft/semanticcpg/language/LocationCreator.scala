package io.shiftleft.semanticcpg.language

import gremlin.scala.Vertex
import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.semanticcpg.utils.ExpandTo

import scala.jdk.CollectionConverters._

/* TODO MP: this should be part of the normal steps, rather than matching on the type at runtime
 * all (and only) steps extending DataFlowObject should/must have `newSink`, `newSource` and `newLocation` */
object LocationCreator {
  def apply(vertex: Vertex): nodes.NewLocation = {
    vertex match {
      case paramIn: nodes.MethodParameterIn =>
        apply(
          paramIn,
          paramIn.name,
          paramIn.label,
          paramIn.lineNumber,
          ExpandTo.parameterInToMethod(paramIn)
        )
      case paramOut: nodes.MethodParameterOut =>
        apply(
          paramOut,
          paramOut.name,
          paramOut.label,
          paramOut.lineNumber,
          ExpandTo.parameterOutToMethod(paramOut)
        )
      case methodReturn: nodes.MethodReturn =>
        apply(
          methodReturn,
          "$ret",
          methodReturn.label,
          methodReturn.lineNumber,
          ExpandTo.methodReturnToMethod(methodReturn)
        )
      case call: nodes.Call =>
        apply(
          call,
          call.code,
          call.label,
          call.lineNumber,
          ExpandTo.expressionToMethod(call)
        )
      case implicitCall: nodes.ImplicitCall =>
        apply(
          implicitCall,
          implicitCall.code,
          implicitCall.label,
          implicitCall.lineNumber,
          ExpandTo.implicitCallToMethod(implicitCall)
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
          ExpandTo.expressionToMethod(identifier)
        )
      case literal: nodes.Literal =>
        apply(
          literal,
          literal.code,
          literal.label,
          literal.lineNumber,
          ExpandTo.expressionToMethod(literal)
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
      val typeOption = ExpandTo.methodToTypeDecl(method).map(_.asInstanceOf[nodes.TypeDecl])
      val typeName = typeOption.map(_.fullName).getOrElse("")
      val typeShortName = typeOption.map(_.name).getOrElse("")

      val namespaceOption = typeOption.flatMap(
        _.astIn.asScala
          .collect { case nb: nodes.NamespaceBlock => nb }
          .flatMap(_.refOut.asScala)
          .nextOption
      )

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

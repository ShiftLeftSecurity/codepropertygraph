package io.shiftleft.semanticcpg.language

import gremlin.scala.Vertex
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeTypes, nodes}
import io.shiftleft.semanticcpg.utils.ExpandTo
import org.apache.tinkerpop.gremlin.structure.Direction

import scala.collection.JavaConverters._

/* TODO MP: this should be part of the normal steps, rather than matching on the type at runtime
 * all (and only) steps extending DataFlowObject should/must have `newSink`, `newSource` and `newLocation` */
object LocationCreator {
  def apply(vertex: Vertex): nodes.NewLocation = {
    vertex match {
      case paramIn: nodes.MethodParameterIn =>
        apply(
          paramIn,
          paramIn.name,
          paramIn.label(),
          paramIn.lineNumber,
          None,
          ExpandTo.parameterInToMethod(paramIn).asInstanceOf[nodes.Method]
        )
      case paramOut: nodes.MethodParameterOut =>
        apply(
          paramOut,
          paramOut.name,
          paramOut.label(),
          paramOut.lineNumber,
          None,
          ExpandTo.parameterInToMethod(paramOut).asInstanceOf[nodes.Method]
        )
      case methodReturn: nodes.MethodReturn =>
        apply(
          methodReturn,
          "$ret",
          methodReturn.label(),
          methodReturn.lineNumber,
          None,
          ExpandTo.methodReturnToMethod(methodReturn).asInstanceOf[nodes.Method]
        )
      case call: nodes.Call =>
        apply(
          call,
          call.code,
          call.label(),
          call.lineNumber,
          None,
          ExpandTo.expressionToMethod(call).asInstanceOf[nodes.Method]
        )
      case implicitCall: nodes.ImplicitCall =>
        apply(
          implicitCall,
          implicitCall.code,
          implicitCall.label(),
          implicitCall.lineNumber,
          None,
          ExpandTo.implicitCallToMethod(implicitCall)
        )
      case method: nodes.Method =>
        apply(
          method,
          method.name,
          method.label(),
          method.lineNumber,
          method.lineNumberEnd,
          method
        )
      case identifier: nodes.Identifier =>
        apply(
          identifier,
          identifier.name,
          identifier.label(),
          identifier.lineNumber,
          None,
          ExpandTo.expressionToMethod(identifier).asInstanceOf[nodes.Method]
        )
      case literal: nodes.Literal =>
        apply(
          literal,
          literal.code,
          literal.label(),
          literal.lineNumber,
          None,
          ExpandTo.expressionToMethod(literal) match {
            case method: nodes.Method =>
              method
            case _: nodes.TypeDecl =>
              // TODO - there are csharp CPGs that have
              // typedecls here, which is invalid.
              null
          }
        )
      case local: nodes.Local =>
        apply(
          local,
          local.name,
          local.label(),
          local.lineNumber,
          None,
          local.start.method.head
        )
      case vertex: Vertex =>
        emptyLocation(vertex.label(), Some(vertex.asInstanceOf[nodes.Node]))
    }
  }

  def apply(node: nodes.Node,
            symbol: String,
            label: String,
            lineNumber: Option[Integer],
            lineNumberEnd: Option[Integer],
            method: nodes.Method): nodes.NewLocation = {

    if (method == null) {
      nodes.NewLocation(node = Some(node))
    } else {
      val typeOption = ExpandTo.methodToTypeDecl(method).map(_.asInstanceOf[nodes.TypeDecl])
      val typeName = typeOption.map(_.fullName).getOrElse("")

      val namespaceOptionVertex = typeOption.flatMap(
        _.vertices(Direction.IN, EdgeTypes.AST).asScala
          .filter(_.label() == NodeTypes.NAMESPACE_BLOCK)
          .flatMap(_.vertices(Direction.OUT, EdgeTypes.REF).asScala)
          .toList
          .headOption
      )
      val namespaceOption = namespaceOptionVertex.map(_.asInstanceOf[nodes.Namespace])
      val namespaceName = namespaceOption.map(_.name).getOrElse("")
      val fileOption = ExpandTo.methodToFile(method).map(_.asInstanceOf[nodes.File])
      val fileName = fileOption.map(_.name).getOrElse("N/A")

      nodes.NewLocation(
        symbol = symbol,
        methodFullName = method.fullName,
        methodShortName = method.name,
        packageName = namespaceName,
        lineNumber = lineNumber,
        lineNumberEnd = lineNumberEnd,
        className = typeName,
        nodeLabel = label,
        filename = fileName,
        node = Some(node)
      )
    }
  }

  def emptyLocation(label: String, node: Option[nodes.Node]): nodes.NewLocation =
    nodes.NewLocation(nodeLabel = label, node = node)
}

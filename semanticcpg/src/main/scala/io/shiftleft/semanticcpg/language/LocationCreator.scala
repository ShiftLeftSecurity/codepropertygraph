package io.shiftleft.semanticcpg.language

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.codepropertygraph.generated.nodes.NewLocation
import org.slf4j.{Logger, LoggerFactory}
import overflowdb.traversal._

import scala.annotation.tailrec

/* TODO MP: this should be part of the normal steps, rather than matching on the type at runtime
 * all (and only) steps extending DataFlowObject should/must have `newSink`, `newSource` and `newLocation` */
object LocationCreator {

  private val logger: Logger = LoggerFactory.getLogger(getClass)

  def apply(node: nodes.StoredNode): nodes.NewLocation = {
    try {
      location(node)
    } catch {
      case exc @ (_: NoSuchElementException | _: ClassCastException) =>
        logger.error(s"Cannot determine location for ${node.label} due to broken CPG", exc)
        emptyLocation(node.label, Some(node))
    }
  }

  private def location(node: nodes.StoredNode): NewLocation = {
    node match {
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
      case postExecutionCall: nodes.PostExecutionCall =>
        apply(
          postExecutionCall,
          postExecutionCall.code,
          postExecutionCall.label,
          postExecutionCall.lineNumber,
          postExecutionCall.method
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
          local.method.head
        )
      case methodRef: nodes.MethodRef =>
        apply(
          methodRef,
          methodRef.code,
          methodRef.label,
          methodRef.lineNumber,
          methodRef._methodViaContainsIn.next
        )
      case source: nodes.Source =>
        apply(source.node)
      case node =>
        emptyLocation(node.label, Some(node))
    }
  }

  def apply(
      node: nodes.CpgNode,
      symbol: String,
      label: String,
      lineNumber: Option[Integer],
      method: nodes.Method
  ): nodes.NewLocation = {

    if (method == null) {
      nodes.NewLocation("", "", "", "", None, "", "", "", "", Some(node))
    } else {
      val typeOption = methodToTypeDecl(method)
      val typeName = typeOption.map(_.fullName).getOrElse("")
      val typeShortName = typeOption.map(_.name).getOrElse("")

      val namespaceOption = for {
        tpe <- typeOption
        namespaceBlock <- tpe._namespaceBlockViaAstIn
        namespace <- namespaceBlock._namespaceViaRefOut.nextOption
      } yield namespace.name
      val namespaceName = namespaceOption.getOrElse("")

      nodes.NewLocation(
        symbol = symbol,
        methodFullName = method.fullName,
        methodShortName = method.name,
        packageName = namespaceName,
        lineNumber = lineNumber,
        className = typeName,
        classShortName = typeShortName,
        nodeLabel = label,
        filename = if (method.filename.isEmpty) "N/A" else method.filename,
        node = Some(node)
      )
    }
  }

  private def methodToTypeDecl(method: nodes.Method): Option[nodes.TypeDecl] =
    findVertex(method, _.isInstanceOf[nodes.TypeDecl]).map(_.asInstanceOf[nodes.TypeDecl])

  @tailrec
  private def findVertex(node: nodes.StoredNode, instanceCheck: nodes.StoredNode => Boolean): Option[nodes.StoredNode] =
    node._astIn.nextOption match {
      case Some(head) if instanceCheck(head) => Some(head)
      case Some(head)                        => findVertex(head, instanceCheck)
      case None                              => None
    }

  def emptyLocation(label: String, node: Option[nodes.CpgNode]): nodes.NewLocation =
    nodes.NewLocation("", "", "", "", None, "", "", label, "", node)
}

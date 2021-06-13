package io.shiftleft.semanticcpg.language

import io.shiftleft.codepropertygraph.generated.nodes._
import io.shiftleft.semanticcpg.language.nodemethods.ILocationCreator
import org.slf4j.{Logger, LoggerFactory}
import overflowdb.traversal._

import scala.annotation.tailrec

/* TODO MP: this should be part of the normal steps, rather than matching on the type at runtime
 * all (and only) steps extending DataFlowObject should/must have `newSink`, `newSource` and `newLocation` */
object LocationCreator extends ILocationCreator {

  private val logger: Logger = LoggerFactory.getLogger(getClass)

  def apply(node: StoredNode): NewLocation = {
    try {
      location(node)
    } catch {
      case exc @ (_: NoSuchElementException | _: ClassCastException) =>
        logger.error(s"Cannot determine location for ${node.label} due to broken CPG", exc)
        emptyLocation(node.label, Some(node))
    }
  }

  private def location(node: StoredNode): NewLocation = {
    node match {
      case paramIn: MethodParameterIn =>
        apply(
          paramIn,
          paramIn.name,
          paramIn.label,
          paramIn.lineNumber,
          paramIn.method
        )
      case paramOut: MethodParameterOut =>
        apply(
          paramOut,
          paramOut.name,
          paramOut.label,
          paramOut.lineNumber,
          paramOut.method
        )
      case methodReturn: MethodReturn =>
        apply(
          methodReturn,
          "$ret",
          methodReturn.label,
          methodReturn.lineNumber,
          methodReturn.method
        )
      case call: CallRepr =>
        apply(
          call,
          call.code,
          call.label,
          call.lineNumber,
          call.method
        )
      case method: Method =>
        apply(
          method,
          method.name,
          method.label,
          method.lineNumber,
          method
        )
      case identifier: Identifier =>
        apply(
          identifier,
          identifier.name,
          identifier.label,
          identifier.lineNumber,
          identifier.method
        )
      case literal: Literal =>
        apply(
          literal,
          literal.code,
          literal.label,
          literal.lineNumber,
          literal.method
        )
      case local: Local =>
        apply(
          local,
          local.name,
          local.label,
          local.lineNumber,
          local.method.head
        )
      case methodRef: MethodRef =>
        apply(
          methodRef,
          methodRef.code,
          methodRef.label,
          methodRef.lineNumber,
          methodRef._methodViaContainsIn.next()
        )
      case source: Source =>
        apply(source.node)
      case node =>
        emptyLocation(node.label, Some(node))
    }
  }

  def apply(
      node: AbstractNode,
      symbol: String,
      label: String,
      lineNumber: Option[Integer],
      method: Method
  ): NewLocation = {

    if (method == null) {
      NewLocation().node(Some(node))
    } else {
      val typeOption = methodToTypeDecl(method)
      val typeName = typeOption.map(_.fullName).getOrElse("")
      val typeShortName = typeOption.map(_.name).getOrElse("")

      val namespaceOption = for {
        tpe <- typeOption
        namespaceBlock <- tpe._namespaceBlockViaAstIn
        namespace <- namespaceBlock._namespaceViaRefOut.nextOption()
      } yield namespace.name
      val namespaceName = namespaceOption.getOrElse("")

      NewLocation()
        .symbol(symbol)
        .methodFullName(method.fullName)
        .methodShortName(method.name)
        .packageName(namespaceName)
        .lineNumber(lineNumber)
        .className(typeName)
        .classShortName(typeShortName)
        .nodeLabel(label)
        .filename(if (method.filename.isEmpty) "N/A" else method.filename)
        .node(Some(node))
    }
  }

  private def methodToTypeDecl(method: Method): Option[TypeDecl] =
    findVertex(method, _.isInstanceOf[TypeDecl]).map(_.asInstanceOf[TypeDecl])

  @tailrec
  private def findVertex(node: StoredNode, instanceCheck: StoredNode => Boolean): Option[StoredNode] =
    node._astIn.nextOption() match {
      case Some(head) if instanceCheck(head) => Some(head)
      case Some(head)                        => findVertex(head, instanceCheck)
      case None                              => None
    }

  def emptyLocation(label: String, node: Option[AbstractNode]): NewLocation =
    NewLocation().nodeLabel(label).node(node)
}

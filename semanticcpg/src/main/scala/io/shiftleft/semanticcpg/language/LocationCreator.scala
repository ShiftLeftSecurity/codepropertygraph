package io.shiftleft.semanticcpg.language

import io.shiftleft.codepropertygraph.generated.nodes
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

  private def runtimeNodeDispatch[X, T <: nodes.StoredNode](node : nodes.StoredNode)(map : Map[String, nodes.StoredNode => Any]) : Any = {
    map.get(node.getClass.getName + "$") match {
      case Some(f) => f(node)
      case None =>
        throw new RuntimeException("Node type not handled")
    }
  }


  // This table should be generated
  def nodeDispatchTable() = {

    def conversion[T <: nodes.StoredNode, M <: AddsMethodsToNode](node : T)(implicit conv : T => M) : M = {
      conv(node)
    }

    List(
      nodes.Method.getClass.getName -> { n : nodes.Method => conversion(n) }.asInstanceOf[nodes.StoredNode => Any],
      nodes.MethodReturn.getClass.getName -> { n : nodes.MethodReturn => conversion(n) }.asInstanceOf[nodes.StoredNode => Any],
      // ...

    ).toMap
  }

  private def location(node: nodes.StoredNode): nodes.NewLocation = {
    val map = nodeDispatchTable()
    runtimeNodeDispatch(node)(map).asInstanceOf[AddsMethodsToNode] match {
      case x : HasLocation => x.location
      case _ =>
        throw new RuntimeException("Node type not handled")
    }
  }

  def apply(
      node: nodes.AbstractNode,
      symbol: String,
      label: String,
      lineNumber: Option[Integer],
      method: nodes.Method
  ): nodes.NewLocation = {

    if (method == null) {
      nodes.NewLocation().node(Some(node))
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

      nodes
        .NewLocation()
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

  private def methodToTypeDecl(method: nodes.Method): Option[nodes.TypeDecl] =
    findVertex(method, _.isInstanceOf[nodes.TypeDecl]).map(_.asInstanceOf[nodes.TypeDecl])

  @tailrec
  private def findVertex(node: nodes.StoredNode, instanceCheck: nodes.StoredNode => Boolean): Option[nodes.StoredNode] =
    node._astIn.nextOption() match {
      case Some(head) if instanceCheck(head) => Some(head)
      case Some(head)                        => findVertex(head, instanceCheck)
      case None                              => None
    }

  def emptyLocation(label: String, node: Option[nodes.AbstractNode]): nodes.NewLocation =
    nodes.NewLocation().nodeLabel(label).node(node)
}

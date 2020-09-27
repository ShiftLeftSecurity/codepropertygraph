package io.shiftleft.dataflowengineoss.language.nodemethods

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.codepropertygraph.generated.nodes.TrackingPoint
import io.shiftleft.dataflowengineoss.queryengine.{Engine, EngineContext, PathElement}
import io.shiftleft.semanticcpg.language.nodemethods.TrackingPointToCfgNode
import overflowdb.traversal.Traversal
import overflowdb.traversal._
import io.shiftleft.dataflowengineoss.language._
import io.shiftleft.dataflowengineoss.semanticsloader.Semantics

class TrackingPointMethods[NodeType <: nodes.TrackingPoint](val node: NodeType) extends AnyVal {

  /**
    * Convert to nearest CFG node for flow pretty printing
    * */
  def cfgNode: nodes.CfgNode = TrackingPointToCfgNode(node)

  /**
    * Convert to nearest AST node
    * */
  def astNode: nodes.AstNode =
    node match {
      case n: nodes.AstNode               => n
      case n: nodes.DetachedTrackingPoint => n.cfgNode
      case _                              => ??? //TODO markus/fabs?
    }

  def reachableBy[NodeType <: nodes.TrackingPoint](sourceTravs: Traversal[NodeType]*)(
      implicit context: EngineContext): Traversal[NodeType] =
    node.start.reachableBy(sourceTravs: _*)

  def ddgIn(implicit semantics: Semantics): Traversal[TrackingPoint] =
    ddgIn(List(PathElement(node)), withInvisible = false)

  def ddgInPathElem(withInvisible: Boolean)(implicit semantics: Semantics): Traversal[PathElement] =
    ddgInPathElem(List(PathElement(node)), withInvisible)

  def ddgInPathElem(implicit semantics: Semantics): Traversal[PathElement] =
    ddgInPathElem(List(PathElement(node)), withInvisible = false)

  /**
    * Traverse back in the data dependence graph by one step, taking into account semantics
    * @param path optional list of path elements that have been expanded already
    * */
  def ddgIn(path: List[PathElement], withInvisible: Boolean)(
      implicit semantics: Semantics): Traversal[TrackingPoint] = {
    ddgInPathElem(path, withInvisible).map(_.node)
  }

  /**
    * Traverse back in the data dependence graph by one step and generate corresponding PathElement,
    * taking into account semantics
    * @param path optional list of path elements that have been expanded already
    * */
  def ddgInPathElem(path: List[PathElement], withInvisible: Boolean)(
      implicit semantics: Semantics): Traversal[PathElement] = {
    val elems = Engine.expandIn(node, path)
    if (withInvisible) {
      elems
    } else {
      (elems.filter(_.visible) ++ elems
        .filterNot(_.visible)
        .flatMap(x => x.node.ddgInPathElem(x :: path, withInvisible = false))).distinct.to(Traversal)
    }
  }

}

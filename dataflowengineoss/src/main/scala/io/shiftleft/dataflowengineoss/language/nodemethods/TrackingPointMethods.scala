package io.shiftleft.dataflowengineoss.language.nodemethods

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.codepropertygraph.generated.nodes.TrackingPoint
import io.shiftleft.dataflowengineoss.queryengine.{Engine, EngineContext, PathElement}
import io.shiftleft.semanticcpg.language.nodemethods.TrackingPointMethodsBase
import overflowdb.traversal.Traversal
import io.shiftleft.dataflowengineoss.language._
import io.shiftleft.dataflowengineoss.semanticsloader.Semantics

import scala.collection.mutable

class TrackingPointMethods[NodeType <: nodes.TrackingPoint](val node: NodeType) extends AnyVal {

  /**
    * Convert to nearest CFG node for flow pretty printing
    * */
  def cfgNode: nodes.CfgNode = TrackingPointMethodsBase.toCfgNode(node)

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
    node.reachableBy(sourceTravs: _*)

  def ddgIn(implicit semantics: Semantics): Traversal[TrackingPoint] = {
    val cache = mutable.HashMap[nodes.TrackingPoint, Vector[PathElement]]()
    val result = ddgIn(Vector(PathElement(node)), withInvisible = false, cache)
    cache.clear()
    result
  }

  def ddgInPathElem(withInvisible: Boolean,
                    cache: mutable.HashMap[nodes.TrackingPoint, Vector[PathElement]] =
                      mutable.HashMap[nodes.TrackingPoint, Vector[PathElement]]())(
      implicit semantics: Semantics): Traversal[PathElement] =
    ddgInPathElem(Vector(PathElement(node)), withInvisible, cache)

  def ddgInPathElem(implicit semantics: Semantics): Traversal[PathElement] = {
    val cache = mutable.HashMap[nodes.TrackingPoint, Vector[PathElement]]()
    val result = ddgInPathElem(Vector(PathElement(node)), withInvisible = false, cache)
    cache.clear()
    result
  }

  /**
    * Traverse back in the data dependence graph by one step, taking into account semantics
    * @param path optional list of path elements that have been expanded already
    * */
  def ddgIn(path: Vector[PathElement],
            withInvisible: Boolean,
            cache: mutable.HashMap[nodes.TrackingPoint, Vector[PathElement]])(
      implicit semantics: Semantics): Traversal[TrackingPoint] = {
    ddgInPathElem(path, withInvisible, cache).map(_.node)
  }

  /**
    * Traverse back in the data dependence graph by one step and generate corresponding PathElement,
    * taking into account semantics
    * @param path optional list of path elements that have been expanded already
    * */
  def ddgInPathElem(path: Vector[PathElement],
                    withInvisible: Boolean,
                    cache: mutable.HashMap[nodes.TrackingPoint, Vector[PathElement]])(
      implicit semantics: Semantics): Traversal[PathElement] = {
    val result = ddgInPathElemInternal(path, withInvisible, cache).to(Traversal)
    result
  }

  private def ddgInPathElemInternal(path: Vector[PathElement],
                                    withInvisible: Boolean,
                                    cache: mutable.HashMap[nodes.TrackingPoint, Vector[PathElement]])(
      implicit semantics: Semantics): Vector[PathElement] = {

    if (cache.contains(node)) {
      return cache(node)
    }

    val elems = Engine.expandIn(node, path)
    val result = if (withInvisible) {
      elems
    } else {
      (elems.filter(_.visible) ++ elems
        .filterNot(_.visible)
        .flatMap(x => x.node.ddgInPathElem(x +: path, withInvisible = false, cache))).distinct
    }
    cache.put(node, result)
    result
  }

}

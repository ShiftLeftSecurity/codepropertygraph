package io.shiftleft.dataflowengineoss.language.nodemethods

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.dataflowengineoss.language._
import io.shiftleft.dataflowengineoss.queryengine.{Engine, EngineContext, PathElement}
import io.shiftleft.dataflowengineoss.semanticsloader.Semantics
import io.shiftleft.semanticcpg.language._
import io.shiftleft.semanticcpg.utils.MemberAccess
import overflowdb.traversal.{Traversal, _}

import scala.collection.mutable
import scala.jdk.CollectionConverters._

class CfgNodeMethods[NodeType <: nodes.CfgNode](val node: NodeType) extends AnyVal {

  /**
    * Convert to nearest AST node
    * */
  def astNode: nodes.AstNode =
    node match {
      case n: nodes.AstNode => n
      case _                => ??? //TODO markus/fabs?
    }

  def reachableBy[NodeType <: nodes.CfgNode](sourceTravs: Traversal[NodeType]*)(
      implicit context: EngineContext): Traversal[NodeType] =
    node.start.reachableBy(sourceTravs: _*)

  def ddgIn(implicit semantics: Semantics): Traversal[nodes.CfgNode] = {
    val cache = mutable.HashMap[nodes.CfgNode, Vector[PathElement]]()
    val result = ddgIn(Vector(PathElement(node)), withInvisible = false, cache)
    cache.clear()
    result
  }

  def ddgInPathElem(withInvisible: Boolean,
                    cache: mutable.HashMap[nodes.CfgNode, Vector[PathElement]] =
                      mutable.HashMap[nodes.CfgNode, Vector[PathElement]]())(
      implicit semantics: Semantics): Traversal[PathElement] =
    ddgInPathElem(Vector(PathElement(node)), withInvisible, cache)

  def ddgInPathElem(implicit semantics: Semantics): Traversal[PathElement] = {
    val cache = mutable.HashMap[nodes.CfgNode, Vector[PathElement]]()
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
            cache: mutable.HashMap[nodes.CfgNode, Vector[PathElement]])(
      implicit semantics: Semantics): Traversal[nodes.CfgNode] = {
    ddgInPathElem(path, withInvisible, cache).map(_.node)
  }

  /**
    * Traverse back in the data dependence graph by one step and generate corresponding PathElement,
    * taking into account semantics
    * @param path optional list of path elements that have been expanded already
    * */
  def ddgInPathElem(path: Vector[PathElement],
                    withInvisible: Boolean,
                    cache: mutable.HashMap[nodes.CfgNode, Vector[PathElement]])(
      implicit semantics: Semantics): Traversal[PathElement] = {
    val result = ddgInPathElemInternal(path, withInvisible, cache).to(Traversal)
    result
  }

  private def ddgInPathElemInternal(path: Vector[PathElement],
                                    withInvisible: Boolean,
                                    cache: mutable.HashMap[nodes.CfgNode, Vector[PathElement]])(
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

  def statement: nodes.CfgNode =
    applyInternal(node, _.parentExpression.get)

  @scala.annotation.tailrec
  private def applyInternal(node: nodes.CfgNode,
                            parentExpansion: nodes.Expression => nodes.Expression): nodes.CfgNode = {

    node match {
      case node: nodes.Identifier => parentExpansion(node)
      case node: nodes.MethodRef  => parentExpansion(node)
      case node: nodes.TypeRef    => parentExpansion(node)
      case node: nodes.Literal    => parentExpansion(node)

      case node: nodes.MethodParameterIn => node.method

      case node: nodes.MethodParameterOut =>
        node.method.methodReturn

      case node: nodes.Call if MemberAccess.isGenericMemberAccessName(node.name) =>
        parentExpansion(node)

      case node: nodes.Call         => node
      case node: nodes.ImplicitCall => node
      case node: nodes.MethodReturn => node
      case block: nodes.Block       =>
        // Just taking the lastExpressionInBlock is not quite correct because a BLOCK could have
        // different return expressions. So we would need to expand via CFG.
        // But currently the frontends do not even put the BLOCK into the CFG so this is the best
        // we can do.
        applyInternal(lastExpressionInBlock(block).get, identity)
      case node: nodes.Expression => node
    }
  }

  private def lastExpressionInBlock(block: nodes.Block): Option[nodes.Expression] =
    block._astOut.asScala
      .collect {
        case node: nodes.Expression if !node.isInstanceOf[nodes.Local] && !node.isInstanceOf[nodes.Method] => node
      }
      .toVector
      .sortBy(_.order)
      .lastOption

}

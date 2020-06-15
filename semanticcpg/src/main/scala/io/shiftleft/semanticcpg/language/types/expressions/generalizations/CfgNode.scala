package io.shiftleft.semanticcpg.language.types.expressions.generalizations

import gremlin.scala.GremlinScala
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeTypes, nodes}
import overflowdb.traversal.help
import overflowdb.traversal.help.Doc
import io.shiftleft.semanticcpg.language._
import io.shiftleft.semanticcpg.utils.ExpandTo

@help.Traversal(elementType = classOf[nodes.CfgNode])
class CfgNode[A <: nodes.CfgNode](val wrapped: NodeSteps[A]) extends AnyVal {
  private def raw: GremlinScala[A] = wrapped.raw

  /**
    * Textual representation of CFG node
    * */
  @Doc("Textual representation of CFG node")
  def repr: Steps[String] = {
    wrapped.map(_.repr)
  }

  /**
  Traverse to enclosing method
    */
  def method: NodeSteps[nodes.Method] =
    new NodeSteps(
      raw
        .map {
          case method: nodes.Method =>
            method
          case methodReturn: nodes.MethodReturn =>
            ExpandTo.methodReturnToMethod(methodReturn)
          case expression: nodes.Expression =>
            ExpandTo.expressionToMethod(expression)
        }
        .cast[nodes.Method])

  /**
    * Traverse to next expression in CFG.
    */

  @Doc("Nodes directly reachable via outgoing CFG edges")
  def cfgNext: NodeSteps[nodes.CfgNode] =
    new NodeSteps(
      raw
        .out(EdgeTypes.CFG)
        .filterNot(_.hasLabel(NodeTypes.METHOD_RETURN))
        .cast[nodes.CfgNode]
    )

  /**
    * Traverse to previous expression in CFG.
    */
  @Doc("Nodes directly reachable via incoming CFG edges")
  def cfgPrev: NodeSteps[nodes.CfgNode] =
    new NodeSteps(
      raw
        .in(EdgeTypes.CFG)
        .filterNot(_.hasLabel(NodeTypes.METHOD))
        .cast[nodes.CfgNode]
    )

  /**
    * Recursively determine all nodes on which any of
    * the nodes in this traversal are control dependent
    * */
  @Doc("All nodes on which this node is control dependent")
  def controlledBy: NodeSteps[nodes.CfgNode] =
    wrapped.flatMap(_.controlledBy)

  /**
    * Recursively determine all nodes which are
    * control dependent on this node
    * */
  @Doc("All nodes control dependent on this node")
  def controls: NodeSteps[nodes.CfgNode] =
    wrapped.flatMap(_.controls)

}

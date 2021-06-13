package io.shiftleft.semanticcpg.language.types.expressions.generalizations

import io.shiftleft.Implicits.JavaIteratorDeco
import io.shiftleft.codepropertygraph.generated.nodes._
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeTypes}
import io.shiftleft.semanticcpg.language._
import overflowdb.traversal.help.Doc
import overflowdb.traversal.{Traversal, help}

@help.Traversal(elementType = classOf[CfgNode])
class CfgNodeTraversal[A <: CfgNode](val traversal: Traversal[A]) extends AnyVal {

  /**
    * Textual representation of CFG node
    * */
  @Doc("Textual representation of CFG node")
  def repr: Traversal[String] =
    traversal.map(_.repr)

  /**
  Traverse to enclosing method
    */
  def method: Traversal[Method] =
    traversal.map {
      case method: Method =>
        method
      case methodReturn: MethodReturn =>
        methodReturn.method
      case expression: Expression =>
        expression.method
      case callRepr: CallRepr =>
        callRepr._astIn.onlyChecked.asInstanceOf[Method]
    }

  /**
    * Traverse to next expression in CFG.
    */

  @Doc("Nodes directly reachable via outgoing CFG edges")
  def cfgNext: Traversal[CfgNode] =
    traversal
      .out(EdgeTypes.CFG)
      .not(_.hasLabel(NodeTypes.METHOD_RETURN))
      .cast[CfgNode]

  /**
    * Traverse to previous expression in CFG.
    */
  @Doc("Nodes directly reachable via incoming CFG edges")
  def cfgPrev: Traversal[CfgNode] =
    traversal
      .in(EdgeTypes.CFG)
      .not(_.hasLabel(NodeTypes.METHOD))
      .cast[CfgNode]

  /**
    * Recursively determine all nodes on which any of
    * the nodes in this traversal are control dependent
    * */
  @Doc("All nodes on which this node is control dependent")
  def controlledBy: Traversal[CfgNode] =
    traversal.flatMap(_.controlledBy)

  /**
    * Recursively determine all nodes which are
    * control dependent on this node
    * */
  @Doc("All nodes control dependent on this node")
  def controls: Traversal[CfgNode] =
    traversal.flatMap(_.controls)

  /**
    * Recursively determine all nodes by which
    * this node is dominated
    * */
  @Doc("All nodes by which this node is dominated")
  def dominatedBy: Traversal[CfgNode] =
    traversal.flatMap(_.dominatedBy)

  /**
    * Recursively determine all nodes which
    * this node dominates
    * */
  @Doc("All nodes that are dominated by this node")
  def dominates: Traversal[CfgNode] =
    traversal.flatMap(_.dominates)

  /**
    * Recursively determine all nodes by which
    * this node is post dominated
    * */
  @Doc("All nodes by which this node is post dominated")
  def postDominatedBy: Traversal[CfgNode] =
    traversal.flatMap(_.postDominatedBy)

  /**
    * Recursively determine all nodes which
    * this node post dominates
    * */
  @Doc("All nodes that are post dominated by this node")
  def postDominates: Traversal[CfgNode] =
    traversal.flatMap(_.postDominates)

}

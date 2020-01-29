package io.shiftleft.semanticcpg.language.types.expressions

import gremlin.scala.GremlinScala
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeKeys, nodes}
import io.shiftleft.semanticcpg.language._
import io.shiftleft.semanticcpg.language.types.expressions.generalizations.{AstNode, Expression}

object ControlStructure {
  val secondChildIndex = new Integer(2)
  val thirdChildIndex = new Integer(3)
}

class ControlStructure(raw: GremlinScala[nodes.ControlStructure]) extends NodeSteps[nodes.ControlStructure](raw) {
  import ControlStructure._

  /**
    * The expression introduced by this control structure, if any
    * */
  def condition: Expression[nodes.Expression] =
    new Expression(raw.out(EdgeTypes.CONDITION).cast[nodes.Expression])

  def whenTrue: AstNode[nodes.AstNode] =
    new AstNode(raw.out.has(NodeKeys.ORDER, secondChildIndex).cast[nodes.AstNode])

  def whenFalse: AstNode[nodes.AstNode] =
    new AstNode(raw.out.has(NodeKeys.ORDER, thirdChildIndex).cast[nodes.AstNode])

}

package io.shiftleft.semanticcpg.language.types.expressions

import gremlin.scala.GremlinScala
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeKeys, nodes}
import io.shiftleft.semanticcpg.language.types.expressions.generalizations.{Expression, ExpressionBase}
import io.shiftleft.semanticcpg.language._

object ControlStructure {

  val secondChildIndex = new Integer(2)
  val thirdChildIndex = new Integer(3)

}

class ControlStructure(raw: GremlinScala[nodes.ControlStructure])
    extends NodeSteps[nodes.ControlStructure](raw)
    with ExpressionBase[nodes.ControlStructure] {

  import ControlStructure._

  /**
    * The expression introduced by this control structure, if any
    * */
  def condition: Expression =
    new Expression(raw.out(EdgeTypes.CONDITION).cast[nodes.Expression])

  def whenTrue: NodeSteps[nodes.AstNode] = new NodeSteps(
    raw.out.has(NodeKeys.ORDER, secondChildIndex).cast[nodes.AstNode]
  )

  def whenFalse: NodeSteps[nodes.AstNode] = new NodeSteps(
    raw.out.has(NodeKeys.ORDER, thirdChildIndex).cast[nodes.AstNode]
  )

}

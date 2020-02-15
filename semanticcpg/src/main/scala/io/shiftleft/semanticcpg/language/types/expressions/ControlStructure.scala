package io.shiftleft.semanticcpg.language.types.expressions

import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeKeys, nodes}
import io.shiftleft.semanticcpg.language._

object ControlStructure {
  val secondChildIndex = new Integer(2)
  val thirdChildIndex = new Integer(3)
}

class ControlStructure(val wrapped: NodeSteps[nodes.ControlStructure]) extends AnyVal {
  import ControlStructure._

  /**
    * The expression introduced by this control structure, if any
    * */
  def condition: NodeSteps[nodes.Expression] =
    new NodeSteps(wrapped.raw.out(EdgeTypes.CONDITION).cast[nodes.Expression])

  def whenTrue: NodeSteps[nodes.AstNode] =
    new NodeSteps(wrapped.raw.out.has(NodeKeys.ORDER, secondChildIndex).cast[nodes.AstNode])

  def whenFalse: NodeSteps[nodes.AstNode] =
    new NodeSteps(wrapped.raw.out.has(NodeKeys.ORDER, thirdChildIndex).cast[nodes.AstNode])

}

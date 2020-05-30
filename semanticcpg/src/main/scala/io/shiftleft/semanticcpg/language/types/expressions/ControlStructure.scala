package io.shiftleft.semanticcpg.language.types.expressions

import gremlin.scala.GremlinScala
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeKeys, nodes}
import io.shiftleft.overflowdb.traversal.help.Doc
import io.shiftleft.semanticcpg.language._

object ControlStructure {
  val secondChildIndex = Int.box(2)
  val thirdChildIndex = Int.box(3)
}

class ControlStructure(val wrapped: NodeSteps[nodes.ControlStructure]) extends AnyVal {
  import ControlStructure._
  private def raw: GremlinScala[nodes.ControlStructure] = wrapped.raw

  @Doc("The condition associated with this control structure")
  def condition: NodeSteps[nodes.Expression] =
    new NodeSteps(raw.out(EdgeTypes.CONDITION).cast[nodes.Expression])

  @Doc("Control structures where condition.code matches regex")
  def condition(regex: String): NodeSteps[nodes.ControlStructure] =
    wrapped.filter(_.condition.code(regex))

  @Doc("Sub tree taken when condition evaluates to true")
  def whenTrue: NodeSteps[nodes.AstNode] =
    new NodeSteps(raw.out.has(NodeKeys.ORDER, secondChildIndex).cast[nodes.AstNode])

  @Doc("Sub tree taken when condition evaluates to false")
  def whenFalse: NodeSteps[nodes.AstNode] =
    new NodeSteps(raw.out.has(NodeKeys.ORDER, thirdChildIndex).cast[nodes.AstNode])

}

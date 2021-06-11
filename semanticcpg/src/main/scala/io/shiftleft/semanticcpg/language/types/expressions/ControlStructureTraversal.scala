package io.shiftleft.semanticcpg.language.types.expressions

import io.shiftleft.codepropertygraph.generated.{EdgeTypes, Properties, nodes}
import io.shiftleft.semanticcpg.language._
import overflowdb.traversal.Traversal
import overflowdb.traversal.help.Doc

object ControlStructureTraversal {
  val secondChildIndex = Int.box(2)
  val thirdChildIndex = Int.box(3)
}

class ControlStructureTraversal(val traversal: Traversal[nodes.ControlStructure]) extends AnyVal {
  import ControlStructureTraversal._

  @Doc("The condition associated with this control structure")
  def condition: Traversal[nodes.Expression] =
    traversal.out(EdgeTypes.CONDITION).cast[nodes.Expression]

  @Doc("Control structures where condition.code matches regex")
  def condition(regex: String): Traversal[nodes.ControlStructure] =
    traversal.where(_.condition.code(regex))

  @Doc("Sub tree taken when condition evaluates to true")
  def whenTrue: Traversal[nodes.AstNode] =
    traversal.out.has(Properties.ORDER, secondChildIndex).cast[nodes.AstNode]

  @Doc("Sub tree taken when condition evaluates to false")
  def whenFalse: Traversal[nodes.AstNode] =
    traversal.out.has(Properties.ORDER, thirdChildIndex).cast[nodes.AstNode]

}

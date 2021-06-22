package io.shiftleft.semanticcpg.language.types.expressions

import io.shiftleft.codepropertygraph.generated.nodes.{AstNode, ControlStructure, Expression}
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, Properties}
import io.shiftleft.semanticcpg.language._
import overflowdb.traversal.Traversal
import overflowdb.traversal.help.Doc

object ControlStructureTraversal {
  val secondChildIndex = Int.box(2)
  val thirdChildIndex = Int.box(3)
}

class ControlStructureTraversal(val traversal: Traversal[ControlStructure]) extends AnyVal {
  import ControlStructureTraversal._

  @Doc("The condition associated with this control structure")
  def condition: Traversal[Expression] =
    traversal.out(EdgeTypes.CONDITION).cast[Expression]

  @Doc("Control structures where condition.code matches regex")
  def condition(regex: String): Traversal[ControlStructure] =
    traversal.where(_.condition.code(regex))

  @Doc("Sub tree taken when condition evaluates to true")
  def whenTrue: Traversal[AstNode] =
    traversal.out.has(Properties.ORDER, secondChildIndex).cast[AstNode]

  @Doc("Sub tree taken when condition evaluates to false")
  def whenFalse: Traversal[AstNode] =
    traversal.out.has(Properties.ORDER, thirdChildIndex).cast[AstNode]

}

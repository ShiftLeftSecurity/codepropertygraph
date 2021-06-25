package io.shiftleft.semanticcpg.language.types.expressions

import io.shiftleft.codepropertygraph.generated.nodes.{AstNode, ControlStructure, Expression}
import io.shiftleft.codepropertygraph.generated.{ControlStructureTypes, EdgeTypes, Properties}
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

  @Doc("Only `Try` control structures")
  def isTry: Traversal[ControlStructure] =
    traversal.controlStructureTypeExact(ControlStructureTypes.TRY)

  @Doc("Only `If` control structures")
  def isIf: Traversal[ControlStructure] =
    traversal.controlStructureTypeExact(ControlStructureTypes.IF)

  @Doc("Only `Else` control structures")
  def isElse: Traversal[ControlStructure] =
    traversal.controlStructureTypeExact(ControlStructureTypes.ELSE)

  @Doc("Only `Switch` control structures")
  def isSwitch: Traversal[ControlStructure] =
    traversal.controlStructureTypeExact(ControlStructureTypes.SWITCH)

  @Doc("Only `Do` control structures")
  def isDo: Traversal[ControlStructure] =
    traversal.controlStructureTypeExact(ControlStructureTypes.DO)

  @Doc("Only `For` control structures")
  def isFor: Traversal[ControlStructure] =
    traversal.controlStructureTypeExact(ControlStructureTypes.FOR)

  @Doc("Only `While` control structures")
  def isWhile: Traversal[ControlStructure] =
    traversal.controlStructureTypeExact(ControlStructureTypes.WHILE)

  @Doc("Only `Goto` control structures")
  def isGoto: Traversal[ControlStructure] =
    traversal.controlStructureTypeExact(ControlStructureTypes.GOTO)

  @Doc("Only `Break` control structures")
  def isBreak: Traversal[ControlStructure] =
    traversal.controlStructureTypeExact(ControlStructureTypes.BREAK)

  @Doc("Only `Continue` control structures")
  def isContinue: Traversal[ControlStructure] =
    traversal.controlStructureTypeExact(ControlStructureTypes.CONTINUE)

}

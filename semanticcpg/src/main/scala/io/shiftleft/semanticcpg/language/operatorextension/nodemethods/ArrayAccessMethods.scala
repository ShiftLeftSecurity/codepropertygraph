package io.shiftleft.semanticcpg.language.operatorextension.nodemethods

import io.shiftleft.codepropertygraph.generated.nodes.{Expression, Identifier}
import io.shiftleft.semanticcpg.language._
import io.shiftleft.semanticcpg.language.operatorextension.OpNodes
import overflowdb.traversal._

class ArrayAccessMethods(val arrayAccess: OpNodes.ArrayAccess) extends AnyVal {

  /**
    * The expression representing the array
    * */
  def array: Expression =
    arrayAccess.argument(1)

  /**
    * The expression representing the offset at which the array is referenced.
    * */
  def offset: Expression = arrayAccess.argument(2)

  /**
    * All identifiers that are part of the offset
    * */
  def subscript: Traversal[Identifier] =
    offset.ast.isIdentifier

  /**
    * Determine if array access is at constant numeric offset, e.g.,
    * `buf[10]` but not `buf[i + 10]`, and for simplicity, not even `buf[1+2]`,
    * `buf[PROBABLY_A_CONSTANT]` or `buf[PROBABLY_A_CONSTANT + 1]`,
    * or even `buf[PROBABLY_A_CONSTANT]`.
    */
  def usesConstantOffset: Boolean = {
    (offset.ast.isIdentifier.size > 0) || {
      val literalIndices = offset.ast.isLiteral.l
      literalIndices.size == 1
    }
  }

  /**
    * If `array` is a lone identifier, return its name
    */
  def simpleName: Traversal[String] = {
    arrayAccess.array match {
      case id: Identifier => Traversal(id.name)
      case _              => Traversal()
    }
  }

}

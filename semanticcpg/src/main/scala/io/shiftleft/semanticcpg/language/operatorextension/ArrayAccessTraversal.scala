package io.shiftleft.semanticcpg.language.operatorextension

import io.shiftleft.codepropertygraph.generated.nodes.{Expression, Identifier}
import io.shiftleft.semanticcpg.language._
import overflowdb.traversal._

class ArrayAccessTraversal(val traversal: Traversal[OpNodes.ArrayAccess]) extends AnyVal {
  def array: Traversal[Expression] = traversal.map(_.array)
  def offset: Traversal[Expression] = traversal.map(_.offset)
  def subscripts: Traversal[Identifier] = traversal.flatMap(_.subscripts)

  /**
    * Determine all array accesses at constant numeric offsets, e.g.,
    * `buf[10]` but not `buf[i + 10]`, and for simplicity, not even `buf[1+2]`,
    * `buf[PROBABLY_A_CONSTANT]` or `buf[PROBABLY_A_CONSTANT + 1]`,
    * or even `buf[PROBABLY_A_CONSTANT]`.
    */
  def usesConstantOffset: Traversal[OpNodes.ArrayAccess] = traversal.filter(_.usesConstantOffset)

}

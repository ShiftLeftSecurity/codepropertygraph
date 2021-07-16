package io.shiftleft.semanticcpg.language.operatorextension

import io.shiftleft.codepropertygraph.generated.nodes.Expression
import io.shiftleft.semanticcpg.language._
import overflowdb.traversal.Traversal

class TargetTraversal(val traversal: Traversal[Expression]) extends AnyVal {

  /** arrayAccess traverses to all array accesses below in the AST. For example, when called on the assignment
    *   `x = buf[idxs[i]];``
    * then it will return two array accesses.
    */
  def arrayAccess: Traversal[opnodes.ArrayAccess] = traversal.flatMap(_.arrayAccess)

  @deprecated("isArrayAccess is deprecated in favor if arrayAccess, due to counterintuitive naming")
  def isArrayAccess: Traversal[opnodes.ArrayAccess] = arrayAccess
}

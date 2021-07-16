package io.shiftleft.semanticcpg.language.operatorextension.nodemethods

import io.shiftleft.codepropertygraph.generated.Operators
import io.shiftleft.codepropertygraph.generated.nodes.Expression
import io.shiftleft.semanticcpg.language._
import io.shiftleft.semanticcpg.language.operatorextension.opnodes
import overflowdb.traversal.Traversal

class TargetMethods(val expr: Expression) extends AnyVal {

  /** arrayAccess traverses to all array accesses below in the AST. For example, when called on the assignment
    *   `x = buf[idxs[i]];``
    * then it will return two array accesses.
    */
  def arrayAccess: Traversal[opnodes.ArrayAccess] =
    expr.ast.isCall
      .nameExact(Operators.computedMemberAccess,
                 Operators.indirectComputedMemberAccess,
                 Operators.indexAccess,
                 Operators.indirectIndexAccess)
      .map(new opnodes.ArrayAccess(_))

  @deprecated("isArrayAccess is deprecated in favor if arrayAccess, due to counterintuitive naming")
  def isArrayAccess: Traversal[opnodes.ArrayAccess] = arrayAccess
}

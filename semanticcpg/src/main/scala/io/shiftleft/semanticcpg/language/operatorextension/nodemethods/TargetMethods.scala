package io.shiftleft.semanticcpg.language.operatorextension.nodemethods

import io.shiftleft.codepropertygraph.generated.Operators
import io.shiftleft.codepropertygraph.generated.nodes.Expression
import io.shiftleft.codepropertygraph.generated.traversal._
import io.shiftleft.semanticcpg.language._
import io.shiftleft.semanticcpg.language.operatorextension.opnodes
import overflowdb.traversal.Traversal

class TargetMethods(val expr: Expression) extends AnyVal {
  def isArrayAccess: Traversal[opnodes.ArrayAccess] =
    expr.ast.isCall
      .nameExact(Operators.computedMemberAccess,
                 Operators.indirectComputedMemberAccess,
                 Operators.indexAccess,
                 Operators.indirectIndexAccess)
      .map(new opnodes.ArrayAccess(_))
}

package io.shiftleft.semanticcpg.language.operatorextension.nodemethods

import io.shiftleft.codepropertygraph.generated.{Operators, nodes}
import io.shiftleft.semanticcpg.language._
import io.shiftleft.semanticcpg.language.operatorextension.opnodes

class TargetMethods(val expr: nodes.Expression) extends AnyVal {
  def isArrayAccess: NodeSteps[opnodes.ArrayAccess] =
    expr.ast.isCall
      .nameExact(Operators.computedMemberAccess,
                 Operators.indirectComputedMemberAccess,
                 Operators.indexAccess,
                 Operators.indirectIndexAccess)
      .map(new opnodes.ArrayAccess(_))
}

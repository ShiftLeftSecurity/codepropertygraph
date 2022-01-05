package io.shiftleft.semanticcpg.language.operatorextension.nodemethods

import io.shiftleft.codepropertygraph.generated.nodes.Expression
import io.shiftleft.semanticcpg.language._
import io.shiftleft.semanticcpg.language.operatorextension.OpNodes

class AssignmentMethods(val assignment: OpNodes.Assignment) extends AnyVal {
  def target: Expression = assignment.argument(1)
  def source: Expression = assignment.argument(2)
}

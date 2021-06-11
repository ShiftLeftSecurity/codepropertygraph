package io.shiftleft.semanticcpg.language.operatorextension.nodemethods

import io.shiftleft.codepropertygraph.generated.nodes.Expression
import io.shiftleft.semanticcpg.language.operatorextension.opnodes

class AssignmentMethods(val assignment: opnodes.Assignment) extends AnyVal {
  def target: Expression = assignment.argument(1)
  def source: Expression = assignment.argument(2)
}

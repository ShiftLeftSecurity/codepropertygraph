package io.shiftleft.semanticcpg.language.operatorextension.nodemethods

import io.shiftleft.codepropertygraph.generated.nodes.Expression
import io.shiftleft.semanticcpg.language.operatorextension.opnodes
import io.shiftleft.semanticcpg.language.toCallMethods

class AssignmentMethods(val assignment: opnodes.Assignment) extends AnyVal {
  def target: Expression = assignment.call.argument(1)
  def source: Expression = assignment.call.argument(2)
}

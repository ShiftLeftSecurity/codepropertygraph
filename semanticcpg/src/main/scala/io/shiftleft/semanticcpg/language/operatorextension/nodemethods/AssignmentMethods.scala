package io.shiftleft.semanticcpg.language.operatorextension.nodemethods

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.semanticcpg.language.operatorextension.opnodes

class AssignmentMethods(val assignment: opnodes.Assignment) extends AnyVal {
  def target: nodes.Expression = assignment.argument(1)
  def source: nodes.Expression = assignment.argument(2)
}

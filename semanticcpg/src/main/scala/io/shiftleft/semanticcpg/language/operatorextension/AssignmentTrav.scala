package io.shiftleft.semanticcpg.language.operatorextension

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.semanticcpg.language.NodeSteps

/**
  * A wrapper for assignment calls that offers syntactic sugar
  * */
class AssignmentTrav(val wrapped: NodeSteps[nodes.Call]) extends AnyVal {
  def target: TargetTrav = new TargetTrav(wrapped.map(_.target).raw)
  def source: NodeSteps[nodes.Expression] = wrapped.map(_.source)
}

package io.shiftleft.semanticcpg.language.operatorextension.nodes

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.codepropertygraph.generated.nodes.Expression
import io.shiftleft.semanticcpg.language.nodemethods.CallMethods

class Assignment(val call: nodes.Call) extends AnyRef {

  def target: nodes.Expression = new CallMethods(call).argument(1)

  def source: Expression = new CallMethods(call).argument(2)
}

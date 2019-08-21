package io.shiftleft.queryprimitives.steps.nodemethods

import io.shiftleft.codepropertygraph.generated.nodes

class CfgNodeMethods(val node: nodes.CfgNode) extends AnyVal {
  def code: String =
    node match {
      case node: nodes.Expression   => node.code
      case node: nodes.Method       => node.name
      case node: nodes.MethodReturn => node.code
    }
}

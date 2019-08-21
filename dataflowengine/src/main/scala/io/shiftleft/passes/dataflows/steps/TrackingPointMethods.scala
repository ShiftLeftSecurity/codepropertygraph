package io.shiftleft.passes.dataflows.steps

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.queryprimitives.utils.{ExpandTo, MemberAccess}

class TrackingPointMethods(val node: nodes.TrackingPointBase) extends AnyVal {
  def cfgNode: nodes.CfgNode =
    node match {
      case node: nodes.Expression => node
      case node: nodes.ImplicitCall => node
      case node: nodes.MethodReturn => node
      case node: nodes.Identifier => ExpandTo.argumentToCallOrReturn(node)
      case node: nodes.MethodRef => ExpandTo.argumentToCallOrReturn(node)
      case node: nodes.Literal => ExpandTo.argumentToCallOrReturn(node)

      case node: nodes.MethodParameterIn =>
        ExpandTo.parameterToMethod(node).asInstanceOf[nodes.CfgNode]

      case node: nodes.MethodParameterOut =>
        val method = ExpandTo.parameterToMethod(node)
        val methodReturn = ExpandTo.methodToFormalReturn(method)
        methodReturn.asInstanceOf[nodes.CfgNode]

      case node: nodes.Call if MemberAccess.isGenericMemberAccessName(node.name) => 
        ExpandTo.argumentToCallOrReturn(node)

      case node: nodes.Call => node
    }

}

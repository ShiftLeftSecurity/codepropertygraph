package io.shiftleft.semanticcpg.language.nodemethods

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.semanticcpg.utils.{ExpandTo, MemberAccess}

object TrackingPointToCfgNode {
  def apply(node: nodes.TrackingPointBase): nodes.CfgNode =
    node match {
      case node: nodes.Identifier => ExpandTo.argumentToCallOrReturn(node)
      case node: nodes.MethodRef  => ExpandTo.argumentToCallOrReturn(node)
      case node: nodes.Literal    => ExpandTo.argumentToCallOrReturn(node)

      case node: nodes.MethodParameterIn =>
        ExpandTo.parameterInToMethod(node).asInstanceOf[nodes.CfgNode]

      case node: nodes.MethodParameterOut =>
        val method = ExpandTo.parameterInToMethod(node)
        val methodReturn = ExpandTo.methodToFormalReturn(method)
        methodReturn.asInstanceOf[nodes.CfgNode]

      case node: nodes.Call if MemberAccess.isGenericMemberAccessName(node.name) =>
        ExpandTo.argumentToCallOrReturn(node)

      case node: nodes.Call         => node
      case node: nodes.ImplicitCall => node
      case node: nodes.MethodReturn => node
      case node: nodes.Expression   => node
    }
}

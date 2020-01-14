package io.shiftleft.semanticcpg.language.nodemethods

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.semanticcpg.utils.{ExpandTo, MemberAccess}
import io.shiftleft.semanticcpg.language._

object TrackingPointToCfgNode {
  def apply(node: nodes.TrackingPointBase): nodes.CfgNode =
    node match {
      case node: nodes.Identifier => node.parentExpression
      case node: nodes.MethodRef  => node.parentExpression
      case node: nodes.Literal    => node.parentExpression

      case node: nodes.MethodParameterIn =>
        ExpandTo.parameterInToMethod(node).asInstanceOf[nodes.CfgNode]

      case node: nodes.MethodParameterOut =>
        val method = ExpandTo.parameterInToMethod(node)
        val methodReturn = ExpandTo.methodToFormalReturn(method)
        methodReturn.asInstanceOf[nodes.CfgNode]

      case node: nodes.Call if MemberAccess.isGenericMemberAccessName(node.name) =>
        node.parentExpression

      case node: nodes.Call         => node
      case node: nodes.ImplicitCall => node
      case node: nodes.MethodReturn => node
      case node: nodes.Expression   => node
    }
}

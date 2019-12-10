package io.shiftleft.dataflowengine.language.nodemethods

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.dataflowengine.language.TrackingPoint
import io.shiftleft.semanticcpg.language._
import io.shiftleft.semanticcpg.utils.{ExpandTo, MemberAccess}
import io.shiftleft.dataflowengine.language._

class TrackingPointMethods(val node: nodes.TrackingPointBase) extends AnyVal {

  /**
    * Convert to nearest CFG node for flow pretty printing
    * */
  def cfgNode: nodes.CfgNode =
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

  /**
    * Convert to nearest AST node
    * */
  def astNode: nodes.AstNode = {
    node match {
      case n: nodes.AstNode               => n
      case n: nodes.DetachedTrackingPoint => n.cfgNode
      case _                              => ??? //TODO markus/fabs?
    }
  }

  def reachableBy[NodeType <: nodes.TrackingPoint](sourceTravs: Steps[NodeType]*): Steps[NodeType] =
    node.start.reachableBy(sourceTravs: _*)

}

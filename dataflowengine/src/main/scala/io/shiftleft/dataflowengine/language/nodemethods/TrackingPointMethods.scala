package io.shiftleft.dataflowengine.language.nodemethods

import io.shiftleft.codepropertygraph.generated.{NodeTypes, nodes}
import io.shiftleft.semanticcpg.language._
import io.shiftleft.semanticcpg.language.nodemethods.TrackingPointToCfgNode
import io.shiftleft.dataflowengine.language._

class TrackingPointMethods(val node: nodes.TrackingPointBase) extends AnyVal {

  /**
    * Convert to nearest CFG node for flow pretty printing
    * */
  def cfgNode: nodes.CfgNode = TrackingPointToCfgNode(node)

  /**
    * Convert to nearest AST node
    * */
  def astNode: nodes.AstNode =
    node match {
      case n: nodes.AstNode               => n
      case n: nodes.DetachedTrackingPoint => n.cfgNode
      case _                              => ??? //TODO markus/fabs?
    }

  def reachableBy[NodeType <: nodes.TrackingPoint](sourceTravs: Steps[NodeType]*): Steps[NodeType] =
    node.start.reachableBy(sourceTravs: _*)

}

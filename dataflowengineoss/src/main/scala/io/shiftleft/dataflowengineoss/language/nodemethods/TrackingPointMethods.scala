package io.shiftleft.dataflowengineoss.language.nodemethods

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.dataflowengineoss.queryengine.EngineContext
import io.shiftleft.semanticcpg.language.nodemethods.TrackingPointToCfgNode
import overflowdb.traversal.Traversal
import overflowdb.traversal._
import io.shiftleft.dataflowengineoss.language._

class TrackingPointMethods[NodeType <: nodes.TrackingPoint](val node: NodeType) extends AnyVal {

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

  def reachableBy[NodeType <: nodes.TrackingPoint](sourceTravs: Traversal[NodeType]*)(
      implicit context: EngineContext): Traversal[NodeType] =
    node.start.reachableBy(sourceTravs: _*)

}

package io.shiftleft.dataflowengine.language.nodemethods

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.dataflowengine.language.TrackingPoint
import io.shiftleft.semanticcpg.language._
import io.shiftleft.dataflowengine.language._
import io.shiftleft.semanticcpg.language.nodemethods.generalizations.TrackingPointToCfgNode

class TrackingPointMethods(val node: nodes.TrackingPointBase) extends AnyVal {

  /**
    * Convert to nearest CFG node for flow pretty printing
    * */
  def cfgNode: nodes.CfgNode = {
    node.accept(TrackingPointToCfgNode)
  }

  /**
    * Convert to nearest AST node
    * */
  def astNode: nodes.AstNode = {
    node match {
      case n: nodes.AstNode               => n
      case n: nodes.DetachedTrackingPoint => n.cfgNode
    }
  }

  def reachableBy(sourceTravs: NodeSteps[nodes.TrackingPoint]*): TrackingPoint =
    node.start.reachableBy(sourceTravs: _*)

}

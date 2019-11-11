package io.shiftleft.dataflowengine.language.nodemethods

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.dataflowengine.language.TrackingPoint
import io.shiftleft.semanticcpg.language._
import io.shiftleft.dataflowengine.language._
import io.shiftleft.semanticcpg.language.nodemethods.generalizations.TrackingPointToCfgNode

class TrackingPointMethods(val node: nodes.TrackingPointBase) extends AnyVal {

  /**
    * Convert to nearest CFG node
    * */
  def cfgNode: nodes.CfgNode = {
    node.accept(TrackingPointToCfgNode)
  }

  def reachableBy(sourceTravs: NodeSteps[nodes.TrackingPoint]*): TrackingPoint =
    node.start.reachableBy(sourceTravs: _*)

}

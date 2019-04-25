package io.shiftleft.passes.dataflows.steps

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.queryprimitives.steps.visitormixins.TrackPointToCfgNode

class TrackingPointMethods(val node: nodes.TrackingPointBase) extends AnyVal {
  def cfgNode: nodes.CfgNode = {
    node.accept(TrackPointToCfgNode)
  }
}

package io.shiftleft.passes.dataflows.steps

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.semanticcpg.language.nodemethods.generalizations.TrackingPointToCfgNode

class TrackingPointMethods(val node: nodes.TrackingPointBase) extends AnyVal {
  def cfgNode: nodes.CfgNode = {
    node.accept(TrackingPointToCfgNode)
  }
}

package io.shiftleft.dataflowengine.language

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.semanticcpg.language.nodemethods.generalizations.TrackingPointToCfgNode

class TrackingPointMethods(val node: nodes.TrackingPointBase) extends AnyVal {
  def cfgNode: nodes.CfgNode = {
    node.accept(TrackingPointToCfgNode)
  }
}

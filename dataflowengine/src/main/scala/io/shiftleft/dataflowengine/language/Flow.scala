package io.shiftleft.dataflowengine.language

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.semanticcpg.language.Steps

class Flow(step: Steps[List[nodes.TrackingPoint]]) {
  def p(): List[String] = {
    step.l.map { flow =>
      FlowPrettyPrinter.prettyPrint(flow)
    }
  }

  def l(): List[List[nodes.TrackingPoint]] = {
    step.l
  }
}

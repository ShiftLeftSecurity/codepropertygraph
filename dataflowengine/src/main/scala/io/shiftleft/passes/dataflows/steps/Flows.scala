package io.shiftleft.passes.dataflows.steps

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.semanticcpg.language.Steps

class Flows(step: Steps[List[nodes.TrackingPoint], _]) {
  def p(): List[String] = {
    step.l.map { flow =>
      FlowPrettyPrinter.prettyPrint(flow)
    }
  }

  def l(): List[List[nodes.TrackingPoint]] = {
    step.l
  }
}

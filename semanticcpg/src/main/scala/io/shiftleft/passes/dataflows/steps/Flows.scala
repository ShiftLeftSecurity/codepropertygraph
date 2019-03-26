package io.shiftleft.passes.dataflows.steps
import gremlin.scala.Vertex
import io.shiftleft.queryprimitives.steps.Steps
import io.shiftleft.codepropertygraph.generated.nodes

class Flows(step: Steps[List[nodes.DataFlowObject], _]) {
  def p(): List[String] = {
    step.l.map { flow =>
      FlowPrettyPrinter.prettyPrint(flow)
    }
  }

  def l(): List[List[nodes.DataFlowObject]] = {
    step.l
  }
}

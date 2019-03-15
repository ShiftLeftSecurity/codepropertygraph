package io.shiftleft.passes.dataflows.steps
import gremlin.scala.Vertex
import gremlin.scala.dsl.Steps
import io.shiftleft.codepropertygraph.generated.nodes

class Flows(step: Steps[List[nodes.DataFlowObject], List[Vertex], _]) {
  def p(): List[String] = {
    step.l.map { flow =>
      FlowPrettyPrinter.prettyPrint(flow)
    }
  }
}

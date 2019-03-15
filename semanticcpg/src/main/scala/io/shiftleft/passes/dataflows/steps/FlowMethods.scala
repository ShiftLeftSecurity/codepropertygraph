package io.shiftleft.passes.dataflows.steps
import gremlin.scala.Vertex
import gremlin.scala.dsl.Steps
import io.shiftleft.codepropertygraph.generated.nodes

class FlowMethods(step: Steps[List[nodes.DataFlowObject], List[Vertex], _]) {
  def p(): Unit = {
    step.l.foreach { flow =>
      FlowPrettyPrinter.prettyPrint(flow)
    }
  }
}

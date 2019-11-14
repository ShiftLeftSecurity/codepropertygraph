package io.shiftleft.dataflowengine.language

import gremlin.scala.GremlinScala
import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.semanticcpg.language.NewNodeSteps

class NewFlow(raw: GremlinScala[nodes.NewFlow]) extends NewNodeSteps(raw) {
  override  def p(): List[String] = {
    raw.l.map(x => x.points.map(_.elem)).map{ flow =>
      FlowPrettyPrinter.prettyPrint(flow)
    }
  }

}

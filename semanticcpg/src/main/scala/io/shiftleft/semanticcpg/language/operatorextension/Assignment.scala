package io.shiftleft.semanticcpg.language.operatorextension

import gremlin.scala.GremlinScala
import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.semanticcpg.language._

class Assignment(val wrapped: Steps[opnodes.Assignment]) extends AnyVal {
  private def raw: GremlinScala[opnodes.Assignment] = wrapped.raw
  def target: NodeSteps[nodes.Expression] = wrapped.map(_.target)
  def source: NodeSteps[nodes.Expression] = wrapped.map(_.source)
}

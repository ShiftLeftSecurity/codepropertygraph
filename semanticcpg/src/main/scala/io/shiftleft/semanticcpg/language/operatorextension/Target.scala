package io.shiftleft.semanticcpg.language.operatorextension

import gremlin.scala.GremlinScala
import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.semanticcpg.language._

class Target(val wrapped: NodeSteps[nodes.Expression]) extends AnyVal {
  private def raw: GremlinScala[nodes.Expression] = wrapped.raw
  def isArrayAccess: NodeSteps[opnodes.ArrayAccess] = wrapped.flatMap(_.isArrayAccess)
}

package io.shiftleft.semanticcpg.language.operatorextension

import gremlin.scala.GremlinScala
import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.semanticcpg.language._

class TargetTrav(override val raw: GremlinScala[nodes.Expression]) extends NodeSteps[nodes.Expression](raw) {

  def isArrayAccess: NodeSteps[nodes.Call] =
    new NodeSteps(raw.map(_.isArrayAccess.l).l.flatten.start.raw)

  def expr: NodeSteps[nodes.Expression] = map(_.expr)

}

package io.shiftleft.semanticcpg.language.operatorextension

import gremlin.scala.GremlinScala
import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.semanticcpg.language.Steps

/**
  * A wrapper for assignment calls that offers syntactic sugar
  * */
class AssignmentTrav(override val raw: GremlinScala[nodes.Call]) extends Steps[nodes.Call](raw) {
  def target: TargetTrav = new TargetTrav(map(_.target).raw)
  def source: Steps[nodes.Expression] = map(_.source)
}

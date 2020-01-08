package io.shiftleft.semanticcpg.language.operatorextension

import gremlin.scala.GremlinScala

import io.shiftleft.codepropertygraph.generated.nodes.{Call, Expression}
import io.shiftleft.semanticcpg.language.Steps

/**
  * A wrapper for assignment calls that offers syntactic sugar
  * */
class AssignmentTrav(override val raw: GremlinScala[Call]) extends Steps[Call](raw) {

  def target: TargetTrav = new TargetTrav(map(_.target).raw)

  def source: Steps[Expression] = map(_.source)
}

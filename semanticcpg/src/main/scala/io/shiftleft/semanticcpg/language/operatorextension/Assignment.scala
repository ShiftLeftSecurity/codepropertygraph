package io.shiftleft.semanticcpg.language.operatorextension

import gremlin.scala.GremlinScala
import io.shiftleft.codepropertygraph.generated.{nodes => basenodes}
import io.shiftleft.semanticcpg.language.NodeSteps

/**
  * A wrapper for assignment calls that offers syntactic sugar
  * */
class Assignment(override val raw: GremlinScala[basenodes.Call]) extends NodeSteps[basenodes.Call](raw) {

  def target: AssignTarget = new AssignTarget(new Assignment(raw).argument(1).raw)

}

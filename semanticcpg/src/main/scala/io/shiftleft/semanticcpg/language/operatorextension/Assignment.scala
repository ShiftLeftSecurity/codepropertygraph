package io.shiftleft.semanticcpg.language.operatorextension

import gremlin.scala.GremlinScala
import io.shiftleft.semanticcpg.language.Steps

/**
  * A wrapper for assignment calls that offers syntactic sugar
  * */
class Assignment(override val raw: GremlinScala[nodes.Assignment]) extends Steps[nodes.Assignment](raw) {

  def target: Target = new Target(map(_.target).map(new nodes.Target(_)).raw)

}

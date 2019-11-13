package io.shiftleft.semanticcpg.language.operatorextension

import gremlin.scala.GremlinScala
import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.semanticcpg.language.Steps
import io.shiftleft.semanticcpg.language.types.expressions.generalizations.Expression

/**
  * A wrapper for assignment calls that offers syntactic sugar
  * */
class Assignment(override val raw: GremlinScala[nodes.Call]) extends Steps[nodes.Call](raw) {

  def target: Expression = new Assignment(raw).argument(1)

}

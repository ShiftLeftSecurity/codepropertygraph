package io.shiftleft.semanticcpg.language.operatorextension

import gremlin.scala.GremlinScala
import io.shiftleft.codepropertygraph.generated.nodes.Expression
import io.shiftleft.semanticcpg.language.Steps

class AssignSource(override val raw: GremlinScala[nodes.AssignSource]) extends Steps[nodes.AssignSource](raw) {

  def expr: Steps[Expression] = map(_.expr)

}

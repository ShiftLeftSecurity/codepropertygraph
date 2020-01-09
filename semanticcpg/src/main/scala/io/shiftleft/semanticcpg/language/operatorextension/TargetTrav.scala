package io.shiftleft.semanticcpg.language.operatorextension

import gremlin.scala.GremlinScala
import io.shiftleft.codepropertygraph.generated.nodes.Expression
import io.shiftleft.semanticcpg.language.Steps
import io.shiftleft.semanticcpg.language._

class TargetTrav(override val raw: GremlinScala[Expression]) extends Steps[Expression](raw) {

  def isArrayAccess: ArrayAccessTrav =
    new ArrayAccessTrav(raw.map(_.isArrayAccess.l).l.flatten.start.raw)

  def expr: Steps[Expression] = map(_.expr)

}

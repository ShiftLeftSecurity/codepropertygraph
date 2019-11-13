package io.shiftleft.semanticcpg.language.operatorextension

import gremlin.scala.GremlinScala
import io.shiftleft.codepropertygraph.generated.nodes.Expression
import io.shiftleft.semanticcpg.language.Steps
import io.shiftleft.semanticcpg.language._

class Target(override val raw: GremlinScala[nodes.Target]) extends Steps[nodes.Target](raw) {

  def isArrayAccess: ArrayAccess =
    new ArrayAccess(raw.map(_.isArrayAccess.call.l).l.flatten.start.map(new nodes.ArrayAccess(_)).raw)

  def expr: Steps[Expression] = map(_.expr)

}

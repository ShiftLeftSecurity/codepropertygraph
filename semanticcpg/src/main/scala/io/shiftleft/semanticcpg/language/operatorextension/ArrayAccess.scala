package io.shiftleft.semanticcpg.language.operatorextension

import gremlin.scala.GremlinScala
import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.semanticcpg.language._

class ArrayAccess(val wrapped: Steps[opnodes.ArrayAccess]) extends AnyVal {
  private def raw: GremlinScala[opnodes.ArrayAccess] = wrapped.raw
  def array: NodeSteps[nodes.Expression] = wrapped.map(_.array)
  def subscripts: NodeSteps[nodes.Identifier] = wrapped.flatMap(_.subscripts)
}

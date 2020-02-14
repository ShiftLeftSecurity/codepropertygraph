package io.shiftleft.semanticcpg.language.operatorextension

import gremlin.scala.GremlinScala
import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.semanticcpg.language._

class ArrayAccessTrav(raw: GremlinScala[nodes.Call]) extends NodeSteps[nodes.Call](raw) {
  def array: NodeSteps[nodes.Expression] = map(_.array)
  def subscripts: NodeSteps[nodes.Identifier] = flatMap(_.subscripts)
}

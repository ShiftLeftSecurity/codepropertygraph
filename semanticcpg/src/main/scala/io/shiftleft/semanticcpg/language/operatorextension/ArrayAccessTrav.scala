package io.shiftleft.semanticcpg.language.operatorextension

import gremlin.scala.GremlinScala
import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.codepropertygraph.generated.nodes.Call
import io.shiftleft.semanticcpg.language.Steps
import io.shiftleft.semanticcpg.language._

class ArrayAccessTrav(raw: GremlinScala[Call]) extends Steps[Call](raw) {

  def array: NodeSteps[nodes.Expression] =
    map(_.array)

  def subscripts: Steps[NodeSteps[nodes.Identifier]] =
    map(_.subscripts)


}

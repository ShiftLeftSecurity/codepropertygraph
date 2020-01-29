package io.shiftleft.semanticcpg.language.operatorextension

import gremlin.scala.GremlinScala

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.semanticcpg.language.types.expressions.Identifier
import io.shiftleft.semanticcpg.language.types.expressions.generalizations.Expression
import io.shiftleft.semanticcpg.language.Steps
import io.shiftleft.semanticcpg.language._

class ArrayAccessTrav(raw: GremlinScala[nodes.Call]) extends Steps[nodes.Call](raw) {

  def array: Expression[nodes.Expression] = map(_.array)

  def subscripts: Steps[Identifier] = map(_.subscripts)

}

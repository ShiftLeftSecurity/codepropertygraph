package io.shiftleft.semanticcpg.language.operatorextension

import gremlin.scala.GremlinScala
import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.semanticcpg.language.types.expressions.Identifier
import io.shiftleft.semanticcpg.language.types.expressions.generalizations.Expression
import io.shiftleft.semanticcpg.language.Steps
import io.shiftleft.semanticcpg.language._

class ArrayAccess(raw: GremlinScala[ArrayAccessNode]) extends Steps[ArrayAccessNode](raw) {

  def array: Expression = map(_.array)

  def subscripts: Steps[Identifier] = map(_.subscripts)

}

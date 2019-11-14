package io.shiftleft.semanticcpg.language.operatorextension

import gremlin.scala.GremlinScala
import io.shiftleft.semanticcpg.language.types.expressions.Identifier
import io.shiftleft.semanticcpg.language.types.expressions.generalizations.Expression
import io.shiftleft.semanticcpg.language.Steps
import io.shiftleft.semanticcpg.language._
import io.shiftleft.codepropertygraph.generated.{nodes => basenodes}

class ArrayAccessTrav(raw: GremlinScala[nodes.ArrayAccess]) extends Steps[nodes.ArrayAccess](raw) {

  def call: Steps[basenodes.Call] = map(_.call)

  def array: Expression = map(_.array)

  def subscripts: Steps[Identifier] = map(_.subscripts)

}

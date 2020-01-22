package io.shiftleft.semanticcpg.language.operatorextension

import gremlin.scala.GremlinScala
import io.shiftleft.codepropertygraph.generated.nodes.{Call, HasArgumentIndex, StoredNode}
import io.shiftleft.semanticcpg.language.types.expressions.Identifier
import io.shiftleft.semanticcpg.language.Steps
import io.shiftleft.semanticcpg.language._

class ArrayAccessTrav(raw: GremlinScala[Call]) extends Steps[Call](raw) {

  def array: Steps[StoredNode with HasArgumentIndex] =
    map(_.array)

  def subscripts: Steps[Identifier] =
    map(_.subscripts)

}

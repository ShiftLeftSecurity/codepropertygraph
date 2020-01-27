package io.shiftleft.semanticcpg.language.types.expressions

import gremlin.scala._
import io.shiftleft.codepropertygraph.generated.EdgeTypes
import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.semanticcpg.language._
import io.shiftleft.semanticcpg.language.types.propertyaccessors._

/**
  An identifier, e.g., an instance of a local variable, or a temporary variable
  */
class Identifier[A <: nodes.Identifier](raw: GremlinScala[A]) extends NodeSteps[A](raw) with EvalTypeAccessors[A] {

  /**
    * Traverse to all declarations of this identifier
    * */
  def refsTo: NodeSteps[nodes.Declaration] =
    new NodeSteps(raw.out(EdgeTypes.REF).cast[nodes.Declaration])

}

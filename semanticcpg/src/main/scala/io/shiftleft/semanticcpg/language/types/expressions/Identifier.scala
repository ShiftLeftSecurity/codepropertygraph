package io.shiftleft.semanticcpg.language.types.expressions

import gremlin.scala._
import io.shiftleft.codepropertygraph.generated.EdgeTypes
import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.semanticcpg.language._
import io.shiftleft.semanticcpg.language.types.propertyaccessors._
import io.shiftleft.semanticcpg.language.types.expressions.generalizations._
import shapeless.HList

/**
  An identifier, e.g., an instance of a local variable, or a temporary variable
  */
class Identifier[Labels <: HList](raw: GremlinScala.Aux[nodes.Identifier, Labels])
    extends NodeSteps[nodes.Identifier, Labels](raw)
    with ExpressionBase[nodes.Identifier, Labels]
    with CodeAccessors[nodes.Identifier, Labels]
    with NameAccessors[nodes.Identifier, Labels]
    with OrderAccessors[nodes.Identifier, Labels]
    with LineNumberAccessors[nodes.Identifier, Labels]
    with EvalTypeAccessors[nodes.Identifier, Labels] {

  /**
    * Traverse to all declarations of this identifier
    * */
  def refsTo: Declaration[Labels] =
    new Declaration[Labels](raw.out(EdgeTypes.REF).cast[nodes.Declaration])

}

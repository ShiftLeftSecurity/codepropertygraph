package io.shiftleft.queryprimitives.steps.types.expressions

import gremlin.scala._
import io.shiftleft.codepropertygraph.generated.EdgeTypes
import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.queryprimitives.steps.NodeSteps
import io.shiftleft.queryprimitives.steps.Implicits.GremlinScalaDeco
import io.shiftleft.queryprimitives.steps.types.propertyaccessors._
import io.shiftleft.queryprimitives.steps.types.expressions.generalizations._
import io.shiftleft.queryprimitives.steps.types.structure.Method
import shapeless.HList

/**
  An identifier, e.g., an instance of a local variable, or a temporary variable
  */
class Identifier[Labels <: HList](raw: GremlinScala.Aux[nodes.IdentifierRef, Labels])
    extends NodeSteps[nodes.IdentifierRef, Labels](raw)
    with ExpressionBase[nodes.IdentifierRef, Labels]
    with CodeAccessors[nodes.IdentifierRef, Labels]
    with NameAccessors[nodes.IdentifierRef, Labels]
    with OrderAccessors[nodes.IdentifierRef, Labels]
    with LineNumberAccessors[nodes.IdentifierRef, Labels]
    with EvalTypeAccessors[nodes.IdentifierRef, Labels] {

  /**
    * Traverse to all declarations of this identifier
    * */
  def refsTo: Declaration[Labels] =
    new Declaration[Labels](raw.out(EdgeTypes.REF).cast[nodes.Declaration])

}

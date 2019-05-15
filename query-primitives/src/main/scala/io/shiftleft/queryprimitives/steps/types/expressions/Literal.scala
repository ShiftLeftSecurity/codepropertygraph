package io.shiftleft.queryprimitives.steps.types.expressions

import gremlin.scala._
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeTypes, nodes}
import io.shiftleft.queryprimitives.steps.{NodeSteps, Steps}
import io.shiftleft.queryprimitives.steps.Implicits.GremlinScalaDeco
import io.shiftleft.queryprimitives.steps.types.expressions.generalizations._
import io.shiftleft.queryprimitives.steps.types.propertyaccessors._
import io.shiftleft.queryprimitives.steps.types.structure.Method
import shapeless.HList

/**
  A literal, e.g., a constant string or number
  */
class Literal[Labels <: HList](raw: GremlinScala.Aux[nodes.LiteralRef, Labels])
    extends NodeSteps[nodes.LiteralRef, Labels](raw)
    with ExpressionBase[nodes.LiteralRef, Labels]
    with CodeAccessors[nodes.LiteralRef, Labels]
    with LineNumberAccessors[nodes.LiteralRef, Labels]
    with EvalTypeAccessors[nodes.LiteralRef, Labels] {

  /**
    * Traverse to method hosting this literal
    * */
  override def method: Method[Labels] =
    new Method[Labels](
      raw
        .cast[nodes.StoredNode]
        .repeat(_.in(EdgeTypes.AST).cast[nodes.StoredNode])
        .until(_.hasLabel(NodeTypes.METHOD))
        .cast[nodes.MethodRef])

}

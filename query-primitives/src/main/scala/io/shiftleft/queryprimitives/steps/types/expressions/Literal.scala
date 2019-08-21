package io.shiftleft.queryprimitives.steps.types.expressions

import gremlin.scala._
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeTypes, nodes}
import io.shiftleft.queryprimitives.Implicits.GremlinScalaDeco
import io.shiftleft.queryprimitives.steps.{NodeSteps, Steps}
import io.shiftleft.queryprimitives.steps.types.expressions.generalizations._
import io.shiftleft.queryprimitives.steps.types.propertyaccessors._
import io.shiftleft.queryprimitives.steps.types.structure.Method
import shapeless.HList

/**
  A literal, e.g., a constant string or number
  */
class Literal[Labels <: HList](raw: GremlinScala.Aux[nodes.Literal, Labels])
    extends NodeSteps[nodes.Literal, Labels](raw)
    with ExpressionBase[nodes.Literal, Labels]
    with CodeAccessors[nodes.Literal, Labels]
    with LineNumberAccessors[nodes.Literal, Labels]
    with EvalTypeAccessors[nodes.Literal, Labels] {

  /**
    * Traverse to method hosting this literal
    * */
  override def method: Method[Labels] =
    new Method[Labels](
      raw
        .cast[nodes.StoredNode]
        .repeat(_.in(EdgeTypes.AST).cast[nodes.StoredNode])
        .until(_.hasLabel(NodeTypes.METHOD))
        .cast[nodes.Method])

}

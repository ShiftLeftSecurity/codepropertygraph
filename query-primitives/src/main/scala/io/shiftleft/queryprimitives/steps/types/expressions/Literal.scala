package io.shiftleft.queryprimitives.steps.types.expressions

import gremlin.scala._
import gremlin.scala.dsl.Converter
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeTypes, nodes}
import io.shiftleft.queryprimitives.steps.CpgSteps
import io.shiftleft.queryprimitives.steps.types.expressions.generalizations._
import io.shiftleft.queryprimitives.steps.types.propertyaccessors._
import io.shiftleft.queryprimitives.steps.types.structure.Method
import shapeless.HList

/**
  A literal, e.g., a constant string or number
  */
class Literal[Labels <: HList](raw: GremlinScala[Vertex])
    extends CpgSteps[nodes.Literal, Labels](raw)
    with DeclarationBase[nodes.Literal, Labels]
    with ExpressionBase[nodes.Literal, Labels]
    with CodeAccessors[nodes.Literal, Labels]
    with NameAccessors[nodes.Literal, Labels]
    with LineNumberAccessors[nodes.Literal, Labels]
    with EvalTypeAccessors[nodes.Literal, Labels] {
  override val converter = Converter.forDomainNode[nodes.Literal]

  /**
    * Traverse to method hosting this literal
    * */
  override def method: Method[Labels] =
    new Method[Labels](
      raw
        .repeat(_.in(EdgeTypes.AST))
        .until(_.hasLabel(NodeTypes.METHOD))
    )

}

package io.shiftleft.semanticcpg.language.types.expressions

import gremlin.scala._
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeTypes, nodes}
import io.shiftleft.semanticcpg.language._
import io.shiftleft.semanticcpg.language.types.propertyaccessors._

/**
  A literal, e.g., a constant string or number
  */
class Literal[A <: nodes.Literal](raw: GremlinScala[A]) extends NodeSteps[A](raw) with EvalTypeAccessors[A] {

  /**
    * Traverse to method hosting this literal
    * */
  def method: NodeSteps[nodes.Method] =
    new NodeSteps(
      raw
        .cast[nodes.StoredNode]
        .repeat(_.in(EdgeTypes.AST).cast[nodes.StoredNode])
        .until(_.hasLabel(NodeTypes.METHOD))
        .cast[nodes.Method])

}

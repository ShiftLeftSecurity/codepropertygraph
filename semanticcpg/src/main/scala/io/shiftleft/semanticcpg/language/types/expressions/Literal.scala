package io.shiftleft.semanticcpg.language.types.expressions

import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeTypes, nodes}
import io.shiftleft.semanticcpg.language._

/**
  A literal, e.g., a constant string or number
  */
class Literal(val wrapped: NodeSteps[nodes.Literal]) extends AnyVal {

  /**
    * Traverse to method hosting this literal
    * */
  def method: NodeSteps[nodes.Method] =
    new NodeSteps(
      wrapped.raw
        .repeat(_.in(EdgeTypes.AST).cast[nodes.StoredNode])
        .until(_.hasLabel(NodeTypes.METHOD))
        .cast[nodes.Method])

}

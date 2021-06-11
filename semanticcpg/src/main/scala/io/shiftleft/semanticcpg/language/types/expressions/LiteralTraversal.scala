package io.shiftleft.semanticcpg.language.types.expressions

import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeTypes, nodes}
import overflowdb.traversal.Traversal

/**
  A literal, e.g., a constant string or number
  */
class LiteralTraversal(val traversal: Traversal[nodes.Literal]) extends AnyVal {

  /**
    * Traverse to method hosting this literal
    * */
  def method: Traversal[nodes.Method] =
    traversal
      .repeat(_.in(EdgeTypes.AST))(_.until(_.hasLabel(NodeTypes.METHOD)))
      .cast[nodes.Method]

}

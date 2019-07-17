package io.shiftleft.queryprimitives.steps

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.queryprimitives.steps.types.expressions.generalizations.AstNode
import shapeless.HNil

class AstNodeMethods(val node: nodes.AstNode) extends AnyVal {

  // Shorthands to allow ast/children to be executed
  // on single nodes.

  def ast: AstNode[HNil] = node.start.ast

  def children: AstNode[HNil] = node.start.astMinusRoot

}

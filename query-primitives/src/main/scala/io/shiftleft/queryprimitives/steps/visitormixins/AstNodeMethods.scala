package io.shiftleft.queryprimitives.steps.visitormixins

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.queryprimitives.steps.types.expressions.generalizations.AstNode
import shapeless.HNil

/**
  * These methods are defined on single nodes. They are shorthands, which
  * forward to the corresponding Step class.
  * */
class AstNodeMethods(val node: nodes.AstNode) extends AnyVal {

  /**
    * All nodes of the AST rooted in this node
    * */
  def ast: AstNode[HNil] = node.start.ast

  /**
    * All nodes of the AST rooted in this node, excluding the root node
    * */
  def astMinusRoot: AstNode[HNil] = node.start.astMinusRoot

}

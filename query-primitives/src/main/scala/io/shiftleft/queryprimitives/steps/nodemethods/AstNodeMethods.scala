package io.shiftleft.queryprimitives.steps.nodemethods

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.queryprimitives.Implicits._
import io.shiftleft.queryprimitives.steps.types.expressions.generalizations.AstNode
import shapeless.HNil

class AstNodeMethods(val node: nodes.AstNode) extends AnyVal {

  /**
    * All nodes of the abstract syntax tree rooted in this node
    * */
  def ast: AstNode[HNil] = node.start.ast

  /**
    * All nodes of the abstract syntax tree rooted in this node,
    * minus this node.
    * */
  def astMinusRoot: AstNode[HNil] = node.start.astMinusRoot

}

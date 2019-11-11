package io.shiftleft.semanticcpg.language.nodemethods

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.semanticcpg.language.types.expressions.generalizations.AstNode

import io.shiftleft.semanticcpg.language._

class AstNodeMethods(val node: nodes.AstNode) extends AnyVal {

  /**
    * All nodes of the abstract syntax tree rooted in this node
    * */
  def ast: AstNode = node.start.ast

  /**
    * All nodes of the abstract syntax tree rooted in this node,
    * minus this node.
    * */
  def astMinusRoot: AstNode = node.start.astMinusRoot

  /**
    * Indicate whether the AST node represents a control structure,
    * e.g., `if`, `for`, `while`.
    * */
  def isControlStructure: Boolean = node.start.isControlStructure.size == 1

  /**
    * The depth of the AST rooted in this node. Upon walking
    * the tree to its leaves, the depth is only increased for
    * nodes where `p(node)` is true.
    * */
  def depth(p: nodes.AstNode => Boolean = _ => true): Int = {

    val additionalDepth = if (p(node)) { 1 } else { 0 }

    val childDepths = node.start.astChildren.map(_.depth(p)).l

    additionalDepth + (if (childDepths.isEmpty) {
                         0
                       } else {
                         childDepths.max
                       })
  }

}

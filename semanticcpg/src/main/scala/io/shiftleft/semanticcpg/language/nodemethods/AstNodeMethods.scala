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

  def isControlStructure : Boolean = node.start.isControlStructure.size == 1

  def depth(p : nodes.AstNode => Boolean = _ => true) : Int = {

    val additionalDepth = if (p(node)) { 1 } else { 0 }

    val childDepths = node.start.astChildren.map(_.depth(p)).l

    additionalDepth + (if (childDepths.isEmpty) {
      0
    } else {
      childDepths.max
    })
  }

}

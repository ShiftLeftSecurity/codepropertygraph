package io.shiftleft.semanticcpg.language.nodemethods

import io.shiftleft.Implicits.JavaIteratorDeco
import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.semanticcpg.language._

class AstNodeMethods(val node: nodes.AstNode) extends AnyVal {

  /**
    * All nodes of the abstract syntax tree rooted in this node
    * */
  def ast: NodeSteps[nodes.AstNode] = node.start.ast

  /**
    * All nodes of the abstract syntax tree rooted in this node,
    * minus this node.
    * */
  def astMinusRoot: NodeSteps[nodes.AstNode] = node.start.astMinusRoot

  /**
    * Indicate whether the AST node represents a control structure,
    * e.g., `if`, `for`, `while`.
    * */
  def isControlStructure: Boolean = node.isInstanceOf[nodes.ControlStructure]

  def isIdentifier: Boolean = node.isInstanceOf[nodes.Identifier]

  def isFieldIdentifier: Boolean = node.isInstanceOf[nodes.FieldIdentifier]

  def isReturn: Boolean = node.isInstanceOf[nodes.Return]

  def isLiteral: Boolean = node.isInstanceOf[nodes.Literal]

  def isCall: Boolean = node.isInstanceOf[nodes.Call]

  def isExpression: Boolean = node.isInstanceOf[nodes.Expression]

  def isMethodRef: Boolean = node.isInstanceOf[nodes.MethodRef]

  def isBlock: Boolean = node.isInstanceOf[nodes.Block]

  def depth: Int = depth(_ => true)

  /**
    * The depth of the AST rooted in this node. Upon walking
    * the tree to its leaves, the depth is only increased for
    * nodes where `p(node)` is true.
    * */
  def depth(p: nodes.AstNode => Boolean): Int = {
    val additionalDepth = if (p(node)) { 1 } else { 0 }

    val childDepths = node.start.astChildren.map(_.depth(p)).l
    additionalDepth + (if (childDepths.isEmpty) {
                         0
                       } else {
                         childDepths.max
                       })
  }

  def astParent: nodes.AstNode =
    node._astIn.onlyChecked.asInstanceOf[nodes.AstNode]

}

package io.shiftleft.semanticcpg.language.nodemethods

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.Implicits.JavaIteratorDeco
import io.shiftleft.semanticcpg.language.types.expressions.generalizations.AstNode
import io.shiftleft.semanticcpg.language._
import io.shiftleft.semanticcpg.utils.MemberAccess

import scala.annotation.tailrec

class AstNodeMethods(val node: nodes.AstNode) extends AnyVal {

  /**
    * All nodes of the abstract syntax tree rooted in this node
    * */
  def ast: AstNode[nodes.AstNode] = node.start.ast

  /**
    * All nodes of the abstract syntax tree rooted in this node,
    * minus this node.
    * */
  def astMinusRoot: AstNode[nodes.AstNode] = node.start.astMinusRoot

  /**
    * Indicate whether the AST node represents a control structure,
    * e.g., `if`, `for`, `while`.
    * */
  def isControlStructure: Boolean = node.start.isControlStructure.size == 1

  def isIdentifier: Boolean = node.start.isIdentifier.size == 1

  def isReturn: Boolean = node.start.isReturn.size == 1

  def isLiteral: Boolean = node.start.isLiteral.size == 1

  def isCall: Boolean = node.start.isCall.size == 1

  def isExpression: Boolean = node.start.isExpression.size == 1

  def isMethodRef: Boolean = node.start.isMethodRef.size == 1

  def isBlock: Boolean = node.start.isBlock.size == 1

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

  /**
    * Traverse to it's parent expression (e.g. call or return) by following the incoming AST nodes.
    * It's continuing it's walk until it hits an expression that's not a generic
    * "member access operation", e.g., "<operator>.memberAccess".
    * */
  def parentExpression: nodes.Expression = _parentExpression(node)

  @tailrec
  final def _parentExpression(argument: nodes.AstNode): nodes.Expression = {
    val parent = argument.asInstanceOf[nodes.StoredNode]._astIn.nextChecked
    parent match {
      case call: nodes.Call if MemberAccess.isGenericMemberAccessName(call.name) =>
        _parentExpression(call)
      case expression: nodes.Expression =>
        expression
    }
  }

  def astParent: nodes.AstNode =
    node._astIn.nextChecked.asInstanceOf[nodes.AstNode]

}

package io.shiftleft.semanticcpg.language.nodemethods

import io.shiftleft.Implicits.JavaIteratorDeco
import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.semanticcpg.language._

class AstNodeMethods(val node: nodes.AstNode) extends AnyVal {

  /**
    * Indicate whether the AST node represents a control structure,
    * e.g., `if`, `for`, `while`.
    * */
  def isControlStructure: Boolean = node.isInstanceOf[nodes.ControlStructure]

  def isIdentifier: Boolean = node.isInstanceOf[nodes.Identifier]

  def isFieldIdentifier: Boolean = node.isInstanceOf[nodes.FieldIdentifier]

  def isFile: Boolean = node.isInstanceOf[nodes.File]

  def isReturn: Boolean = node.isInstanceOf[nodes.Return]

  def isLiteral: Boolean = node.isInstanceOf[nodes.Literal]

  def isLocal: Boolean = node.isInstanceOf[nodes.Local]

  def isCall: Boolean = node.isInstanceOf[nodes.Call]

  def isExpression: Boolean = node.isInstanceOf[nodes.Expression]

  def isMember: Boolean = node.isInstanceOf[nodes.Member]

  def isMethodRef: Boolean = node.isInstanceOf[nodes.MethodRef]

  def isMethod: Boolean = node.isInstanceOf[nodes.Method]

  def isModifier: Boolean = node.isInstanceOf[nodes.Modifier]

  def isNamespaceBlock: Boolean = node.isInstanceOf[nodes.NamespaceBlock]

  def isBlock: Boolean = node.isInstanceOf[nodes.Block]

  def isParameter: Boolean = node.isInstanceOf[nodes.MethodParameterIn]

  def isTypeDecl: Boolean = node.isInstanceOf[nodes.TypeDecl]

  def depth: Int = depth(_ => true)

  /**
    * The depth of the AST rooted in this node. Upon walking
    * the tree to its leaves, the depth is only increased for
    * nodes where `p(node)` is true.
    * */
  def depth(p: nodes.AstNode => Boolean): Int = {
    val additionalDepth = if (p(node)) { 1 } else { 0 }

    val childDepths = node.astChildren.map(_.depth(p)).l
    additionalDepth + (if (childDepths.isEmpty) {
                         0
                       } else {
                         childDepths.max
                       })
  }

  def astParent: nodes.AstNode =
    node._astIn.onlyChecked.asInstanceOf[nodes.AstNode]

}

package io.shiftleft.semanticcpg.language.types.expressions.generalizations

import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeTypes, nodes}
import io.shiftleft.semanticcpg.language._
import overflowdb.traversal.help.Doc
import overflowdb.traversal.{Traversal, help}

@help.Traversal(elementType = classOf[nodes.AstNode])
class AstNode[A <: nodes.AstNode](val traversal: Traversal[A]) extends AnyVal {

  /**
    * Nodes of the AST rooted in this node, including the node itself.
    * */
  @Doc("All nodes of the abstract syntax tree")
  def ast: Traversal[nodes.AstNode] =
    traversal.repeat(_.out(EdgeTypes.AST))(_.emit).cast[nodes.AstNode]

  /**
    * All nodes of the abstract syntax tree rooted in this node,
    * which match `predicate`. Equivalent of `match` in the original
    * CPG paper.
    * */
  def ast(predicate: nodes.AstNode => Boolean): Traversal[nodes.AstNode] =
    ast.filter(predicate)

  def containsCallTo(regex: String): Traversal[A] =
    traversal.where(_.ast.isCall.name(regex).size.filter(_ > 0))

  @Doc("Depth of the abstract syntax tree")
  def depth: Traversal[Int] =
    traversal.map(_.depth)

  def depth(p: nodes.AstNode => Boolean): Traversal[Int] =
    traversal.map(_.depth(p))

  def isCallTo(regex: String): Traversal[nodes.Call] =
    isCall.name(regex)

  /**
    * Nodes of the AST rooted in this node, minus the node itself
    * */
  def astMinusRoot: Traversal[nodes.AstNode] =
    traversal.repeat(_.out(EdgeTypes.AST))(_.emitAllButFirst).cast[nodes.AstNode]

  /**
    * Direct children of node in the AST. Siblings are ordered by their `order` fields
    * */
  def astChildren: Traversal[nodes.AstNode] =
  // TODO MP add orderBy step to avoid conversion list/traversal
    traversal.out(EdgeTypes.AST).cast[nodes.AstNode].l.sortBy(_.order).to(Traversal)

  /**
    * Parent AST node
    * */
  def astParent: Traversal[nodes.AstNode] =
    traversal.in(EdgeTypes.AST).cast[nodes.AstNode]

  /**
    * Nodes of the AST obtained by expanding AST edges backwards until the method root is reached
    * */
  def inAst: Traversal[nodes.AstNode] =
    inAst(null)

  /**
    * Nodes of the AST obtained by expanding AST edges backwards until the method root is reached, minus this node
    * */
  def inAstMinusLeaf: Traversal[nodes.AstNode] =
    inAstMinusLeaf(null)

  /**
    * Nodes of the AST obtained by expanding AST edges backwards until `root` or the method root is reached
    * */
  def inAst(root: nodes.AstNode): Traversal[nodes.AstNode] =
    traversal.repeat(_.in(EdgeTypes.AST))(
      _.emit
      .until(_.or(
        _.hasLabel(NodeTypes.METHOD),
        _.filter(n => root != null && root == n)
      ))).cast[nodes.AstNode]

  /**
    * Nodes of the AST obtained by expanding AST edges backwards until `root` or the method root is reached,
    * minus this node
    * */
  def inAstMinusLeaf(root: nodes.AstNode): Traversal[nodes.AstNode] =
    traversal.repeat(_.in(EdgeTypes.AST))(
      _.emitAllButFirst
        .until(_.or(
          _.hasLabel(NodeTypes.METHOD),
          _.filter(n => root != null && root == n)
        ))).cast[nodes.AstNode]

  /**
    * Traverse only to those AST nodes that are also control flow graph nodes
    * */
  def isCfgNode: Traversal[nodes.CfgNode] =
    traversal.collect { case node: nodes.CfgNode => node }

  /**
    * Traverse only to those AST nodes that are blocks
    * */
  def isBlock: Traversal[nodes.Block] =
    traversal.hasLabel(NodeTypes.BLOCK).cast[nodes.Block]

  /**
    * Traverse only to those AST nodes that are control structures
    * */
  def isControlStructure: Traversal[nodes.ControlStructure] =
    traversal.hasLabel(NodeTypes.CONTROL_STRUCTURE).cast[nodes.ControlStructure]

  /**
    * Traverse only to AST nodes that are expressions
    * */
  def isExpression: Traversal[nodes.Expression] =
    traversal.collect { case node: nodes.Expression => node }

  /**
    * Traverse only to AST nodes that are calls
    * */
  def isCall: Traversal[nodes.Call] =
    traversal.hasLabel(NodeTypes.CALL).cast[nodes.Call]

  /**
  Cast to call if applicable and filter on call code `calleeRegex`
    */
  def isCall(calleeRegex: String): Traversal[nodes.Call] =
    isCall.where(_.code(calleeRegex))

  /**
    * Traverse only to AST nodes that are literals
    * */
  def isLiteral: Traversal[nodes.Literal] =
    traversal.hasLabel(NodeTypes.LITERAL).cast[nodes.Literal]

  def isLocal: Traversal[nodes.Local] =
    traversal.hasLabel(NodeTypes.LOCAL).cast[nodes.Local]

  /**
    * Traverse only to AST nodes that are identifier
    * */
  def isIdentifier: Traversal[nodes.Identifier] =
    traversal.hasLabel(NodeTypes.IDENTIFIER).cast[nodes.Identifier]

  /**
    * Traverse only to FILE AST nodes
    * */
  def isFile: Traversal[nodes.File] =
    traversal.hasLabel(NodeTypes.FILE).cast[nodes.File]

  /**
    * Traverse only to AST nodes that are field identifier
    * */
  def isFieldIdentifier: Traversal[nodes.FieldIdentifier] =
    traversal.hasLabel(NodeTypes.FIELD_IDENTIFIER).cast[nodes.FieldIdentifier]

  /**
    * Traverse only to AST nodes that are return nodes
    * */
  def isReturn: Traversal[nodes.Return] =
    traversal.hasLabel(NodeTypes.RETURN).cast[nodes.Return]

  /**
    * Traverse only to AST nodes that are MEMBER nodes.
    */
  def isMember: Traversal[nodes.Member] =
    traversal.hasLabel(NodeTypes.MEMBER).cast[nodes.Member]

  /**
    * Traverse only to AST nodes that are method reference nodes.
    */
  def isMethodRef: Traversal[nodes.MethodRef] =
    traversal.hasLabel(NodeTypes.METHOD_REF).cast[nodes.MethodRef]

  /**
    * Traverse only to AST nodes that are METHOD nodes.
    */
  def isMethod: Traversal[nodes.Method] =
    traversal.hasLabel(NodeTypes.METHOD).cast[nodes.Method]

  /**
    * Traverse only to AST nodes that are MODIFIER nodes.
    */
  def isModifier: Traversal[nodes.Modifier] =
    traversal.hasLabel(NodeTypes.MODIFIER).cast[nodes.Modifier]

  /**
    * Traverse only to AST nodes that are NAMESPACE_BLOCK nodes.
    */
  def isNamespaceBlock: Traversal[nodes.NamespaceBlock] =
    traversal.hasLabel(NodeTypes.NAMESPACE_BLOCK).cast[nodes.NamespaceBlock]

  /**
    * Traverse only to AST nodes that are METHOD_PARAMETER_IN nodes.
    */
  def isParameter: Traversal[nodes.MethodParameterIn] =
    traversal.hasLabel(NodeTypes.METHOD_PARAMETER_IN).cast[nodes.MethodParameterIn]

  /**
    * Traverse only to AST nodes that are TYPE_DECL nodes.
    */
  def isTypeDecl: Traversal[nodes.TypeDecl] =
    traversal.hasLabel(NodeTypes.TYPE_DECL).cast[nodes.TypeDecl]

  def walkAstUntilReaching(labels: List[String]): Traversal[nodes.StoredNode] =
    traversal
      .repeat(_.out(EdgeTypes.AST))(
        _.emitAllButFirst
      .until(_.hasLabel(labels.head, labels.tail: _*)))
    .dedup
    .cast[nodes.StoredNode]

}

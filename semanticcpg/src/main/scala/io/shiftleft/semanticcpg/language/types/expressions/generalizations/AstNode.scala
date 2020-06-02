package io.shiftleft.semanticcpg.language.types.expressions.generalizations

import gremlin.scala.GremlinScala
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeTypes, nodes}
import io.shiftleft.overflowdb.traversal.help
import io.shiftleft.overflowdb.traversal.help.Doc
import io.shiftleft.semanticcpg.language.NodeSteps
import io.shiftleft.semanticcpg.language._

@help.Traversal(elementType = classOf[nodes.AstNode])
class AstNode[A <: nodes.AstNode](val wrapped: NodeSteps[A]) extends AnyVal {
  private def raw: GremlinScala[A] = wrapped.raw

  /**
    * Nodes of the AST rooted in this node, including the node itself.
    * */
  @Doc("All nodes of the abstract syntax tree")
  def ast: NodeSteps[nodes.AstNode] =
    new NodeSteps(raw.emit.repeat(_.out(EdgeTypes.AST)).cast[nodes.AstNode])

  /**
    * All nodes of the abstract syntax tree rooted in this node,
    * which match `predicate`. Equivalent of `match` in the original
    * CPG paper.
    * */
  def ast(predicate: nodes.AstNode => Boolean): NodeSteps[nodes.AstNode] =
    ast.where(predicate)

  def containsCallTo(regex: String): NodeSteps[A] =
    wrapped.where(_.ast.isCall.name(regex).size > 0)

  @Doc("Depth of the abstract syntax tree")
  def depth: Steps[Int] =
    wrapped.map(_.depth)

  def depth(p: nodes.AstNode => Boolean): Steps[Int] =
    wrapped.map(_.depth(p))

  def isCallTo(regex: String): NodeSteps[nodes.Call] =
    isCall.name(regex)

  /**
    * Nodes of the AST rooted in this node, minus the node itself
    * */
  def astMinusRoot: NodeSteps[nodes.AstNode] =
    new NodeSteps(raw.repeat(_.out(EdgeTypes.AST)).emit.cast[nodes.AstNode])

  /**
    * Direct children of node in the AST
    * */
  def astChildren: NodeSteps[nodes.AstNode] =
    new NodeSteps(raw.out(EdgeTypes.AST).cast[nodes.AstNode])

  /**
    * Parent AST node
    * */
  def astParent: NodeSteps[nodes.AstNode] =
    new NodeSteps(raw.in(EdgeTypes.AST).cast[nodes.AstNode])

  /**
    * Nodes of the AST obtained by expanding AST edges backwards until the method root is reached
    * */
  def inAst: NodeSteps[nodes.AstNode] =
    inAst(null)

  /**
    * Nodes of the AST obtained by expanding AST edges backwards until the method root is reached, minus this node
    * */
  def inAstMinusLeaf: NodeSteps[nodes.AstNode] =
    inAstMinusLeaf(null)

  /**
    * Nodes of the AST obtained by expanding AST edges backwards until `root` or the method root is reached
    * */
  def inAst(root: nodes.AstNode): NodeSteps[nodes.AstNode] =
    new NodeSteps(
      raw.emit
        .until(_.or(_.hasLabel(NodeTypes.METHOD), _.filterOnEnd(n => root != null && root == n)))
        .repeat(_.in(EdgeTypes.AST))
        .cast[nodes.AstNode])

  /**
    * Nodes of the AST obtained by expanding AST edges backwards until `root` or the method root is reached,
    * minus this node
    * */
  def inAstMinusLeaf(root: nodes.AstNode): NodeSteps[nodes.AstNode] =
    new NodeSteps(
      raw
        .until(_.or(_.hasLabel(NodeTypes.METHOD), _.filterOnEnd(n => root != null && root == n)))
        .repeat(_.in(EdgeTypes.AST))
        .emit
        .cast[nodes.AstNode])

  /**
    * Traverse only to those AST nodes that are also control flow graph nodes
    * */
  def isCfgNode: NodeSteps[nodes.CfgNode] =
    new NodeSteps(raw.filterOnEnd(_.isInstanceOf[nodes.CfgNode]).cast[nodes.CfgNode])

  /**
    * Traverse only to those AST nodes that are blocks
    * */
  def isBlock: NodeSteps[nodes.Block] =
    new NodeSteps(raw.hasLabel(NodeTypes.BLOCK).cast[nodes.Block])

  /**
    * Traverse only to those AST nodes that are control structures
    * */
  def isControlStructure: NodeSteps[nodes.ControlStructure] =
    new NodeSteps(raw.hasLabel(NodeTypes.CONTROL_STRUCTURE).cast[nodes.ControlStructure])

  /**
    * Traverse only to AST nodes that are expressions
    * */
  def isExpression: NodeSteps[nodes.Expression] =
    new NodeSteps(raw.filterOnEnd(_.isInstanceOf[nodes.Expression]).cast[nodes.Expression])

  /**
    * Traverse only to AST nodes that are calls
    * */
  def isCall: NodeSteps[nodes.Call] =
    new NodeSteps(raw.hasLabel(NodeTypes.CALL).cast[nodes.Call])

  /**
  Cast to call if applicable and filter on call code `calleeRegex`
    */
  def isCall(calleeRegex: String): NodeSteps[nodes.Call] =
    isCall.filter(_.code(calleeRegex))

  /**
    * Traverse only to AST nodes that are literals
    * */
  def isLiteral: NodeSteps[nodes.Literal] =
    new NodeSteps(raw.hasLabel(NodeTypes.LITERAL).cast[nodes.Literal])

  def isLocal: NodeSteps[nodes.Local] =
    new NodeSteps(raw.hasLabel(NodeTypes.LOCAL).cast[nodes.Local])

  /**
    * Traverse only to AST nodes that are identifier
    * */
  def isIdentifier: NodeSteps[nodes.Identifier] =
    new NodeSteps(raw.hasLabel(NodeTypes.IDENTIFIER).cast[nodes.Identifier])

  /**
    * Traverse only to AST nodes that are field identifier
    * */
  def isFieldIdentifier: NodeSteps[nodes.FieldIdentifier] =
    new NodeSteps(raw.hasLabel(NodeTypes.FIELD_IDENTIFIER).cast[nodes.FieldIdentifier])

  /**
    * Traverse only to AST nodes that are return nodes
    * */
  def isReturn: NodeSteps[nodes.Return] =
    new NodeSteps(raw.hasLabel(NodeTypes.RETURN).cast[nodes.Return])

  /**
    * Traverse only to AST nodes that are method reference nodes.
    */
  def isMethodRef: NodeSteps[nodes.MethodRef] =
    new NodeSteps(raw.hasLabel(NodeTypes.METHOD_REF).cast[nodes.MethodRef])

  def walkAstUntilReaching(labels: List[String]): NodeSteps[nodes.StoredNode] =
    new NodeSteps[nodes.StoredNode](
      raw
        .repeat(_.out(EdgeTypes.AST))
        .until(_.hasLabel(labels.head, labels.tail: _*))
        .emit
        .dedup
        .cast)

}

package io.shiftleft.codepropertygraph.generated.neighboraccessors

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.codepropertygraph.generated.language.*

final class AccessNeighborsForModifier(val node: nodes.Modifier) extends AnyVal {

  /** Traverse to CONTROL_STRUCTURE via AST IN edge.
    */
  def _controlStructureViaAstIn: Iterator[nodes.ControlStructure] = astIn.collectAll[nodes.ControlStructure]

  /** Traverse to MEMBER via AST IN edge.
    */
  def _memberViaAstIn: Iterator[nodes.Member] = astIn.collectAll[nodes.Member]

  /** Traverse to METHOD via AST IN edge.
    */
  def _methodViaAstIn: nodes.Method = {
    try { astIn.collectAll[nodes.Method].next() }
    catch {
      case e: java.util.NoSuchElementException =>
        val nodeInfo = String.format("id=%d, (seq=%d)", node.id, node.seq)
        throw new flatgraph.SchemaViolationException(
          "IN edge with label AST to an adjacent METHOD is mandatory, but not defined for this MODIFIER node with " + nodeInfo,
          e
        )
    }
  }

  /** Traverse to TYPE_DECL via AST IN edge.
    */
  def _typeDeclViaAstIn: nodes.TypeDecl = {
    try { astIn.collectAll[nodes.TypeDecl].next() }
    catch {
      case e: java.util.NoSuchElementException =>
        val nodeInfo = String.format("id=%d, (seq=%d)", node.id, node.seq)
        throw new flatgraph.SchemaViolationException(
          "IN edge with label AST to an adjacent TYPE_DECL is mandatory, but not defined for this MODIFIER node with " + nodeInfo,
          e
        )
    }
  }

  /** Traverse to UNKNOWN via AST IN edge.
    */
  def _unknownViaAstIn: Iterator[nodes.Unknown] = astIn.collectAll[nodes.Unknown]

  def astIn: Iterator[nodes.AstNode] = node._astIn.cast[nodes.AstNode]
}

final class AccessNeighborsForModifierTraversal(val traversal: Iterator[nodes.Modifier]) extends AnyVal {

  /** Traverse to CONTROL_STRUCTURE via AST IN edge.
    */
  def _controlStructureViaAstIn: Iterator[nodes.ControlStructure] = traversal.flatMap(_._controlStructureViaAstIn)

  /** Traverse to MEMBER via AST IN edge.
    */
  def _memberViaAstIn: Iterator[nodes.Member] = traversal.flatMap(_._memberViaAstIn)

  /** Traverse to METHOD via AST IN edge.
    */
  def _methodViaAstIn: Iterator[nodes.Method] = traversal.map(_._methodViaAstIn)

  /** Traverse to TYPE_DECL via AST IN edge.
    */
  def _typeDeclViaAstIn: Iterator[nodes.TypeDecl] = traversal.map(_._typeDeclViaAstIn)

  /** Traverse to UNKNOWN via AST IN edge.
    */
  def _unknownViaAstIn: Iterator[nodes.Unknown] = traversal.flatMap(_._unknownViaAstIn)

  def astIn: Iterator[nodes.AstNode] = traversal.flatMap(_.astIn)
}

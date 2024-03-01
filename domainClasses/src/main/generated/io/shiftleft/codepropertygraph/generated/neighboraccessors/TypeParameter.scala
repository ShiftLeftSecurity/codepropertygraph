package io.shiftleft.codepropertygraph.generated.neighboraccessors

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.codepropertygraph.generated.Language.*

final class AccessNeighborsForTypeParameter(val node: nodes.TypeParameter) extends AnyVal {

  /** Traverse to METHOD via AST IN edge.
    */
  def methodViaAstIn: nodes.Method = {
    try { astIn.collectAll[nodes.Method].next() }
    catch {
      case e: java.util.NoSuchElementException =>
        throw new flatgraph.SchemaViolationException(
          "IN edge with label AST to an adjacent METHOD is mandatory, but not defined for this TYPE_PARAMETER node with seq=" + node.seq,
          e
        )
    }
  }

  /** Traverse to TYPE_ARGUMENT via BINDS_TO IN edge.
    */
  def typeArgumentViaBindsToIn: Iterator[nodes.TypeArgument] = bindsToIn.collectAll[nodes.TypeArgument]

  /** Traverse to TYPE_DECL via AST IN edge.
    */
  def typeDeclViaAstIn: Iterator[nodes.TypeDecl] = astIn.collectAll[nodes.TypeDecl]

  def astIn: Iterator[nodes.AstNode] = node._astIn.cast[nodes.AstNode]

  def bindsToIn: Iterator[nodes.TypeArgument] = node._bindsToIn.cast[nodes.TypeArgument]
}

final class AccessNeighborsForTypeParameterTraversal(val traversal: Iterator[nodes.TypeParameter]) extends AnyVal {

  /** Traverse to METHOD via AST IN edge.
    */
  def methodViaAstIn: Iterator[nodes.Method] = traversal.map(_.methodViaAstIn)

  /** Traverse to TYPE_ARGUMENT via BINDS_TO IN edge.
    */
  def typeArgumentViaBindsToIn: Iterator[nodes.TypeArgument] = traversal.flatMap(_.typeArgumentViaBindsToIn)

  /** Traverse to TYPE_DECL via AST IN edge.
    */
  def typeDeclViaAstIn: Iterator[nodes.TypeDecl] = traversal.flatMap(_.typeDeclViaAstIn)

  def astIn: Iterator[nodes.AstNode] = traversal.flatMap(_.astIn)

  def bindsToIn: Iterator[nodes.TypeArgument] = traversal.flatMap(_.bindsToIn)
}

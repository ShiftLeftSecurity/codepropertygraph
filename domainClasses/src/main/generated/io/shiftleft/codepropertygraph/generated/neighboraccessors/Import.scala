package io.shiftleft.codepropertygraph.generated.neighboraccessors

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.codepropertygraph.generated.language.*

final class AccessNeighborsForImport(val node: nodes.Import) extends AnyVal {

  /** Traverse to BLOCK via AST IN edge.
    */
  def _blockViaAstIn: Iterator[nodes.Block] = astIn.collectAll[nodes.Block]

  /** Traverse to CALL via IS_CALL_FOR_IMPORT IN edge.
    */
  def _callViaIsCallForImportIn: Iterator[nodes.Call] = isCallForImportIn.collectAll[nodes.Call]

  /** Traverse to DEPENDENCY via IMPORTS OUT edge.
    */
  def _dependencyViaImportsOut: Iterator[nodes.Dependency] = importsOut.collectAll[nodes.Dependency]

  /** Traverse to FILE via AST IN edge.
    */
  def _fileViaAstIn: Iterator[nodes.File] = astIn.collectAll[nodes.File]

  /** Traverse to TAG via TAGGED_BY OUT edge.
    */
  def _tagViaTaggedByOut: Iterator[nodes.Tag] = taggedByOut.collectAll[nodes.Tag]

  /** Traverse to TYPE_DECL via AST IN edge.
    */
  def _typeDeclViaAstIn: Iterator[nodes.TypeDecl] = astIn.collectAll[nodes.TypeDecl]

  def astIn: Iterator[nodes.AstNode] = node._astIn.cast[nodes.AstNode]

  def importsOut: Iterator[nodes.Dependency] = node._importsOut.cast[nodes.Dependency]

  def isCallForImportIn: Iterator[nodes.Call] = node._isCallForImportIn.cast[nodes.Call]

  def taggedByOut: Iterator[nodes.Tag] = node._taggedByOut.cast[nodes.Tag]
}

final class AccessNeighborsForImportTraversal(val traversal: Iterator[nodes.Import]) extends AnyVal {

  /** Traverse to BLOCK via AST IN edge.
    */
  def _blockViaAstIn: Iterator[nodes.Block] = traversal.flatMap(_._blockViaAstIn)

  /** Traverse to CALL via IS_CALL_FOR_IMPORT IN edge.
    */
  def _callViaIsCallForImportIn: Iterator[nodes.Call] = traversal.flatMap(_._callViaIsCallForImportIn)

  /** Traverse to DEPENDENCY via IMPORTS OUT edge.
    */
  def _dependencyViaImportsOut: Iterator[nodes.Dependency] = traversal.flatMap(_._dependencyViaImportsOut)

  /** Traverse to FILE via AST IN edge.
    */
  def _fileViaAstIn: Iterator[nodes.File] = traversal.flatMap(_._fileViaAstIn)

  /** Traverse to TAG via TAGGED_BY OUT edge.
    */
  def _tagViaTaggedByOut: Iterator[nodes.Tag] = traversal.flatMap(_._tagViaTaggedByOut)

  /** Traverse to TYPE_DECL via AST IN edge.
    */
  def _typeDeclViaAstIn: Iterator[nodes.TypeDecl] = traversal.flatMap(_._typeDeclViaAstIn)

  def astIn: Iterator[nodes.AstNode] = traversal.flatMap(_.astIn)

  def importsOut: Iterator[nodes.Dependency] = traversal.flatMap(_.importsOut)

  def isCallForImportIn: Iterator[nodes.Call] = traversal.flatMap(_.isCallForImportIn)

  def taggedByOut: Iterator[nodes.Tag] = traversal.flatMap(_.taggedByOut)
}

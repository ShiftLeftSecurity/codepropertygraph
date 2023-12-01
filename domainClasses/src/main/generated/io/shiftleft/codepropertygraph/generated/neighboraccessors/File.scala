package io.shiftleft.codepropertygraph.generated.neighboraccessors

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.codepropertygraph.generated.Language.*

final class AccessNeighborsForFile(val node: nodes.File) extends AnyVal {

  /** Traverse to COMMENT via AST OUT edge.
    */
  @deprecated("please use comment instead")
  def commentViaAstOut: Iterator[nodes.Comment] = comment

  /** Traverse to COMMENT via AST OUT edge.
    */
  def comment: Iterator[nodes.Comment] = astOut.collectAll[nodes.Comment]

  /** Traverse to IMPORT via AST OUT edge.
    */
  def importViaAstOut: Iterator[nodes.Import] = astOut.collectAll[nodes.Import]

  /** Traverse to METHOD via CONTAINS OUT edge.
    */
  def methodViaContainsOut: Iterator[nodes.Method] = containsOut.collectAll[nodes.Method]

  /** Traverse to METHOD via SOURCE_FILE IN edge.
    */
  @deprecated("please use method instead")
  def methodViaSourceFileIn: Iterator[nodes.Method] = method

  /** Traverse to METHOD via SOURCE_FILE IN edge.
    */
  def method: Iterator[nodes.Method] = sourceFileIn.collectAll[nodes.Method]

  /** Traverse to NAMESPACE_BLOCK via AST OUT edge.
    */
  def namespaceBlockViaAstOut: Iterator[nodes.NamespaceBlock] = astOut.collectAll[nodes.NamespaceBlock]

  /** Traverse to NAMESPACE_BLOCK via SOURCE_FILE IN edge.
    */
  @deprecated("please use namespaceBlock instead")
  def namespaceBlockViaSourceFileIn: Iterator[nodes.NamespaceBlock] = namespaceBlock

  /** Traverse to NAMESPACE_BLOCK via SOURCE_FILE IN edge.
    */
  def namespaceBlock: Iterator[nodes.NamespaceBlock] = sourceFileIn.collectAll[nodes.NamespaceBlock]

  /** Traverse to TAG via TAGGED_BY OUT edge.
    */
  def tagViaTaggedByOut: Iterator[nodes.Tag] = taggedByOut.collectAll[nodes.Tag]

  /** Traverse to TEMPLATE_DOM via CONTAINS OUT edge.
    */
  def templateDomViaContainsOut: Iterator[nodes.TemplateDom] = containsOut.collectAll[nodes.TemplateDom]

  /** Traverse to TYPE_DECL via CONTAINS OUT edge.
    */
  def typeDeclViaContainsOut: Iterator[nodes.TypeDecl] = containsOut.collectAll[nodes.TypeDecl]

  /** Traverse to TYPE_DECL via SOURCE_FILE IN edge.
    */
  @deprecated("please use typeDecl instead")
  def typeDeclViaSourceFileIn: Iterator[nodes.TypeDecl] = typeDecl

  /** Traverse to TYPE_DECL via SOURCE_FILE IN edge.
    */
  def typeDecl: Iterator[nodes.TypeDecl] = sourceFileIn.collectAll[nodes.TypeDecl]

  def astOut: Iterator[nodes.AstNode] = node._astOut.cast[nodes.AstNode]

  def containsOut: Iterator[nodes.AstNode] = node._containsOut.cast[nodes.AstNode]

  def sourceFileIn: Iterator[nodes.AstNode] = node._sourceFileIn.cast[nodes.AstNode]

  def taggedByOut: Iterator[nodes.Tag] = node._taggedByOut.cast[nodes.Tag]
}

final class AccessNeighborsForFileTraversal(val traversal: Iterator[nodes.File]) extends AnyVal {

  /** Traverse to COMMENT via AST OUT edge.
    */
  def comment: Iterator[nodes.Comment] = traversal.flatMap(_.comment)

  /** Traverse to COMMENT via AST OUT edge.
    */
  @deprecated("please use comment instead")
  def commentViaAstOut: Iterator[nodes.Comment] = traversal.flatMap(_.commentViaAstOut)

  /** Traverse to IMPORT via AST OUT edge.
    */
  def importViaAstOut: Iterator[nodes.Import] = traversal.flatMap(_.importViaAstOut)

  /** Traverse to METHOD via CONTAINS OUT edge.
    */
  def methodViaContainsOut: Iterator[nodes.Method] = traversal.flatMap(_.methodViaContainsOut)

  /** Traverse to METHOD via SOURCE_FILE IN edge.
    */
  def method: Iterator[nodes.Method] = traversal.flatMap(_.method)

  /** Traverse to METHOD via SOURCE_FILE IN edge.
    */
  @deprecated("please use method instead")
  def methodViaSourceFileIn: Iterator[nodes.Method] = traversal.flatMap(_.methodViaSourceFileIn)

  /** Traverse to NAMESPACE_BLOCK via AST OUT edge.
    */
  def namespaceBlockViaAstOut: Iterator[nodes.NamespaceBlock] = traversal.flatMap(_.namespaceBlockViaAstOut)

  /** Traverse to NAMESPACE_BLOCK via SOURCE_FILE IN edge.
    */
  def namespaceBlock: Iterator[nodes.NamespaceBlock] = traversal.flatMap(_.namespaceBlock)

  /** Traverse to NAMESPACE_BLOCK via SOURCE_FILE IN edge.
    */
  @deprecated("please use namespaceBlock instead")
  def namespaceBlockViaSourceFileIn: Iterator[nodes.NamespaceBlock] = traversal.flatMap(_.namespaceBlockViaSourceFileIn)

  /** Traverse to TAG via TAGGED_BY OUT edge.
    */
  def tagViaTaggedByOut: Iterator[nodes.Tag] = traversal.flatMap(_.tagViaTaggedByOut)

  /** Traverse to TEMPLATE_DOM via CONTAINS OUT edge.
    */
  def templateDomViaContainsOut: Iterator[nodes.TemplateDom] = traversal.flatMap(_.templateDomViaContainsOut)

  /** Traverse to TYPE_DECL via CONTAINS OUT edge.
    */
  def typeDeclViaContainsOut: Iterator[nodes.TypeDecl] = traversal.flatMap(_.typeDeclViaContainsOut)

  /** Traverse to TYPE_DECL via SOURCE_FILE IN edge.
    */
  def typeDecl: Iterator[nodes.TypeDecl] = traversal.flatMap(_.typeDecl)

  /** Traverse to TYPE_DECL via SOURCE_FILE IN edge.
    */
  @deprecated("please use typeDecl instead")
  def typeDeclViaSourceFileIn: Iterator[nodes.TypeDecl] = traversal.flatMap(_.typeDeclViaSourceFileIn)

  def astOut: Iterator[nodes.AstNode] = traversal.flatMap(_.astOut)

  def containsOut: Iterator[nodes.AstNode] = traversal.flatMap(_.containsOut)

  def sourceFileIn: Iterator[nodes.AstNode] = traversal.flatMap(_.sourceFileIn)

  def taggedByOut: Iterator[nodes.Tag] = traversal.flatMap(_.taggedByOut)
}

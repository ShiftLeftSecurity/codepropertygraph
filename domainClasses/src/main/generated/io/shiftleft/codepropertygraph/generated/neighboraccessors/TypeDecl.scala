package io.shiftleft.codepropertygraph.generated.neighboraccessors

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.codepropertygraph.generated.language.*

final class AccessNeighborsForTypeDecl(val node: nodes.TypeDecl) extends AnyVal {

  /** Traverse to ANNOTATION via AST OUT edge.
    */
  def _annotationViaAstOut: Iterator[nodes.Annotation] = astOut.collectAll[nodes.Annotation]

  /** Traverse to BINDING via BINDS OUT edge.
    */
  def _bindingViaBindsOut: Iterator[nodes.Binding] = bindsOut.collectAll[nodes.Binding]

  /** Traverse to FILE via CONTAINS IN edge.
    */
  def _fileViaContainsIn: Iterator[nodes.File] = containsIn.collectAll[nodes.File]

  /** Traverse to FILE via SOURCE_FILE OUT edge.
    */
  def _fileViaSourceFileOut: Iterator[nodes.File] = sourceFileOut.collectAll[nodes.File]

  /** Traverse to IMPORT via AST OUT edge.
    */
  def _importViaAstOut: Iterator[nodes.Import] = astOut.collectAll[nodes.Import]

  /** Traverse to MEMBER via AST OUT edge.
    */
  def _memberViaAstOut: Iterator[nodes.Member] = astOut.collectAll[nodes.Member]

  /** Traverse to METHOD via AST IN edge.
    */
  def _methodViaAstIn: Option[nodes.Method] = astIn.collectAll[nodes.Method].nextOption()

  /** Traverse to METHOD via AST OUT edge.
    */
  def _methodViaAstOut: Iterator[nodes.Method] = astOut.collectAll[nodes.Method]

  /** Traverse to METHOD via CONTAINS OUT edge.
    */
  def _methodViaContainsOut: Iterator[nodes.Method] = containsOut.collectAll[nodes.Method]

  /** Traverse to MODIFIER via AST OUT edge.
    */
  def _modifierViaAstOut: Iterator[nodes.Modifier] = astOut.collectAll[nodes.Modifier]

  /** Traverse to NAMESPACE_BLOCK via AST IN edge.
    */
  @deprecated("please use namespaceBlock instead")
  def _namespaceBlockViaAstIn: Option[nodes.NamespaceBlock] = namespaceBlock

  /** Traverse to NAMESPACE_BLOCK via AST IN edge.
    */
  def namespaceBlock: Option[nodes.NamespaceBlock] = astIn.collectAll[nodes.NamespaceBlock].nextOption()

  /** Traverse to TAG via TAGGED_BY OUT edge.
    */
  def _tagViaTaggedByOut: Iterator[nodes.Tag] = taggedByOut.collectAll[nodes.Tag]

  /** Traverse to TYPE via ALIAS_OF OUT edge.
    */
  @deprecated("please use aliasedType instead")
  def _typeViaAliasOfOut: Iterator[nodes.Type] = aliasedType

  /** Traverse to TYPE via ALIAS_OF OUT edge.
    */
  def aliasedType: Iterator[nodes.Type] = aliasOfOut.collectAll[nodes.Type]

  /** Traverse to TYPE via INHERITS_FROM OUT edge.
    */
  def _typeViaInheritsFromOut: Iterator[nodes.Type] = inheritsFromOut.collectAll[nodes.Type]

  /** Traverse to TYPE via REF IN edge.
    */
  def _typeViaRefIn: Iterator[nodes.Type] = refIn.collectAll[nodes.Type]

  /** Traverse to TYPE_DECL via AST IN edge.
    */
  def _typeDeclViaAstIn: Option[nodes.TypeDecl] = astIn.collectAll[nodes.TypeDecl].nextOption()

  /** Traverse to TYPE_DECL via AST OUT edge.
    */
  def _typeDeclViaAstOut: Iterator[nodes.TypeDecl] = astOut.collectAll[nodes.TypeDecl]

  /** Traverse to TYPE_PARAMETER via AST OUT edge.
    */
  def _typeParameterViaAstOut: Iterator[nodes.TypeParameter] = astOut.collectAll[nodes.TypeParameter]

  def aliasOfOut: Iterator[nodes.Type] = node._aliasOfOut.cast[nodes.Type]

  def astIn: Iterator[nodes.AstNode] = node._astIn.cast[nodes.AstNode]

  def astOut: Iterator[nodes.AstNode] = node._astOut.cast[nodes.AstNode]

  def bindsOut: Iterator[nodes.Binding] = node._bindsOut.cast[nodes.Binding]

  def containsIn: Iterator[nodes.File] = node._containsIn.cast[nodes.File]

  def containsOut: Iterator[nodes.Method] = node._containsOut.cast[nodes.Method]

  def inheritsFromOut: Iterator[nodes.Type] = node._inheritsFromOut.cast[nodes.Type]

  def refIn: Iterator[nodes.Type] = node._refIn.cast[nodes.Type]

  def sourceFileOut: Iterator[nodes.File] = node._sourceFileOut.cast[nodes.File]

  def taggedByOut: Iterator[nodes.Tag] = node._taggedByOut.cast[nodes.Tag]
}

final class AccessNeighborsForTypeDeclTraversal(val traversal: Iterator[nodes.TypeDecl]) extends AnyVal {

  /** Traverse to ANNOTATION via AST OUT edge.
    */
  def _annotationViaAstOut: Iterator[nodes.Annotation] = traversal.flatMap(_._annotationViaAstOut)

  /** Traverse to BINDING via BINDS OUT edge.
    */
  def _bindingViaBindsOut: Iterator[nodes.Binding] = traversal.flatMap(_._bindingViaBindsOut)

  /** Traverse to FILE via CONTAINS IN edge.
    */
  def _fileViaContainsIn: Iterator[nodes.File] = traversal.flatMap(_._fileViaContainsIn)

  /** Traverse to FILE via SOURCE_FILE OUT edge.
    */
  def _fileViaSourceFileOut: Iterator[nodes.File] = traversal.flatMap(_._fileViaSourceFileOut)

  /** Traverse to IMPORT via AST OUT edge.
    */
  def _importViaAstOut: Iterator[nodes.Import] = traversal.flatMap(_._importViaAstOut)

  /** Traverse to MEMBER via AST OUT edge.
    */
  def _memberViaAstOut: Iterator[nodes.Member] = traversal.flatMap(_._memberViaAstOut)

  /** Traverse to METHOD via AST IN edge.
    */
  def _methodViaAstIn: Iterator[nodes.Method] = traversal.flatMap(_._methodViaAstIn)

  /** Traverse to METHOD via AST OUT edge.
    */
  def _methodViaAstOut: Iterator[nodes.Method] = traversal.flatMap(_._methodViaAstOut)

  /** Traverse to METHOD via CONTAINS OUT edge.
    */
  def _methodViaContainsOut: Iterator[nodes.Method] = traversal.flatMap(_._methodViaContainsOut)

  /** Traverse to MODIFIER via AST OUT edge.
    */
  def _modifierViaAstOut: Iterator[nodes.Modifier] = traversal.flatMap(_._modifierViaAstOut)

  /** Traverse to NAMESPACE_BLOCK via AST IN edge.
    */
  def namespaceBlock: Iterator[nodes.NamespaceBlock] = traversal.flatMap(_.namespaceBlock)

  /** Traverse to NAMESPACE_BLOCK via AST IN edge.
    */
  @deprecated("please use namespaceBlock instead")
  def _namespaceBlockViaAstIn: Iterator[nodes.NamespaceBlock] = traversal.flatMap(_._namespaceBlockViaAstIn)

  /** Traverse to TAG via TAGGED_BY OUT edge.
    */
  def _tagViaTaggedByOut: Iterator[nodes.Tag] = traversal.flatMap(_._tagViaTaggedByOut)

  /** Traverse to TYPE via ALIAS_OF OUT edge.
    */
  def aliasedType: Iterator[nodes.Type] = traversal.flatMap(_.aliasedType)

  /** Traverse to TYPE via ALIAS_OF OUT edge.
    */
  @deprecated("please use aliasedType instead")
  def _typeViaAliasOfOut: Iterator[nodes.Type] = traversal.flatMap(_._typeViaAliasOfOut)

  /** Traverse to TYPE via INHERITS_FROM OUT edge.
    */
  def _typeViaInheritsFromOut: Iterator[nodes.Type] = traversal.flatMap(_._typeViaInheritsFromOut)

  /** Traverse to TYPE via REF IN edge.
    */
  def _typeViaRefIn: Iterator[nodes.Type] = traversal.flatMap(_._typeViaRefIn)

  /** Traverse to TYPE_DECL via AST IN edge.
    */
  def _typeDeclViaAstIn: Iterator[nodes.TypeDecl] = traversal.flatMap(_._typeDeclViaAstIn)

  /** Traverse to TYPE_DECL via AST OUT edge.
    */
  def _typeDeclViaAstOut: Iterator[nodes.TypeDecl] = traversal.flatMap(_._typeDeclViaAstOut)

  /** Traverse to TYPE_PARAMETER via AST OUT edge.
    */
  def _typeParameterViaAstOut: Iterator[nodes.TypeParameter] = traversal.flatMap(_._typeParameterViaAstOut)

  def aliasOfOut: Iterator[nodes.Type] = traversal.flatMap(_.aliasOfOut)

  def astIn: Iterator[nodes.AstNode] = traversal.flatMap(_.astIn)

  def astOut: Iterator[nodes.AstNode] = traversal.flatMap(_.astOut)

  def bindsOut: Iterator[nodes.Binding] = traversal.flatMap(_.bindsOut)

  def containsIn: Iterator[nodes.File] = traversal.flatMap(_.containsIn)

  def containsOut: Iterator[nodes.Method] = traversal.flatMap(_.containsOut)

  def inheritsFromOut: Iterator[nodes.Type] = traversal.flatMap(_.inheritsFromOut)

  def refIn: Iterator[nodes.Type] = traversal.flatMap(_.refIn)

  def sourceFileOut: Iterator[nodes.File] = traversal.flatMap(_.sourceFileOut)

  def taggedByOut: Iterator[nodes.Tag] = traversal.flatMap(_.taggedByOut)
}

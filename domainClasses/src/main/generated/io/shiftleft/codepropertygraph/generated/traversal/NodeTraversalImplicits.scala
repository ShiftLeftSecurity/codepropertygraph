package io.shiftleft.codepropertygraph.generated.traversal

import io.shiftleft.codepropertygraph.generated.nodes._

trait NodeTraversalImplicits extends NodeBaseTypeTraversalImplicits {
  implicit def toStoredNodeTraversalExtGen[NodeType <: StoredNode](
    trav: IterableOnce[NodeType]
  ): StoredNodeTraversalExtGen[NodeType] = new StoredNodeTraversalExtGen(trav.iterator)

  implicit def toAnnotationTraversalExtGen[NodeType <: Annotation](
    trav: IterableOnce[NodeType]
  ): AnnotationTraversalExtGen[NodeType] = new AnnotationTraversalExtGen(trav.iterator)
  implicit def toAnnotationLiteralTraversalExtGen[NodeType <: AnnotationLiteral](
    trav: IterableOnce[NodeType]
  ): AnnotationLiteralTraversalExtGen[NodeType] = new AnnotationLiteralTraversalExtGen(trav.iterator)
  implicit def toAnnotationParameterTraversalExtGen[NodeType <: AnnotationParameter](
    trav: IterableOnce[NodeType]
  ): AnnotationParameterTraversalExtGen[NodeType] = new AnnotationParameterTraversalExtGen(trav.iterator)
  implicit def toAnnotationParameterAssignTraversalExtGen[NodeType <: AnnotationParameterAssign](
    trav: IterableOnce[NodeType]
  ): AnnotationParameterAssignTraversalExtGen[NodeType] = new AnnotationParameterAssignTraversalExtGen(trav.iterator)
  implicit def toArrayInitializerTraversalExtGen[NodeType <: ArrayInitializer](
    trav: IterableOnce[NodeType]
  ): ArrayInitializerTraversalExtGen[NodeType] = new ArrayInitializerTraversalExtGen(trav.iterator)
  implicit def toBindingTraversalExtGen[NodeType <: Binding](
    trav: IterableOnce[NodeType]
  ): BindingTraversalExtGen[NodeType] = new BindingTraversalExtGen(trav.iterator)
  implicit def toBlockTraversalExtGen[NodeType <: Block](trav: IterableOnce[NodeType]): BlockTraversalExtGen[NodeType] =
    new BlockTraversalExtGen(trav.iterator)
  implicit def toCallTraversalExtGen[NodeType <: Call](trav: IterableOnce[NodeType]): CallTraversalExtGen[NodeType] =
    new CallTraversalExtGen(trav.iterator)
  implicit def toClosureBindingTraversalExtGen[NodeType <: ClosureBinding](
    trav: IterableOnce[NodeType]
  ): ClosureBindingTraversalExtGen[NodeType] = new ClosureBindingTraversalExtGen(trav.iterator)
  implicit def toCommentTraversalExtGen[NodeType <: Comment](
    trav: IterableOnce[NodeType]
  ): CommentTraversalExtGen[NodeType] = new CommentTraversalExtGen(trav.iterator)
  implicit def toConfigFileTraversalExtGen[NodeType <: ConfigFile](
    trav: IterableOnce[NodeType]
  ): ConfigFileTraversalExtGen[NodeType] = new ConfigFileTraversalExtGen(trav.iterator)
  implicit def toControlStructureTraversalExtGen[NodeType <: ControlStructure](
    trav: IterableOnce[NodeType]
  ): ControlStructureTraversalExtGen[NodeType] = new ControlStructureTraversalExtGen(trav.iterator)
  implicit def toDependencyTraversalExtGen[NodeType <: Dependency](
    trav: IterableOnce[NodeType]
  ): DependencyTraversalExtGen[NodeType] = new DependencyTraversalExtGen(trav.iterator)
  implicit def toFieldIdentifierTraversalExtGen[NodeType <: FieldIdentifier](
    trav: IterableOnce[NodeType]
  ): FieldIdentifierTraversalExtGen[NodeType] = new FieldIdentifierTraversalExtGen(trav.iterator)
  implicit def toFileTraversalExtGen[NodeType <: File](trav: IterableOnce[NodeType]): FileTraversalExtGen[NodeType] =
    new FileTraversalExtGen(trav.iterator)
  implicit def toFindingTraversalExtGen[NodeType <: Finding](
    trav: IterableOnce[NodeType]
  ): FindingTraversalExtGen[NodeType] = new FindingTraversalExtGen(trav.iterator)
  implicit def toIdentifierTraversalExtGen[NodeType <: Identifier](
    trav: IterableOnce[NodeType]
  ): IdentifierTraversalExtGen[NodeType] = new IdentifierTraversalExtGen(trav.iterator)
  implicit def toImportTraversalExtGen[NodeType <: Import](
    trav: IterableOnce[NodeType]
  ): ImportTraversalExtGen[NodeType] = new ImportTraversalExtGen(trav.iterator)
  implicit def toJumpLabelTraversalExtGen[NodeType <: JumpLabel](
    trav: IterableOnce[NodeType]
  ): JumpLabelTraversalExtGen[NodeType] = new JumpLabelTraversalExtGen(trav.iterator)
  implicit def toJumpTargetTraversalExtGen[NodeType <: JumpTarget](
    trav: IterableOnce[NodeType]
  ): JumpTargetTraversalExtGen[NodeType] = new JumpTargetTraversalExtGen(trav.iterator)
  implicit def toKeyValuePairTraversalExtGen[NodeType <: KeyValuePair](
    trav: IterableOnce[NodeType]
  ): KeyValuePairTraversalExtGen[NodeType] = new KeyValuePairTraversalExtGen(trav.iterator)
  implicit def toLiteralTraversalExtGen[NodeType <: Literal](
    trav: IterableOnce[NodeType]
  ): LiteralTraversalExtGen[NodeType] = new LiteralTraversalExtGen(trav.iterator)
  implicit def toLocalTraversalExtGen[NodeType <: Local](trav: IterableOnce[NodeType]): LocalTraversalExtGen[NodeType] =
    new LocalTraversalExtGen(trav.iterator)
  implicit def toLocationTraversalExtGen[NodeType <: Location](
    trav: IterableOnce[NodeType]
  ): LocationTraversalExtGen[NodeType] = new LocationTraversalExtGen(trav.iterator)
  implicit def toMemberTraversalExtGen[NodeType <: Member](
    trav: IterableOnce[NodeType]
  ): MemberTraversalExtGen[NodeType] = new MemberTraversalExtGen(trav.iterator)
  implicit def toMetaDataTraversalExtGen[NodeType <: MetaData](
    trav: IterableOnce[NodeType]
  ): MetaDataTraversalExtGen[NodeType] = new MetaDataTraversalExtGen(trav.iterator)
  implicit def toMethodTraversalExtGen[NodeType <: Method](
    trav: IterableOnce[NodeType]
  ): MethodTraversalExtGen[NodeType] = new MethodTraversalExtGen(trav.iterator)
  implicit def toMethodParameterInTraversalExtGen[NodeType <: MethodParameterIn](
    trav: IterableOnce[NodeType]
  ): MethodParameterInTraversalExtGen[NodeType] = new MethodParameterInTraversalExtGen(trav.iterator)
  implicit def toMethodParameterOutTraversalExtGen[NodeType <: MethodParameterOut](
    trav: IterableOnce[NodeType]
  ): MethodParameterOutTraversalExtGen[NodeType] = new MethodParameterOutTraversalExtGen(trav.iterator)
  implicit def toMethodRefTraversalExtGen[NodeType <: MethodRef](
    trav: IterableOnce[NodeType]
  ): MethodRefTraversalExtGen[NodeType] = new MethodRefTraversalExtGen(trav.iterator)
  implicit def toMethodReturnTraversalExtGen[NodeType <: MethodReturn](
    trav: IterableOnce[NodeType]
  ): MethodReturnTraversalExtGen[NodeType] = new MethodReturnTraversalExtGen(trav.iterator)
  implicit def toModifierTraversalExtGen[NodeType <: Modifier](
    trav: IterableOnce[NodeType]
  ): ModifierTraversalExtGen[NodeType] = new ModifierTraversalExtGen(trav.iterator)
  implicit def toNamespaceTraversalExtGen[NodeType <: Namespace](
    trav: IterableOnce[NodeType]
  ): NamespaceTraversalExtGen[NodeType] = new NamespaceTraversalExtGen(trav.iterator)
  implicit def toNamespaceBlockTraversalExtGen[NodeType <: NamespaceBlock](
    trav: IterableOnce[NodeType]
  ): NamespaceBlockTraversalExtGen[NodeType] = new NamespaceBlockTraversalExtGen(trav.iterator)
  implicit def toReturnTraversalExtGen[NodeType <: Return](
    trav: IterableOnce[NodeType]
  ): ReturnTraversalExtGen[NodeType] = new ReturnTraversalExtGen(trav.iterator)
  implicit def toTagTraversalExtGen[NodeType <: Tag](trav: IterableOnce[NodeType]): TagTraversalExtGen[NodeType] =
    new TagTraversalExtGen(trav.iterator)
  implicit def toTagNodePairTraversalExtGen[NodeType <: TagNodePair](
    trav: IterableOnce[NodeType]
  ): TagNodePairTraversalExtGen[NodeType] = new TagNodePairTraversalExtGen(trav.iterator)
  implicit def toTemplateDomTraversalExtGen[NodeType <: TemplateDom](
    trav: IterableOnce[NodeType]
  ): TemplateDomTraversalExtGen[NodeType] = new TemplateDomTraversalExtGen(trav.iterator)
  implicit def toTypeTraversalExtGen[NodeType <: Type](trav: IterableOnce[NodeType]): TypeTraversalExtGen[NodeType] =
    new TypeTraversalExtGen(trav.iterator)
  implicit def toTypeArgumentTraversalExtGen[NodeType <: TypeArgument](
    trav: IterableOnce[NodeType]
  ): TypeArgumentTraversalExtGen[NodeType] = new TypeArgumentTraversalExtGen(trav.iterator)
  implicit def toTypeDeclTraversalExtGen[NodeType <: TypeDecl](
    trav: IterableOnce[NodeType]
  ): TypeDeclTraversalExtGen[NodeType] = new TypeDeclTraversalExtGen(trav.iterator)
  implicit def toTypeParameterTraversalExtGen[NodeType <: TypeParameter](
    trav: IterableOnce[NodeType]
  ): TypeParameterTraversalExtGen[NodeType] = new TypeParameterTraversalExtGen(trav.iterator)
  implicit def toTypeRefTraversalExtGen[NodeType <: TypeRef](
    trav: IterableOnce[NodeType]
  ): TypeRefTraversalExtGen[NodeType] = new TypeRefTraversalExtGen(trav.iterator)
  implicit def toUnknownTraversalExtGen[NodeType <: Unknown](
    trav: IterableOnce[NodeType]
  ): UnknownTraversalExtGen[NodeType] = new UnknownTraversalExtGen(trav.iterator)
}

// lower priority implicits for base types
trait NodeBaseTypeTraversalImplicits extends overflowdb.traversal.Implicits {
  implicit def toAstNodeTraversalExtGen[NodeType <: AstNode](
    trav: IterableOnce[NodeType]
  ): AstNodeTraversalExtGen[NodeType] = new AstNodeTraversalExtGen(trav.iterator)
  implicit def toCallReprTraversalExtGen[NodeType <: CallRepr](
    trav: IterableOnce[NodeType]
  ): CallReprTraversalExtGen[NodeType] = new CallReprTraversalExtGen(trav.iterator)
  implicit def toCfgNodeTraversalExtGen[NodeType <: CfgNode](
    trav: IterableOnce[NodeType]
  ): CfgNodeTraversalExtGen[NodeType] = new CfgNodeTraversalExtGen(trav.iterator)
  implicit def toDeclarationTraversalExtGen[NodeType <: Declaration](
    trav: IterableOnce[NodeType]
  ): DeclarationTraversalExtGen[NodeType] = new DeclarationTraversalExtGen(trav.iterator)
  implicit def toExpressionTraversalExtGen[NodeType <: Expression](
    trav: IterableOnce[NodeType]
  ): ExpressionTraversalExtGen[NodeType] = new ExpressionTraversalExtGen(trav.iterator)
}

class StoredNodeTraversalExtGen[NodeType <: StoredNode](val traversal: Iterator[NodeType]) extends AnyVal {
  def _aliasOfOut: Iterator[StoredNode]         = traversal.flatMap { _._aliasOfOut }
  def _aliasOfIn: Iterator[StoredNode]          = traversal.flatMap { _._aliasOfIn }
  def _argumentOut: Iterator[StoredNode]        = traversal.flatMap { _._argumentOut }
  def _argumentIn: Iterator[StoredNode]         = traversal.flatMap { _._argumentIn }
  def _astOut: Iterator[StoredNode]             = traversal.flatMap { _._astOut }
  def _astIn: Iterator[StoredNode]              = traversal.flatMap { _._astIn }
  def _bindsOut: Iterator[StoredNode]           = traversal.flatMap { _._bindsOut }
  def _bindsIn: Iterator[StoredNode]            = traversal.flatMap { _._bindsIn }
  def _bindsToOut: Iterator[StoredNode]         = traversal.flatMap { _._bindsToOut }
  def _bindsToIn: Iterator[StoredNode]          = traversal.flatMap { _._bindsToIn }
  def _callOut: Iterator[StoredNode]            = traversal.flatMap { _._callOut }
  def _callIn: Iterator[StoredNode]             = traversal.flatMap { _._callIn }
  def _captureOut: Iterator[StoredNode]         = traversal.flatMap { _._captureOut }
  def _captureIn: Iterator[StoredNode]          = traversal.flatMap { _._captureIn }
  def _capturedByOut: Iterator[StoredNode]      = traversal.flatMap { _._capturedByOut }
  def _capturedByIn: Iterator[StoredNode]       = traversal.flatMap { _._capturedByIn }
  def _cdgOut: Iterator[StoredNode]             = traversal.flatMap { _._cdgOut }
  def _cdgIn: Iterator[StoredNode]              = traversal.flatMap { _._cdgIn }
  def _cfgOut: Iterator[StoredNode]             = traversal.flatMap { _._cfgOut }
  def _cfgIn: Iterator[StoredNode]              = traversal.flatMap { _._cfgIn }
  def _conditionOut: Iterator[StoredNode]       = traversal.flatMap { _._conditionOut }
  def _conditionIn: Iterator[StoredNode]        = traversal.flatMap { _._conditionIn }
  def _containsOut: Iterator[StoredNode]        = traversal.flatMap { _._containsOut }
  def _containsIn: Iterator[StoredNode]         = traversal.flatMap { _._containsIn }
  def _dominateOut: Iterator[StoredNode]        = traversal.flatMap { _._dominateOut }
  def _dominateIn: Iterator[StoredNode]         = traversal.flatMap { _._dominateIn }
  def _evalTypeOut: Iterator[StoredNode]        = traversal.flatMap { _._evalTypeOut }
  def _evalTypeIn: Iterator[StoredNode]         = traversal.flatMap { _._evalTypeIn }
  def _importsOut: Iterator[StoredNode]         = traversal.flatMap { _._importsOut }
  def _importsIn: Iterator[StoredNode]          = traversal.flatMap { _._importsIn }
  def _inheritsFromOut: Iterator[StoredNode]    = traversal.flatMap { _._inheritsFromOut }
  def _inheritsFromIn: Iterator[StoredNode]     = traversal.flatMap { _._inheritsFromIn }
  def _isCallForImportOut: Iterator[StoredNode] = traversal.flatMap { _._isCallForImportOut }
  def _isCallForImportIn: Iterator[StoredNode]  = traversal.flatMap { _._isCallForImportIn }
  def _parameterLinkOut: Iterator[StoredNode]   = traversal.flatMap { _._parameterLinkOut }
  def _parameterLinkIn: Iterator[StoredNode]    = traversal.flatMap { _._parameterLinkIn }
  def _postDominateOut: Iterator[StoredNode]    = traversal.flatMap { _._postDominateOut }
  def _postDominateIn: Iterator[StoredNode]     = traversal.flatMap { _._postDominateIn }
  def _reachingDefOut: Iterator[StoredNode]     = traversal.flatMap { _._reachingDefOut }
  def _reachingDefIn: Iterator[StoredNode]      = traversal.flatMap { _._reachingDefIn }
  def _receiverOut: Iterator[StoredNode]        = traversal.flatMap { _._receiverOut }
  def _receiverIn: Iterator[StoredNode]         = traversal.flatMap { _._receiverIn }
  def _refOut: Iterator[StoredNode]             = traversal.flatMap { _._refOut }
  def _refIn: Iterator[StoredNode]              = traversal.flatMap { _._refIn }
  def _sourceFileOut: Iterator[StoredNode]      = traversal.flatMap { _._sourceFileOut }
  def _sourceFileIn: Iterator[StoredNode]       = traversal.flatMap { _._sourceFileIn }
  def _taggedByOut: Iterator[StoredNode]        = traversal.flatMap { _._taggedByOut }
  def _taggedByIn: Iterator[StoredNode]         = traversal.flatMap { _._taggedByIn }
}

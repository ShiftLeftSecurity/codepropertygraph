package io.shiftleft.codepropertygraph.generated.traversal

import io.shiftleft.codepropertygraph.generated.nodes._

trait NodeTraversalImplicits extends NodeBaseTypeTraversalImplicits {
  implicit def toAnnotationTraversalExtGen[NodeType <: Annotation](
    trav: IterableOnce[NodeType]
  ): AnnotationTraversalExtGen[NodeType] = new AnnotationTraversalExtGen(trav)
  implicit def toAnnotationLiteralTraversalExtGen[NodeType <: AnnotationLiteral](
    trav: IterableOnce[NodeType]
  ): AnnotationLiteralTraversalExtGen[NodeType] = new AnnotationLiteralTraversalExtGen(trav)
  implicit def toAnnotationParameterTraversalExtGen[NodeType <: AnnotationParameter](
    trav: IterableOnce[NodeType]
  ): AnnotationParameterTraversalExtGen[NodeType] = new AnnotationParameterTraversalExtGen(trav)
  implicit def toAnnotationParameterAssignTraversalExtGen[NodeType <: AnnotationParameterAssign](
    trav: IterableOnce[NodeType]
  ): AnnotationParameterAssignTraversalExtGen[NodeType] = new AnnotationParameterAssignTraversalExtGen(trav)
  implicit def toArrayInitializerTraversalExtGen[NodeType <: ArrayInitializer](
    trav: IterableOnce[NodeType]
  ): ArrayInitializerTraversalExtGen[NodeType] = new ArrayInitializerTraversalExtGen(trav)
  implicit def toBindingTraversalExtGen[NodeType <: Binding](
    trav: IterableOnce[NodeType]
  ): BindingTraversalExtGen[NodeType] = new BindingTraversalExtGen(trav)
  implicit def toBlockTraversalExtGen[NodeType <: Block](trav: IterableOnce[NodeType]): BlockTraversalExtGen[NodeType] =
    new BlockTraversalExtGen(trav)
  implicit def toCallTraversalExtGen[NodeType <: Call](trav: IterableOnce[NodeType]): CallTraversalExtGen[NodeType] =
    new CallTraversalExtGen(trav)
  implicit def toClosureBindingTraversalExtGen[NodeType <: ClosureBinding](
    trav: IterableOnce[NodeType]
  ): ClosureBindingTraversalExtGen[NodeType] = new ClosureBindingTraversalExtGen(trav)
  implicit def toCommentTraversalExtGen[NodeType <: Comment](
    trav: IterableOnce[NodeType]
  ): CommentTraversalExtGen[NodeType] = new CommentTraversalExtGen(trav)
  implicit def toConfigFileTraversalExtGen[NodeType <: ConfigFile](
    trav: IterableOnce[NodeType]
  ): ConfigFileTraversalExtGen[NodeType] = new ConfigFileTraversalExtGen(trav)
  implicit def toControlStructureTraversalExtGen[NodeType <: ControlStructure](
    trav: IterableOnce[NodeType]
  ): ControlStructureTraversalExtGen[NodeType] = new ControlStructureTraversalExtGen(trav)
  implicit def toDependencyTraversalExtGen[NodeType <: Dependency](
    trav: IterableOnce[NodeType]
  ): DependencyTraversalExtGen[NodeType] = new DependencyTraversalExtGen(trav)
  implicit def toFieldIdentifierTraversalExtGen[NodeType <: FieldIdentifier](
    trav: IterableOnce[NodeType]
  ): FieldIdentifierTraversalExtGen[NodeType] = new FieldIdentifierTraversalExtGen(trav)
  implicit def toFileTraversalExtGen[NodeType <: File](trav: IterableOnce[NodeType]): FileTraversalExtGen[NodeType] =
    new FileTraversalExtGen(trav)
  implicit def toFindingTraversalExtGen[NodeType <: Finding](
    trav: IterableOnce[NodeType]
  ): FindingTraversalExtGen[NodeType] = new FindingTraversalExtGen(trav)
  implicit def toIdentifierTraversalExtGen[NodeType <: Identifier](
    trav: IterableOnce[NodeType]
  ): IdentifierTraversalExtGen[NodeType] = new IdentifierTraversalExtGen(trav)
  implicit def toImportTraversalExtGen[NodeType <: Import](
    trav: IterableOnce[NodeType]
  ): ImportTraversalExtGen[NodeType] = new ImportTraversalExtGen(trav)
  implicit def toJumpLabelTraversalExtGen[NodeType <: JumpLabel](
    trav: IterableOnce[NodeType]
  ): JumpLabelTraversalExtGen[NodeType] = new JumpLabelTraversalExtGen(trav)
  implicit def toJumpTargetTraversalExtGen[NodeType <: JumpTarget](
    trav: IterableOnce[NodeType]
  ): JumpTargetTraversalExtGen[NodeType] = new JumpTargetTraversalExtGen(trav)
  implicit def toKeyValuePairTraversalExtGen[NodeType <: KeyValuePair](
    trav: IterableOnce[NodeType]
  ): KeyValuePairTraversalExtGen[NodeType] = new KeyValuePairTraversalExtGen(trav)
  implicit def toLiteralTraversalExtGen[NodeType <: Literal](
    trav: IterableOnce[NodeType]
  ): LiteralTraversalExtGen[NodeType] = new LiteralTraversalExtGen(trav)
  implicit def toLocalTraversalExtGen[NodeType <: Local](trav: IterableOnce[NodeType]): LocalTraversalExtGen[NodeType] =
    new LocalTraversalExtGen(trav)
  implicit def toLocationTraversalExtGen[NodeType <: Location](
    trav: IterableOnce[NodeType]
  ): LocationTraversalExtGen[NodeType] = new LocationTraversalExtGen(trav)
  implicit def toMemberTraversalExtGen[NodeType <: Member](
    trav: IterableOnce[NodeType]
  ): MemberTraversalExtGen[NodeType] = new MemberTraversalExtGen(trav)
  implicit def toMetaDataTraversalExtGen[NodeType <: MetaData](
    trav: IterableOnce[NodeType]
  ): MetaDataTraversalExtGen[NodeType] = new MetaDataTraversalExtGen(trav)
  implicit def toMethodTraversalExtGen[NodeType <: Method](
    trav: IterableOnce[NodeType]
  ): MethodTraversalExtGen[NodeType] = new MethodTraversalExtGen(trav)
  implicit def toMethodParameterInTraversalExtGen[NodeType <: MethodParameterIn](
    trav: IterableOnce[NodeType]
  ): MethodParameterInTraversalExtGen[NodeType] = new MethodParameterInTraversalExtGen(trav)
  implicit def toMethodParameterOutTraversalExtGen[NodeType <: MethodParameterOut](
    trav: IterableOnce[NodeType]
  ): MethodParameterOutTraversalExtGen[NodeType] = new MethodParameterOutTraversalExtGen(trav)
  implicit def toMethodRefTraversalExtGen[NodeType <: MethodRef](
    trav: IterableOnce[NodeType]
  ): MethodRefTraversalExtGen[NodeType] = new MethodRefTraversalExtGen(trav)
  implicit def toMethodReturnTraversalExtGen[NodeType <: MethodReturn](
    trav: IterableOnce[NodeType]
  ): MethodReturnTraversalExtGen[NodeType] = new MethodReturnTraversalExtGen(trav)
  implicit def toModifierTraversalExtGen[NodeType <: Modifier](
    trav: IterableOnce[NodeType]
  ): ModifierTraversalExtGen[NodeType] = new ModifierTraversalExtGen(trav)
  implicit def toNamespaceTraversalExtGen[NodeType <: Namespace](
    trav: IterableOnce[NodeType]
  ): NamespaceTraversalExtGen[NodeType] = new NamespaceTraversalExtGen(trav)
  implicit def toNamespaceBlockTraversalExtGen[NodeType <: NamespaceBlock](
    trav: IterableOnce[NodeType]
  ): NamespaceBlockTraversalExtGen[NodeType] = new NamespaceBlockTraversalExtGen(trav)
  implicit def toReturnTraversalExtGen[NodeType <: Return](
    trav: IterableOnce[NodeType]
  ): ReturnTraversalExtGen[NodeType] = new ReturnTraversalExtGen(trav)
  implicit def toTagTraversalExtGen[NodeType <: Tag](trav: IterableOnce[NodeType]): TagTraversalExtGen[NodeType] =
    new TagTraversalExtGen(trav)
  implicit def toTagNodePairTraversalExtGen[NodeType <: TagNodePair](
    trav: IterableOnce[NodeType]
  ): TagNodePairTraversalExtGen[NodeType] = new TagNodePairTraversalExtGen(trav)
  implicit def toTemplateDomTraversalExtGen[NodeType <: TemplateDom](
    trav: IterableOnce[NodeType]
  ): TemplateDomTraversalExtGen[NodeType] = new TemplateDomTraversalExtGen(trav)
  implicit def toTypeTraversalExtGen[NodeType <: Type](trav: IterableOnce[NodeType]): TypeTraversalExtGen[NodeType] =
    new TypeTraversalExtGen(trav)
  implicit def toTypeArgumentTraversalExtGen[NodeType <: TypeArgument](
    trav: IterableOnce[NodeType]
  ): TypeArgumentTraversalExtGen[NodeType] = new TypeArgumentTraversalExtGen(trav)
  implicit def toTypeDeclTraversalExtGen[NodeType <: TypeDecl](
    trav: IterableOnce[NodeType]
  ): TypeDeclTraversalExtGen[NodeType] = new TypeDeclTraversalExtGen(trav)
  implicit def toTypeParameterTraversalExtGen[NodeType <: TypeParameter](
    trav: IterableOnce[NodeType]
  ): TypeParameterTraversalExtGen[NodeType] = new TypeParameterTraversalExtGen(trav)
  implicit def toTypeRefTraversalExtGen[NodeType <: TypeRef](
    trav: IterableOnce[NodeType]
  ): TypeRefTraversalExtGen[NodeType] = new TypeRefTraversalExtGen(trav)
  implicit def toUnknownTraversalExtGen[NodeType <: Unknown](
    trav: IterableOnce[NodeType]
  ): UnknownTraversalExtGen[NodeType] = new UnknownTraversalExtGen(trav)
}

// lower priority implicits for base types
trait NodeBaseTypeTraversalImplicits extends overflowdb.traversal.Implicits {
  implicit def toAstNodeTraversalExtGen[NodeType <: AstNode](
    trav: IterableOnce[NodeType]
  ): AstNodeTraversalExtGen[NodeType] = new AstNodeTraversalExtGen(trav)
  implicit def toCallReprTraversalExtGen[NodeType <: CallRepr](
    trav: IterableOnce[NodeType]
  ): CallReprTraversalExtGen[NodeType] = new CallReprTraversalExtGen(trav)
  implicit def toCfgNodeTraversalExtGen[NodeType <: CfgNode](
    trav: IterableOnce[NodeType]
  ): CfgNodeTraversalExtGen[NodeType] = new CfgNodeTraversalExtGen(trav)
  implicit def toDeclarationTraversalExtGen[NodeType <: Declaration](
    trav: IterableOnce[NodeType]
  ): DeclarationTraversalExtGen[NodeType] = new DeclarationTraversalExtGen(trav)
  implicit def toExpressionTraversalExtGen[NodeType <: Expression](
    trav: IterableOnce[NodeType]
  ): ExpressionTraversalExtGen[NodeType] = new ExpressionTraversalExtGen(trav)
}

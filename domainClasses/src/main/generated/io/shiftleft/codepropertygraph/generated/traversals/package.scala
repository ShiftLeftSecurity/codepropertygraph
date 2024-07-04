package io.shiftleft.codepropertygraph.generated

import io.shiftleft.codepropertygraph.generated.nodes

package object traversals {

  /** not supposed to be used directly by users, hence the `bootstrap` in the name */
  object languagebootstrap extends ConcreteStoredConversions

  trait ConcreteStoredConversions extends ConcreteBaseConversions {
    implicit def accessPropertyAliasTypeFullNameTraversal[
      NodeType <: nodes.StoredNode & nodes.StaticType[nodes.HasAliasTypeFullNameEMT]
    ](traversal: IterableOnce[NodeType]): TraversalPropertyAliasTypeFullName[NodeType] =
      new TraversalPropertyAliasTypeFullName(traversal.iterator)
    implicit def accessPropertyArgumentIndexTraversal[
      NodeType <: nodes.StoredNode & nodes.StaticType[nodes.HasArgumentIndexEMT]
    ](traversal: IterableOnce[NodeType]): TraversalPropertyArgumentIndex[NodeType] = new TraversalPropertyArgumentIndex(
      traversal.iterator
    )
    implicit def accessPropertyArgumentNameTraversal[
      NodeType <: nodes.StoredNode & nodes.StaticType[nodes.HasArgumentNameEMT]
    ](traversal: IterableOnce[NodeType]): TraversalPropertyArgumentName[NodeType] = new TraversalPropertyArgumentName(
      traversal.iterator
    )
    implicit def accessPropertyAstParentFullNameTraversal[
      NodeType <: nodes.StoredNode & nodes.StaticType[nodes.HasAstParentFullNameEMT]
    ](traversal: IterableOnce[NodeType]): TraversalPropertyAstParentFullName[NodeType] =
      new TraversalPropertyAstParentFullName(traversal.iterator)
    implicit def accessPropertyAstParentTypeTraversal[
      NodeType <: nodes.StoredNode & nodes.StaticType[nodes.HasAstParentTypeEMT]
    ](traversal: IterableOnce[NodeType]): TraversalPropertyAstParentType[NodeType] = new TraversalPropertyAstParentType(
      traversal.iterator
    )
    implicit def accessPropertyCanonicalNameTraversal[
      NodeType <: nodes.StoredNode & nodes.StaticType[nodes.HasCanonicalNameEMT]
    ](traversal: IterableOnce[NodeType]): TraversalPropertyCanonicalName[NodeType] = new TraversalPropertyCanonicalName(
      traversal.iterator
    )
    implicit def accessPropertyClassNameTraversal[
      NodeType <: nodes.StoredNode & nodes.StaticType[nodes.HasClassNameEMT]
    ](traversal: IterableOnce[NodeType]): TraversalPropertyClassName[NodeType] = new TraversalPropertyClassName(
      traversal.iterator
    )
    implicit def accessPropertyClassShortNameTraversal[
      NodeType <: nodes.StoredNode & nodes.StaticType[nodes.HasClassShortNameEMT]
    ](traversal: IterableOnce[NodeType]): TraversalPropertyClassShortName[NodeType] =
      new TraversalPropertyClassShortName(traversal.iterator)
    implicit def accessPropertyClosureBindingIdTraversal[
      NodeType <: nodes.StoredNode & nodes.StaticType[nodes.HasClosureBindingIdEMT]
    ](traversal: IterableOnce[NodeType]): TraversalPropertyClosureBindingId[NodeType] =
      new TraversalPropertyClosureBindingId(traversal.iterator)
    implicit def accessPropertyClosureOriginalNameTraversal[
      NodeType <: nodes.StoredNode & nodes.StaticType[nodes.HasClosureOriginalNameEMT]
    ](traversal: IterableOnce[NodeType]): TraversalPropertyClosureOriginalName[NodeType] =
      new TraversalPropertyClosureOriginalName(traversal.iterator)
    implicit def accessPropertyCodeTraversal[NodeType <: nodes.StoredNode & nodes.StaticType[nodes.HasCodeEMT]](
      traversal: IterableOnce[NodeType]
    ): TraversalPropertyCode[NodeType] = new TraversalPropertyCode(traversal.iterator)
    implicit def accessPropertyColumnNumberTraversal[
      NodeType <: nodes.StoredNode & nodes.StaticType[nodes.HasColumnNumberEMT]
    ](traversal: IterableOnce[NodeType]): TraversalPropertyColumnNumber[NodeType] = new TraversalPropertyColumnNumber(
      traversal.iterator
    )
    implicit def accessPropertyColumnNumberEndTraversal[
      NodeType <: nodes.StoredNode & nodes.StaticType[nodes.HasColumnNumberEndEMT]
    ](traversal: IterableOnce[NodeType]): TraversalPropertyColumnNumberEnd[NodeType] =
      new TraversalPropertyColumnNumberEnd(traversal.iterator)
    implicit def accessPropertyContainedRefTraversal[
      NodeType <: nodes.StoredNode & nodes.StaticType[nodes.HasContainedRefEMT]
    ](traversal: IterableOnce[NodeType]): TraversalPropertyContainedRef[NodeType] = new TraversalPropertyContainedRef(
      traversal.iterator
    )
    implicit def accessPropertyContentTraversal[NodeType <: nodes.StoredNode & nodes.StaticType[nodes.HasContentEMT]](
      traversal: IterableOnce[NodeType]
    ): TraversalPropertyContent[NodeType] = new TraversalPropertyContent(traversal.iterator)
    implicit def accessPropertyControlStructureTypeTraversal[
      NodeType <: nodes.StoredNode & nodes.StaticType[nodes.HasControlStructureTypeEMT]
    ](traversal: IterableOnce[NodeType]): TraversalPropertyControlStructureType[NodeType] =
      new TraversalPropertyControlStructureType(traversal.iterator)
    implicit def accessPropertyDependencyGroupIdTraversal[
      NodeType <: nodes.StoredNode & nodes.StaticType[nodes.HasDependencyGroupIdEMT]
    ](traversal: IterableOnce[NodeType]): TraversalPropertyDependencyGroupId[NodeType] =
      new TraversalPropertyDependencyGroupId(traversal.iterator)
    implicit def accessPropertyDispatchTypeTraversal[
      NodeType <: nodes.StoredNode & nodes.StaticType[nodes.HasDispatchTypeEMT]
    ](traversal: IterableOnce[NodeType]): TraversalPropertyDispatchType[NodeType] = new TraversalPropertyDispatchType(
      traversal.iterator
    )
    implicit def accessPropertyDynamicTypeHintFullNameTraversal[
      NodeType <: nodes.StoredNode & nodes.StaticType[nodes.HasDynamicTypeHintFullNameEMT]
    ](traversal: IterableOnce[NodeType]): TraversalPropertyDynamicTypeHintFullName[NodeType] =
      new TraversalPropertyDynamicTypeHintFullName(traversal.iterator)
    implicit def accessPropertyEvaluationStrategyTraversal[
      NodeType <: nodes.StoredNode & nodes.StaticType[nodes.HasEvaluationStrategyEMT]
    ](traversal: IterableOnce[NodeType]): TraversalPropertyEvaluationStrategy[NodeType] =
      new TraversalPropertyEvaluationStrategy(traversal.iterator)
    implicit def accessPropertyExplicitAsTraversal[
      NodeType <: nodes.StoredNode & nodes.StaticType[nodes.HasExplicitAsEMT]
    ](traversal: IterableOnce[NodeType]): TraversalPropertyExplicitAs[NodeType] = new TraversalPropertyExplicitAs(
      traversal.iterator
    )
    implicit def accessPropertyFilenameTraversal[NodeType <: nodes.StoredNode & nodes.StaticType[nodes.HasFilenameEMT]](
      traversal: IterableOnce[NodeType]
    ): TraversalPropertyFilename[NodeType] = new TraversalPropertyFilename(traversal.iterator)
    implicit def accessPropertyFullNameTraversal[NodeType <: nodes.StoredNode & nodes.StaticType[nodes.HasFullNameEMT]](
      traversal: IterableOnce[NodeType]
    ): TraversalPropertyFullName[NodeType] = new TraversalPropertyFullName(traversal.iterator)
    implicit def accessPropertyHashTraversal[NodeType <: nodes.StoredNode & nodes.StaticType[nodes.HasHashEMT]](
      traversal: IterableOnce[NodeType]
    ): TraversalPropertyHash[NodeType] = new TraversalPropertyHash(traversal.iterator)
    implicit def accessPropertyImportedAsTraversal[
      NodeType <: nodes.StoredNode & nodes.StaticType[nodes.HasImportedAsEMT]
    ](traversal: IterableOnce[NodeType]): TraversalPropertyImportedAs[NodeType] = new TraversalPropertyImportedAs(
      traversal.iterator
    )
    implicit def accessPropertyImportedEntityTraversal[
      NodeType <: nodes.StoredNode & nodes.StaticType[nodes.HasImportedEntityEMT]
    ](traversal: IterableOnce[NodeType]): TraversalPropertyImportedEntity[NodeType] =
      new TraversalPropertyImportedEntity(traversal.iterator)
    implicit def accessPropertyIndexTraversal[NodeType <: nodes.StoredNode & nodes.StaticType[nodes.HasIndexEMT]](
      traversal: IterableOnce[NodeType]
    ): TraversalPropertyIndex[NodeType] = new TraversalPropertyIndex(traversal.iterator)
    implicit def accessPropertyInheritsFromTypeFullNameTraversal[
      NodeType <: nodes.StoredNode & nodes.StaticType[nodes.HasInheritsFromTypeFullNameEMT]
    ](traversal: IterableOnce[NodeType]): TraversalPropertyInheritsFromTypeFullName[NodeType] =
      new TraversalPropertyInheritsFromTypeFullName(traversal.iterator)
    implicit def accessPropertyIsExplicitTraversal[
      NodeType <: nodes.StoredNode & nodes.StaticType[nodes.HasIsExplicitEMT]
    ](traversal: IterableOnce[NodeType]): TraversalPropertyIsExplicit[NodeType] = new TraversalPropertyIsExplicit(
      traversal.iterator
    )
    implicit def accessPropertyIsExternalTraversal[
      NodeType <: nodes.StoredNode & nodes.StaticType[nodes.HasIsExternalEMT]
    ](traversal: IterableOnce[NodeType]): TraversalPropertyIsExternal[NodeType] = new TraversalPropertyIsExternal(
      traversal.iterator
    )
    implicit def accessPropertyIsVariadicTraversal[
      NodeType <: nodes.StoredNode & nodes.StaticType[nodes.HasIsVariadicEMT]
    ](traversal: IterableOnce[NodeType]): TraversalPropertyIsVariadic[NodeType] = new TraversalPropertyIsVariadic(
      traversal.iterator
    )
    implicit def accessPropertyIsWildcardTraversal[
      NodeType <: nodes.StoredNode & nodes.StaticType[nodes.HasIsWildcardEMT]
    ](traversal: IterableOnce[NodeType]): TraversalPropertyIsWildcard[NodeType] = new TraversalPropertyIsWildcard(
      traversal.iterator
    )
    implicit def accessPropertyKeyTraversal[NodeType <: nodes.StoredNode & nodes.StaticType[nodes.HasKeyEMT]](
      traversal: IterableOnce[NodeType]
    ): TraversalPropertyKey[NodeType] = new TraversalPropertyKey(traversal.iterator)
    implicit def accessPropertyLanguageTraversal[NodeType <: nodes.StoredNode & nodes.StaticType[nodes.HasLanguageEMT]](
      traversal: IterableOnce[NodeType]
    ): TraversalPropertyLanguage[NodeType] = new TraversalPropertyLanguage(traversal.iterator)
    implicit def accessPropertyLineNumberTraversal[
      NodeType <: nodes.StoredNode & nodes.StaticType[nodes.HasLineNumberEMT]
    ](traversal: IterableOnce[NodeType]): TraversalPropertyLineNumber[NodeType] = new TraversalPropertyLineNumber(
      traversal.iterator
    )
    implicit def accessPropertyLineNumberEndTraversal[
      NodeType <: nodes.StoredNode & nodes.StaticType[nodes.HasLineNumberEndEMT]
    ](traversal: IterableOnce[NodeType]): TraversalPropertyLineNumberEnd[NodeType] = new TraversalPropertyLineNumberEnd(
      traversal.iterator
    )
    implicit def accessPropertyMethodFullNameTraversal[
      NodeType <: nodes.StoredNode & nodes.StaticType[nodes.HasMethodFullNameEMT]
    ](traversal: IterableOnce[NodeType]): TraversalPropertyMethodFullName[NodeType] =
      new TraversalPropertyMethodFullName(traversal.iterator)
    implicit def accessPropertyMethodShortNameTraversal[
      NodeType <: nodes.StoredNode & nodes.StaticType[nodes.HasMethodShortNameEMT]
    ](traversal: IterableOnce[NodeType]): TraversalPropertyMethodShortName[NodeType] =
      new TraversalPropertyMethodShortName(traversal.iterator)
    implicit def accessPropertyModifierTypeTraversal[
      NodeType <: nodes.StoredNode & nodes.StaticType[nodes.HasModifierTypeEMT]
    ](traversal: IterableOnce[NodeType]): TraversalPropertyModifierType[NodeType] = new TraversalPropertyModifierType(
      traversal.iterator
    )
    implicit def accessPropertyNameTraversal[NodeType <: nodes.StoredNode & nodes.StaticType[nodes.HasNameEMT]](
      traversal: IterableOnce[NodeType]
    ): TraversalPropertyName[NodeType] = new TraversalPropertyName(traversal.iterator)
    implicit def accessPropertyNodeLabelTraversal[
      NodeType <: nodes.StoredNode & nodes.StaticType[nodes.HasNodeLabelEMT]
    ](traversal: IterableOnce[NodeType]): TraversalPropertyNodeLabel[NodeType] = new TraversalPropertyNodeLabel(
      traversal.iterator
    )
    implicit def accessPropertyOffsetTraversal[NodeType <: nodes.StoredNode & nodes.StaticType[nodes.HasOffsetEMT]](
      traversal: IterableOnce[NodeType]
    ): TraversalPropertyOffset[NodeType] = new TraversalPropertyOffset(traversal.iterator)
    implicit def accessPropertyOffsetEndTraversal[
      NodeType <: nodes.StoredNode & nodes.StaticType[nodes.HasOffsetEndEMT]
    ](traversal: IterableOnce[NodeType]): TraversalPropertyOffsetEnd[NodeType] = new TraversalPropertyOffsetEnd(
      traversal.iterator
    )
    implicit def accessPropertyOrderTraversal[NodeType <: nodes.StoredNode & nodes.StaticType[nodes.HasOrderEMT]](
      traversal: IterableOnce[NodeType]
    ): TraversalPropertyOrder[NodeType] = new TraversalPropertyOrder(traversal.iterator)
    implicit def accessPropertyOverlaysTraversal[NodeType <: nodes.StoredNode & nodes.StaticType[nodes.HasOverlaysEMT]](
      traversal: IterableOnce[NodeType]
    ): TraversalPropertyOverlays[NodeType] = new TraversalPropertyOverlays(traversal.iterator)
    implicit def accessPropertyPackageNameTraversal[
      NodeType <: nodes.StoredNode & nodes.StaticType[nodes.HasPackageNameEMT]
    ](traversal: IterableOnce[NodeType]): TraversalPropertyPackageName[NodeType] = new TraversalPropertyPackageName(
      traversal.iterator
    )
    implicit def accessPropertyParserTypeNameTraversal[
      NodeType <: nodes.StoredNode & nodes.StaticType[nodes.HasParserTypeNameEMT]
    ](traversal: IterableOnce[NodeType]): TraversalPropertyParserTypeName[NodeType] =
      new TraversalPropertyParserTypeName(traversal.iterator)
    implicit def accessPropertyPossibleTypesTraversal[
      NodeType <: nodes.StoredNode & nodes.StaticType[nodes.HasPossibleTypesEMT]
    ](traversal: IterableOnce[NodeType]): TraversalPropertyPossibleTypes[NodeType] = new TraversalPropertyPossibleTypes(
      traversal.iterator
    )
    implicit def accessPropertyRootTraversal[NodeType <: nodes.StoredNode & nodes.StaticType[nodes.HasRootEMT]](
      traversal: IterableOnce[NodeType]
    ): TraversalPropertyRoot[NodeType] = new TraversalPropertyRoot(traversal.iterator)
    implicit def accessPropertySignatureTraversal[
      NodeType <: nodes.StoredNode & nodes.StaticType[nodes.HasSignatureEMT]
    ](traversal: IterableOnce[NodeType]): TraversalPropertySignature[NodeType] = new TraversalPropertySignature(
      traversal.iterator
    )
    implicit def accessPropertySymbolTraversal[NodeType <: nodes.StoredNode & nodes.StaticType[nodes.HasSymbolEMT]](
      traversal: IterableOnce[NodeType]
    ): TraversalPropertySymbol[NodeType] = new TraversalPropertySymbol(traversal.iterator)
    implicit def accessPropertyTypeDeclFullNameTraversal[
      NodeType <: nodes.StoredNode & nodes.StaticType[nodes.HasTypeDeclFullNameEMT]
    ](traversal: IterableOnce[NodeType]): TraversalPropertyTypeDeclFullName[NodeType] =
      new TraversalPropertyTypeDeclFullName(traversal.iterator)
    implicit def accessPropertyTypeFullNameTraversal[
      NodeType <: nodes.StoredNode & nodes.StaticType[nodes.HasTypeFullNameEMT]
    ](traversal: IterableOnce[NodeType]): TraversalPropertyTypeFullName[NodeType] = new TraversalPropertyTypeFullName(
      traversal.iterator
    )
    implicit def accessPropertyValueTraversal[NodeType <: nodes.StoredNode & nodes.StaticType[nodes.HasValueEMT]](
      traversal: IterableOnce[NodeType]
    ): TraversalPropertyValue[NodeType] = new TraversalPropertyValue(traversal.iterator)
    implicit def accessPropertyVersionTraversal[NodeType <: nodes.StoredNode & nodes.StaticType[nodes.HasVersionEMT]](
      traversal: IterableOnce[NodeType]
    ): TraversalPropertyVersion[NodeType] = new TraversalPropertyVersion(traversal.iterator)
  }

  trait ConcreteBaseConversions extends AbstractBaseConversions0 {
    implicit def traversalAnnotationBase[NodeType <: nodes.AnnotationBase](
      traversal: IterableOnce[NodeType]
    ): TraversalAnnotationBase[NodeType] = new TraversalAnnotationBase(traversal.iterator)
    implicit def traversalAnnotationliteralBase[NodeType <: nodes.AnnotationLiteralBase](
      traversal: IterableOnce[NodeType]
    ): TraversalAnnotationliteralBase[NodeType] = new TraversalAnnotationliteralBase(traversal.iterator)
    implicit def traversalAnnotationparameterBase[NodeType <: nodes.AnnotationParameterBase](
      traversal: IterableOnce[NodeType]
    ): TraversalAnnotationparameterBase[NodeType] = new TraversalAnnotationparameterBase(traversal.iterator)
    implicit def traversalAnnotationparameterassignBase[NodeType <: nodes.AnnotationParameterAssignBase](
      traversal: IterableOnce[NodeType]
    ): TraversalAnnotationparameterassignBase[NodeType] = new TraversalAnnotationparameterassignBase(traversal.iterator)
    implicit def traversalArrayinitializerBase[NodeType <: nodes.ArrayInitializerBase](
      traversal: IterableOnce[NodeType]
    ): TraversalArrayinitializerBase[NodeType] = new TraversalArrayinitializerBase(traversal.iterator)
    implicit def traversalBindingBase[NodeType <: nodes.BindingBase](
      traversal: IterableOnce[NodeType]
    ): TraversalBindingBase[NodeType] = new TraversalBindingBase(traversal.iterator)
    implicit def traversalBlockBase[NodeType <: nodes.BlockBase](
      traversal: IterableOnce[NodeType]
    ): TraversalBlockBase[NodeType] = new TraversalBlockBase(traversal.iterator)
    implicit def traversalCallBase[NodeType <: nodes.CallBase](
      traversal: IterableOnce[NodeType]
    ): TraversalCallBase[NodeType] = new TraversalCallBase(traversal.iterator)
    implicit def traversalClosurebindingBase[NodeType <: nodes.ClosureBindingBase](
      traversal: IterableOnce[NodeType]
    ): TraversalClosurebindingBase[NodeType] = new TraversalClosurebindingBase(traversal.iterator)
    implicit def traversalCommentBase[NodeType <: nodes.CommentBase](
      traversal: IterableOnce[NodeType]
    ): TraversalCommentBase[NodeType] = new TraversalCommentBase(traversal.iterator)
    implicit def traversalConfigfileBase[NodeType <: nodes.ConfigFileBase](
      traversal: IterableOnce[NodeType]
    ): TraversalConfigfileBase[NodeType] = new TraversalConfigfileBase(traversal.iterator)
    implicit def traversalControlstructureBase[NodeType <: nodes.ControlStructureBase](
      traversal: IterableOnce[NodeType]
    ): TraversalControlstructureBase[NodeType] = new TraversalControlstructureBase(traversal.iterator)
    implicit def traversalDependencyBase[NodeType <: nodes.DependencyBase](
      traversal: IterableOnce[NodeType]
    ): TraversalDependencyBase[NodeType] = new TraversalDependencyBase(traversal.iterator)
    implicit def traversalFieldidentifierBase[NodeType <: nodes.FieldIdentifierBase](
      traversal: IterableOnce[NodeType]
    ): TraversalFieldidentifierBase[NodeType] = new TraversalFieldidentifierBase(traversal.iterator)
    implicit def traversalFileBase[NodeType <: nodes.FileBase](
      traversal: IterableOnce[NodeType]
    ): TraversalFileBase[NodeType] = new TraversalFileBase(traversal.iterator)
    implicit def traversalFindingBase[NodeType <: nodes.FindingBase](
      traversal: IterableOnce[NodeType]
    ): TraversalFindingBase[NodeType] = new TraversalFindingBase(traversal.iterator)
    implicit def traversalIdentifierBase[NodeType <: nodes.IdentifierBase](
      traversal: IterableOnce[NodeType]
    ): TraversalIdentifierBase[NodeType] = new TraversalIdentifierBase(traversal.iterator)
    implicit def traversalImportBase[NodeType <: nodes.ImportBase](
      traversal: IterableOnce[NodeType]
    ): TraversalImportBase[NodeType] = new TraversalImportBase(traversal.iterator)
    implicit def traversalJumplabelBase[NodeType <: nodes.JumpLabelBase](
      traversal: IterableOnce[NodeType]
    ): TraversalJumplabelBase[NodeType] = new TraversalJumplabelBase(traversal.iterator)
    implicit def traversalJumptargetBase[NodeType <: nodes.JumpTargetBase](
      traversal: IterableOnce[NodeType]
    ): TraversalJumptargetBase[NodeType] = new TraversalJumptargetBase(traversal.iterator)
    implicit def traversalKeyvaluepairBase[NodeType <: nodes.KeyValuePairBase](
      traversal: IterableOnce[NodeType]
    ): TraversalKeyvaluepairBase[NodeType] = new TraversalKeyvaluepairBase(traversal.iterator)
    implicit def traversalLiteralBase[NodeType <: nodes.LiteralBase](
      traversal: IterableOnce[NodeType]
    ): TraversalLiteralBase[NodeType] = new TraversalLiteralBase(traversal.iterator)
    implicit def traversalLocalBase[NodeType <: nodes.LocalBase](
      traversal: IterableOnce[NodeType]
    ): TraversalLocalBase[NodeType] = new TraversalLocalBase(traversal.iterator)
    implicit def traversalLocationBase[NodeType <: nodes.LocationBase](
      traversal: IterableOnce[NodeType]
    ): TraversalLocationBase[NodeType] = new TraversalLocationBase(traversal.iterator)
    implicit def traversalMemberBase[NodeType <: nodes.MemberBase](
      traversal: IterableOnce[NodeType]
    ): TraversalMemberBase[NodeType] = new TraversalMemberBase(traversal.iterator)
    implicit def traversalMetadataBase[NodeType <: nodes.MetaDataBase](
      traversal: IterableOnce[NodeType]
    ): TraversalMetadataBase[NodeType] = new TraversalMetadataBase(traversal.iterator)
    implicit def traversalMethodBase[NodeType <: nodes.MethodBase](
      traversal: IterableOnce[NodeType]
    ): TraversalMethodBase[NodeType] = new TraversalMethodBase(traversal.iterator)
    implicit def traversalMethodparameterinBase[NodeType <: nodes.MethodParameterInBase](
      traversal: IterableOnce[NodeType]
    ): TraversalMethodparameterinBase[NodeType] = new TraversalMethodparameterinBase(traversal.iterator)
    implicit def traversalMethodparameteroutBase[NodeType <: nodes.MethodParameterOutBase](
      traversal: IterableOnce[NodeType]
    ): TraversalMethodparameteroutBase[NodeType] = new TraversalMethodparameteroutBase(traversal.iterator)
    implicit def traversalMethodrefBase[NodeType <: nodes.MethodRefBase](
      traversal: IterableOnce[NodeType]
    ): TraversalMethodrefBase[NodeType] = new TraversalMethodrefBase(traversal.iterator)
    implicit def traversalMethodreturnBase[NodeType <: nodes.MethodReturnBase](
      traversal: IterableOnce[NodeType]
    ): TraversalMethodreturnBase[NodeType] = new TraversalMethodreturnBase(traversal.iterator)
    implicit def traversalModifierBase[NodeType <: nodes.ModifierBase](
      traversal: IterableOnce[NodeType]
    ): TraversalModifierBase[NodeType] = new TraversalModifierBase(traversal.iterator)
    implicit def traversalNamespaceBase[NodeType <: nodes.NamespaceBase](
      traversal: IterableOnce[NodeType]
    ): TraversalNamespaceBase[NodeType] = new TraversalNamespaceBase(traversal.iterator)
    implicit def traversalNamespaceblockBase[NodeType <: nodes.NamespaceBlockBase](
      traversal: IterableOnce[NodeType]
    ): TraversalNamespaceblockBase[NodeType] = new TraversalNamespaceblockBase(traversal.iterator)
    implicit def traversalReturnBase[NodeType <: nodes.ReturnBase](
      traversal: IterableOnce[NodeType]
    ): TraversalReturnBase[NodeType] = new TraversalReturnBase(traversal.iterator)
    implicit def traversalTagBase[NodeType <: nodes.TagBase](
      traversal: IterableOnce[NodeType]
    ): TraversalTagBase[NodeType] = new TraversalTagBase(traversal.iterator)
    implicit def traversalTagnodepairBase[NodeType <: nodes.TagNodePairBase](
      traversal: IterableOnce[NodeType]
    ): TraversalTagnodepairBase[NodeType] = new TraversalTagnodepairBase(traversal.iterator)
    implicit def traversalTemplatedomBase[NodeType <: nodes.TemplateDomBase](
      traversal: IterableOnce[NodeType]
    ): TraversalTemplatedomBase[NodeType] = new TraversalTemplatedomBase(traversal.iterator)
    implicit def traversalTypeBase[NodeType <: nodes.TypeBase](
      traversal: IterableOnce[NodeType]
    ): TraversalTypeBase[NodeType] = new TraversalTypeBase(traversal.iterator)
    implicit def traversalTypeargumentBase[NodeType <: nodes.TypeArgumentBase](
      traversal: IterableOnce[NodeType]
    ): TraversalTypeargumentBase[NodeType] = new TraversalTypeargumentBase(traversal.iterator)
    implicit def traversalTypedeclBase[NodeType <: nodes.TypeDeclBase](
      traversal: IterableOnce[NodeType]
    ): TraversalTypedeclBase[NodeType] = new TraversalTypedeclBase(traversal.iterator)
    implicit def traversalTypeparameterBase[NodeType <: nodes.TypeParameterBase](
      traversal: IterableOnce[NodeType]
    ): TraversalTypeparameterBase[NodeType] = new TraversalTypeparameterBase(traversal.iterator)
    implicit def traversalTyperefBase[NodeType <: nodes.TypeRefBase](
      traversal: IterableOnce[NodeType]
    ): TraversalTyperefBase[NodeType] = new TraversalTyperefBase(traversal.iterator)
    implicit def traversalUnknownBase[NodeType <: nodes.UnknownBase](
      traversal: IterableOnce[NodeType]
    ): TraversalUnknownBase[NodeType] = new TraversalUnknownBase(traversal.iterator)
  }

  trait AbstractBaseConversions0 extends AbstractBaseConversions1 {
    implicit def traversalAstnodeBase[NodeType <: nodes.AstNodeBase](
      traversal: IterableOnce[NodeType]
    ): TraversalAstnodeBase[NodeType] = new TraversalAstnodeBase(traversal.iterator)
    implicit def traversalCallreprBase[NodeType <: nodes.CallReprBase](
      traversal: IterableOnce[NodeType]
    ): TraversalCallreprBase[NodeType] = new TraversalCallreprBase(traversal.iterator)
    implicit def traversalCfgnodeBase[NodeType <: nodes.CfgNodeBase](
      traversal: IterableOnce[NodeType]
    ): TraversalCfgnodeBase[NodeType] = new TraversalCfgnodeBase(traversal.iterator)
    implicit def traversalExpressionBase[NodeType <: nodes.ExpressionBase](
      traversal: IterableOnce[NodeType]
    ): TraversalExpressionBase[NodeType] = new TraversalExpressionBase(traversal.iterator)
  }

  trait AbstractBaseConversions1 {
    implicit def traversalDeclarationBase[NodeType <: nodes.DeclarationBase](
      traversal: IterableOnce[NodeType]
    ): TraversalDeclarationBase[NodeType] = new TraversalDeclarationBase(traversal.iterator)
  }
}

package io.shiftleft.codepropertygraph.generated

import flatgraph.traversal.language.*
import io.shiftleft.codepropertygraph.generated.nodes

package object neighboraccessors {
  object Lang extends Conversions

  trait Conversions {
    implicit def accessNeighborsForAnnotation(node: nodes.Annotation): AccessNeighborsForAnnotation =
      new AccessNeighborsForAnnotation(node)

    implicit def accessNeighborsForAnnotationTraversal(
      traversal: IterableOnce[nodes.Annotation]
    ): AccessNeighborsForAnnotationTraversal =
      new AccessNeighborsForAnnotationTraversal(traversal.iterator)

    implicit def accessNeighborsForAnnotationLiteral(
      node: nodes.AnnotationLiteral
    ): AccessNeighborsForAnnotationLiteral =
      new AccessNeighborsForAnnotationLiteral(node)

    implicit def accessNeighborsForAnnotationLiteralTraversal(
      traversal: IterableOnce[nodes.AnnotationLiteral]
    ): AccessNeighborsForAnnotationLiteralTraversal =
      new AccessNeighborsForAnnotationLiteralTraversal(traversal.iterator)

    implicit def accessNeighborsForAnnotationParameter(
      node: nodes.AnnotationParameter
    ): AccessNeighborsForAnnotationParameter =
      new AccessNeighborsForAnnotationParameter(node)

    implicit def accessNeighborsForAnnotationParameterTraversal(
      traversal: IterableOnce[nodes.AnnotationParameter]
    ): AccessNeighborsForAnnotationParameterTraversal =
      new AccessNeighborsForAnnotationParameterTraversal(traversal.iterator)

    implicit def accessNeighborsForAnnotationParameterAssign(
      node: nodes.AnnotationParameterAssign
    ): AccessNeighborsForAnnotationParameterAssign =
      new AccessNeighborsForAnnotationParameterAssign(node)

    implicit def accessNeighborsForAnnotationParameterAssignTraversal(
      traversal: IterableOnce[nodes.AnnotationParameterAssign]
    ): AccessNeighborsForAnnotationParameterAssignTraversal =
      new AccessNeighborsForAnnotationParameterAssignTraversal(traversal.iterator)

    implicit def accessNeighborsForArrayInitializer(node: nodes.ArrayInitializer): AccessNeighborsForArrayInitializer =
      new AccessNeighborsForArrayInitializer(node)

    implicit def accessNeighborsForArrayInitializerTraversal(
      traversal: IterableOnce[nodes.ArrayInitializer]
    ): AccessNeighborsForArrayInitializerTraversal =
      new AccessNeighborsForArrayInitializerTraversal(traversal.iterator)

    implicit def accessNeighborsForBinding(node: nodes.Binding): AccessNeighborsForBinding =
      new AccessNeighborsForBinding(node)

    implicit def accessNeighborsForBindingTraversal(
      traversal: IterableOnce[nodes.Binding]
    ): AccessNeighborsForBindingTraversal =
      new AccessNeighborsForBindingTraversal(traversal.iterator)

    implicit def accessNeighborsForBlock(node: nodes.Block): AccessNeighborsForBlock =
      new AccessNeighborsForBlock(node)

    implicit def accessNeighborsForBlockTraversal(
      traversal: IterableOnce[nodes.Block]
    ): AccessNeighborsForBlockTraversal =
      new AccessNeighborsForBlockTraversal(traversal.iterator)

    implicit def accessNeighborsForCall(node: nodes.Call): AccessNeighborsForCall =
      new AccessNeighborsForCall(node)

    implicit def accessNeighborsForCallTraversal(traversal: IterableOnce[nodes.Call]): AccessNeighborsForCallTraversal =
      new AccessNeighborsForCallTraversal(traversal.iterator)

    implicit def accessNeighborsForClosureBinding(node: nodes.ClosureBinding): AccessNeighborsForClosureBinding =
      new AccessNeighborsForClosureBinding(node)

    implicit def accessNeighborsForClosureBindingTraversal(
      traversal: IterableOnce[nodes.ClosureBinding]
    ): AccessNeighborsForClosureBindingTraversal =
      new AccessNeighborsForClosureBindingTraversal(traversal.iterator)

    implicit def accessNeighborsForComment(node: nodes.Comment): AccessNeighborsForComment =
      new AccessNeighborsForComment(node)

    implicit def accessNeighborsForCommentTraversal(
      traversal: IterableOnce[nodes.Comment]
    ): AccessNeighborsForCommentTraversal =
      new AccessNeighborsForCommentTraversal(traversal.iterator)

    implicit def accessNeighborsForControlStructure(node: nodes.ControlStructure): AccessNeighborsForControlStructure =
      new AccessNeighborsForControlStructure(node)

    implicit def accessNeighborsForControlStructureTraversal(
      traversal: IterableOnce[nodes.ControlStructure]
    ): AccessNeighborsForControlStructureTraversal =
      new AccessNeighborsForControlStructureTraversal(traversal.iterator)

    implicit def accessNeighborsForDependency(node: nodes.Dependency): AccessNeighborsForDependency =
      new AccessNeighborsForDependency(node)

    implicit def accessNeighborsForDependencyTraversal(
      traversal: IterableOnce[nodes.Dependency]
    ): AccessNeighborsForDependencyTraversal =
      new AccessNeighborsForDependencyTraversal(traversal.iterator)

    implicit def accessNeighborsForFieldIdentifier(node: nodes.FieldIdentifier): AccessNeighborsForFieldIdentifier =
      new AccessNeighborsForFieldIdentifier(node)

    implicit def accessNeighborsForFieldIdentifierTraversal(
      traversal: IterableOnce[nodes.FieldIdentifier]
    ): AccessNeighborsForFieldIdentifierTraversal =
      new AccessNeighborsForFieldIdentifierTraversal(traversal.iterator)

    implicit def accessNeighborsForFile(node: nodes.File): AccessNeighborsForFile =
      new AccessNeighborsForFile(node)

    implicit def accessNeighborsForFileTraversal(traversal: IterableOnce[nodes.File]): AccessNeighborsForFileTraversal =
      new AccessNeighborsForFileTraversal(traversal.iterator)

    implicit def accessNeighborsForIdentifier(node: nodes.Identifier): AccessNeighborsForIdentifier =
      new AccessNeighborsForIdentifier(node)

    implicit def accessNeighborsForIdentifierTraversal(
      traversal: IterableOnce[nodes.Identifier]
    ): AccessNeighborsForIdentifierTraversal =
      new AccessNeighborsForIdentifierTraversal(traversal.iterator)

    implicit def accessNeighborsForImport(node: nodes.Import): AccessNeighborsForImport =
      new AccessNeighborsForImport(node)

    implicit def accessNeighborsForImportTraversal(
      traversal: IterableOnce[nodes.Import]
    ): AccessNeighborsForImportTraversal =
      new AccessNeighborsForImportTraversal(traversal.iterator)

    implicit def accessNeighborsForJumpLabel(node: nodes.JumpLabel): AccessNeighborsForJumpLabel =
      new AccessNeighborsForJumpLabel(node)

    implicit def accessNeighborsForJumpLabelTraversal(
      traversal: IterableOnce[nodes.JumpLabel]
    ): AccessNeighborsForJumpLabelTraversal =
      new AccessNeighborsForJumpLabelTraversal(traversal.iterator)

    implicit def accessNeighborsForJumpTarget(node: nodes.JumpTarget): AccessNeighborsForJumpTarget =
      new AccessNeighborsForJumpTarget(node)

    implicit def accessNeighborsForJumpTargetTraversal(
      traversal: IterableOnce[nodes.JumpTarget]
    ): AccessNeighborsForJumpTargetTraversal =
      new AccessNeighborsForJumpTargetTraversal(traversal.iterator)

    implicit def accessNeighborsForLiteral(node: nodes.Literal): AccessNeighborsForLiteral =
      new AccessNeighborsForLiteral(node)

    implicit def accessNeighborsForLiteralTraversal(
      traversal: IterableOnce[nodes.Literal]
    ): AccessNeighborsForLiteralTraversal =
      new AccessNeighborsForLiteralTraversal(traversal.iterator)

    implicit def accessNeighborsForLocal(node: nodes.Local): AccessNeighborsForLocal =
      new AccessNeighborsForLocal(node)

    implicit def accessNeighborsForLocalTraversal(
      traversal: IterableOnce[nodes.Local]
    ): AccessNeighborsForLocalTraversal =
      new AccessNeighborsForLocalTraversal(traversal.iterator)

    implicit def accessNeighborsForMember(node: nodes.Member): AccessNeighborsForMember =
      new AccessNeighborsForMember(node)

    implicit def accessNeighborsForMemberTraversal(
      traversal: IterableOnce[nodes.Member]
    ): AccessNeighborsForMemberTraversal =
      new AccessNeighborsForMemberTraversal(traversal.iterator)

    implicit def accessNeighborsForMethod(node: nodes.Method): AccessNeighborsForMethod =
      new AccessNeighborsForMethod(node)

    implicit def accessNeighborsForMethodTraversal(
      traversal: IterableOnce[nodes.Method]
    ): AccessNeighborsForMethodTraversal =
      new AccessNeighborsForMethodTraversal(traversal.iterator)

    implicit def accessNeighborsForMethodParameterIn(
      node: nodes.MethodParameterIn
    ): AccessNeighborsForMethodParameterIn =
      new AccessNeighborsForMethodParameterIn(node)

    implicit def accessNeighborsForMethodParameterInTraversal(
      traversal: IterableOnce[nodes.MethodParameterIn]
    ): AccessNeighborsForMethodParameterInTraversal =
      new AccessNeighborsForMethodParameterInTraversal(traversal.iterator)

    implicit def accessNeighborsForMethodParameterOut(
      node: nodes.MethodParameterOut
    ): AccessNeighborsForMethodParameterOut =
      new AccessNeighborsForMethodParameterOut(node)

    implicit def accessNeighborsForMethodParameterOutTraversal(
      traversal: IterableOnce[nodes.MethodParameterOut]
    ): AccessNeighborsForMethodParameterOutTraversal =
      new AccessNeighborsForMethodParameterOutTraversal(traversal.iterator)

    implicit def accessNeighborsForMethodRef(node: nodes.MethodRef): AccessNeighborsForMethodRef =
      new AccessNeighborsForMethodRef(node)

    implicit def accessNeighborsForMethodRefTraversal(
      traversal: IterableOnce[nodes.MethodRef]
    ): AccessNeighborsForMethodRefTraversal =
      new AccessNeighborsForMethodRefTraversal(traversal.iterator)

    implicit def accessNeighborsForMethodReturn(node: nodes.MethodReturn): AccessNeighborsForMethodReturn =
      new AccessNeighborsForMethodReturn(node)

    implicit def accessNeighborsForMethodReturnTraversal(
      traversal: IterableOnce[nodes.MethodReturn]
    ): AccessNeighborsForMethodReturnTraversal =
      new AccessNeighborsForMethodReturnTraversal(traversal.iterator)

    implicit def accessNeighborsForModifier(node: nodes.Modifier): AccessNeighborsForModifier =
      new AccessNeighborsForModifier(node)

    implicit def accessNeighborsForModifierTraversal(
      traversal: IterableOnce[nodes.Modifier]
    ): AccessNeighborsForModifierTraversal =
      new AccessNeighborsForModifierTraversal(traversal.iterator)

    implicit def accessNeighborsForNamespace(node: nodes.Namespace): AccessNeighborsForNamespace =
      new AccessNeighborsForNamespace(node)

    implicit def accessNeighborsForNamespaceTraversal(
      traversal: IterableOnce[nodes.Namespace]
    ): AccessNeighborsForNamespaceTraversal =
      new AccessNeighborsForNamespaceTraversal(traversal.iterator)

    implicit def accessNeighborsForNamespaceBlock(node: nodes.NamespaceBlock): AccessNeighborsForNamespaceBlock =
      new AccessNeighborsForNamespaceBlock(node)

    implicit def accessNeighborsForNamespaceBlockTraversal(
      traversal: IterableOnce[nodes.NamespaceBlock]
    ): AccessNeighborsForNamespaceBlockTraversal =
      new AccessNeighborsForNamespaceBlockTraversal(traversal.iterator)

    implicit def accessNeighborsForReturn(node: nodes.Return): AccessNeighborsForReturn =
      new AccessNeighborsForReturn(node)

    implicit def accessNeighborsForReturnTraversal(
      traversal: IterableOnce[nodes.Return]
    ): AccessNeighborsForReturnTraversal =
      new AccessNeighborsForReturnTraversal(traversal.iterator)

    implicit def accessNeighborsForTag(node: nodes.Tag): AccessNeighborsForTag =
      new AccessNeighborsForTag(node)

    implicit def accessNeighborsForTagTraversal(traversal: IterableOnce[nodes.Tag]): AccessNeighborsForTagTraversal =
      new AccessNeighborsForTagTraversal(traversal.iterator)

    implicit def accessNeighborsForTemplateDom(node: nodes.TemplateDom): AccessNeighborsForTemplateDom =
      new AccessNeighborsForTemplateDom(node)

    implicit def accessNeighborsForTemplateDomTraversal(
      traversal: IterableOnce[nodes.TemplateDom]
    ): AccessNeighborsForTemplateDomTraversal =
      new AccessNeighborsForTemplateDomTraversal(traversal.iterator)

    implicit def accessNeighborsForType(node: nodes.Type): AccessNeighborsForType =
      new AccessNeighborsForType(node)

    implicit def accessNeighborsForTypeTraversal(traversal: IterableOnce[nodes.Type]): AccessNeighborsForTypeTraversal =
      new AccessNeighborsForTypeTraversal(traversal.iterator)

    implicit def accessNeighborsForTypeArgument(node: nodes.TypeArgument): AccessNeighborsForTypeArgument =
      new AccessNeighborsForTypeArgument(node)

    implicit def accessNeighborsForTypeArgumentTraversal(
      traversal: IterableOnce[nodes.TypeArgument]
    ): AccessNeighborsForTypeArgumentTraversal =
      new AccessNeighborsForTypeArgumentTraversal(traversal.iterator)

    implicit def accessNeighborsForTypeDecl(node: nodes.TypeDecl): AccessNeighborsForTypeDecl =
      new AccessNeighborsForTypeDecl(node)

    implicit def accessNeighborsForTypeDeclTraversal(
      traversal: IterableOnce[nodes.TypeDecl]
    ): AccessNeighborsForTypeDeclTraversal =
      new AccessNeighborsForTypeDeclTraversal(traversal.iterator)

    implicit def accessNeighborsForTypeParameter(node: nodes.TypeParameter): AccessNeighborsForTypeParameter =
      new AccessNeighborsForTypeParameter(node)

    implicit def accessNeighborsForTypeParameterTraversal(
      traversal: IterableOnce[nodes.TypeParameter]
    ): AccessNeighborsForTypeParameterTraversal =
      new AccessNeighborsForTypeParameterTraversal(traversal.iterator)

    implicit def accessNeighborsForTypeRef(node: nodes.TypeRef): AccessNeighborsForTypeRef =
      new AccessNeighborsForTypeRef(node)

    implicit def accessNeighborsForTypeRefTraversal(
      traversal: IterableOnce[nodes.TypeRef]
    ): AccessNeighborsForTypeRefTraversal =
      new AccessNeighborsForTypeRefTraversal(traversal.iterator)

    implicit def accessNeighborsForUnknown(node: nodes.Unknown): AccessNeighborsForUnknown =
      new AccessNeighborsForUnknown(node)

    implicit def accessNeighborsForUnknownTraversal(
      traversal: IterableOnce[nodes.Unknown]
    ): AccessNeighborsForUnknownTraversal =
      new AccessNeighborsForUnknownTraversal(traversal.iterator)

    implicit def accessNeighborsForCfgNode(node: nodes.CfgNode): AccessNeighborsForCfgNode =
      new AccessNeighborsForCfgNode(node)

    implicit def accessNeighborsForCfgNodeTraversal(
      traversal: IterableOnce[nodes.CfgNode]
    ): AccessNeighborsForCfgNodeTraversal =
      new AccessNeighborsForCfgNodeTraversal(traversal.iterator)

    implicit def accessNeighborsForExpression(node: nodes.Expression): AccessNeighborsForExpression =
      new AccessNeighborsForExpression(node)

    implicit def accessNeighborsForExpressionTraversal(
      traversal: IterableOnce[nodes.Expression]
    ): AccessNeighborsForExpressionTraversal =
      new AccessNeighborsForExpressionTraversal(traversal.iterator)
  }
}

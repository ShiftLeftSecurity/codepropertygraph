package io.shiftleft.semanticcpg.language.types.propertyaccessors

import io.shiftleft.codepropertygraph.generated.nodes._
import overflowdb.Node
import overflowdb.traversal.Traversal

trait Implicits extends LowLowPrioImplicits

trait LowLowPrioImplicits {
  implicit def toCodeAccessors[A <: Node with HasCode](trav: Traversal[A]): CodeAccessors[A] = new CodeAccessors(trav)

  implicit def toCanonicalNameAccessors[A <: Node with HasCanonicalName](
      trav: Traversal[A]): CanonicalNameAccessors[A] = new CanonicalNameAccessors(trav)

  implicit def toDependencyGroupIdAccessors[A <: Node with HasDependencyGroupId](
      trav: Traversal[A]): DependencyGroupIdAccessors[A] = new DependencyGroupIdAccessors(trav)

  implicit def toDispatchTypeAccessors[A <: Node with HasDispatchType](trav: Traversal[A]): DispatchTypeAccessors[A] =
    new DispatchTypeAccessors(trav)

  implicit def toIsExternalAccessors[A <: Node with HasIsExternal](trav: Traversal[A]): IsExternalAccessors[A] =
    new IsExternalAccessors(trav)

  implicit def toFullNameAccessors[A <: Node with HasFullName](trav: Traversal[A]): FullNameAccessors[A] =
    new FullNameAccessors(trav)

  implicit def toLineNumberAccessors[A <: Node with HasLineNumber](trav: Traversal[A]): LineNumberAccessors[A] =
    new LineNumberAccessors(trav)

  implicit def toLineNumberEndAccessors[A <: Node with HasLineNumberEnd](
      trav: Traversal[A]): LineNumberEndAccessors[A] = new LineNumberEndAccessors(trav)

  implicit def toNameAccessors[A <: Node with HasName](trav: Traversal[A]): NameAccessors[A] = new NameAccessors(trav)

  implicit def toOrderAccessors[A <: Node with HasOrder](trav: Traversal[A]): OrderAccessors[A] =
    new OrderAccessors(trav)

  implicit def toParserTypeNameAccessors[A <: Node with HasParserTypeName](
      trav: Traversal[A]): ParserTypeNameAccessors[A] = new ParserTypeNameAccessors(trav)

  implicit def toSignatureAccessors[A <: Node with HasSignature](trav: Traversal[A]): SignatureAccessors[A] =
    new SignatureAccessors(trav)

  implicit def toValueAccessors[A <: Node with HasValue](trav: Traversal[A]): ValueAccessors[A] =
    new ValueAccessors(trav)

  implicit def toVersionAccessors[A <: Node with HasVersion](trav: Traversal[A]): VersionAccessors[A] =
    new VersionAccessors(trav)

  implicit def toArgumentIndexAccessors[NodeType <: Expression](
      trav: Traversal[NodeType]): ArgumentIndexAccessors[NodeType] =
    new ArgumentIndexAccessors(trav)

  // ~ EvalType accessors
  implicit def toEvalTypeAccessorsExpression(trav: Traversal[Expression]): EvalTypeAccessors[Expression] =
    new EvalTypeAccessors(trav)
  implicit def toEvalTypeAccessorsIdentifier(trav: Traversal[Identifier]): EvalTypeAccessors[Identifier] =
    new EvalTypeAccessors(trav)
  implicit def toEvalTypeAccessorsCall(trav: Traversal[Call]): EvalTypeAccessors[Call] =
    new EvalTypeAccessors(trav)
  implicit def toEvalTypeAccessorsLiteral(trav: Traversal[Literal]): EvalTypeAccessors[Literal] =
    new EvalTypeAccessors(trav)
  implicit def toEvalTypeAccessorsLocal(trav: Traversal[Local]): EvalTypeAccessors[Local] =
    new EvalTypeAccessors(trav)
  implicit def toEvalTypeAccessorsMember(trav: Traversal[Member]): EvalTypeAccessors[Member] =
    new EvalTypeAccessors(trav)
  implicit def toEvalTypeAccessorsMethod(trav: Traversal[Method]): EvalTypeAccessors[Method] =
    new EvalTypeAccessors(trav)
  implicit def toEvalTypeAccessorsMethodParameterIn(
      trav: Traversal[MethodParameterIn]): EvalTypeAccessors[MethodParameterIn] =
    new EvalTypeAccessors(trav)
  implicit def toEvalTypeAccessorsMethodParameterOut(
      trav: Traversal[MethodParameterOut]): EvalTypeAccessors[MethodParameterOut] =
    new EvalTypeAccessors(trav)
  implicit def toEvalTypeAccessorsMethodReturn(
      trav: Traversal[MethodReturn]): EvalTypeAccessors[MethodReturn] =
    new EvalTypeAccessors(trav)
  // EvalType accessors ~

  // ~ Modifier accessors
  implicit def toModifierAccessorsMember(trav: Traversal[Member]): ModifierAccessors[Member] =
    new ModifierAccessors(trav)
  implicit def toModifierAccessorsMethod(trav: Traversal[Method]): ModifierAccessors[Method] =
    new ModifierAccessors(trav)
  implicit def toModifierAccessorsTypeDecl(trav: Traversal[TypeDecl]): ModifierAccessors[TypeDecl] =
    new ModifierAccessors(trav)
  // Modifier accessors ~
}

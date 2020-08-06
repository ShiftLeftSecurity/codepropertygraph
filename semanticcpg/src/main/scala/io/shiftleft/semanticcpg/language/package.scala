package io.shiftleft.semanticcpg

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.nodes.{
  HasCanonicalName,
  HasCode,
  HasDependencyGroupId,
  HasDispatchType,
  HasFullName,
  HasIsExternal,
  HasLineNumber,
  HasLineNumberEnd,
  HasName,
  HasOrder,
  HasParserTypeName,
  HasSignature,
  HasValue,
  HasVersion,
  StoredNode
}
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, nodes}
import io.shiftleft.semanticcpg.language.callgraphextension.{Call, Method}
import io.shiftleft.semanticcpg.language.dotextension.{AstNodeDot, CfgNodeDot}
import io.shiftleft.semanticcpg.language.nodemethods._
import io.shiftleft.semanticcpg.language.types.expressions.generalizations._
import io.shiftleft.semanticcpg.language.types.expressions.{Call => OriginalCall, _}
import io.shiftleft.semanticcpg.language.types.propertyaccessors._
import io.shiftleft.semanticcpg.language.types.structure.{Method => OriginalMethod, _}
import overflowdb._
import overflowdb.traversal.{Traversal, _}

/**
  Language for traversing the code property graph

  Implicit conversions to specific steps, based on the node at hand.
  Automatically in scope when using anything in the `steps` package, e.g. `Steps`
  */
package object language extends operatorextension.Implicits {
  // Implicit conversions from generated node types. We use these to add methods
  // to generated node types.

  implicit def cfgNodeToAstNode(node: nodes.CfgNode): AstNodeMethods = new AstNodeMethods(node)
  implicit def toExtendedNode(node: nodes.CpgNode): NodeMethods = new NodeMethods(node)
  implicit def withMethodMethodsQp(node: nodes.WithinMethod): WithinMethodMethods = new WithinMethodMethods(node)
  implicit def toAstNodeMethods(node: nodes.AstNode): AstNodeMethods = new AstNodeMethods(node)
  implicit def toCfgNodeMethods(node: nodes.CfgNode): CfgNodeMethods = new CfgNodeMethods(node)
  implicit def toExpressionMethods(node: nodes.Expression): ExpressionMethods = new ExpressionMethods(node)
  implicit def toMethodMethods(node: nodes.Method): MethodMethods = new MethodMethods(node)
  implicit def toMethodReturnMethods(node: nodes.MethodReturn): MethodReturnMethods = new MethodReturnMethods(node)
  implicit def toCallMethods(node: nodes.Call): CallMethods = new CallMethods(node)

  // Implicit conversions from Step[NodeType, Label] to corresponding Step classes.
  // If you introduce a new Step-type, that is, one that inherits from `Steps[NodeType]`,
  // then you need to add an implicit conversion from `Steps[NodeType]` to your type
  // here.

  implicit def toLiteral(trav: Traversal[nodes.Literal]): Literal = new Literal(trav)
  implicit def toType(trav: Traversal[nodes.Type]): Type = new Type(trav)
  implicit def toTypeDecl(trav: Traversal[nodes.TypeDecl]): TypeDecl = new TypeDecl(trav)
  implicit def toCall(trav: Traversal[nodes.Call]): OriginalCall = new OriginalCall(trav)
  implicit def toModifier(trav: Traversal[nodes.Modifier]): Modifier = new Modifier(trav)
  implicit def toControlStructure(trav: Traversal[nodes.ControlStructure]): ControlStructure =
    new ControlStructure(trav)
  implicit def toIdentifier(trav: Traversal[nodes.Identifier]): IdentifierTrav = new IdentifierTrav(trav)
  implicit def toMember(trav: Traversal[nodes.Member]): Member = new Member(trav)
  implicit def toMetaData(trav: Traversal[nodes.MetaData]): MetaData = new MetaData(trav)
  implicit def toLocal(trav: Traversal[nodes.Local]): Local = new Local(trav)
  implicit def toMethod(trav: Traversal[nodes.Method]): OriginalMethod = new OriginalMethod(trav)
  implicit def toMethodParameter(trav: Traversal[nodes.MethodParameterIn]): MethodParameter = new MethodParameter(trav)
  implicit def toMethodParameterOut(trav: Traversal[nodes.MethodParameterOut]): MethodParameterOut =
    new MethodParameterOut(trav)
  implicit def toMethodReturn(trav: Traversal[nodes.MethodReturn]): MethodReturn = new MethodReturn(trav)
  implicit def toNamespace(trav: Traversal[nodes.Namespace]): Namespace = new Namespace(trav)
  implicit def toNamespaceBlock(trav: Traversal[nodes.NamespaceBlock]): NamespaceBlock = new NamespaceBlock(trav)
  implicit def toExpression[A <: nodes.Expression](trav: Traversal[A]): Expression[A] = new Expression[A](trav)
  implicit def toCfgNode[A <: nodes.CfgNode](trav: Traversal[A]): CfgNode[A] = new CfgNode(trav)
  implicit def toAstNode[A <: nodes.AstNode](trav: Traversal[A]): AstNode[A] = new AstNode(trav)
  implicit def toFile(trav: Traversal[nodes.File]): File = new File(trav)
  implicit def toBlock(trav: Traversal[nodes.Block]): Block = new Block(trav)
  implicit def toMethodRef(trav: Traversal[nodes.MethodRef]): MethodRef = new MethodRef(trav)
  implicit def toBinding(trav: Traversal[nodes.Binding]): Binding = new Binding(trav)

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

  implicit class NodeStepsExt(val traversal: Traversal[_ <: StoredNode]) extends AnyVal {
    /**
    Traverse to tags of nodes in enhanced graph
      */
    def tag: Traversal[nodes.Tag] =
      traversal.out(EdgeTypes.TAGGED_BY).cast[nodes.Tag]
  }

  // Call graph extension
  implicit def toMethodForCallGraph(trav: Traversal[nodes.Method]): Method = new Method(trav)
  implicit def toCallForCallGraph(trav: Traversal[nodes.Call]): Call = new Call(trav)
  // / Call graph extension

  implicit def toAstNodeDot[NodeType <: nodes.AstNode](trav: Traversal[NodeType]): AstNodeDot[NodeType] =
    new AstNodeDot(trav)

  implicit def toCfgNodeDot(trav: Traversal[nodes.Method]): CfgNodeDot =
    new CfgNodeDot(trav)

  implicit def toSteps[A](trav: Traversal[A]): Steps[A] = new Steps(trav)

  implicit def toNodeSteps[NodeType <: nodes.StoredNode](trav: Traversal[NodeType]): NodeSteps[NodeType] =
    new NodeSteps[NodeType](trav)

  implicit def toNewNodeTrav[NodeType <: nodes.NewNode](trav: Traversal[NodeType]): NewNodeSteps[NodeType] =
    new NewNodeSteps[NodeType](trav)

  implicit def toNodeTypeStarters(cpg: Cpg): NodeTypeStarters = new NodeTypeStarters(cpg)
  implicit def toTagTraversal(trav: Traversal[nodes.Tag]): Tag = new Tag(trav)

  implicit def toArgumentIndexAccessors[NodeType <: nodes.Expression](
      trav: Traversal[NodeType]): ArgumentIndexAccessors[NodeType] =
    new ArgumentIndexAccessors(trav)

  // ~ EvalType accessors
  implicit def toEvalTypeAccessorsExpression(trav: Traversal[nodes.Expression]): EvalTypeAccessors[nodes.Expression] =
    new EvalTypeAccessors(trav)
  implicit def toEvalTypeAccessorsIdentifier(trav: Traversal[nodes.Identifier]): EvalTypeAccessors[nodes.Identifier] =
    new EvalTypeAccessors(trav)
  implicit def toEvalTypeAccessorsCall(trav: Traversal[nodes.Call]): EvalTypeAccessors[nodes.Call] =
    new EvalTypeAccessors(trav)
  implicit def toEvalTypeAccessorsLiteral(trav: Traversal[nodes.Literal]): EvalTypeAccessors[nodes.Literal] =
    new EvalTypeAccessors(trav)
  implicit def toEvalTypeAccessorsLocal(trav: Traversal[nodes.Local]): EvalTypeAccessors[nodes.Local] =
    new EvalTypeAccessors(trav)
  implicit def toEvalTypeAccessorsMember(trav: Traversal[nodes.Member]): EvalTypeAccessors[nodes.Member] =
    new EvalTypeAccessors(trav)
  implicit def toEvalTypeAccessorsMethod(trav: Traversal[nodes.Method]): EvalTypeAccessors[nodes.Method] =
    new EvalTypeAccessors(trav)
  implicit def toEvalTypeAccessorsMethodParameterIn(
      trav: Traversal[nodes.MethodParameterIn]): EvalTypeAccessors[nodes.MethodParameterIn] =
    new EvalTypeAccessors(trav)
  implicit def toEvalTypeAccessorsMethodParameterOut(
      trav: Traversal[nodes.MethodParameterOut]): EvalTypeAccessors[nodes.MethodParameterOut] =
    new EvalTypeAccessors(trav)
  implicit def toEvalTypeAccessorsMethodReturn(
      trav: Traversal[nodes.MethodReturn]): EvalTypeAccessors[nodes.MethodReturn] =
    new EvalTypeAccessors(trav)
  // EvalType accessors ~

  // ~ Modifier accessors
  implicit def toModifierAccessorsMember(trav: Traversal[nodes.Member]): ModifierAccessors[nodes.Member] =
    new ModifierAccessors(trav)
  implicit def toModifierAccessorsMethod(trav: Traversal[nodes.Method]): ModifierAccessors[nodes.Method] =
    new ModifierAccessors(trav)
  implicit def toModifierAccessorsTypeDecl(trav: Traversal[nodes.TypeDecl]): ModifierAccessors[nodes.TypeDecl] =
    new ModifierAccessors(trav)
  // Modifier accessors ~
}

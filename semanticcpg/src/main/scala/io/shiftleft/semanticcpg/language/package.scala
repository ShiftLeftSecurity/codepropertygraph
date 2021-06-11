package io.shiftleft.semanticcpg

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.codepropertygraph.generated.nodes.{AbstractNode, StoredNode}
import io.shiftleft.semanticcpg.language.callgraphextension.{CallTraversal, MethodTraversal}
import io.shiftleft.semanticcpg.language.dotextension.{AstNodeDot, CfgNodeDot}
import io.shiftleft.semanticcpg.language.nodemethods._
import io.shiftleft.semanticcpg.language.types.expressions.generalizations._
import io.shiftleft.semanticcpg.language.types.expressions.{CallTraversal => OriginalCall, _}
import io.shiftleft.semanticcpg.language.types.propertyaccessors._
import io.shiftleft.semanticcpg.language.types.structure.{MethodTraversal => OriginalMethod, _}
import overflowdb.traversal.Traversal

/**
  Language for traversing the code property graph

  Implicit conversions to specific steps, based on the node at hand.
  Automatically in scope when using anything in the `steps` package, e.g. `Steps`
  */
package object language extends operatorextension.Implicits with LowPrioImplicits {
  // Implicit conversions from generated node types. We use these to add methods
  // to generated node types.

  implicit def cfgNodeToAsNode(node: nodes.CfgNode): AstNodeMethods = new AstNodeMethods(node)
  implicit def toExtendedNode(node: nodes.AbstractNode): NodeMethods = new NodeMethods(node)
  implicit def toExtendedStoredNode(node: nodes.StoredNode): StoredNodeMethods = new StoredNodeMethods(node)
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

  implicit def toLiteral[A](a: A)(implicit f: A => Traversal[nodes.Literal]): LiteralTraversal = new LiteralTraversal(f(a))

  implicit def toType[A](a: A)(implicit f: A => Traversal[nodes.Type]): TypeTraversal = new TypeTraversal(f(a))
  implicit def toTypeDecl[A](a: A)(implicit f: A => Traversal[nodes.TypeDecl]): TypeDeclTraversal = new TypeDeclTraversal(f(a))
  implicit def toCall[A](a: A)(implicit f: A => Traversal[nodes.Call]): OriginalCall = new OriginalCall(f(a))
  implicit def toControlStructure[A](a: A)(implicit f: A => Traversal[nodes.ControlStructure]): ControlStructureTraversal =
    new ControlStructureTraversal(f(a))
  implicit def toIdentifier[A](a: A)(implicit f: A => Traversal[nodes.Identifier]): IdentifierTraversal =
    new IdentifierTraversal(f(a))
  implicit def toMember[A](a: A)(implicit f: A => Traversal[nodes.Member]): MemberTraversal = new MemberTraversal(f(a))
  implicit def toLocal[A](a: A)(implicit f: A => Traversal[nodes.Local]): LocalTraversal = new LocalTraversal(f(a))
  implicit def toMethod[A](a: A)(implicit f: A => Traversal[nodes.Method]): OriginalMethod = new OriginalMethod(f(a))
  implicit def toMethodParameter[A](a: A)(implicit f: A => Traversal[nodes.MethodParameterIn]): MethodParameterTraversal =
    new MethodParameterTraversal(f(a))
  implicit def toMethodParameterOut[A](a: A)(implicit f: A => Traversal[nodes.MethodParameterOut]): MethodParameterOutTraversal =
    new MethodParameterOutTraversal(f(a))
  implicit def toMethodReturn[A](a: A)(implicit f: A => Traversal[nodes.MethodReturn]): MethodReturnTraversal =
    new MethodReturnTraversal(f(a))
  implicit def toNamespace[A](a: A)(implicit f: A => Traversal[nodes.Namespace]): NamespaceTraversal = new NamespaceTraversal(f(a))
  implicit def toNamespaceBlock[A](a: A)(implicit f: A => Traversal[nodes.NamespaceBlock]): NamespaceBlockTraversal =
    new NamespaceBlockTraversal(f(a))
  implicit def toFile[A](a: A)(implicit f: A => Traversal[nodes.File]): FileTraversal = new FileTraversal(f(a))
  implicit def toBlock[A](a: A)(implicit f: A => Traversal[nodes.Block]): BlockTraversal = new BlockTraversal(f(a))
  implicit def toMethodRef[A](a: A)(implicit f: A => Traversal[nodes.MethodRef]): MethodRefTraversal = new MethodRefTraversal(f(a))
  implicit def toBinding[A](a: A)(implicit f: A => Traversal[nodes.Binding]): BindingTraversal = new BindingTraversal(f(a))

  // Call graph extension
  implicit def toMethodForCallGraph[A](a: A)(implicit f: A => Traversal[nodes.Method]): MethodTraversal = new MethodTraversal(f(a))
  implicit def toCallForCallGraph[A](a: A)(implicit f: A => Traversal[nodes.Call]): CallTraversal = new CallTraversal(f(a))
  // / Call graph extension

  implicit def toAstNodeDot[A, NodeType <: nodes.AstNode](a: A)(
      implicit f: A => Traversal[NodeType]): AstNodeDot[NodeType] =
    new AstNodeDot(f(a))

  implicit def toCfgNodeDot[A](a: A)(implicit f: A => Traversal[nodes.Method]): CfgNodeDot =
    new CfgNodeDot(f(a))

  implicit def toTraversal[NodeType <: AbstractNode](node: NodeType): Traversal[NodeType] =
    Traversal.fromSingle(node)

  implicit def toSteps[A](trav: Traversal[A]): Steps[A] = new Steps(trav)

  implicit def toNodeSteps[A, NodeType <: StoredNode](a: A)(implicit f: A => Traversal[NodeType]): NodeSteps[NodeType] =
    new NodeSteps[NodeType](f(a))

  implicit def toNewNodeTrav[NodeType <: nodes.NewNode](trav: Traversal[NodeType]): NewNodeSteps[NodeType] =
    new NewNodeSteps[NodeType](trav)

  implicit def toNodeTypeStarters(cpg: Cpg): NodeTypeStarters = new NodeTypeStarters(cpg)
  implicit def toTagTraversal(trav: Traversal[nodes.Tag]): TagTraversal = new TagTraversal(trav)

  // ~ EvalType accessors
  implicit def toEvalTypeAccessorsLocal[A](a: A)(
      implicit f: A => Traversal[nodes.Local]): EvalTypeAccessors[nodes.Local] =
    new EvalTypeAccessors(f(a))
  implicit def toEvalTypeAccessorsMember[A](a: A)(
      implicit f: A => Traversal[nodes.Member]): EvalTypeAccessors[nodes.Member] =
    new EvalTypeAccessors(f(a))
  implicit def toEvalTypeAccessorsMethod[A](a: A)(
      implicit f: A => Traversal[nodes.Method]): EvalTypeAccessors[nodes.Method] =
    new EvalTypeAccessors(f(a))
  implicit def toEvalTypeAccessorsMethodParameterIn[A](a: A)(
      implicit f: A => Traversal[nodes.MethodParameterIn]): EvalTypeAccessors[nodes.MethodParameterIn] =
    new EvalTypeAccessors(f(a))
  implicit def toEvalTypeAccessorsMethodParameterOut[A](a: A)(
      implicit f: A => Traversal[nodes.MethodParameterOut]): EvalTypeAccessors[nodes.MethodParameterOut] =
    new EvalTypeAccessors(f(a))
  implicit def toEvalTypeAccessorsMethodReturn[A](a: A)(
      implicit f: A => Traversal[nodes.MethodReturn]): EvalTypeAccessors[nodes.MethodReturn] =
    new EvalTypeAccessors(f(a))
  // EvalType accessors ~

  // ~ Modifier accessors
  implicit def toModifierAccessorsMember[A](a: A)(
      implicit f: A => Traversal[nodes.Member]): ModifierAccessors[nodes.Member] =
    new ModifierAccessors(f(a))
  implicit def toModifierAccessorsMethod[A](a: A)(
      implicit f: A => Traversal[nodes.Method]): ModifierAccessors[nodes.Method] =
    new ModifierAccessors(f(a))
  implicit def toModifierAccessorsTypeDecl[A](a: A)(
      implicit f: A => Traversal[nodes.TypeDecl]): ModifierAccessors[nodes.TypeDecl] =
    new ModifierAccessors(f(a))
  // Modifier accessors ~

  implicit class NewNodeTypeDeco[NodeType <: nodes.NewNode](val node: NodeType) extends AnyVal {

    /**
    Start a new traversal from this node
      */
    def start: Traversal[NodeType] =
      Traversal.fromSingle(node)
  }

}

trait LowPrioImplicits extends LowLowPrioImplicits {
  implicit def toExpression[A, NodeType <: nodes.Expression](a: A)(
      implicit f: A => Traversal[NodeType]): ExpressionTraversal[NodeType] = new ExpressionTraversal[NodeType](f(a))

  implicit def toEvalTypeAccessorsExpression[A, NodeType <: nodes.Expression](a: A)(
      implicit f: A => Traversal[NodeType]): EvalTypeAccessors[NodeType] =
    new EvalTypeAccessors(f(a))
}

trait LowLowPrioImplicits {
  implicit def toCfgNode[A, NodeType <: nodes.CfgNode](a: A)(implicit f: A => Traversal[NodeType]): CfgNodeTraversal[NodeType] =
    new CfgNodeTraversal(f(a))
  implicit def toAstNode[A, NodeType <: nodes.AstNode](a: A)(implicit f: A => Traversal[NodeType]): AstNodeTraversal[NodeType] =
    new AstNodeTraversal[NodeType](f(a))
}

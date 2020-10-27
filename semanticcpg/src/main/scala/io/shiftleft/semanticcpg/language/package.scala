package io.shiftleft.semanticcpg

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.nodes.{CpgNode, StoredNode}
import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.semanticcpg.language.callgraphextension.{Call, Method}
import io.shiftleft.semanticcpg.language.dotextension.{AstNodeDot, CfgNodeDot}
import io.shiftleft.semanticcpg.language.nodemethods._
import io.shiftleft.semanticcpg.language.types.expressions.generalizations._
import io.shiftleft.semanticcpg.language.types.expressions.{Call => OriginalCall, _}
import io.shiftleft.semanticcpg.language.types.propertyaccessors._
import io.shiftleft.semanticcpg.language.types.structure.{Method => OriginalMethod, _}
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

  implicit def toLiteral[A](a: A)(implicit f: A => Traversal[nodes.Literal]): Literal = new Literal(f(a))

  implicit def toType[A](a: A)(implicit f: A => Traversal[nodes.Type]): Type = new Type(f(a))
  implicit def toTypeDecl[A](a: A)(implicit f: A => Traversal[nodes.TypeDecl]): TypeDecl = new TypeDecl(f(a))
  implicit def toCall[A](a: A)(implicit f: A => Traversal[nodes.Call]): OriginalCall = new OriginalCall(f(a))
  implicit def toControlStructure[A](a: A)(implicit f: A => Traversal[nodes.ControlStructure]): ControlStructure = new ControlStructure(f(a))
  implicit def toIdentifier[A](a: A)(implicit f: A => Traversal[nodes.Identifier]): IdentifierTrav = new IdentifierTrav(f(a))
  implicit def toMember[A](a: A)(implicit f: A => Traversal[nodes.Member]): Member = new Member(f(a))
  implicit def toLocal[A](a: A)(implicit f: A => Traversal[nodes.Local]): Local = new Local(f(a))
  implicit def toMethod[A](a: A)(implicit f: A => Traversal[nodes.Method]): OriginalMethod = new OriginalMethod(f(a))
  implicit def toMethodParameter[A](a: A)(implicit f: A => Traversal[nodes.MethodParameterIn]): MethodParameter = new MethodParameter(f(a))
  implicit def toMethodParameterOut[A](a: A)(implicit f: A => Traversal[nodes.MethodParameterOut]): MethodParameterOut = new MethodParameterOut(f(a))
  implicit def toMethodReturn[A](a: A)(implicit f: A => Traversal[nodes.MethodReturn]): MethodReturn = new MethodReturn(f(a))
  implicit def toNamespace[A](a: A)(implicit f: A => Traversal[nodes.Namespace]): Namespace = new Namespace(f(a))
  implicit def toNamespaceBlock[A](a: A)(implicit f: A => Traversal[nodes.NamespaceBlock]): NamespaceBlock = new NamespaceBlock(f(a))
  implicit def toFile[A](a: A)(implicit f: A => Traversal[nodes.File]): File = new File(f(a))
  implicit def toBlock[A](a: A)(implicit f: A => Traversal[nodes.Block]): Block = new Block(f(a))
  implicit def toMethodRef[A](a: A)(implicit f: A => Traversal[nodes.MethodRef]): MethodRef = new MethodRef(f(a))
  implicit def toBinding[A](a: A)(implicit f: A => Traversal[nodes.Binding]): Binding = new Binding(f(a))

  implicit def toExpression[A, NodeType <: nodes.Expression](a: A)(implicit f: A => Traversal[NodeType]): Expression[NodeType] = new Expression[NodeType](f(a))
  implicit def toCfgNode[A <: nodes.CfgNode](trav: Traversal[A]): CfgNode[A] = new CfgNode(trav)
  implicit def toAstNode[A, NodeType <: nodes.AstNode](a: A)(implicit f: A => Traversal[NodeType]): AstNode[NodeType] = new AstNode[NodeType](f(a))

  // Call graph extension
  implicit def toMethodForCallGraph(trav: Traversal[nodes.Method]): Method = new Method(trav)
  implicit def toCallForCallGraph(trav: Traversal[nodes.Call]): Call = new Call(trav)
  // / Call graph extension

  implicit def toAstNodeDot[NodeType <: nodes.AstNode](trav: Traversal[NodeType]): AstNodeDot[NodeType] =
    new AstNodeDot(trav)

  implicit def toCfgNodeDot(trav: Traversal[nodes.Method]): CfgNodeDot =
    new CfgNodeDot(trav)

  implicit def toTraversal[NodeType <: CpgNode](node: NodeType): Traversal[NodeType] =
    Traversal.fromSingle(node)

  implicit def toSteps[A](trav: Traversal[A]): Steps[A] = new Steps(trav)

  implicit def toNodeSteps[A, NodeType <: StoredNode](a: A)(implicit f: A => Traversal[NodeType]): NodeSteps[NodeType] =
    new NodeSteps[NodeType](f(a))

  implicit def toNewNodeTrav[NodeType <: nodes.NewNode](trav: Traversal[NodeType]): NewNodeSteps[NodeType] =
    new NewNodeSteps[NodeType](trav)

  implicit def toNodeTypeStarters(cpg: Cpg): NodeTypeStarters = new NodeTypeStarters(cpg)
  implicit def toTagTraversal(trav: Traversal[nodes.Tag]): Tag = new Tag(trav)

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

  implicit class NewNodeTypeDeco[NodeType <: nodes.NewNode](val node: NodeType) extends AnyVal {

    /**
    Start a new traversal from this node
      */
    def start: Traversal[NodeType] =
      Traversal.fromSingle(node)
  }

}

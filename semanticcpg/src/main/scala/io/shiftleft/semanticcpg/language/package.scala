package io.shiftleft.semanticcpg

import gremlin.scala._
import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, nodes}
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
  Node,
  StoredNode
}
import io.shiftleft.overflowdb.traversal.Traversal
import io.shiftleft.semanticcpg.language.callgraphextension.{Call, Method}
import io.shiftleft.semanticcpg.language.dotextension.{AstNodeDot, CfgNodeDot}
import io.shiftleft.semanticcpg.language.nodemethods.{
  AstNodeMethods,
  CallMethods,
  CfgNodeMethods,
  ExpressionMethods,
  MethodMethods,
  MethodReturnMethods,
  NodeMethods,
  WithinMethodMethods
}
import io.shiftleft.semanticcpg.language.types.structure._
import io.shiftleft.semanticcpg.language.types.expressions._
import io.shiftleft.semanticcpg.language.types.expressions.generalizations._
import io.shiftleft.semanticcpg.language.types.structure.{Method => OriginalMethod}
import io.shiftleft.semanticcpg.language.types.expressions.{Call => OriginalCall}
import io.shiftleft.semanticcpg.language.types.propertyaccessors._

/**
  Language for traversing the code property graph

  Implicit conversions to specific steps, based on the node at hand.
  Automatically in scope when using anything in the `steps` package, e.g. `Steps`
  */
package object language extends operatorextension.Implicits {
  // Implicit conversions from generated node types. We use these to add methods
  // to generated node types.

  implicit def cfgNodeToAstNode(node: nodes.CfgNode): AstNodeMethods = new AstNodeMethods(node)
  implicit def toExtendedNode(node: Node): NodeMethods = new NodeMethods(node)
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

  implicit def toLiteral(steps: Steps[nodes.Literal]): Literal = new Literal(steps)
  implicit def toType(steps: Steps[nodes.Type]): Type = new Type(steps)
  implicit def toTypeDecl(steps: Steps[nodes.TypeDecl]): TypeDecl = new TypeDecl(steps)
  implicit def toCall(steps: Steps[nodes.Call]): OriginalCall = new OriginalCall(steps)
  implicit def toControlStructure(steps: Steps[nodes.ControlStructure]): ControlStructure = new ControlStructure(steps)
  implicit def toIdentifier(steps: Steps[nodes.Identifier]): IdentifierTrav = new IdentifierTrav(steps)
  implicit def toMember(steps: Steps[nodes.Member]): Member = new Member(steps)
  implicit def toMetaData(steps: Steps[nodes.MetaData]): MetaData = new MetaData(steps)
  implicit def toLocal(steps: Steps[nodes.Local]): Local = new Local(steps)
  implicit def toMethod(steps: Steps[nodes.Method]): OriginalMethod = new OriginalMethod(steps)
  implicit def toMethodParameter(steps: Steps[nodes.MethodParameterIn]): MethodParameter = new MethodParameter(steps)
  implicit def toMethodParameterOut(steps: Steps[nodes.MethodParameterOut]): MethodParameterOut =
    new MethodParameterOut(steps)
  implicit def toMethodReturn(steps: Steps[nodes.MethodReturn]): MethodReturn = new MethodReturn(steps)
  implicit def toNamespace(steps: Steps[nodes.Namespace]): Namespace = new Namespace(steps)
  implicit def toNamespaceBlock(steps: Steps[nodes.NamespaceBlock]): NamespaceBlock = new NamespaceBlock(steps)
  implicit def toExpression[A <: nodes.Expression](steps: Steps[A]): Expression[A] = new Expression[A](steps)
  implicit def toCfgNode[A <: nodes.CfgNode](steps: Steps[A]): CfgNode[A] = new CfgNode(steps)
  implicit def toAstNode[A <: nodes.AstNode](steps: Steps[A]): AstNode[A] = new AstNode(steps)
  implicit def toFile(steps: Steps[nodes.File]): File = new File(steps)
  implicit def toBlock(steps: Steps[nodes.Block]): Block = new Block(steps)
  implicit def toMethodRef(steps: Steps[nodes.MethodRef]): MethodRef = new MethodRef(steps)
  implicit def toBinding(steps: Steps[nodes.Binding]): Binding = new Binding(steps)

  implicit def toCodeAccessors[A <: StoredNode with HasCode](steps: Steps[A]): CodeAccessors[A] =
    new CodeAccessors(steps)

  implicit def toCanonicalNameAccessors[A <: StoredNode with HasCanonicalName](
      steps: Steps[A]): CanonicalNameAccessors[A] =
    new CanonicalNameAccessors(steps)

  implicit def toDependencyGroupIdAccessors[A <: StoredNode with HasDependencyGroupId](
      steps: Steps[A]): DependencyGroupIdAccessors[A] =
    new DependencyGroupIdAccessors(steps)

  implicit def toDispatchTypeAccessors[A <: StoredNode with HasDispatchType](
      steps: Steps[A]): DispatchTypeAccessors[A] =
    new DispatchTypeAccessors(steps)

  implicit def toIsExternalAccessors[A <: StoredNode with HasIsExternal](steps: Steps[A]): IsExternalAccessors[A] =
    new IsExternalAccessors(steps)

  implicit def toFullNameAccessors[A <: StoredNode with HasFullName](steps: Steps[A]): FullNameAccessors[A] =
    new FullNameAccessors(steps)

  implicit def toLineNumberAccessors[A <: StoredNode with HasLineNumber](steps: Steps[A]): LineNumberAccessors[A] =
    new LineNumberAccessors(steps)

  implicit def toLineNumberEndAccessors[A <: StoredNode with HasLineNumberEnd](
      steps: Steps[A]): LineNumberEndAccessors[A] =
    new LineNumberEndAccessors(steps)

  implicit def toNameAccessors[A <: StoredNode with HasName](steps: Steps[A]): NameAccessors[A] =
    new NameAccessors(steps)

  implicit def toOrderAccessors[A <: StoredNode with HasOrder](steps: Steps[A]): OrderAccessors[A] =
    new OrderAccessors(steps)

  implicit def toParserTypeNameAccessors[A <: StoredNode with HasParserTypeName](
      steps: Steps[A]): ParserTypeNameAccessors[A] =
    new ParserTypeNameAccessors(steps)

  implicit def toSignatureAccessors[A <: StoredNode with HasSignature](steps: Steps[A]): SignatureAccessors[A] =
    new SignatureAccessors(steps)

  implicit def toValueAccessors[A <: StoredNode with HasValue](steps: Steps[A]): ValueAccessors[A] =
    new ValueAccessors(steps)

  implicit def toVersionAccessors[A <: StoredNode with HasVersion](steps: Steps[A]): VersionAccessors[A] =
    new VersionAccessors(steps)

  implicit class GremlinScalaDeco[End](val raw: GremlinScala[End]) extends AnyVal {
    /* in some cases we cannot statically determine the type of the node, e.g. when traversing
     * from a known nodeType via AST edges, so we have to cast */
    def cast[NodeType]: GremlinScala[NodeType] =
      raw.asInstanceOf[GremlinScala[NodeType]]
  }

  private def newAnonymousTraversalWithAssociatedGraph[NodeType <: StoredNode](
      seq: NodeType*): GremlinScala[NodeType] = {
    val anonymousTraversal = __[NodeType](seq: _*)
    if (seq.nonEmpty) {
      anonymousTraversal.traversal.asAdmin().setGraph(seq.head.graph)
    }
    anonymousTraversal
  }

  implicit class NodeTypeDeco[NodeType <: StoredNode](val node: NodeType) extends AnyVal {

    /**
    Start a new traversal from this node
      */
    def start: NodeSteps[NodeType] =
      new NodeSteps[NodeType](newAnonymousTraversalWithAssociatedGraph(node))
  }

  implicit class NodeTypeDecoForIterable[NodeType <: nodes.StoredNode](val iter: Iterable[NodeType]) extends AnyVal {

    /**
    Start a new traversal from these nodes
      */
    def start: NodeSteps[NodeType] =
      new NodeSteps[NodeType](newAnonymousTraversalWithAssociatedGraph(iter.to(Seq): _*))
  }

  implicit class NewNodeTypeDeco[NodeType <: nodes.NewNode](val node: NodeType) extends AnyVal {

    /**
    Start a new traversal from this node
      */
    def start: NewNodeSteps[NodeType] =
      new NewNodeSteps[NodeType](__[NodeType](node))
  }

  implicit class NewNodeTypeDecoForIterable[NodeType <: nodes.NewNode](val iter: Iterable[NodeType]) extends AnyVal {

    /**
    Start a new traversal from these nodes
      */
    def start: NewNodeSteps[NodeType] =
      new NewNodeSteps[NodeType](__[NodeType](iter.to(Seq): _*))
  }

  implicit class BaseNodeTypeDeco[NodeType <: nodes.Node](val node: NodeType) extends AnyVal {

    /**
    Start a new traversal from this node
      */
    def start: Steps[NodeType] =
      new Steps[NodeType](__[NodeType](node))
  }

  implicit class BaseNodeTypeDecoForIterable[NodeType <: nodes.Node](val iter: Iterable[NodeType]) extends AnyVal {

    /**
    Start a new traversal from these nodes
      */
    def start: Steps[NodeType] =
      new Steps[NodeType](__[NodeType](iter.to(Seq): _*))
  }

  implicit class NodeStepsExt(val steps: Steps[_ <: StoredNode]) extends AnyVal {
    private def raw: GremlinScala[_ <: StoredNode] = steps.raw

    /**
    Traverse to tags of nodes in enhanced graph
      */
    def tag: NodeSteps[nodes.Tag] =
      new NodeSteps(raw.out(EdgeTypes.TAGGED_BY).cast[nodes.Tag])
  }

  // Call graph extension
  implicit def toMethodForCallGraph(steps: Steps[nodes.Method]): Method = new Method(steps)
  implicit def toCallForCallGraph(steps: Steps[nodes.Call]): Call = new Call(steps)
  // / Call graph extension

  //
  implicit def toAstNodeDot[NodeType <: nodes.AstNode](steps: Steps[NodeType]): AstNodeDot[NodeType] =
    new AstNodeDot(steps)

  implicit def toCfgNodeDot[NodeType <: nodes.CfgNode](steps: Steps[NodeType]): CfgNodeDot[NodeType] =
    new CfgNodeDot(steps)

  implicit def toNodeSteps[NodeType <: nodes.StoredNode](original: Steps[NodeType]): NodeSteps[NodeType] =
    new NodeSteps[NodeType](original.raw)

  implicit def toNewNodeSteps[NodeType <: nodes.NewNode](original: Steps[NodeType]): NewNodeSteps[NodeType] =
    new NewNodeSteps[NodeType](original.raw)

  implicit def toNodeTypeStarters(cpg: Cpg): NodeTypeStarters = new NodeTypeStarters(cpg)
  implicit def toTagTraversal(steps: Steps[nodes.Tag]): Tag = new Tag(steps)

  implicit def toArgumentIndexAccessors[NodeType <: nodes.Expression](
      steps: Steps[NodeType]): ArgumentIndexAccessors[NodeType] =
    new ArgumentIndexAccessors(steps)

  // ~ EvalType accessors
  implicit def toEvalTypeAccessorsExpression(steps: Steps[nodes.Expression]): EvalTypeAccessors[nodes.Expression] =
    new EvalTypeAccessors(steps)
  implicit def toEvalTypeAccessorsIdentifier(steps: Steps[nodes.Identifier]): EvalTypeAccessors[nodes.Identifier] =
    new EvalTypeAccessors(steps)
  implicit def toEvalTypeAccessorsCall(steps: Steps[nodes.Call]): EvalTypeAccessors[nodes.Call] =
    new EvalTypeAccessors(steps)
  implicit def toEvalTypeAccessorsLiteral(steps: Steps[nodes.Literal]): EvalTypeAccessors[nodes.Literal] =
    new EvalTypeAccessors(steps)
  implicit def toEvalTypeAccessorsLocal(steps: Steps[nodes.Local]): EvalTypeAccessors[nodes.Local] =
    new EvalTypeAccessors(steps)
  implicit def toEvalTypeAccessorsMember(steps: Steps[nodes.Member]): EvalTypeAccessors[nodes.Member] =
    new EvalTypeAccessors(steps)
  implicit def toEvalTypeAccessorsMethod(steps: Steps[nodes.Method]): EvalTypeAccessors[nodes.Method] =
    new EvalTypeAccessors(steps)
  implicit def toEvalTypeAccessorsMethodParameterIn(
      steps: Steps[nodes.MethodParameterIn]): EvalTypeAccessors[nodes.MethodParameterIn] =
    new EvalTypeAccessors(steps)
  implicit def toEvalTypeAccessorsMethodParameterOut(
      steps: Steps[nodes.MethodParameterOut]): EvalTypeAccessors[nodes.MethodParameterOut] =
    new EvalTypeAccessors(steps)
  implicit def toEvalTypeAccessorsMethodReturn(
      steps: Steps[nodes.MethodReturn]): EvalTypeAccessors[nodes.MethodReturn] =
    new EvalTypeAccessors(steps)
  // EvalType accessors ~

  // ~ Modifier accessors
  implicit def toModifierAccessorsMember(steps: Steps[nodes.Member]): ModifierAccessors[nodes.Member] =
    new ModifierAccessors(steps)
  implicit def toModifierAccessorsMethod(steps: Steps[nodes.Method]): ModifierAccessors[nodes.Method] =
    new ModifierAccessors(steps)
  implicit def toModifierAccessorsTypeDecl(steps: Steps[nodes.TypeDecl]): ModifierAccessors[nodes.TypeDecl] =
    new ModifierAccessors(steps)
  // Modifier accessors ~

  // ~ cpg steps / overflowdb traversal interop
  implicit def toOdbTraversal[A](steps: Steps[A]): Traversal[A] =
    Traversal.from(steps.raw.toIterator)

  /** tinkerpop doesn't allow to start from an iterator unfortunately, so this executes the traversal :( */
  implicit def toCpgSteps[A](traversal: Traversal[A]): Steps[A] =
    new Steps[A](gremlin.scala.__(traversal.to(Seq): _*))
  // cpg steps / overflowdb traversal interop ~

}

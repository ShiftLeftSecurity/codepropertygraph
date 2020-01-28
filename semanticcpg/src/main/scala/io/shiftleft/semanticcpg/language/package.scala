package io.shiftleft.semanticcpg

import gremlin.scala._
import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.codepropertygraph.generated.nodes.{
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
import io.shiftleft.semanticcpg.language.callgraphextension.{Call, Method}
import io.shiftleft.semanticcpg.language.dotextension.MethodDOT
import io.shiftleft.semanticcpg.language.nodemethods.{
  AstNodeMethods,
  CallMethods,
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
package object language {

  // Implicit conversions from generated node types. We use these to add methods
  // to generated node types.

  implicit def cfgNodeToAstNode(node: nodes.CfgNode): AstNodeMethods = new AstNodeMethods(node)

  implicit def toExtendedNode(node: Node): NodeMethods = new NodeMethods(node)

  implicit def withMethodMethodsQp(node: nodes.WithinMethod): WithinMethodMethods =
    new WithinMethodMethods(node)

  implicit def toAstNodeMethods(node: nodes.AstNode): AstNodeMethods =
    new AstNodeMethods(node)

  implicit def toMethodMethods(node: nodes.Method): MethodMethods =
    new MethodMethods(node)

  implicit def toMethodReturnMethods(node: nodes.MethodReturn): MethodReturnMethods =
    new MethodReturnMethods(node)

  implicit def toCallMethods(node: nodes.Call): CallMethods =
    new CallMethods(node)

  // Implicit conversions from Step[NodeType, Label] to corresponding Step classes.
  // If you introduce a new Step-type, that is, one that inherits from `Steps[NodeType]`,
  // then you need to add an implicit conversion from `Steps[NodeType]` to your type
  // here.

  implicit def toLiteral[A <: nodes.Literal](steps: Steps[A]): Literal[A] =
    new Literal(steps.raw)

  implicit def toType[A <: nodes.Type](steps: Steps[A]): Type[A] =
    new Type(steps.raw)

  implicit def toTypeDecl[A <: nodes.TypeDecl](steps: Steps[A]): TypeDecl[A] =
    new TypeDecl(steps.raw)

  implicit def toCall[A <: nodes.Call](steps: Steps[A]): OriginalCall[A] =
    new OriginalCall(steps.raw)

  implicit def toControlStructure[A <: nodes.ControlStructure](steps: Steps[A]): ControlStructure[A] =
    new ControlStructure(steps.raw)

  implicit def toIdentifier[A <: nodes.Identifier](steps: Steps[A]): Identifier[A] =
    new Identifier(steps.raw)

  implicit def toMember[A <: nodes.Member](steps: Steps[A]): Member[A] =
    new Member(steps.raw)

  implicit def toMetaData[A <: nodes.MetaData](steps: Steps[A]): MetaData[A] =
    new MetaData(steps.raw)

  implicit def toLocal[A <: nodes.Local](steps: Steps[A]): Local[A] =
    new Local(steps.raw)

  implicit def toMethod[A <: nodes.Method](steps: Steps[A]): OriginalMethod[A] =
    new OriginalMethod(steps.raw)

  implicit def toMethodParameter[A <: nodes.MethodParameterIn](steps: Steps[A]): MethodParameter[A] =
    new MethodParameter(steps.raw)

  implicit def toMethodParameterOut[A <: nodes.MethodParameterOut](steps: Steps[A]): MethodParameterOut[A] =
    new MethodParameterOut(steps.raw)

  implicit def toMethodReturn[A <: nodes.MethodReturn](steps: Steps[A]): MethodReturn[A] =
    new MethodReturn(steps.raw)

  implicit def toNamespace[A <: nodes.Namespace](steps: Steps[A]): Namespace[A] =
    new Namespace(steps.raw)

  implicit def toNamespaceBlock[A <: nodes.NamespaceBlock](steps: Steps[A]): NamespaceBlock[A] =
    new NamespaceBlock(steps.raw)

  implicit def toExpression[A <: nodes.Expression](steps: Steps[A]): Expression[A] =
    new Expression[A](steps.raw)

  implicit def toCfgNode[A <: nodes.CfgNode](steps: Steps[A]): CfgNode[A] =
    new CfgNode(steps.raw)

  implicit def toAstNode[A <: nodes.AstNode](steps: Steps[A]): AstNode[A] =
    new AstNode(steps.raw)

  implicit def toFile[A <: nodes.File](steps: Steps[A]): File[A] =
    new File(steps.raw)

  implicit def toBlock[A <: nodes.Block](steps: Steps[A]): Block[A] =
    new Block(steps.raw)

  implicit def toMethodRef[A <: nodes.MethodRef](steps: Steps[A]): MethodRef[A] =
    new MethodRef(steps.raw)

  implicit def toBinding[A <: nodes.Binding](steps: Steps[A]): Binding[A] =
    new Binding(steps.raw)

  implicit def toComment[A <: nodes.Comment](steps: Steps[A]): Comment[A] =
    new Comment(steps.raw)

  implicit def toCodeAccessors[A <: StoredNode with HasCode](steps: Steps[A]): CodeAccessors[A] =
    new CodeAccessors(steps)

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

  implicit class GremlinScalaDeco[End](raw: GremlinScala[End]) {
    /* in some cases we cannot statically determine the type of the node, e.g. when traversing
     * from a known nodeType via AST edges, so we have to cast */
    def cast[NodeType]: GremlinScala[NodeType] =
      raw.asInstanceOf[GremlinScala[NodeType]]
  }

  implicit def toNodeTypeStarters(cpg: Cpg): NodeTypeStarters =
    new NodeTypeStarters(cpg)

  private def newAnonymousTraversalWithAssociatedGraph[NodeType <: StoredNode](
      seq: NodeType*): GremlinScala[NodeType] = {
    val anonymousTraversal = __[NodeType](seq: _*)
    if (seq.nonEmpty) {
      anonymousTraversal.traversal.asAdmin().setGraph(seq.head.graph)
    }
    anonymousTraversal
  }

  implicit class NodeTypeDeco[NodeType <: StoredNode](node: NodeType) {

    /**
    Start a new traversal from this node
      */
    def start: NodeSteps[NodeType] =
      new NodeSteps[NodeType](newAnonymousTraversalWithAssociatedGraph(node))
  }

  implicit class NodeTypeDecoForIterable[NodeType <: nodes.StoredNode](iter: Iterable[NodeType]) {

    /**
    Start a new traversal from these nodes
      */
    def start: NodeSteps[NodeType] =
      new NodeSteps[NodeType](newAnonymousTraversalWithAssociatedGraph(iter.to(Seq): _*))
  }

  implicit class NewNodeTypeDeco[NodeType <: nodes.NewNode](node: NodeType) {

    /**
    Start a new traversal from this node
      */
    def start: NewNodeSteps[NodeType] =
      new NewNodeSteps[NodeType](__[NodeType](node))
  }

  implicit class NewNodeTypeDecoForIterable[NodeType <: nodes.NewNode](iter: Iterable[NodeType]) {

    /**
    Start a new traversal from these nodes
      */
    def start: NewNodeSteps[NodeType] =
      new NewNodeSteps[NodeType](__[NodeType](iter.to(Seq): _*))
  }

  implicit class BaseNodeTypeDeco[NodeType <: nodes.Node](node: NodeType) {

    /**
    Start a new traversal from this node
      */
    def start: Steps[NodeType] =
      new Steps[NodeType](__[NodeType](node))
  }

  implicit class BaseNodeTypeDecoForIterable[NodeType <: nodes.Node](iter: Iterable[NodeType]) {

    /**
    Start a new traversal from these nodes
      */
    def start: Steps[NodeType] =
      new Steps[NodeType](__[NodeType](iter.to(Seq): _*))
  }

  // Call graph extension

  implicit def toMethodForCallGraph[A <: nodes.Method](steps: Steps[A]): Method[A] =
    new Method(steps.raw)

  implicit def toMethodDOTForCallGraph[A <: nodes.Method](steps: Steps[A]): MethodDOT[A] =
    new MethodDOT(steps.raw)

  implicit def toCallForCallGraph[A <: nodes.Call](steps: Steps[A]): Call[A] =
    new Call(steps.raw)

  // / Call graph extension

  // Operator extension

  import io.shiftleft.semanticcpg.language.operatorextension.{NodeTypeStarters => OpNodeTypeStarters}

  implicit def toNodeTypeStartersOps(cpg: Cpg): OpNodeTypeStarters =
    new OpNodeTypeStarters(cpg)

  implicit def toOpsAstNodeTrav2[NodeType <: nodes.AstNode](
      steps: Steps[NodeType]): operatorextension.OpAstNodeTrav[NodeType] =
    new operatorextension.OpAstNodeTrav(steps.raw)

  // /Operator extension

  implicit def toNodeStepsTag[NodeType <: nodes.StoredNode](original: Steps[NodeType]): NodeSteps[NodeType] =
    new NodeSteps[NodeType](original.raw)

  implicit def toTagTag[A <: nodes.Tag](steps: Steps[A]): Tag[A] =
    new Tag(steps.raw)

}

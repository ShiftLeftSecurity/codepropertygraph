package io.shiftleft.semanticcpg

import gremlin.scala._

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.codepropertygraph.generated.nodes.{Node, StoredNode}
import io.shiftleft.semanticcpg.language.callgraphextension.{Call, Method}
import io.shiftleft.semanticcpg.language.dotextension.MethodDOT
import io.shiftleft.semanticcpg.language.nodemethods.{
  AstNodeMethods,
  CallMethods,
  MethodMethods,
  NodeMethods,
  WithinMethodMethods
}
import io.shiftleft.semanticcpg.language.operatorextension.ArrayAccessTrav
import io.shiftleft.semanticcpg.language.operatorextension.nodes.ArrayAccess
import io.shiftleft.semanticcpg.language.types.structure._
import io.shiftleft.semanticcpg.language.types.expressions._
import io.shiftleft.semanticcpg.language.types.expressions.generalizations._
import io.shiftleft.semanticcpg.language.types.structure.{Method => OriginalMethod}
import io.shiftleft.semanticcpg.language.types.expressions.{Call => OriginalCall}

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

  implicit def astNodeMethodsQp(node: nodes.AstNode): AstNodeMethods =
    new AstNodeMethods(node)

  implicit def toMethodMethods(node: nodes.Method): MethodMethods =
    new MethodMethods(node)

  implicit def toCallMethods(node: nodes.Call): CallMethods =
    new CallMethods(node)

  // Implicit conversions from Step[NodeType, Label] to corresponding Step classes.
  // If you introduce a new Step-type, that is, one that inherits from `Steps[NodeType,Labels]`,
  // then you need to add an implicit conversion from `Steps[NodeType,Labels]` to your type
  // here.

  implicit def toLiteral(steps: Steps[nodes.Literal]): Literal =
    new Literal(steps.raw)

  implicit def toType(steps: Steps[nodes.Type]): Type =
    new Type(steps.raw)

  implicit def toTypeDecl(steps: Steps[nodes.TypeDecl]): TypeDecl =
    new TypeDecl(steps.raw)

  implicit def toCall(steps: Steps[nodes.Call]): OriginalCall =
    new OriginalCall(steps.raw)

  implicit def toControlStructure(steps: Steps[nodes.ControlStructure]): ControlStructure =
    new ControlStructure(steps.raw)

  implicit def toIdentifier(steps: Steps[nodes.Identifier]): Identifier =
    new Identifier(steps.raw)

  implicit def toMember(steps: Steps[nodes.Member]): Member =
    new Member(steps.raw)

  implicit def toLocal(steps: Steps[nodes.Local]): Local =
    new Local(steps.raw)

  implicit def toMethod(steps: Steps[nodes.Method]): OriginalMethod =
    new OriginalMethod(steps.raw)

  implicit def toMethodParameter(steps: Steps[nodes.MethodParameterIn]): MethodParameter =
    new MethodParameter(steps.raw)

  implicit def toMethodParameterOut(steps: Steps[nodes.MethodParameterOut]): MethodParameterOut =
    new MethodParameterOut(steps.raw)

  implicit def toMethodReturn(steps: Steps[nodes.MethodReturn]): MethodReturn =
    new MethodReturn(steps.raw)

  implicit def toNamespace(steps: Steps[nodes.Namespace]): Namespace =
    new Namespace(steps.raw)

  implicit def toNamespaceBlock(steps: Steps[nodes.NamespaceBlock]): NamespaceBlock =
    new NamespaceBlock(steps.raw)

  implicit def toModifier(steps: Steps[nodes.Modifier]): Modifier =
    new Modifier(steps.raw)

  implicit def toExpression(steps: Steps[nodes.Expression]): Expression =
    new Expression(steps.raw)

  implicit def toDeclaration(steps: Steps[nodes.Declaration]): Declaration =
    new Declaration(steps.raw)

  implicit def toCfgNode(steps: Steps[nodes.CfgNode]): CfgNode =
    new CfgNode(steps.raw)

  implicit def toAstNode(steps: Steps[nodes.AstNode]): AstNode =
    new AstNode(steps.raw)

  implicit def toFile(steps: Steps[nodes.File]): File =
    new File(steps.raw)

  implicit def toBlock(steps: Steps[nodes.Block]): Block =
    new Block(steps.raw)

  implicit def toReturn(steps: Steps[nodes.Return]): Return =
    new Return(steps.raw)

  implicit def toMethodRef(steps: Steps[nodes.MethodRef]): MethodRef =
    new MethodRef(steps.raw)

  implicit def toBinding(steps: Steps[nodes.Binding]): Binding =
    new Binding(steps.raw)

  implicit def toComment(steps: Steps[nodes.Comment]): Comment =
    new Comment(steps.raw)

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

  implicit def toMethodForCallGraph(steps: Steps[nodes.Method]): Method =
    new Method(steps.raw)

  implicit def toMethodDOTForCallGraph(steps: Steps[nodes.Method]): MethodDOT =
    new MethodDOT(steps.raw)

  implicit def toCallForCallGraph(steps: Steps[nodes.Call]): Call =
    new Call(steps.raw)

  // / Call graph extension

  // Operator extension

  import io.shiftleft.semanticcpg.language.operatorextension.{NodeTypeStarters => OpNodeTypeStarters}

  implicit def toNodeTypeStartersOps(cpg: Cpg): OpNodeTypeStarters =
    new OpNodeTypeStarters(cpg)

  implicit def toOpsAstNodeTrav2[NodeType <: nodes.AstNode](
      steps: Steps[NodeType]): operatorextension.OpAstNodeTrav[NodeType] =
    new operatorextension.OpAstNodeTrav(steps.raw)

  implicit def toOpAstNode(node: nodes.AstNode): operatorextension.nodes.OpAstNode =
    new operatorextension.nodes.OpAstNode(node)

  // /Operator extension

  implicit def toNodeStepsTag[NodeType <: nodes.StoredNode](original: Steps[NodeType]): NodeSteps[NodeType] =
    new NodeSteps[NodeType](original.raw)

  implicit def toTagTag(steps: Steps[nodes.Tag]): Tag =
    new Tag(steps.raw)

}

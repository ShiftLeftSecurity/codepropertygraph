package io.shiftleft.queryprimitives.steps.starters

import gremlin.scala._
import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.{NodeTypes, nodes}
import io.shiftleft.queryprimitives.steps.types.structure._
import io.shiftleft.queryprimitives.steps.Implicits.GremlinScalaDeco
import io.shiftleft.queryprimitives.steps.NodeSteps
import io.shiftleft.queryprimitives.steps.types.expressions._
import io.shiftleft.queryprimitives.steps.types.expressions.generalizations._
import shapeless.HNil

class NodeTypeStarters(cpg: Cpg) {

  /**
    * The underlying graph
    * */
  def scalaGraph: ScalaGraph = cpg.scalaGraph

  /**
    Traverse to all nodes.
    */
  def all: NodeSteps[nodes.StoredNode, HNil] =
    new NodeSteps[nodes.StoredNode, HNil](scalaGraph.V.cast[nodes.StoredNode])

  /**
    Traverse to all source files
    */
  def file: File[HNil] =
    new File[HNil](scalaGraph.V.hasLabel(NodeTypes.FILE).cast[nodes.File])

  /**
    Traverse to all namespaces, e.g., packages in Java.
    */
  def namespace: Namespace[HNil] =
    new Namespace[HNil](scalaGraph.V.hasLabel(NodeTypes.NAMESPACE).cast[nodes.Namespace])

  /**
  Traverse to all namespace blocks, e.g., packages in Java.
    */
  def namespaceBlock: NamespaceBlock[HNil] =
    new NamespaceBlock[HNil](scalaGraph.V.hasLabel(NodeTypes.NAMESPACE_BLOCK).cast[nodes.NamespaceBlock])

  /**
    Traverse to all types, e.g., Set<String>
    */
  def types: Type[HNil] =
    new Type(scalaGraph.V.hasLabel(NodeTypes.TYPE).cast[nodes.Type])

  /**
    Traverse to all declarations, e.g., Set<T>
    */
  def typeDecl: TypeDecl[HNil] =
    new TypeDecl(scalaGraph.V.hasLabel(NodeTypes.TYPE_DECL).cast[nodes.TypeDecl])

  /**
    Traverse to all methods
    */
  def method: Method[HNil] =
    new Method(scalaGraph.V.hasLabel(NodeTypes.METHOD).cast[nodes.Method])

  /**
    Traverse to all method instances
    */
  def methodInstance: MethodInst[HNil] =
    new MethodInst(scalaGraph.V.hasLabel(NodeTypes.METHOD_INST).cast[nodes.MethodInst])

  /**
    Traverse to all formal return parameters
    */
  def methodReturn: MethodReturn[HNil] =
    new MethodReturn(scalaGraph.V.hasLabel(NodeTypes.METHOD_RETURN).cast[nodes.MethodReturn])

  /**
    Traverse to all input parameters
    */
  def parameter: MethodParameter[HNil] =
    new MethodParameter(scalaGraph.V.hasLabel(NodeTypes.METHOD_PARAMETER_IN).cast[nodes.MethodParameterIn])

  /**
    Traverse to all class members
    */
  def member: Member[HNil] =
    new Member(scalaGraph.V.hasLabel(NodeTypes.MEMBER).cast[nodes.Member])

  /**
    Traverse to all call sites
    */
  def call: Call[HNil] =
    new Call(scalaGraph.V.hasLabel(NodeTypes.CALL).cast[nodes.Call])

  /**
    Traverse to all local variable declarations

    */
  def local: Local[HNil] =
    new Local(scalaGraph.V.hasLabel(NodeTypes.LOCAL).cast[nodes.Local])

  /**
    Traverse to all literals (constant strings and numbers provided directly in the code).
    */
  def literal: Literal[HNil] =
    new Literal(scalaGraph.V.hasLabel(NodeTypes.LITERAL).cast[nodes.Literal])

  /**
    Traverse to all identifiers, e.g., occurrences of local variables or class members in method bodies.
    */
  def identifier: Identifier[HNil] =
    new Identifier(scalaGraph.V.hasLabel(NodeTypes.IDENTIFIER).cast[nodes.Identifier])

  /**
    Traverse to all arguments passed to methods
    */
  def argument: Expression[HNil] =
    call.argument

  /**
    * Traverse to all meta data entries
    */
  def metaData: MetaData[HNil] =
    new MetaData(scalaGraph.V.hasLabel(NodeTypes.META_DATA).cast[nodes.MetaData])

  /**
  Begin traversal at set of nodes - specified by their ids
    */
  def atVerticesWithId[NodeType <: nodes.StoredNode](ids: Seq[Any]): NodeSteps[NodeType, HNil] =
    if (ids.size == 0) new NodeSteps[NodeType, HNil](scalaGraph.V(-1).cast[NodeType])
    else new NodeSteps[NodeType, HNil](scalaGraph.V(ids: _*).cast[NodeType])
}

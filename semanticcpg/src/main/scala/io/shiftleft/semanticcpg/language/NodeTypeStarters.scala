package io.shiftleft.semanticcpg.language

import gremlin.scala._
import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.{NodeTypes, nodes}
import io.shiftleft.semanticcpg.language.types.expressions._
import io.shiftleft.semanticcpg.language.types.expressions.generalizations._
import io.shiftleft.semanticcpg.language.types.structure._

class NodeTypeStarters(cpg: Cpg) {

  /**
    * The underlying graph
    * */
  def scalaGraph: ScalaGraph = cpg.scalaGraph

  /**
    Traverse to all nodes.
    */
  def all: NodeSteps[nodes.StoredNode] =
    new NodeSteps[nodes.StoredNode](scalaGraph.V.cast[nodes.StoredNode])

  /**
    * Traverse to all comments in source-based CPGs.
    * */
  def comment: Comment =
    new Comment(scalaGraph.V.hasLabel(NodeTypes.COMMENT).cast[nodes.Comment])

  /**
    * Shorthand for `cpg.comment.code(code)`
    * */
  def comment(code: String): Comment = comment.code(code)

  /**
    Traverse to all source files
    */
  def file: File =
    new File(scalaGraph.V.hasLabel(NodeTypes.FILE).cast[nodes.File])

  /**
    * Shorthand for `cpg.file.name(name)`
    * */
  def file(name: String): File = file.name(name)

  /**
    Traverse to all namespaces, e.g., packages in Java.
    */
  def namespace: Namespace =
    new Namespace(scalaGraph.V.hasLabel(NodeTypes.NAMESPACE).cast[nodes.Namespace])

  /**
    * Shorthand for `cpg.namespace.name(name)`
    * */
  def namespace(name: String): Namespace = namespace.name(name)

  /**
  Traverse to all namespace blocks, e.g., packages in Java.
    */
  def namespaceBlock: NamespaceBlock =
    new NamespaceBlock(scalaGraph.V.hasLabel(NodeTypes.NAMESPACE_BLOCK).cast[nodes.NamespaceBlock])

  /**
    * Shorthand for `cpg.namespaceBlock.name(name)`
    * */
  def namespaceBlock(name: String): NamespaceBlock = namespaceBlock.name(name)

  /**
    Traverse to all types, e.g., Set<String>
    */
  def types: Type =
    new Type(scalaGraph.V.hasLabel(NodeTypes.TYPE).cast[nodes.Type])

  /**
    * Shorthand for `cpg.types.fullName(fullName)`
    * */
  def types(fullName: String): Type = types.fullName(fullName)

  /**
    Traverse to all declarations, e.g., Set<T>
    */
  def typeDecl: TypeDecl =
    new TypeDecl(scalaGraph.V.hasLabel(NodeTypes.TYPE_DECL).cast[nodes.TypeDecl])

  /**
    * Shorthand for cpg.typeDecl.fullName(fullName)
    * */
  def typeDecl(fullName: String): TypeDecl = typeDecl.fullName(fullName)

  /**
    Traverse to all methods
    */
  def method: Method =
    new Method(scalaGraph.V.hasLabel(NodeTypes.METHOD).cast[nodes.Method])

  /**
    * Shorthand for `cpg.method.fullName(fullName)`
    * */
  def method(fullName: String): Method = method.fullName(fullName)

  /**
    Traverse to all formal return parameters
    */
  def methodReturn: MethodReturn =
    new MethodReturn(scalaGraph.V.hasLabel(NodeTypes.METHOD_RETURN).cast[nodes.MethodReturn])

  /**
    Traverse to all input parameters
    */
  def parameter: MethodParameter =
    new MethodParameter(scalaGraph.V.hasLabel(NodeTypes.METHOD_PARAMETER_IN).cast[nodes.MethodParameterIn])

  /**
    * Shorthand for `cpg.parameter.name(name)`
    * */
  def parameter(name: String): MethodParameter = parameter.name(name)

  /**
    Traverse to all class members
    */
  def member: Member =
    new Member(scalaGraph.V.hasLabel(NodeTypes.MEMBER).cast[nodes.Member])

  /**
    * Shorthand for `cpg.member.name(name)`
    * */
  def member(name: String): Member = member.name(name)

  /**
    Traverse to all call sites
    */
  def call: Call =
    new Call(scalaGraph.V.hasLabel(NodeTypes.CALL).cast[nodes.Call])

  /**
    * Shorthand for `cpg.call.name(name)`
    * */
  def call(name: String): Call = call.name(name)

  /**
    Traverse to all local variable declarations

    */
  def local: Local =
    new Local(scalaGraph.V.hasLabel(NodeTypes.LOCAL).cast[nodes.Local])

  /**
    * Shorthand for `cpg.local.name`
    * */
  def local(name: String): Local = local.name(name)

  /**
    Traverse to all literals (constant strings and numbers provided directly in the code).
    */
  def literal: Literal =
    new Literal(scalaGraph.V.hasLabel(NodeTypes.LITERAL).cast[nodes.Literal])

  /**
    * Shorthand for `cpg.literal.code(code)`
    * */
  def literal(code: String): Literal = literal.code(code)

  /**
    Traverse to all identifiers, e.g., occurrences of local variables or class members in method bodies.
    */
  def identifier: Identifier =
    new Identifier(scalaGraph.V.hasLabel(NodeTypes.IDENTIFIER).cast[nodes.Identifier])

  /**
    * Shorthand for `cpg.identifier.name(name)`
    * */
  def identifier(name: String): Identifier = identifier.name(name)

  /**
    Traverse to all arguments passed to methods
    */
  def argument: NodeSteps[nodes.Expression] =
    call.argument

  /**
    * Shorthand for `cpg.argument.code(code)`
    * */
  def argument(code: String): NodeSteps[nodes.Expression] =
    argument.code(code)

  /**
    * Traverse to all return expressions
    */
  def returns: NodeSteps[nodes.Return] =
    new NodeSteps(scalaGraph.V.hasLabel(NodeTypes.RETURN).cast[nodes.Return])

  /**
    * Shorthand for `returns.code(code)`
    * */
  def returns(code: String): NodeSteps[nodes.Return] =
    returns.code(code)

  /**
    * Traverse to all meta data entries
    */
  def metaData: MetaData =
    new MetaData(scalaGraph.V.hasLabel(NodeTypes.META_DATA).cast[nodes.MetaData])

  /**
    * Traverse to all method references
    * */
  def methodRef: MethodRef =
    new MethodRef(scalaGraph.V.hasLabel(NodeTypes.METHOD_REF).cast[nodes.MethodRef])

  /**
    * Shorthand for `cpg.methodRef
    * .filter(_.referencedMethod.fullName(fullName))`
    * */
  def methodRef(fullName: String): MethodRef =
    methodRef
      .filter(_.referencedMethod.fullName(fullName))

  /**
  Begin traversal at node with id.
    */
  def id[NodeType <: nodes.StoredNode](anId: Any): NodeSteps[NodeType] = id(Seq(anId))

  /**
  Begin traversal at set of nodes - specified by their ids
    */
  def id[NodeType <: nodes.StoredNode](ids: Seq[Any]): NodeSteps[NodeType] =
    if (ids.isEmpty) new NodeSteps[NodeType](scalaGraph.V(-1).cast[NodeType])
    else new NodeSteps[NodeType](scalaGraph.V(ids: _*).cast[NodeType])

  /**
  Traverse to all tags
    */
  def tag: Tag =
    new Tag(scalaGraph.V.hasLabel(NodeTypes.TAG).cast[nodes.Tag])

}

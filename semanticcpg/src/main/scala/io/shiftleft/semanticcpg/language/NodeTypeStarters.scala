package io.shiftleft.semanticcpg.language

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.nodes._
import io.shiftleft.codepropertygraph.generated.traversal._
import io.shiftleft.codepropertygraph.generated.{NodeTypes, Properties}
import overflowdb._
import overflowdb.traversal._
import overflowdb.traversal.help.{Doc, TraversalSource}

@TraversalSource
class NodeTypeStarters(cpg: Cpg) {

  /**
    Traverse to all nodes.
    */
  @Doc("All nodes of the graph")
  def all: Traversal[StoredNode] =
    cpg.graph.nodes.cast[StoredNode]

  /**
    * Traverse to all comments in source-based CPGs.
    * */
  @Doc("All comments in source-based CPGs")
  def comment: Traversal[Comment] =
    cpg.graph.nodes(NodeTypes.COMMENT).cast[Comment]

  /**
    * Shorthand for `cpg.comment.code(code)`
    * */
  def comment(code: String): Traversal[Comment] =
    comment.has(Properties.CODE -> code)

  @Doc("All control structures (source-based frontends)")
  def controlStructure: Traversal[ControlStructure] =
    cpg.graph.nodes(NodeTypes.CONTROL_STRUCTURE).cast[ControlStructure]

  /**
    Traverse to all source files
    */
  @Doc("All source files")
  def file: Traversal[File] =
    cpg.graph.nodes(NodeTypes.FILE).cast[File]

  /**
    * Shorthand for `cpg.file.name(name)`
    * */
  def file(name: String): Traversal[File] =
    file.name(name)

  /**
    * Traverse to all jump targets
    * */
  @Doc("All jump targets, i.e., labels")
  def jumpTarget: Traversal[JumpTarget] =
    cpg.graph.nodes(NodeTypes.JUMP_TARGET).cast[JumpTarget]

  /**
    Traverse to all namespaces, e.g., packages in Java.
    */
  @Doc("All namespaces")
  def namespace: Traversal[Namespace] =
    cpg.graph.nodes(NodeTypes.NAMESPACE).cast[Namespace]

  /**
    * Shorthand for `cpg.namespace.name(name)`
    * */
  def namespace(name: String): Traversal[Namespace] =
    namespace.name(name)

  /**
  Traverse to all namespace blocks, e.g., packages in Java.
    */
  def namespaceBlock: Traversal[NamespaceBlock] =
    cpg.graph.nodes(NodeTypes.NAMESPACE_BLOCK).cast[NamespaceBlock]

  /**
    * Shorthand for `cpg.namespaceBlock.name(name)`
    * */
  def namespaceBlock(name: String): Traversal[NamespaceBlock] =
    namespaceBlock.name(name)

  /**
  Traverse to all types, e.g., Set<String>
    */
  @Doc("All used types")
  def typ: Traversal[Type] =
    cpg.graph.nodes(NodeTypes.TYPE).cast[Type]

  /**
    * Shorthand for `cpg.types.fullName(fullName)`
    * */
  def typ(fullName: String): Traversal[Type] =
    typ.fullName(fullName)

  /**
    Traverse to all types, e.g., Set<String>
    */
  @deprecated("Use typ")
  def types: Traversal[Type] =
    cpg.graph.nodes(NodeTypes.TYPE).cast[Type]

  /**
    * Shorthand for `cpg.types.fullName(fullName)`
    * */
  @deprecated("Use typ")
  def types(fullName: String): Traversal[Type] =
    typ.fullName(fullName)

  /**
    Traverse to all declarations, e.g., Set<T>
    */
  @Doc("All declarations of types")
  def typeDecl: Traversal[TypeDecl] =
    cpg.graph.nodes(NodeTypes.TYPE_DECL).cast[TypeDecl]

  /**
    * Shorthand for cpg.typeDecl.fullName(fullName)
    * */
  def typeDecl(fullName: String): Traversal[TypeDecl] =
    typeDecl.fullName(fullName)

  /**
    Traverse to all methods
    */
  @Doc("All methods")
  def method: Traversal[Method] =
    cpg.graph.nodes(NodeTypes.METHOD).cast[Method]

  /**
    * Shorthand for `cpg.method.fullName(fullName)`
    * */
  def method(fullName: String): Traversal[Method] =
    method.fullName(fullName)

  /**
    Traverse to all formal return parameters
    */
  @Doc("All formal return parameters")
  def methodReturn: Traversal[MethodReturn] =
    cpg.graph.nodes(NodeTypes.METHOD_RETURN).cast[MethodReturn]

  /**
    Traverse to all input parameters
    */
  @Doc("All parameters")
  def parameter: Traversal[MethodParameterIn] =
    cpg.graph.nodes(NodeTypes.METHOD_PARAMETER_IN).cast[MethodParameterIn]

  /**
    * Shorthand for `cpg.parameter.name(name)`
    * */
  def parameter(name: String): Traversal[MethodParameterIn] =
    parameter.name(name)

  /**
    Traverse to all class members
    */
  @Doc("All members of complex types (e.g., classes/structures)")
  def member: Traversal[Member] =
    cpg.graph.nodes(NodeTypes.MEMBER).cast[Member]

  /**
    * Shorthand for `cpg.member.name(name)`
    * */
  def member(name: String): Traversal[Member] =
    member.name(name)

  /**
    Traverse to all call sites
    */
  @Doc("All call sites")
  def call: Traversal[Call] =
    cpg.graph.nodes(NodeTypes.CALL).cast[Call]

  /**
    * Shorthand for `cpg.call.name(name)`
    * */
  def call(name: String): Traversal[Call] =
    call.name(name)

  /**
    Traverse to all local variable declarations

    */
  @Doc("All local variables")
  def local: Traversal[Local] =
    cpg.graph.nodes(NodeTypes.LOCAL).cast[Local]

  /**
    * Shorthand for `cpg.local.name`
    * */
  def local(name: String): Traversal[Local] =
    local.name(name)

  /**
    Traverse to all literals (constant strings and numbers provided directly in the code).
    */
  @Doc("All literals, e.g., numbers or strings")
  def literal: Traversal[Literal] =
    cpg.graph.nodes(NodeTypes.LITERAL).cast[Literal]

  /**
    * Shorthand for `cpg.literal.code(code)`
    * */
  def literal(code: String): Traversal[Literal] =
    literal.code(code)

  /**
    Traverse to all identifiers, e.g., occurrences of local variables or class members in method bodies.
    */
  @Doc("All identifier usages")
  def identifier: Traversal[Identifier] =
    cpg.graph.nodes(NodeTypes.IDENTIFIER).cast[Identifier]

  /**
    * Shorthand for `cpg.identifier.name(name)`
    * */
  def identifier(name: String): Traversal[Identifier] =
    identifier.name(name)

  /**
    Traverse to all arguments passed to methods
    */
  @Doc("All arguments (actual parameters)")
  def argument: Traversal[Expression] =
    call.argument

  /**
    * Shorthand for `cpg.argument.code(code)`
    * */
  def argument(code: String): Traversal[Expression] =
    argument.code(code)

  /**
    * Traverse to all return expressions
    */
  @Doc("All actual return parameters")
  def ret: Traversal[Return] =
    cpg.graph.nodes(NodeTypes.RETURN).cast[Return]

  /**
    * Shorthand for `returns.code(code)`
    * */
  def ret(code: String): Traversal[Return] =
    ret.code(code)

  /**
    * Traverse to all return expressions
    */
  @deprecated("Use ret")
  def returns: Traversal[Return] =
    cpg.graph.nodes(NodeTypes.RETURN).cast[Return]

  /**
    * Shorthand for `returns.code(code)`
    * */
  @deprecated("Use ret")
  def returns(code: String): Traversal[Return] =
    ret.code(code)

  /**
    * Traverse to all meta data entries
    */
  @Doc("Meta data blocks for graph")
  def metaData: Traversal[MetaData] =
    cpg.graph.nodes(NodeTypes.META_DATA).cast[MetaData]

  /**
    * Traverse to all method references
    * */
  @Doc("All method references")
  def methodRef: Traversal[MethodRef] =
    cpg.graph.nodes(NodeTypes.METHOD_REF).cast[MethodRef]

  /**
    * Traverse to all type references
    * */
  @Doc("All type references")
  def typeRef: Traversal[TypeRef] =
    cpg.graph.nodes(NodeTypes.TYPE_REF).cast[TypeRef]

  /**
    * Shorthand for `cpg.methodRef
    * .filter(_.referencedMethod.fullName(fullName))`
    * */
  def methodRef(fullName: String): Traversal[MethodRef] =
    methodRef.where(_.referencedMethod.fullName(fullName))

  /**
  Begin traversal at node with id.
    */
  def id[NodeType <: StoredNode](anId: Long): Traversal[NodeType] = {
    cpg.graph.nodes(anId).cast[NodeType]
  }

  /**
  Begin traversal at set of nodes - specified by their ids
    */
  def id[NodeType <: StoredNode](ids: Seq[Long]): Traversal[NodeType] =
    if (ids.isEmpty) Traversal.empty
    else cpg.graph.nodes(ids: _*).cast[NodeType]

  /**
  Traverse to all tags
    */
  @Doc("All tags")
  def tag: Traversal[Tag] =
    cpg.graph.nodes(NodeTypes.TAG).cast[Tag]

  @Doc("All tags with given name")
  def tag(name: String): Traversal[Tag] =
    tag.name(name)

}

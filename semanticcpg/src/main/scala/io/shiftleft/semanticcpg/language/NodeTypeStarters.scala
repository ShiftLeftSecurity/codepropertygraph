package io.shiftleft.semanticcpg.language

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.{NodeKeys, NodeTypes, nodes}
import overflowdb._
import overflowdb.traversal._
import overflowdb.traversal.help.{Doc, TraversalSource}

@TraversalSource
class NodeTypeStarters(cpg: Cpg) {

  /**
    Traverse to all nodes.
    */
  @Doc("All nodes of the graph")
  def all: Traversal[nodes.StoredNode] =
    cpg.graph.nodes.cast[nodes.StoredNode]

  /**
    * Traverse to all comments in source-based CPGs.
    * */
  @Doc("All comments in source-based CPGs")
  def comment: Traversal[nodes.Comment] =
    cpg.graph.nodes(NodeTypes.COMMENT).cast[nodes.Comment]

  /**
    * Shorthand for `cpg.comment.code(code)`
    * */
  def comment(code: String): Traversal[nodes.Comment] =
    comment.has(NodeKeys.CODE -> code)

  @Doc("All control structures (source-based frontends)")
  def controlStructure: Traversal[nodes.ControlStructure] =
    cpg.graph.nodes(NodeTypes.CONTROL_STRUCTURE).cast[nodes.ControlStructure]

  /**
    Traverse to all source files
    */
  @Doc("All source files")
  def file: Traversal[nodes.File] =
    cpg.graph.nodes(NodeTypes.FILE).cast[nodes.File]

  /**
    * Shorthand for `cpg.file.name(name)`
    * */
  def file(name: String): Traversal[nodes.File] =
    file.name(name)

  /**
    * Traverse to all jump targets
    * */
  @Doc("All jump targets, i.e., labels")
  def jumpTarget: Traversal[nodes.JumpTarget] =
    cpg.graph.nodes(NodeTypes.JUMP_TARGET).cast[nodes.JumpTarget]

  /**
    Traverse to all namespaces, e.g., packages in Java.
    */
  @Doc("All namespaces")
  def namespace: Traversal[nodes.Namespace] =
    cpg.graph.nodes(NodeTypes.NAMESPACE).cast[nodes.Namespace]

  /**
    * Shorthand for `cpg.namespace.name(name)`
    * */
  def namespace(name: String): Traversal[nodes.Namespace] =
    namespace.name(name)

  /**
  Traverse to all namespace blocks, e.g., packages in Java.
    */
  def namespaceBlock: Traversal[nodes.NamespaceBlock] =
    cpg.graph.nodes(NodeTypes.NAMESPACE_BLOCK).cast[nodes.NamespaceBlock]

  /**
    * Shorthand for `cpg.namespaceBlock.name(name)`
    * */
  def namespaceBlock(name: String): Traversal[nodes.NamespaceBlock] =
    namespaceBlock.name(name)

  /**
  Traverse to all types, e.g., Set<String>
    */
  @Doc("All used types")
  def typ: Traversal[nodes.Type] =
    cpg.graph.nodes(NodeTypes.TYPE).cast[nodes.Type]

  /**
    * Shorthand for `cpg.types.fullName(fullName)`
    * */
  def typ(fullName: String): Traversal[nodes.Type] =
    typ.fullName(fullName)

  /**
    Traverse to all types, e.g., Set<String>
    */
  @deprecated("Use typ")
  def types: Traversal[nodes.Type] =
    cpg.graph.nodes(NodeTypes.TYPE).cast[nodes.Type]

  /**
    * Shorthand for `cpg.types.fullName(fullName)`
    * */
  @deprecated("Use typ")
  def types(fullName: String): Traversal[nodes.Type] =
    typ.fullName(fullName)

  /**
    Traverse to all declarations, e.g., Set<T>
    */
  @Doc("All declarations of types")
  def typeDecl: Traversal[nodes.TypeDecl] =
    cpg.graph.nodes(NodeTypes.TYPE_DECL).cast[nodes.TypeDecl]

  /**
    * Shorthand for cpg.typeDecl.fullName(fullName)
    * */
  def typeDecl(fullName: String): Traversal[nodes.TypeDecl] =
    typeDecl.fullName(fullName)

  /**
    Traverse to all methods
    */
  @Doc("All methods")
  def method: Traversal[nodes.Method] =
    cpg.graph.nodes(NodeTypes.METHOD).cast[nodes.Method]

  /**
    * Shorthand for `cpg.method.fullName(fullName)`
    * */
  def method(fullName: String): Traversal[nodes.Method] =
    method.fullName(fullName)

  /**
    Traverse to all formal return parameters
    */
  @Doc("All formal return parameters")
  def methodReturn: Traversal[nodes.MethodReturn] =
    cpg.graph.nodes(NodeTypes.METHOD_RETURN).cast[nodes.MethodReturn]

  /**
    Traverse to all input parameters
    */
  @Doc("All parameters")
  def parameter: Traversal[nodes.MethodParameterIn] =
    cpg.graph.nodes(NodeTypes.METHOD_PARAMETER_IN).cast[nodes.MethodParameterIn]

  /**
    * Shorthand for `cpg.parameter.name(name)`
    * */
  def parameter(name: String): Traversal[nodes.MethodParameterIn] =
    parameter.name(name)

  /**
    Traverse to all class members
    */
  @Doc("All members of complex types (e.g., classes/structures)")
  def member: Traversal[nodes.Member] =
    cpg.graph.nodes(NodeTypes.MEMBER).cast[nodes.Member]

  /**
    * Shorthand for `cpg.member.name(name)`
    * */
  def member(name: String): Traversal[nodes.Member] =
    member.name(name)

  /**
    Traverse to all call sites
    */
  @Doc("All call sites")
  def call: Traversal[nodes.Call] =
    cpg.graph.nodes(NodeTypes.CALL).cast[nodes.Call]

  /**
    * Shorthand for `cpg.call.name(name)`
    * */
  def call(name: String): Traversal[nodes.Call] =
    call.name(name)

  /**
    Traverse to all local variable declarations

    */
  @Doc("All local variables")
  def local: Traversal[nodes.Local] =
    cpg.graph.nodes(NodeTypes.LOCAL).cast[nodes.Local]

  /**
    * Shorthand for `cpg.local.name`
    * */
  def local(name: String): Traversal[nodes.Local] =
    local.name(name)

  /**
    Traverse to all literals (constant strings and numbers provided directly in the code).
    */
  @Doc("All literals, e.g., numbers or strings")
  def literal: Traversal[nodes.Literal] =
    cpg.graph.nodes(NodeTypes.LITERAL).cast[nodes.Literal]

  /**
    * Shorthand for `cpg.literal.code(code)`
    * */
  def literal(code: String): Traversal[nodes.Literal] =
    literal.code(code)

  /**
    Traverse to all identifiers, e.g., occurrences of local variables or class members in method bodies.
    */
  @Doc("All identifier usages")
  def identifier: Traversal[nodes.Identifier] =
    cpg.graph.nodes(NodeTypes.IDENTIFIER).cast[nodes.Identifier]

  /**
    * Shorthand for `cpg.identifier.name(name)`
    * */
  def identifier(name: String): Traversal[nodes.Identifier] =
    identifier.name(name)

  /**
    Traverse to all arguments passed to methods
    */
  @Doc("All arguments (actual parameters)")
  def argument: Traversal[nodes.Expression] =
    call.argument

  /**
    * Shorthand for `cpg.argument.code(code)`
    * */
  def argument(code: String): Traversal[nodes.Expression] =
    argument.code(code)

  /**
    * Traverse to all return expressions
    */
  @Doc("All actual return parameters")
  def ret: Traversal[nodes.Return] =
    cpg.graph.nodes(NodeTypes.RETURN).cast[nodes.Return]

  /**
    * Shorthand for `returns.code(code)`
    * */
  def ret(code: String): Traversal[nodes.Return] =
    ret.code(code)

  /**
    * Traverse to all return expressions
    */
  @deprecated("Use ret")
  def returns: Traversal[nodes.Return] =
    cpg.graph.nodes(NodeTypes.RETURN).cast[nodes.Return]

  /**
    * Shorthand for `returns.code(code)`
    * */
  @deprecated("Use ret")
  def returns(code: String): Traversal[nodes.Return] =
    ret.code(code)

  /**
    * Traverse to all meta data entries
    */
  @Doc("Meta data blocks for graph")
  def metaData: Traversal[nodes.MetaData] =
    cpg.graph.nodes(NodeTypes.META_DATA).cast[nodes.MetaData]

  /**
    * Traverse to all method references
    * */
  @Doc("All method references")
  def methodRef: Traversal[nodes.MethodRef] =
    cpg.graph.nodes(NodeTypes.METHOD_REF).cast[nodes.MethodRef]

  /**
    * Traverse to all type references
    * */
  @Doc("All type references")
  def typeRef: Traversal[nodes.TypeRef] =
    cpg.graph.nodes(NodeTypes.TYPE_REF).cast[nodes.TypeRef]

  /**
    * Shorthand for `cpg.methodRef
    * .filter(_.referencedMethod.fullName(fullName))`
    * */
  def methodRef(fullName: String): Traversal[nodes.MethodRef] =
    methodRef.where(_.referencedMethod.fullName(fullName))

  /**
  Begin traversal at node with id.
    */
  def id[NodeType <: nodes.StoredNode](anId: Long): Traversal[NodeType] = {
    cpg.graph.nodes(anId).cast[NodeType]
  }

  /**
  Begin traversal at set of nodes - specified by their ids
    */
  def id[NodeType <: nodes.StoredNode](ids: Seq[Long]): Traversal[NodeType] =
    if (ids.isEmpty) Traversal.empty
    else cpg.graph.nodes(ids: _*).cast[NodeType]

  /**
  Traverse to all tags
    */
  @Doc("All tags")
  def tag: Traversal[nodes.Tag] =
    cpg.graph.nodes(NodeTypes.TAG).cast[nodes.Tag]

  @Doc("All tags with given name")
  def tag(name: String): Traversal[nodes.Tag] = tag.name(name)

  @Doc("All closure bindings (binding of a LOCAL or METHOD_PARAMETER_IN into the closure of a method)")
  def closureBinding: Traversal[nodes.ClosureBinding] =
    cpg.graph.nodes(NodeTypes.CLOSURE_BINDING).cast[nodes.ClosureBinding]
}

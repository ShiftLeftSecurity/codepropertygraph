package io.shiftleft.semanticcpg.language.types.structure

import gremlin.scala._
import io.shiftleft.codepropertygraph.generated._
import io.shiftleft.codepropertygraph.generated.nodes
import overflowdb.traversal.help
import overflowdb.traversal.help.Doc
import io.shiftleft.semanticcpg.language._

/**
  * A method, function, or procedure
  * */
@help.Traversal(elementType = classOf[nodes.Method])
class Method(val wrapped: NodeSteps[nodes.Method]) extends AnyVal {
  private def raw: GremlinScala[nodes.Method] = wrapped.raw

  /**
    * Traverse to parameters of the method
    * */
  @Doc("All parameters")
  def parameter: NodeSteps[nodes.MethodParameterIn] =
    // TODO once we use OdbTraversal, this will simply become `wrapped.flatMap(_.parameter)` - for now it's not that simple :(
    new NodeSteps(
      raw
        .out(EdgeTypes.AST)
        .hasLabel(NodeTypes.METHOD_PARAMETER_IN)
        .cast[nodes.MethodParameterIn]
    )

  /**
    * Traverse to formal return parameter
    * */
  @Doc("All formal return parameters")
  def methodReturn: NodeSteps[nodes.MethodReturn] =
    new NodeSteps(raw.map(_.methodReturn))

  /**
    * Traverse to type decl which have this method bound to it.
    */
  def bindingTypeDecl: NodeSteps[nodes.TypeDecl] =
    referencingBinding.bindingTypeDecl

  /**
    * Traverse to bindings which reference to this method.
    */
  def referencingBinding: NodeSteps[nodes.Binding] =
    new NodeSteps(raw.in(EdgeTypes.REF).filter(_.hasLabel(NodeTypes.BINDING)).cast[nodes.Binding])

  /**
    * All control structures of this method
    * */
  @Doc("Control structures (source frontends only)")
  def controlStructure: NodeSteps[nodes.ControlStructure] =
    wrapped.ast.isControlStructure

  /**
    * Shorthand to traverse to control structures where condition matches `regex`
    * */
  def controlStructure(regex: String): NodeSteps[nodes.ControlStructure] =
    wrapped.ast.isControlStructure.code(regex)

  /**
    * The type declaration associated with this method, e.g., the class it is defined in.
    * */
  @Doc("Type this method is defined in")
  def definingTypeDecl: NodeSteps[nodes.TypeDecl] =
    new NodeSteps(
      raw
        .repeat(_.in(EdgeTypes.AST).cast[nodes.StoredNode])
        .until(_.hasLabel(NodeTypes.TYPE_DECL))
        .cast[nodes.TypeDecl])

  /**
    * The method in which this method is defined
    * */
  @Doc("Method this method is defined in")
  def definingMethod: NodeSteps[nodes.Method] =
    new NodeSteps(
      raw
        .repeat(_.in(EdgeTypes.AST).cast[nodes.StoredNode])
        .until(_.hasLabel(NodeTypes.METHOD))
        .cast[nodes.Method])

  /**
    * Traverse only to methods that are stubs, e.g., their code is not available
    * */
  def isStub: NodeSteps[nodes.Method] =
    new NodeSteps(raw.filter(_.not(_.out(EdgeTypes.CFG))))

  /**
    * Traverse only to methods that are not stubs.
    * */
  def isNotStub: NodeSteps[nodes.Method] =
    new NodeSteps(raw.filter(_.out(EdgeTypes.CFG)))

  /**
    * Traverse to external methods, that is, methods not present
    * but only referenced in the CPG.
    * */
  @Doc("External methods (called, but no body available)")
  def external: NodeSteps[nodes.Method] =
    new NodeSteps(raw.has(NodeKeys.IS_EXTERNAL -> true))

  /**
    * Traverse to internal methods, that is, methods for which
    * code is included in this CPG.
    * */
  @Doc("Internal methods, i.e., a body is available")
  def internal: NodeSteps[nodes.Method] =
    new NodeSteps(raw.has(NodeKeys.IS_EXTERNAL -> false))

  /**
    * Traverse to the methods local variables
    * */
  @Doc("Local variables declared in the method")
  def local: NodeSteps[nodes.Local] =
    new NodeSteps(
      raw
        .out(EdgeTypes.CONTAINS)
        .hasLabel(NodeTypes.BLOCK)
        .out(EdgeTypes.AST)
        .hasLabel(NodeTypes.LOCAL)
        .cast[nodes.Local])

  /**
    * Traverse to literals of method
    * */
  @Doc("Literals used in the method")
  def literal: NodeSteps[nodes.Literal] =
    new NodeSteps(raw.out(EdgeTypes.CONTAINS).hasLabel(NodeTypes.LITERAL).cast[nodes.Literal])

  @Doc("Top level expressions (\"Statements\")")
  def topLevelExpressions: NodeSteps[nodes.Expression] =
    new NodeSteps(
      raw
        .out(EdgeTypes.AST)
        .hasLabel(NodeTypes.BLOCK)
        .out(EdgeTypes.AST)
        .not(_.hasLabel(NodeTypes.LOCAL))
        .cast[nodes.Expression])

  @Doc("Control flow graph nodes")
  def cfgNode: NodeSteps[nodes.CfgNode] =
    new NodeSteps(raw.flatMap { method =>
      __(method.cfgNode.to(Seq): _*)
    })

  /**
    *  Traverse to first expression in CFG.
    */
  @Doc("First control flow graph node")
  def cfgFirst: NodeSteps[nodes.Expression] =
    new NodeSteps(raw.out(EdgeTypes.CFG).cast[nodes.Expression])

  /**
    *  Traverse to last expression in CFG.
    */
  @Doc("Last control flow graph node")
  def cfgLast: NodeSteps[nodes.Expression] =
    methodReturn.cfgLast

  /**
    * Traverse to block
    * */
  @Doc("Root of the abstract syntax tree")
  def block: NodeSteps[nodes.Block] =
    new NodeSteps(raw.out(EdgeTypes.AST).hasLabel(NodeTypes.BLOCK).cast[nodes.Block])

  /** Traverse to method body (alias for `block`) */
  @Doc("Alias for `block`")
  def body: NodeSteps[nodes.Block] = block

  /** Traverse to namespace */
  @Doc("Namespace this method is declared in")
  def namespace: NodeSteps[nodes.Namespace] =
    new NodeSteps(definingTypeDecl.namespace.raw)

  def numberOfLines: Steps[Int] = wrapped.map(_.numberOfLines)

}

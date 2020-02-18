package io.shiftleft.semanticcpg.language.types.structure

import gremlin.scala._
import io.shiftleft.codepropertygraph.generated._
import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.semanticcpg.language._
import io.shiftleft.semanticcpg.language.types.propertyaccessors._

/**
  * A method, function, or procedure
  * */
class Method(val wrapped: NodeSteps[nodes.Method]) extends EvalTypeAccessors[nodes.Method] {
  override val raw: GremlinScala[nodes.Method] = wrapped.raw

  /**
    * Traverse to parameters of the method
    * */
  def parameter: NodeSteps[nodes.MethodParameterIn] =
    new NodeSteps(
      raw
        .out(EdgeTypes.AST)
        .hasLabel(NodeTypes.METHOD_PARAMETER_IN)
        .cast[nodes.MethodParameterIn])

  /**
    * Traverse to formal return parameter
    * */
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
  def controlStructure: NodeSteps[nodes.ControlStructure] =
    wrapped.ast.isControlStructure

  /**
    * Shorthand to traverse to control structures where condition matches `regex`
    * */
  def controlStructure(regex: String): NodeSteps[nodes.ControlStructure] =
    wrapped.ast.isControlStructure.code(regex)

  /**
    * Outgoing call sites
    * */
  def callOut: NodeSteps[nodes.Call] =
    new NodeSteps(raw.out(EdgeTypes.CONTAINS).hasLabel(NodeTypes.CALL).cast[nodes.Call])

  /**
    * The type declaration associated with this method, e.g., the class it is defined in.
    * */
  def definingTypeDecl: NodeSteps[nodes.TypeDecl] =
    new NodeSteps(
      raw
        .cast[nodes.StoredNode]
        .repeat(_.in(EdgeTypes.AST).cast[nodes.StoredNode])
        .until(_.hasLabel(NodeTypes.TYPE_DECL))
        .cast[nodes.TypeDecl])

  /**
    * The method in which this method is defined
    * */
  def definingMethod: NodeSteps[nodes.Method] =
    new NodeSteps(
      raw
        .cast[nodes.StoredNode]
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
    * Traverse to public methods
    * */
  def isPublic: NodeSteps[nodes.Method] =
    wrapped.hasModifier(ModifierTypes.PUBLIC)

  /**
    * Traverse to private methods
    * */
  def isPrivate: NodeSteps[nodes.Method] =
    wrapped.hasModifier(ModifierTypes.PRIVATE)

  /**
    * Traverse to protected methods
    * */
  def isProtected: NodeSteps[nodes.Method] =
    wrapped.hasModifier(ModifierTypes.PROTECTED)

  /**
    * Traverse to abstract methods
    * */
  def isAbstract: NodeSteps[nodes.Method] =
    wrapped.hasModifier(ModifierTypes.ABSTRACT)

  /**
    * Traverse to static methods
    * */
  def isStatic: NodeSteps[nodes.Method] =
    wrapped.hasModifier(ModifierTypes.STATIC)

  /**
    * Traverse to native methods
    * */
  def isNative: NodeSteps[nodes.Method] =
    wrapped.hasModifier(ModifierTypes.NATIVE)

  /**
    * Traverse to constructors, that is, keep methods that are constructors
    * */
  def isConstructor: NodeSteps[nodes.Method] =
    wrapped.hasModifier(ModifierTypes.CONSTRUCTOR)

  /**
    * Traverse to virtual method
    * */
  def isVirtual: NodeSteps[nodes.Method] =
    wrapped.hasModifier(ModifierTypes.VIRTUAL)

  /**
    * Traverse to external methods, that is, methods not present
    * but only referenced in the CPG.
    * */
  def external: NodeSteps[nodes.Method] =
    new NodeSteps(raw.has(NodeKeys.IS_EXTERNAL -> true))

  /**
    * Traverse to internal methods, that is, methods for which
    * code is included in this CPG.
    * */
  def internal: NodeSteps[nodes.Method] =
    new NodeSteps(raw.has(NodeKeys.IS_EXTERNAL -> false))

  /**
    * Traverse to the methods local variables
    * */
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
  def literal: NodeSteps[nodes.Literal] =
    new NodeSteps(raw.out(EdgeTypes.CONTAINS).hasLabel(NodeTypes.LITERAL).cast[nodes.Literal])

  def topLevelExpressions: NodeSteps[nodes.Expression] =
    new NodeSteps(
      raw
        .out(EdgeTypes.AST)
        .hasLabel(NodeTypes.BLOCK)
        .out(EdgeTypes.AST)
        .not(_.hasLabel(NodeTypes.LOCAL))
        .cast[nodes.Expression])

  def cfgNode: NodeSteps[nodes.CfgNode] =
    new NodeSteps(raw.flatMap { method =>
      __(method.cfgNode.to(Seq): _*)
    })

  /**
    *  Traverse to first expressions in CFG.
    *  Can be multiple.
    */
  def cfgFirst: NodeSteps[nodes.Expression] =
    new NodeSteps(raw.out(EdgeTypes.CFG).cast[nodes.Expression])

  /**
    *  Traverse to last expressions in CFG.
    *  Can be multiple.
    */
  def cfgLast: NodeSteps[nodes.Expression] =
    methodReturn.cfgLast

  /**
    * Traverse to block
    * */
  def block: NodeSteps[nodes.Block] =
    new NodeSteps(raw.out(EdgeTypes.AST).hasLabel(NodeTypes.BLOCK).cast[nodes.Block])

  /**
    * Traverse to method body (alias for `block`)
    * */
  def body: NodeSteps[nodes.Block] = block

  /**
    * Traverse to namespace
    * */
  def namespace: NodeSteps[nodes.Namespace] =
    new NodeSteps(definingTypeDecl.namespace.raw)

  def numberOfLines: Steps[Int] = wrapped.map(_.numberOfLines)

}

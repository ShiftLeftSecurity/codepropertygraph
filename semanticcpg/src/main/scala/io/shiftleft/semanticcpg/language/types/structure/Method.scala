package io.shiftleft.semanticcpg.language.types.structure

import gremlin.scala._
import io.shiftleft.codepropertygraph.generated._
import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.semanticcpg.language._
import io.shiftleft.semanticcpg.language.types.expressions.generalizations.{CfgNode, Expression}
import io.shiftleft.semanticcpg.language.types.expressions.{Call, ControlStructure, Literal}
import io.shiftleft.semanticcpg.language.types.propertyaccessors._

/**
  * A method, function, or procedure
  * */
class Method[A <: nodes.Method](override val raw: GremlinScala[A])
    extends NodeSteps[A](raw)
    with EvalTypeAccessors[A]
    with ModifierAccessors[A] {

  /**
    * Traverse to parameters of the method
    * */
  def parameter: MethodParameter[nodes.MethodParameterIn] =
    new MethodParameter(
      raw
        .out(EdgeTypes.AST)
        .hasLabel(NodeTypes.METHOD_PARAMETER_IN)
        .cast[nodes.MethodParameterIn])

  /**
    * Traverse to formal return parameter
    * */
  def methodReturn: MethodReturn[nodes.MethodReturn] =
    new MethodReturn(raw.map(_.methodReturn))

  /**
    * Traverse to type decl which have this method bound to it.
    */
  def bindingTypeDecl: TypeDecl[nodes.TypeDecl] =
    referencingBinding.bindingTypeDecl

  /**
    * Traverse to bindings which reference to this method.
    */
  def referencingBinding: Binding[nodes.Binding] =
    new Binding(raw.in(EdgeTypes.REF).filter(_.hasLabel(NodeTypes.BINDING)).cast[nodes.Binding])

  /**
    * All control structures of this method
    * */
  def controlStructure: ControlStructure[nodes.ControlStructure] =
    this.ast.isControlStructure

  /**
    * Shorthand to traverse to control structures where condition matches `regex`
    * */
  def controlStructure(regex: String): ControlStructure[nodes.ControlStructure] =
    this.ast.isControlStructure.code(regex)

  /**
    * Outgoing call sites
    * */
  def callOut: Call[nodes.Call] =
    new Call(raw.out(EdgeTypes.CONTAINS).hasLabel(NodeTypes.CALL).cast[nodes.Call])

  /**
    * The type declaration associated with this method, e.g., the class it is defined in.
    * */
  def definingTypeDecl: TypeDecl[nodes.TypeDecl] =
    new TypeDecl(
      raw
        .cast[nodes.StoredNode]
        .repeat(_.in(EdgeTypes.AST).cast[nodes.StoredNode])
        .until(_.hasLabel(NodeTypes.TYPE_DECL))
        .cast[nodes.TypeDecl])

  /**
    * The method in which this method is defined
    * */
  def definingMethod: Method[nodes.Method] =
    new Method(
      raw
        .cast[nodes.StoredNode]
        .repeat(_.in(EdgeTypes.AST).cast[nodes.StoredNode])
        .until(_.hasLabel(NodeTypes.METHOD))
        .cast[nodes.Method])

  /**
    * Traverse only to methods that are stubs, e.g., their code is not available
    * */
  def isStub: Method[A] =
    new Method(raw.filter(_.not(_.out(EdgeTypes.CFG))))

  /**
    * Traverse only to methods that are not stubs.
    * */
  def isNotStub: Method[A] =
    new Method(raw.filter(_.out(EdgeTypes.CFG)))

  /**
    * Traverse to public methods
    * */
  def isPublic: Method[A] =
    hasModifier(ModifierTypes.PUBLIC)

  /**
    * Traverse to private methods
    * */
  def isPrivate: Method[A] =
    hasModifier(ModifierTypes.PRIVATE)

  /**
    * Traverse to protected methods
    * */
  def isProtected: Method[A] =
    hasModifier(ModifierTypes.PROTECTED)

  /**
    * Traverse to abstract methods
    * */
  def isAbstract: Method[A] =
    hasModifier(ModifierTypes.ABSTRACT)

  /**
    * Traverse to static methods
    * */
  def isStatic: Method[A] =
    hasModifier(ModifierTypes.STATIC)

  /**
    * Traverse to native methods
    * */
  def isNative: Method[A] =
    hasModifier(ModifierTypes.NATIVE)

  /**
    * Traverse to constructors, that is, keep methods that are constructors
    * */
  def isConstructor: Method[A] =
    hasModifier(ModifierTypes.CONSTRUCTOR)

  /**
    * Traverse to virtual method
    * */
  def isVirtual: Method[A] =
    hasModifier(ModifierTypes.VIRTUAL)

  /**
    * Traverse to external methods, that is, methods not present
    * but only referenced in the CPG.
    * */
  def external: Method[A] =
    new Method(raw.has(NodeKeys.IS_EXTERNAL -> true))

  /**
    * Traverse to internal methods, that is, methods for which
    * code is included in this CPG.
    * */
  def internal: Method[A] =
    new Method(raw.has(NodeKeys.IS_EXTERNAL -> false))

  /**
    * Traverse to the methods local variables
    * */
  def local: Local[nodes.Local] =
    new Local(
      raw
        .out(EdgeTypes.CONTAINS)
        .hasLabel(NodeTypes.BLOCK)
        .out(EdgeTypes.AST)
        .hasLabel(NodeTypes.LOCAL)
        .cast[nodes.Local])

  /**
    * Traverse to literals of method
    * */
  def literal: Literal[nodes.Literal] =
    new Literal(raw.out(EdgeTypes.CONTAINS).hasLabel(NodeTypes.LITERAL).cast[nodes.Literal])

  def topLevelExpressions: Expression[nodes.Expression] =
    new Expression(
      raw
        .out(EdgeTypes.AST)
        .hasLabel(NodeTypes.BLOCK)
        .out(EdgeTypes.AST)
        .not(_.hasLabel(NodeTypes.LOCAL))
        .cast[nodes.Expression])

  def cfgNode: CfgNode[nodes.CfgNode] =
    new CfgNode(raw.out(EdgeTypes.CONTAINS).filterOnEnd(_.isInstanceOf[nodes.CfgNode]).cast[nodes.CfgNode])

  /**
    *  Traverse to first expressions in CFG.
    *  Can be multiple.
    */
  def cfgFirst: Expression[nodes.Expression] =
    new Expression(raw.out(EdgeTypes.CFG).cast[nodes.Expression])

  /**
    *  Traverse to last expressions in CFG.
    *  Can be multiple.
    */
  def cfgLast: Expression[nodes.Expression] =
    methodReturn.cfgLast

  /**
    * Traverse to block
    * */
  def block: Block[nodes.Block] =
    new Block(raw.out(EdgeTypes.AST).hasLabel(NodeTypes.BLOCK).cast[nodes.Block])

  /**
    * Traverse to method body (alias for `block`)
    * */
  def body: Block[nodes.Block] = block

  /**
    * Traverse to namespace
    * */
  def namespace: Namespace[nodes.Namespace] =
    new Namespace(definingTypeDecl.namespace.raw)

  def numberOfLines: Steps[Int] = map(_.numberOfLines)

}

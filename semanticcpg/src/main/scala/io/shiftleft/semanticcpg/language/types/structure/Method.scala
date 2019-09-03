package io.shiftleft.semanticcpg.language.types.structure

import gremlin.scala._
import io.shiftleft.codepropertygraph.generated._
import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.semanticcpg.language.types.expressions.generalizations.{
  AstNodeBase,
  CfgNode,
  DeclarationBase,
  Expression,
  Modifier
}
import io.shiftleft.semanticcpg.language._
import io.shiftleft.semanticcpg.language.types.expressions.{Call, ControlStructure, Literal}
import io.shiftleft.semanticcpg.language.types.propertyaccessors._

/**
  * A method, function, or procedure
  * */
class Method(override val raw: GremlinScala[nodes.Method])
    extends NodeSteps[nodes.Method](raw)
    with DeclarationBase[nodes.Method]
    with NameAccessors[nodes.Method]
    with FullNameAccessors[nodes.Method]
    with SignatureAccessors[nodes.Method]
    with LineNumberAccessors[nodes.Method]
    with EvalTypeAccessors[nodes.Method]
    with AstNodeBase[nodes.Method] {

  /**
    * Traverse to concrete instances of method.
    */
  def methodInstance: MethodInst = {
    new MethodInst(
      raw.in(EdgeTypes.REF).cast[nodes.MethodInst]
    )
  }

  /**
    * Traverse to parameters of the method
    * */
  def parameter: MethodParameter =
    new MethodParameter(
      raw
        .out(EdgeTypes.AST)
        .hasLabel(NodeTypes.METHOD_PARAMETER_IN)
        .cast[nodes.MethodParameterIn])

  /**
    * Traverse to formal return parameter
    * */
  def methodReturn: MethodReturn =
    new MethodReturn(raw.out(EdgeTypes.AST).hasLabel(NodeTypes.METHOD_RETURN).cast[nodes.MethodReturn])

  /**
    * Traverse to the type declarations were this method is in the VTable.
    */
  def inVTableOfTypeDecl: TypeDecl = {
    new TypeDecl(
      raw.in(EdgeTypes.VTABLE).cast[nodes.TypeDecl]
    )
  }

  /**
    * Shorthand to traverse to control structures where condition matches `regex`
    * */
  def condition(regex: String): ControlStructure = ast.isControlStructure.condition(regex)

  /**
    * Outgoing call sites
    * */
  def callOut: Call =
    new Call(raw.out(EdgeTypes.CONTAINS).hasLabel(NodeTypes.CALL).cast[nodes.Call])

  /**
    * The type declaration associated with this method, e.g., the class it is defined in.
    * */
  def definingTypeDecl: TypeDecl =
    new TypeDecl(
      raw
        .cast[nodes.StoredNode]
        .repeat(_.in(EdgeTypes.AST).cast[nodes.StoredNode])
        .until(_.hasLabel(NodeTypes.TYPE_DECL))
        .cast[nodes.TypeDecl])

  /**
    * The method in which this method is defined
    * */
  def definingMethod: Method =
    new Method(
      raw
        .cast[nodes.StoredNode]
        .repeat(_.in(EdgeTypes.AST).cast[nodes.StoredNode])
        .until(_.hasLabel(NodeTypes.METHOD))
        .cast[nodes.Method])

  /**
    * Traverse only to methods that are stubs, e.g., their code is not available
    * */
  def isStub: Method =
    new Method(raw.filter(_.not(_.out(EdgeTypes.CFG))))

  /**
    * Traverse only to methods that are not stubs.
    * */
  def isNotStub: Method =
    new Method(raw.filter(_.out(EdgeTypes.CFG)))

  /**
    * Traverse to public methods
    * */
  def isPublic: Method =
    isMethodWithModifier(ModifierTypes.PUBLIC)

  /**
    * Traverse to private methods
    * */
  def isPrivate: Method =
    isMethodWithModifier(ModifierTypes.PRIVATE)

  /**
    * Traverse to protected methods
    * */
  def isProtected: Method =
    isMethodWithModifier(ModifierTypes.PROTECTED)

  /**
    * Traverse to abstract methods
    * */
  def isAbstract: Method =
    isMethodWithModifier(ModifierTypes.ABSTRACT)

  /**
    * Traverse to static methods
    * */
  def isStatic: Method =
    isMethodWithModifier(ModifierTypes.STATIC)

  /**
    * Traverse to native methods
    * */
  def isNative: Method =
    isMethodWithModifier(ModifierTypes.NATIVE)

  /**
    * Traverse to constructors, that is, keep methods that are constructors
    * */
  def isConstructor: Method =
    isMethodWithModifier(ModifierTypes.CONSTRUCTOR)

  /**
    * Traverse to virtual method
    * */
  def isVirtual: Method =
    isMethodWithModifier(ModifierTypes.VIRTUAL)

  private def isMethodWithModifier(modifier: String): Method =
    new Method(raw.filter(_.out.hasLabel(NodeTypes.MODIFIER).has(NodeKeys.MODIFIER_TYPE -> modifier)))

  /**
    * Traverse to external methods, that is, methods not present
    * but only referenced in the CPG.
    * */
  def external: Method =
    new Method(raw.has(NodeKeys.IS_EXTERNAL -> true))

  /**
    * Traverse to internal methods, that is, methods for which
    * code is included in this CPG.
    * */
  def internal: Method =
    new Method(raw.has(NodeKeys.IS_EXTERNAL -> false))

  /**
    * Traverse to method modifiers, e.g., "static", "public".
    * */
  def modifier: Modifier =
    new Modifier(
      raw.out
        .hasLabel(NodeTypes.MODIFIER)
        .cast[nodes.Modifier])

  /**
    * Traverse to the methods local variables
    * */
  def local: Local =
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
  override def literal: Literal =
    new Literal(raw.out(EdgeTypes.CONTAINS).hasLabel(NodeTypes.LITERAL).cast[nodes.Literal])

  def topLevelExpressions: Expression =
    new Expression(
      raw
        .out(EdgeTypes.AST)
        .hasLabel(NodeTypes.BLOCK)
        .out(EdgeTypes.AST)
        .not(_.hasLabel(NodeTypes.LOCAL))
        .cast[nodes.Expression])

  def cfgNode: CfgNode =
    new CfgNode(
      raw.out(EdgeTypes.CONTAINS).filterOnEnd(_.isInstanceOf[nodes.CfgNode]).cast[nodes.CfgNode]
    )

  /**
    *  Traverse to first expressions in CFG.
    *  Can be multiple.
    */
  def cfgFirst: Expression =
    new Expression(
      raw.out(EdgeTypes.CFG).cast[nodes.Expression]
    )

  /**
    *  Traverse to last expressions in CFG.
    *  Can be multiple.
    */
  def cfgLast: Expression =
    methodReturn.cfgLast

  /**
    * Traverse to block
    * */
  def block: Block =
    new Block(raw.out(EdgeTypes.AST).hasLabel(NodeTypes.BLOCK).cast[nodes.Block])

  /**
    * Traverse to method body (alias for `block`)
    * */
  def body: Block = block

  /**
    * Traverse to namespace
    * */
  def namespace: Namespace =
    new Namespace(definingTypeDecl.namespace.raw)

}

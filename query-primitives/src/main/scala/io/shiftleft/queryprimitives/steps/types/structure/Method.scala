package io.shiftleft.queryprimitives.steps.types.structure

import java.io._

import gremlin.scala._
import io.shiftleft.codepropertygraph.generated._
import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.queryprimitives.steps.types.expressions.generalizations.{
  DeclarationBase,
  Expression,
  Modifier
}
import io.shiftleft.queryprimitives.steps.CpgSteps
import io.shiftleft.queryprimitives.steps.Implicits._
import io.shiftleft.queryprimitives.steps.ICallResolver
import io.shiftleft.queryprimitives.steps.types.expressions.{Call, Literal}
import io.shiftleft.queryprimitives.steps.types.propertyaccessors._
import org.json4s._
import org.json4s.native.JsonMethods._
import shapeless.HList

import scala.collection.mutable
import scala.collection.JavaConverters._

/**
  * A method, function, or procedure
  * */
class Method[Labels <: HList](override val raw: GremlinScala[Vertex])
    extends CpgSteps[nodes.Method, Labels](raw)
    with DeclarationBase[nodes.Method, Labels]
    with NameAccessors[nodes.Method, Labels]
    with FullNameAccessors[nodes.Method, Labels]
    with SignatureAccessors[nodes.Method, Labels]
    with LineNumberAccessors[nodes.Method, Labels]
    with EvalTypeAccessors[nodes.Method, Labels] {

  /**
    * Traverse to concrete instances of method.
    */
  def methodInstance: MethodInst[Labels] =
    new MethodInst[Labels](raw.in(EdgeTypes.REF))

  /**
    * Traverse to parameters of the method
    * */
  def parameter: MethodParameter[Labels] =
    new MethodParameter[Labels](
      raw
        .out(EdgeTypes.AST)
        .hasLabel(NodeTypes.METHOD_PARAMETER_IN)
    )

  /**
    * Traverse to formal return parameter
    * */
  def methodReturn: MethodReturn[Labels] =
    new MethodReturn[Labels](raw.out(EdgeTypes.AST).hasLabel(NodeTypes.METHOD_RETURN))

  /**
    * Traverse to the type declarations were this method is in the VTable.
    */
  def inVTableOfTypeDecl: TypeDecl[Labels] = {
    new TypeDecl[Labels](
      raw.in(EdgeTypes.VTABLE)
    )
  }

  /**
    * Traverse to direct and transitive callers of the method.
    * */
  def calledBy(sourceTrav: Method[Labels])(implicit callResolver: ICallResolver): Method[Labels] = {
    caller(callResolver).calledByIncludingSink(sourceTrav)(callResolver)
  }

  /**
    * Traverse to direct and transitive callers of the method.
    * */
  def calledBy(sourceTrav: MethodInst[Labels])(
      implicit callResolver: ICallResolver): Method[Labels] = {
    caller(callResolver).calledByIncludingSink(sourceTrav.method)(callResolver)
  }

  /**
    * Intendend for internal use!
    * Traverse to direct and transitive callers of the method.
    */
  def calledByIncludingSink(sourceTrav: Method[Labels], resolve: Boolean = true)(
      implicit callResolver: ICallResolver): Method[Labels] = {
    val sourceMethods = sourceTrav.raw.toSet
    val sinkMethods   = raw.dedup.toList()

    if (sourceMethods.isEmpty || sinkMethods.isEmpty) {
      new Method[Labels](graph.V(-1).asInstanceOf[GremlinScala[Vertex]])
    } else {
      val ids        = sinkMethods.map(_.id)
      val methodTrav = graph.V(ids: _*)

      new Method[Labels](
        methodTrav
          .emit(_.is(P.within(sourceMethods)))
          .repeat(
            _.sideEffect { method =>
              if (resolve) {
                callResolver.resolveDynamicMethodCallSites(method.toCC[nodes.Method])
              }
            }.in(EdgeTypes.REF) // expand to method instance
              .in(EdgeTypes.CALL) // expand to call site
              .in(EdgeTypes.CONTAINS) // expand to method
              .dedup
              .simplePath()
          )
          .asInstanceOf[GremlinScala[Vertex]]
      )
    }
  }

  /**
    * Traverse to direct callers of this method
    * */
  def caller(implicit callResolver: ICallResolver): Method[Labels] =
    callIn(callResolver).method

  /**
    * Traverse to methods called by this method
    * */
  def callee(implicit callResolver: ICallResolver): Method[Labels] =
    callOut.calledMethod(callResolver)

  /**
    * Incoming call sites
    * */
  def callIn(implicit callResolver: ICallResolver): Call[Labels] = {
    new Call[Labels](
      sideEffect(callResolver.resolveDynamicMethodCallSites).raw
        .in(EdgeTypes.REF)
        .in(EdgeTypes.CALL))
  }

  /**
    * Outgoing call sites
    * */
  def callOut: Call[Labels] =
    new Call[Labels](raw.out(EdgeTypes.CONTAINS).hasLabel(NodeTypes.CALL))

  /**
    * The type declaration associated with this method, e.g., the class it is defined in.
    * */
  def definingTypeDecl: TypeDecl[Labels] =
    new TypeDecl[Labels](
      raw
        .repeat(_.in(EdgeTypes.AST))
        .until(_.hasLabel(NodeTypes.TYPE_DECL))
    )

  /**
    * The method in which this method is defined
    * */
  def definingMethod: Method[Labels] =
    new Method[Labels](
      raw
        .repeat(_.in(EdgeTypes.AST))
        .until(_.hasLabel(NodeTypes.METHOD)))

  /**
    * Traverse only to methods that are stubs, e.g., their code is not available
    * */
  def isStub: Method[Labels] =
    new Method[Labels](raw.filter(_.not(_.out(EdgeTypes.CFG))))

  /**
    * Traverse only to methods that are not stubs.
    * */
  def isNotStub: Method[Labels] =
    new Method[Labels](raw.filter(_.out(EdgeTypes.CFG)))

  /**
    * Traverse to public methods
    * */
  def isPublic: Method[Labels] =
    new Method[Labels](
      raw.filter(
        _.out.hasLabel(NodeTypes.MODIFIER).has(NodeKeys.MODIFIER_TYPE -> ModifierTypes.PUBLIC)))

  /**
    * Traverse to private methods
    * */
  def isPrivate: Method[Labels] =
    new Method[Labels](
      raw.filter(
        _.out.hasLabel(NodeTypes.MODIFIER).has(NodeKeys.MODIFIER_TYPE -> ModifierTypes.PRIVATE)))

  /**
    * Traverse to protected methods
    * */
  def isProtected: Method[Labels] =
    new Method[Labels](
      raw.filter(
        _.out.hasLabel(NodeTypes.MODIFIER).has(NodeKeys.MODIFIER_TYPE -> ModifierTypes.PROTECTED)))

  /**
    * Traverse to abstract methods
    * */
  def isAbstract: Method[Labels] =
    new Method[Labels](
      raw.filter(
        _.out.hasLabel(NodeTypes.MODIFIER).has(NodeKeys.MODIFIER_TYPE -> ModifierTypes.ABSTRACT)))

  /**
    * Traverse to static methods
    * */
  def isStatic: Method[Labels] =
    new Method[Labels](
      raw.filter(
        _.out.hasLabel(NodeTypes.MODIFIER).has(NodeKeys.MODIFIER_TYPE -> ModifierTypes.STATIC)))

  /**
    * Traverse to native methods
    * */
  def isNative: Method[Labels] =
    new Method[Labels](
      raw.filter(
        _.out.hasLabel(NodeTypes.MODIFIER).has(NodeKeys.MODIFIER_TYPE -> ModifierTypes.NATIVE)))

  /**
    * Traverse to constructors, that is, keep methods that are constructors
    * */
  def isConstructor: Method[Labels] =
    new Method[Labels](raw.filter(
      _.out.hasLabel(NodeTypes.MODIFIER).has(NodeKeys.MODIFIER_TYPE -> ModifierTypes.CONSTRUCTOR)))

  /**
    * Traverse to virtual method
    * */
  def isVirtual: Method[Labels] =
    new Method[Labels](
      raw.filter(
        _.out.hasLabel(NodeTypes.MODIFIER).has(NodeKeys.MODIFIER_TYPE -> ModifierTypes.VIRTUAL)))

  /**
    * Traverse to external methods, that is, methods not present
    * but only referenced in the CPG.
    * */
  def external: Method[Labels] =
    new Method[Labels](
      filter(_.definingTypeDecl.external).raw
    )

  /**
    * Traverse to internal methods, that is, methods for which
    * code is included in this CPG.
    * */
  def internal: Method[Labels] =
    new Method[Labels](
      filter(_.definingTypeDecl.internal).raw
    )

  /**
    * Traverse to method modifiers, e.g., "static", "public".
    * */
  def modifier: Modifier[Labels] =
    new Modifier[Labels](
      raw.out
        .hasLabel(NodeTypes.MODIFIER)
    )

  /**
    * Traverse to the methods local variables
    * */
  def local: Local[Labels] =
    new Local[Labels](
      raw
        .out(EdgeTypes.CONTAINS)
        .hasLabel(NodeTypes.BLOCK)
        .out(EdgeTypes.AST)
        .hasLabel(NodeTypes.LOCAL))

  /**
    * Traverse to literals of method
    * */
  def literal: Literal[Labels] =
    new Literal[Labels](raw.out(EdgeTypes.CONTAINS).hasLabel(NodeTypes.LITERAL))

  def topLevelExpressions: Expression[Labels] =
    new Expression[Labels](
      raw
        .out(EdgeTypes.AST)
        .hasLabel(NodeTypes.BLOCK)
        .out(EdgeTypes.AST)
        .not(_.hasLabel(NodeTypes.LOCAL)))

  /**
    *  Traverse to first expressions in CFG.
    *  Can be multiple.
    */
  def cfgFirst: Expression[Labels] =
    new Expression[Labels](
      raw.out(EdgeTypes.CFG)
    )

  /**
    *  Traverse to last expressions in CFG.
    *  Can be multiple.
    */
  def cfgLast: Expression[Labels] =
    methodReturn.cfgLast

  /**
    * Traverse to block
    * */
  def block: Block[Labels] =
    new Block[Labels](raw.out(EdgeTypes.AST).hasLabel(NodeTypes.BLOCK))

  /**
    * Traverse to namespace
    * */
  def namespace: Namespace[Labels] =
    new Namespace[Labels](definingTypeDecl.namespace.raw)

}

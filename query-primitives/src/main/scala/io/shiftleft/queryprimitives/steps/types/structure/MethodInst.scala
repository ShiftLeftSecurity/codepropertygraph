package io.shiftleft.queryprimitives.steps.types.structure

import gremlin.scala._
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, nodes}
import io.shiftleft.queryprimitives.steps.Implicits._
import io.shiftleft.queryprimitives.steps.ICallResolver
import io.shiftleft.queryprimitives.steps.CpgSteps
import io.shiftleft.queryprimitives.steps.types.expressions.{Call, Literal}
import io.shiftleft.queryprimitives.steps.types.expressions.generalizations.Modifier
import io.shiftleft.queryprimitives.steps.types.propertyaccessors.{FullNameAccessors, NameAccessors, SignatureAccessors}
import shapeless.HList

class MethodInst[Labels <: HList](override val raw: GremlinScala[Vertex])
    extends CpgSteps[nodes.MethodInst, Labels](raw)
    with NameAccessors[nodes.MethodInst, Labels]
    with FullNameAccessors[nodes.MethodInst, Labels]
    with SignatureAccessors[nodes.MethodInst, Labels] {

  def method: Method[Labels] = {
    new Method[Labels](raw.out(EdgeTypes.REF))
  }

  /**
    * Traverse to parameters of method.
    * */
  def parameter: MethodParameter[Labels] = {
    method.parameter
  }

  /**
    * Traverse to formal return parameter of method.
    * */
  def methodReturn: MethodReturn[Labels] = {
    method.methodReturn
  }

  /**
    * Traverse to the type declarations were method is in the VTable.
    */
  def inVTableOfTypeDecl: TypeDecl[Labels] = {
    method.inVTableOfTypeDecl
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
  def calledBy(sourceTrav: MethodInst[Labels])(implicit callResolver: ICallResolver): Method[Labels] = {
    caller(callResolver).calledByIncludingSink(sourceTrav.method)(callResolver)
  }

  /**
    * Traverse to direct callers of this method
    * */
  def caller(implicit callResolver: ICallResolver): Method[Labels] = {
    callIn(callResolver).method
  }

  /**
    * Traverse to methods called by method.
    * */
  def callee(implicit callResolver: ICallResolver): Method[Labels] = {
    method.callee(callResolver)
  }

  /**
    * Incoming call sites
    * */
  def callIn(implicit callResolver: ICallResolver): Call[Labels] = {
    // Check whether possible call sides are resolved or resolve them.
    // We only do this for virtual method calls.
    // note: side effect writes edges into the graph
    // TODO Also resolve function pointers.
    new Call[Labels](
      sideEffect(callResolver.resolveDynamicMethodInstCallSites).raw.in(EdgeTypes.CALL)
    )
  }

  /**
    * Outgoing call sites of method.
    * */
  def callOut: Call[Labels] = {
    method.callOut
  }

  /**
    * The type declaration associated with method.
    * */
  def definingTypeDecl: TypeDecl[Labels] = {
    method.definingTypeDecl
  }

  /**
    * The method in which the method is defined.
    * */
  def definingMethod: Method[Labels] = {
    method.definingMethod
  }

  /**
    * Traverse only to methods that are stubs, e.g., their code is not available
    * */
  def isStub: MethodInst[Labels] = {
    filter(_.method.isStub)
  }

  /**
    * Traverse only to methods that are not stubs.
    * */
  def isNotStub: MethodInst[Labels] = {
    filter(_.method.isNotStub)
  }

  /**
    * Traverse to public methods
    * */
  def isPublic: MethodInst[Labels] = {
    filter(_.method.isPublic)
  }

  /**
    * Traverse to private methods
    * */
  def isPrivate: MethodInst[Labels] = {
    filter(_.method.isPublic)
  }

  /**
    * Traverse to protected methods
    * */
  def isProtected: MethodInst[Labels] = {
    filter(_.method.isProtected)
  }

  /**
    * Traverse to abstract methods
    * */
  def isAbstract: MethodInst[Labels] = {
    filter(_.method.isAbstract)
  }

  /**
    * Traverse to static methods
    * */
  def isStatic: MethodInst[Labels] = {
    filter(_.method.isStatic)
  }

  /**
    * Traverse to native methods
    * */
  def isNative: MethodInst[Labels] = {
    filter(_.method.isNative)
  }

  /**
    * Traverse to constructors, that is, keep methods that are constructors
    * */
  def isConstructor: MethodInst[Labels] = {
    filter(_.method.isConstructor)
  }

  /**
    * Traverse to virtual method
    * */
  def isVirtual: MethodInst[Labels] = {
    filter(_.method.isVirtual)
  }

  /**
    * Traverse to method modifiers of method.
    * */
  def modifier: Modifier[Labels] = {
    method.modifier
  }

  /**
    * Traverse to the methods local variables.
    * */
  def local: Local[Labels] = {
    method.local
  }

  /**
    * Traverse to literals used in method.
    * */
  def literal: Literal[Labels] = {
    method.literal
  }
}

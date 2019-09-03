package io.shiftleft.semanticcpg.language.types.structure

import gremlin.scala.GremlinScala
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, nodes}
import io.shiftleft.semanticcpg.language._
import io.shiftleft.semanticcpg.language.types.expressions.{Call, Literal}
import io.shiftleft.semanticcpg.language.types.expressions.generalizations.Modifier
import io.shiftleft.semanticcpg.language.types.propertyaccessors.{FullNameAccessors, NameAccessors, SignatureAccessors}

class MethodInst(override val raw: GremlinScala[nodes.MethodInst])
    extends NodeSteps[nodes.MethodInst](raw)
    with NameAccessors[nodes.MethodInst]
    with FullNameAccessors[nodes.MethodInst]
    with SignatureAccessors[nodes.MethodInst] {

  def method: Method = {
    new Method(
      raw.out(EdgeTypes.REF).cast[nodes.Method]
    )
  }

  /**
    * Traverse to parameters of method.
    * */
  def parameter: MethodParameter = {
    method.parameter
  }

  /**
    * Traverse to formal return parameter of method.
    * */
  def methodReturn: MethodReturn = {
    method.methodReturn
  }

  /**
    * Traverse to the type declarations were method is in the VTable.
    */
  def inVTableOfTypeDecl: TypeDecl = {
    method.inVTableOfTypeDecl
  }

  /**
    * Incoming call sites
    * */
  def callIn(implicit callResolver: ICallResolver): Call = {
    // Check whether possible call sides are resolved or resolve them.
    // We only do this for virtual method calls.
    // TODO Also resolve function pointers.
    new Call(
      sideEffect(callResolver.resolveDynamicMethodInstCallSites).raw
        .in(EdgeTypes.CALL)
        .cast[nodes.Call])
  }

  /**
    * Outgoing call sites of method.
    * */
  def callOut: Call = {
    method.callOut
  }

  /**
    * The type declaration associated with method.
    * */
  def definingTypeDecl: TypeDecl = {
    method.definingTypeDecl
  }

  /**
    * The method in which the method is defined.
    * */
  def definingMethod: Method = {
    method.definingMethod
  }

  /**
    * Traverse only to methods that are stubs, e.g., their code is not available
    * */
  def isStub: MethodInst = {
    filter(_.method.isStub)
  }

  /**
    * Traverse only to methods that are not stubs.
    * */
  def isNotStub: MethodInst = {
    filter(_.method.isNotStub)
  }

  /**
    * Traverse to public methods
    * */
  def isPublic: MethodInst = {
    filter(_.method.isPublic)
  }

  /**
    * Traverse to private methods
    * */
  def isPrivate: MethodInst = {
    filter(_.method.isPublic)
  }

  /**
    * Traverse to protected methods
    * */
  def isProtected: MethodInst = {
    filter(_.method.isProtected)
  }

  /**
    * Traverse to abstract methods
    * */
  def isAbstract: MethodInst = {
    filter(_.method.isAbstract)
  }

  /**
    * Traverse to static methods
    * */
  def isStatic: MethodInst = {
    filter(_.method.isStatic)
  }

  /**
    * Traverse to native methods
    * */
  def isNative: MethodInst = {
    filter(_.method.isNative)
  }

  /**
    * Traverse to constructors, that is, keep methods that are constructors
    * */
  def isConstructor: MethodInst = {
    filter(_.method.isConstructor)
  }

  /**
    * Traverse to virtual method
    * */
  def isVirtual: MethodInst = {
    filter(_.method.isVirtual)
  }

  /**
    * Traverse to method modifiers of method.
    * */
  def modifier: Modifier = {
    method.modifier
  }

  /**
    * Traverse to the methods local variables.
    * */
  def local: Local = {
    method.local
  }

  /**
    * Traverse to literals used in method.
    * */
  def literal: Literal = {
    method.literal
  }
}

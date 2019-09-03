package io.shiftleft.dataflowengine.language.extensions

import io.shiftleft.codepropertygraph.generated.{EdgeTypes, nodes}
import io.shiftleft.dataflowengine.language.ICallResolver
import io.shiftleft.semanticcpg.language.types.expressions.{Call => OriginalCall}
import io.shiftleft.semanticcpg.language.types.structure.{Method => OriginalMethod, MethodInst => OriginalMethodInst}
import io.shiftleft.semanticcpg.language._

class MethodInst(original : OriginalMethodInst) {

  /**
    * Traverse to direct and transitive callers of the method.
    * */
  def calledBy(sourceTrav: OriginalMethod)(implicit callResolver: ICallResolver): OriginalMethod = {
    new Method(caller(callResolver)).calledByIncludingSink(sourceTrav)(callResolver)
  }

  /**
    * Traverse to direct and transitive callers of the method.
    * */
  def calledBy(sourceTrav: OriginalMethodInst)(implicit callResolver: ICallResolver): OriginalMethod = {
    new Method(caller(callResolver)).calledByIncludingSink(sourceTrav.method)(callResolver)
  }

  /**
    * Traverse to direct callers of this method
    * */
  def caller(implicit callResolver: ICallResolver): OriginalMethod = {
    callIn(callResolver).method
  }

  /**
    * Traverse to methods called by method.
    * */
  def callee(implicit callResolver: ICallResolver): Method = {
    new Method(original.method).callee(callResolver)
  }

  /**
    * Incoming call sites
    * */
  def callIn(implicit callResolver: ICallResolver): OriginalCall = {
    // Check whether possible call sides are resolved or resolve them.
    // We only do this for virtual method calls.
    // TODO Also resolve function pointers.
    new OriginalCall(
      original.sideEffect(callResolver.resolveDynamicMethodInstCallSites).raw
        .in(EdgeTypes.CALL)
        .cast[nodes.Call])
  }



}

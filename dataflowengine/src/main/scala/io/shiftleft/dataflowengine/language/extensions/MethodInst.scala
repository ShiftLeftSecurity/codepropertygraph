package io.shiftleft.dataflowengine.language.extensions

import io.shiftleft.semanticcpg.language.ICallResolver
import io.shiftleft.semanticcpg.language.types.structure.{Method => OriginalMethod, MethodInst => OriginalMethodInst}

class MethodInst(original : OriginalMethodInst) {

  /**
    * Traverse to direct and transitive callers of the method.
    * */
  def calledBy(sourceTrav: OriginalMethod)(implicit callResolver: ICallResolver): Method = {
    caller(callResolver).calledByIncludingSink(sourceTrav)(callResolver)
  }

  /**
    * Traverse to direct and transitive callers of the method.
    * */
  def calledBy(sourceTrav: OriginalMethodInst)(implicit callResolver: ICallResolver): Method = {
    caller(callResolver).calledByIncludingSink(sourceTrav.method)(callResolver)
  }

  /**
    * Traverse to direct callers of this method
    * */
  def caller(implicit callResolver: ICallResolver): Method = {
    original.callIn(callResolver).method
  }

  /**
    * Traverse to methods called by method.
    * */
  def callee(implicit callResolver: ICallResolver): Method = {
    new Method(original.method).callee(callResolver)
  }


}

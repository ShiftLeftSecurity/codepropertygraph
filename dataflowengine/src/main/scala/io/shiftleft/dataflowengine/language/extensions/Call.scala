package io.shiftleft.dataflowengine.language.extensions

import io.shiftleft.codepropertygraph.generated.{EdgeTypes, nodes}
import io.shiftleft.semanticcpg.language.ICallResolver
import io.shiftleft.semanticcpg.language.types.structure.{Method => OriginalMethod}
import io.shiftleft.semanticcpg.language.types.expressions.{Call => OriginalCall}
import io.shiftleft.semanticcpg.language.types.structure.{MethodInst => OriginalMethodInst}

import io.shiftleft.semanticcpg.language._

class Call(original : OriginalCall) {

  /**
  The callee method
    */
  def calledMethod(implicit callResolver: ICallResolver): OriginalMethod = {
    calledMethodInstance(callResolver).method
  }

  /**
  The callee method instance
    */
  def calledMethodInstance(implicit callResolver: ICallResolver): OriginalMethodInst =
    new OriginalMethodInst(
      original.sideEffect(callResolver.resolveDynamicCallSite).raw
        .out(EdgeTypes.CALL)
        .cast[nodes.MethodInst])

}

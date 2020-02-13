package io.shiftleft.semanticcpg.language.callgraphextension

import io.shiftleft.codepropertygraph.generated.{EdgeTypes, nodes}
import io.shiftleft.semanticcpg.language._

class Call(val wrapped: NodeSteps[nodes.Call]) extends AnyVal {

  /** The callee method */
  def calledMethod(implicit callResolver: ICallResolver): NodeSteps[nodes.Method] = {
    new NodeSteps(
      wrapped.sideEffect(callResolver.resolveDynamicCallSite).raw
        .out(EdgeTypes.CALL)
        .cast[nodes.Method])
  }

}

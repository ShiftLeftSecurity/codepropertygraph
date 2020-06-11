package io.shiftleft.semanticcpg.language.callgraphextension

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.semanticcpg.language._

class Call(val wrapped: NodeSteps[nodes.Call]) extends AnyVal {

  /** The callee method */
  def callee(implicit callResolver: ICallResolver): NodeSteps[nodes.Method] = {
    new NodeSteps(wrapped.raw.flatMap { call =>
      callResolver.getCalledMethodsAsTraversal(call)
    })
  }

}

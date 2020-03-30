package io.shiftleft.semanticcpg.language.callgraphextension

import gremlin.scala.GremlinScala
import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.semanticcpg.language._

class Call(val wrapped: NodeSteps[nodes.Call]) extends AnyVal {
  private def raw: GremlinScala[nodes.Call] = wrapped.raw

  /** The callee method */
  def calledMethod(implicit callResolver: ICallResolver): NodeSteps[nodes.Method] = {
    new NodeSteps(wrapped.raw.flatMap { call =>
      callResolver.resolveDynamicCallSite(call)
      callResolver.getCalledMethods(call)
    })
  }

}

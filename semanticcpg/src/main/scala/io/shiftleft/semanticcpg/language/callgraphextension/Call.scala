package io.shiftleft.semanticcpg.language.callgraphextension

import gremlin.scala.GremlinScala
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, nodes}
import io.shiftleft.semanticcpg.language._

class Call(override val raw: GremlinScala[nodes.Call]) extends NodeSteps[nodes.Call](raw) {

  /**
  The callee method
    */
  def calledMethod(implicit callResolver: ICallResolver): NodeSteps[nodes.Method] = {
    new NodeSteps[nodes.Method](sideEffect(callResolver.resolveDynamicCallSite).raw
      .out(EdgeTypes.CALL).cast[nodes.Method])
  }

  /**
  The callee method instance
    */
  def calledMethodInstance(implicit callResolver: ICallResolver): NodeSteps[nodes.MethodInst] =
    new NodeSteps[nodes.MethodInst](
      sideEffect(callResolver.resolveDynamicCallSite).raw
        .out(EdgeTypes.CALL)
        .cast[nodes.MethodInst])

}

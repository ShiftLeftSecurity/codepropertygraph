package io.shiftleft.semanticcpg.language.callgraphextension

import gremlin.scala.GremlinScala
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, nodes}
import io.shiftleft.semanticcpg.language._

class Call[A <: nodes.Call](override val raw: GremlinScala[A]) extends NodeSteps[A](raw) {

  /**
  The callee method
    */
  def calledMethod(implicit callResolver: ICallResolver): Method[nodes.Method] = {
    new Method(
      sideEffect(callResolver.resolveDynamicCallSite).raw
        .out(EdgeTypes.CALL)
        .cast[nodes.Method])
  }

}

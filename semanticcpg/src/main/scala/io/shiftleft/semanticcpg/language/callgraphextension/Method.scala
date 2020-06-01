package io.shiftleft.semanticcpg.language.callgraphextension

import gremlin.scala._
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, nodes}
import io.shiftleft.semanticcpg.language._
import io.shiftleft.semanticcpg.language.types.structure.{Method => OriginalMethod}

class Method(val wrapped: NodeSteps[nodes.Method]) extends AnyVal {
  private def raw: GremlinScala[nodes.Method] = wrapped.raw

  /**
    * Intended for internal use!
    * Traverse to direct and transitive callers of the method.
    */
  def calledByIncludingSink(sourceTrav: NodeSteps[nodes.Method])(
      implicit callResolver: ICallResolver): NodeSteps[nodes.Method] = {
    val sourceMethods = sourceTrav.raw.toSet
    val sinkMethods = raw.dedup.toList

    if (sourceMethods.isEmpty || sinkMethods.isEmpty) {
      new NodeSteps(wrapped.graph.asScala.V(-1).cast[nodes.Method])
    } else {
      val ids = sinkMethods.map(_.id)
      val methodTrav = wrapped.graph.asScala.V(ids: _*)

      new Steps[nodes.Method](
        methodTrav
          .emit(_.is(P.within(sourceMethods)))
          .repeat(
            _.flatMap(method => callResolver.getMethodCallsitesAsTraversal(method.asInstanceOf[nodes.Method]))
              .in(EdgeTypes.CONTAINS) // expand to method
              .dedup
              .simplePath
          )
          .cast[nodes.Method]
      )
    }
  }

  /**
    * Traverse to direct callers of this method
    * */
  def caller(implicit callResolver: ICallResolver): NodeSteps[nodes.Method] =
    callIn(callResolver).method

  /**
    * Traverse to methods called by this method
    * */
  def callee(implicit callResolver: ICallResolver): NodeSteps[nodes.Method] =
    new OriginalMethod(wrapped).callOut.calledMethod(callResolver)

  /**
    * Incoming call sites
    * */
  def callIn(implicit callResolver: ICallResolver): NodeSteps[nodes.Call] =
    new NodeSteps(wrapped.raw.flatMap { method =>
      callResolver
        .getMethodCallsitesAsTraversal(method)
        .asInstanceOf[GremlinScala[nodes.Call]]
    })

  /**
    * Traverse to direct and transitive callers of the method.
    * */
  def calledBy(sourceTrav: Steps[nodes.Method])(implicit callResolver: ICallResolver): NodeSteps[nodes.Method] =
    caller(callResolver).calledByIncludingSink(sourceTrav)(callResolver)

  /**
    * Outgoing call sites to methods where fullName matches `regex`.
    * */
  def callOutRegex(regex: String)(implicit callResolver: ICallResolver): NodeSteps[nodes.Call] =
    new OriginalMethod(wrapped).callOut.filter(_.calledMethod.fullName(regex))

}

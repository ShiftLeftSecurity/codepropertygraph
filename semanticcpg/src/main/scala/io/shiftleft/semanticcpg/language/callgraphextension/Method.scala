package io.shiftleft.semanticcpg.language.callgraphextension

import gremlin.scala._
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeTypes, nodes}
import io.shiftleft.semanticcpg.language._
import overflowdb.traversal.help.Doc

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
      new NodeSteps(__())
    } else {
      val methodTrav = sinkMethods.start.raw

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
    call.callee(callResolver)

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

  @deprecated("Use call", "")
  def callOut: NodeSteps[nodes.Call] = call

  @deprecated("Use call", "")
  def callOutRegex(regex: String)(implicit callResolver: ICallResolver): NodeSteps[nodes.Call] =
    call(regex)

  /**
    * Outgoing call sites to methods where fullName matches `regex`.
    * */
  def call(regex: String)(implicit callResolver: ICallResolver): NodeSteps[nodes.Call] =
    call.filter(_.callee.fullName(regex))

  /**
    * Outgoing call sites
    * */
  @Doc("Call sites (outgoing calls)")
  def call: NodeSteps[nodes.Call] =
    new NodeSteps(raw.out(EdgeTypes.CONTAINS).hasLabel(NodeTypes.CALL).cast[nodes.Call])

}

package io.shiftleft.semanticcpg.language.callgraphextension

import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeTypes, nodes}
import io.shiftleft.semanticcpg.language._
import overflowdb.traversal.Traversal
import overflowdb.traversal.help.Doc

class Method(val traversal: Traversal[nodes.Method]) extends AnyVal {

  /**
    * Intended for internal use!
    * Traverse to direct and transitive callers of the method.
    */
  def calledByIncludingSink(sourceTrav: Traversal[nodes.Method])(
      implicit callResolver: ICallResolver): Traversal[nodes.Method] = {
    val sourceMethods = sourceTrav.toSet
    val sinkMethods = traversal.dedup

    if (sourceMethods.isEmpty || sinkMethods.isEmpty) {
      Traversal.empty
    } else {
      sinkMethods
        .repeat(
          _.flatMap(callResolver.getMethodCallsitesAsTraversal)
            .in(EdgeTypes.CONTAINS) // expand to method
        )(_.dedup.emit(_.collect {
          case method: nodes.Method if sourceMethods.contains(method) => method
        }))
        .cast[nodes.Method]
    }
  }

  /**
    * Traverse to direct callers of this method
    * */
  def caller(implicit callResolver: ICallResolver): Traversal[nodes.Method] =
    callIn(callResolver).method

  /**
    * Traverse to methods called by this method
    * */
  def callee(implicit callResolver: ICallResolver): Traversal[nodes.Method] =
    call.callee(callResolver)

  /**
    * Incoming call sites
    * */
  def callIn(implicit callResolver: ICallResolver): Traversal[nodes.Call] =
    traversal.flatMap(method => callResolver.getMethodCallsitesAsTraversal(method).cast[nodes.Call])

  /**
    * Traverse to direct and transitive callers of the method.
    * */
  def calledBy(sourceTrav: Traversal[nodes.Method])(implicit callResolver: ICallResolver): Traversal[nodes.Method] =
    caller(callResolver).calledByIncludingSink(sourceTrav)(callResolver)

  @deprecated("Use call", "")
  def callOut: Traversal[nodes.Call] =
    call

  @deprecated("Use call", "")
  def callOutRegex(regex: String)(implicit callResolver: ICallResolver): Traversal[nodes.Call] =
    call(regex)

  /**
    * Outgoing call sites to methods where fullName matches `regex`.
    * */
  def call(regex: String)(implicit callResolver: ICallResolver): Traversal[nodes.Call] =
    call.where(_.callee.fullName(regex))

  /**
    * Outgoing call sites
    * */
  @Doc("Call sites (outgoing calls)")
  def call: Traversal[nodes.Call] =
    traversal.out(EdgeTypes.CONTAINS).hasLabel(NodeTypes.CALL).cast[nodes.Call]

}

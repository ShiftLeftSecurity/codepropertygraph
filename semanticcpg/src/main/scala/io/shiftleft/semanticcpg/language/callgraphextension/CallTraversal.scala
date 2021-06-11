package io.shiftleft.semanticcpg.language.callgraphextension

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.semanticcpg.language._
import overflowdb.traversal.Traversal

class CallTraversal(val traversal: Traversal[nodes.Call]) extends AnyVal {

  @deprecated("Use callee", "")
  def calledMethod(implicit callResolver: ICallResolver): Traversal[nodes.Method] = callee

  /** The callee method */
  def callee(implicit callResolver: ICallResolver): Traversal[nodes.Method] =
    traversal.flatMap(callResolver.getCalledMethodsAsTraversal)

}

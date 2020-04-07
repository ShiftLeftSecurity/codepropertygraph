package io.shiftleft.semanticcpg.language

import io.shiftleft.codepropertygraph.generated.nodes

trait ICallResolver {
  def resolveDynamicCallSite(callsite: nodes.Call): Unit
  def resolveDynamicMethodCallSites(method: nodes.Method): Unit
}

object NoResolve extends ICallResolver {
  def resolveDynamicCallSite(callsite: nodes.Call): Unit = {}

  def resolveDynamicMethodCallSites(method: nodes.Method): Unit = {}
}

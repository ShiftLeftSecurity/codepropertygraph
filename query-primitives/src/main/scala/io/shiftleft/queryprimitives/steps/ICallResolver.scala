package io.shiftleft.queryprimitives.steps

import io.shiftleft.codepropertygraph.generated.nodes.StoredNode

trait ICallResolver {
  def resolveDynamicCallSite(callsite: StoredNode): Unit
  def resolveDynamicMethodCallSites(method: StoredNode): Unit
  def resolveDynamicMethodInstCallSites(methodInst: StoredNode): Unit
}

object NoResolve extends ICallResolver {
  def resolveDynamicCallSite(callsite: StoredNode): Unit = {}
  def resolveDynamicMethodCallSites(method: StoredNode): Unit = {}
  def resolveDynamicMethodInstCallSites(methodInst: StoredNode): Unit = {}
}

package io.shiftleft.queryprimitives.steps

import io.shiftleft.codepropertygraph.generated.nodes

trait ICallResolver {
  def resolveDynamicCallSite(callsite: nodes.CallRef): Unit
  def resolveDynamicMethodCallSites(method: nodes.MethodRef): Unit
  def resolveDynamicMethodInstCallSites(methodInst: nodes.MethodInstRef): Unit
}

object NoResolve extends ICallResolver {
  def resolveDynamicCallSite(callsite: nodes.CallRef): Unit = {}

  def resolveDynamicMethodCallSites(method: nodes.MethodRef): Unit = {}
  def resolveDynamicMethodInstCallSites(methodInst: nodes.MethodInstRef): Unit = {}
}

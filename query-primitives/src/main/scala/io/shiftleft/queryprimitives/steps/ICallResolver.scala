package io.shiftleft.queryprimitives.steps

import io.shiftleft.codepropertygraph.generated.nodes

trait ICallResolver {
  def resolveDynamicCallSite(callsite: nodes.Call): Unit
  def resolveDynamicMethodCallSites(method: nodes.Method): Unit
  def resolveDynamicMethodInstCallSites(methodInst: nodes.MethodInst): Unit
}

object NoResolve extends ICallResolver {
  def resolveDynamicCallSite(callsite: nodes.Call): Unit = {}

  def resolveDynamicMethodCallSites(method: nodes.Method): Unit             = {}
  def resolveDynamicMethodInstCallSites(methodInst: nodes.MethodInst): Unit = {}
}

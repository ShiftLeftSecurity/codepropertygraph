package io.shiftleft.queryprimitives.steps

import gremlin.scala.Vertex

trait ICallResolver {
  def resolveDynamicCallSite(callsite: Vertex): Unit
  def resolveDynamicMethodCallSites(method: Vertex): Unit
  def resolveDynamicMethodInstCallSites(methodInst: Vertex): Unit
}

object NoResolve extends ICallResolver {
  def resolveDynamicCallSite(callsite: Vertex): Unit = {}
  def resolveDynamicMethodCallSites(method: Vertex): Unit = {}
  def resolveDynamicMethodInstCallSites(methodInst: Vertex): Unit = {}
}

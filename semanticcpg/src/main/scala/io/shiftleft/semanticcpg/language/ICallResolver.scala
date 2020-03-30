package io.shiftleft.semanticcpg.language

import gremlin.scala.GremlinScala
import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.codepropertygraph.generated.nodes.{CallRepr, Method}

import scala.collection.mutable
import scala.jdk.CollectionConverters._

trait ICallResolver {
  def resolveDynamicCallSite(callsite: nodes.Call): Unit
  def resolveDynamicMethodCallSites(method: nodes.Method): Unit
  def getResolvedCalledMethods(callsite: nodes.CallRepr): Iterable[nodes.Method]
  def getResolvedCallsites(method: nodes.Method): Iterable[nodes.CallRepr]

  def getCalledMethods(callsite: nodes.CallRepr): GremlinScala[nodes.Method] = {
    val combined = mutable.ArrayBuffer.empty[nodes.Method]
    callsite._callOut.asScala.foreach(method => combined.append(method.asInstanceOf[nodes.Method]))
    combined.appendAll(getResolvedCalledMethods(callsite))

    gremlin.scala.__(combined.toSeq: _*)
  }

  def getCallsites(method: nodes.Method): GremlinScala[nodes.CallRepr] = {
    val combined = mutable.ArrayBuffer.empty[nodes.CallRepr]
    method._callIn.asScala.foreach(call => combined.append(call.asInstanceOf[nodes.CallRepr]))
    combined.appendAll(getResolvedCallsites(method))

    gremlin.scala.__(combined.toSeq: _*)
  }
}

object NoResolve extends ICallResolver {
  def resolveDynamicCallSite(callsite: nodes.Call): Unit = {}

  def resolveDynamicMethodCallSites(method: nodes.Method): Unit = {}

  override def getResolvedCalledMethods(callsite: nodes.CallRepr): Iterable[Method] = {
    Iterable.empty
  }

  override def getResolvedCallsites(method: Method): Iterable[CallRepr] = {
    Iterable.empty
  }
}

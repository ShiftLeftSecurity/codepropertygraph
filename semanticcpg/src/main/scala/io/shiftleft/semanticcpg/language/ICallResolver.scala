package io.shiftleft.semanticcpg.language

import gremlin.scala.GremlinScala
import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.codepropertygraph.generated.nodes.{CallRepr, Method}

import scala.collection.mutable
import scala.jdk.CollectionConverters._

trait ICallResolver {

  /**
    * Get methods called at the given callsite.
    * This internally calls triggerCallsiteResolution.
    */
  def getCalledMethods(callsite: nodes.CallRepr): Iterable[nodes.Method] = {
    triggerCallsiteResolution(callsite)
    val combined = mutable.ArrayBuffer.empty[nodes.Method]
    callsite._callOut.asScala.foreach(method => combined.append(method.asInstanceOf[nodes.Method]))
    combined.appendAll(getResolvedCalledMethods(callsite))

    combined
  }

  /**
    * Same as getCalledMethods but with traversal return type.
    */
  def getCalledMethodsAsTraversal(callsite: nodes.CallRepr): GremlinScala[nodes.Method] = {
    val calledMethods = getCalledMethods(callsite)

    gremlin.scala.__(calledMethods.toSeq: _*)
  }

  /**
    * Get callsites of the given method.
    * This internally calls triggerMethodResolution.
    */
  def getMethodCallsites(method: nodes.Method): Iterable[nodes.CallRepr] = {
    triggerMethodCallsiteResolution(method)
    val combined = mutable.ArrayBuffer.empty[nodes.CallRepr]
    method._callIn.asScala.foreach(call => combined.append(call.asInstanceOf[nodes.CallRepr]))
    combined.appendAll(getResolvedMethodCallsites(method))

    combined
  }

  /**
    * Same as getMethodCallsites but with traversal return type.
    */
  def getMethodCallsitesAsTraversal(method: nodes.Method): GremlinScala[nodes.CallRepr] = {
    val methodCallsites = getMethodCallsites(method)

    gremlin.scala.__(methodCallsites.toSeq: _*)
  }

  /**
    * Starts data flow tracking to find all method which could be called at the given callsite.
    * The result is stored in the resolver internal cache.
    */
  def triggerCallsiteResolution(callsite: nodes.CallRepr): Unit

  /**
    * Starts data flow tracking to find all callsites which could call the given method.
    * The result is stored in the resolver internal cache.
    */
  def triggerMethodCallsiteResolution(method: nodes.Method): Unit

  /**
    * Retrieve results of triggerCallsiteResolution.
    */
  def getResolvedCalledMethods(callsite: nodes.CallRepr): Iterable[nodes.Method]

  /**
    * Retrieve results of triggerMethodResolution.
    */
  def getResolvedMethodCallsites(method: nodes.Method): Iterable[nodes.CallRepr]
}

object NoResolve extends ICallResolver {
  def triggerCallsiteResolution(callsite: nodes.CallRepr): Unit = {}

  def triggerMethodCallsiteResolution(method: nodes.Method): Unit = {}

  override def getResolvedCalledMethods(callsite: nodes.CallRepr): Iterable[Method] = {
    Iterable.empty
  }

  override def getResolvedMethodCallsites(method: Method): Iterable[CallRepr] = {
    Iterable.empty
  }
}

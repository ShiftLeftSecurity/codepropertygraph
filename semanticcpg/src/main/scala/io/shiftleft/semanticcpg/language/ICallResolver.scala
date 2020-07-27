package io.shiftleft.semanticcpg.language

import gremlin.scala.GremlinScala
import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.codepropertygraph.generated.nodes.{CallRepr, Method}
import overflowdb.traversal.Traversal

import scala.collection.mutable
import scala.jdk.CollectionConverters._

trait ICallResolver {

  def getUnresolvedMethodFullNames(callsite: nodes.CallRepr): Iterable[String] = {
    triggerCallsiteResolution(callsite)
    getUnresolvedMethodFullNamesInternal(callsite)
  }

  def getUnresolvedMethodFullNamesInternal(callsite: nodes.CallRepr): Iterable[String]

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
  def getCalledMethodsAsTraversal(callsite: nodes.CallRepr): Traversal[nodes.Method] =
    getCalledMethods(callsite).to(Traversal)

  /**
    * Get callsites of the given method.
    * This internally calls triggerMethodResolution.
    */
  def getMethodCallsites(method: nodes.Method): Iterable[nodes.CallRepr] = {
    triggerMethodCallsiteResolution(method)
    // The same call sites of a method can be found via static and dynamic lookup.
    // This is for example the case for Java virtual call sites which are statically assert
    // a certain method which could be overriden. If we are looking for the call sites of
    // such a statically asserted method, we find it twice and thus deduplicate here.
    val combined = mutable.LinkedHashSet.empty[nodes.CallRepr]
    method._callIn.asScala.foreach(call => combined.add(call.asInstanceOf[nodes.CallRepr]))
    combined.addAll(getResolvedMethodCallsites(method))

    combined.toBuffer
  }

  /**
    * Same as getMethodCallsites but with traversal return type.
    */
  def getMethodCallsitesAsTraversal(method: nodes.Method): Traversal[nodes.CallRepr] =
     getMethodCallsites(method).to(Traversal)

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

  override def getUnresolvedMethodFullNamesInternal(callsite: CallRepr): Iterable[String] = {
    Iterable.empty
  }
}

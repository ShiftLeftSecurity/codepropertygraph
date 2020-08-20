package io.shiftleft.dataflowengineoss.language

import gremlin.scala._
import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.dataflowengineoss.semanticsloader.Semantics
import io.shiftleft.semanticcpg.language._

import scala.jdk.CollectionConverters._

/**
  * Base class for nodes that can occur in data flows
  * */
class TrackingPoint(val wrapped: NodeSteps[nodes.TrackingPoint]) extends AnyVal {
  private def raw: GremlinScala[nodes.TrackingPoint] = wrapped.raw

  /**
    * The enclosing method of the tracking point
    * */
  def method: NodeSteps[nodes.Method] = wrapped.map(_.method)

  /**
    * Convert to nearest CFG node
    * */
  def cfgNode: NodeSteps[nodes.CfgNode] = wrapped.map(_.cfgNode)

  def reachableBy[NodeType <: nodes.TrackingPoint](sourceTravs: Steps[NodeType]*)(
      implicit semantics: Semantics): NodeSteps[NodeType] = {
    val reachedSources = reachableByInternal(sourceTravs).map(_.reachedSource)
    new NodeSteps[NodeType](__(reachedSources: _*).asInstanceOf[GremlinScala[NodeType]])
  }

  def reachableByFlows[A <: nodes.TrackingPoint](sourceTravs: NodeSteps[A]*)(
      implicit semantics: Semantics): Steps[Path] = {
    val paths = reachableByInternal(sourceTravs).map { result =>
      Path(result.path)
    }
    new Steps(__(paths: _*))
  }

  private def reachableByInternal[NodeType <: nodes.TrackingPoint](sourceTravs: Seq[Steps[NodeType]])(
      implicit semantics: Semantics): List[ReachableByResult] = {
    val sourceSymbols = sourceTravs
      .flatMap(_.raw.clone.toList)
      .collect { case n: nodes.TrackingPoint => n }
      .toSet

    // Recursive part of this function
    def traverseDdgBack(path: List[nodes.TrackingPoint]): List[ReachableByResult] = {
      val node = path.head

      val resultsForNode = Some(node)
        .filter(n => sourceSymbols.contains(n))
        .map { n =>
          new ReachableByResult(n, path)
        }
        .toList

      val resultsForParents = ddgIn(node)
        .filter(parent => !path.contains(parent))
        .flatMap(parent => traverseDdgBack(parent :: node :: path.tail))
        .toList
      resultsForParents ++ resultsForNode
    }

    val sinkSymbols = raw.clone.dedup.toList.sortBy { _.id.asInstanceOf[java.lang.Long] }
    sinkSymbols.flatMap(s => traverseDdgBack(List(s)))
  }

  private def ddgIn(node: nodes.TrackingPoint)(implicit semantics: Semantics): Iterator[nodes.TrackingPoint] = {
    node match {
      case n: nodes.Expression =>
        val inArgs = argToInArgsViaPropagate(n)
        val inCalls = Some(n).toList.collect {
          case call: nodes.Call =>
            callToInArgs(call)
        }.flatten
        (inArgs ++ inCalls ++ inReachingDefNonSiblings(n)).iterator
      case x =>
        inReachingDef(node)
    }

  }

  private def inReachingDef(node: nodes.StoredNode) =
    node._reachingDefIn().asScala.collect { case n: nodes.TrackingPoint => n }.toList.iterator

  private def inReachingDefNonSiblings(node: nodes.StoredNode) = {
    node match {
      case n: nodes.Expression =>
        val allArgsForCall = argToCall(n).toList.flatMap { call =>
          call._argumentOut().asScala.toList.collect { case t: nodes.TrackingPoint => t }
        }.toSet
        (inReachingDef(n).toSet -- allArgsForCall).iterator
      case _ =>
        inReachingDef(node)
    }
  }

  private def inReachingDefForSiblings(n: nodes.Expression) = {
    val allArgsForCall = argToCall(n).toList.flatMap { call =>
      call._argumentOut().asScala.toList.collect { case t: nodes.TrackingPoint => t }
    }.toSet
    (inReachingDef(n).toSet.intersect(allArgsForCall)).iterator
  }

  /**
    * At a given argument, determine arguments that influence
    * it, taking into account propagate edges (created by
    * annotation). This is achieved as follows:
    * From the argument, traverse to the corresponding
    * formal method out parameter and follow incoming propagate edges
    * to formal input parameters. From there, traverse to the
    * corresponding arguments. Note that in the CPG, we have dedicated
    * nodes for formal input and output parameters, but for arguments,
    * we only have one node for both states, that is, the tracker will
    * need to keep a record of whether it is in the in- or out-state.
    * */
  private def argToInArgsViaPropagate(n: nodes.Expression)(implicit semantics: Semantics): List[nodes.Expression] = {
    val parentCall = argToCall(n)
    val methods = argToCall(n).toList.flatMap(x => methodsForCall(x))
    val existingSemantics = methods.flatMap { method =>
      semantics.forMethod(method.fullName)
    }

    if (existingSemantics.isEmpty) {
      inReachingDefForSiblings(n).collect { case expr: nodes.Expression => expr }.toList
    } else {
      val indices = existingSemantics
        .flatMap { semantic =>
          semantic.mappings.collect { case (srcIndex, dstIndex) if dstIndex == n.order => srcIndex }
        }
      val inParams = methods.start.parameter.where(p => indices.contains(p.order)).l
      val orders = inParams.start.order.l
      parentCall.toList
        .flatMap { call =>
          orders.map(o => call.argument(o))
        }
        .collect { case t: nodes.TrackingPoint => t }
    }
  }

  /**
    * From the call node (which represents the actual return as well),
    * determine arguments that influence the return value, taking into
    * account propagate edges.
    * */
  private def callToInArgs(call: nodes.Call)(implicit semantics: Semantics): List[nodes.Expression] = {
    val existingSemantics = methodsForCall(call)
      .flatMap { method =>
        semantics.forMethod(method.fullName)
      }
    if (existingSemantics.isEmpty) {
      inReachingDefForSiblings(call).collect { case expr: nodes.Expression => expr }.toList
    } else {
      val indices = existingSemantics
        .flatMap { semantic =>
          semantic.mappings.collect { case (srcIndex, dstIndex) if dstIndex == -1 => srcIndex }
        }
      indices.map(i => call.argument(i)).distinct
    }
  }

  private def argToCall(n: nodes.Expression) =
    n._argumentIn.asScala.collectFirst { case c: nodes.Call => c }

  private def methodsForCall(call: nodes.Call): List[nodes.Method] = {
    NoResolve.getCalledMethods(call).toList
  }

}

private class ReachableByResult(val reachedSource: nodes.TrackingPoint, val path: List[nodes.TrackingPoint]) {
  override def clone(): ReachableByResult = {
    new ReachableByResult(reachedSource, path)
  }
}

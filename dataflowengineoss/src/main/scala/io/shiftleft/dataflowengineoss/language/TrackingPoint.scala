package io.shiftleft.dataflowengineoss.language

import gremlin.scala._
import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.dataflowengineoss.queryengine.{Engine, ReachableByResult}
import io.shiftleft.dataflowengineoss.semanticsloader.Semantics
import io.shiftleft.semanticcpg.language._

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
    val reachedSources = reachableByInternal(sourceTravs).map(_.source)
    new NodeSteps[NodeType](__(reachedSources: _*).asInstanceOf[GremlinScala[NodeType]])
  }

  def reachableByFlows[A <: nodes.TrackingPoint](sourceTravs: NodeSteps[A]*)(
      implicit semantics: Semantics): Steps[Path] = {
    val paths = reachableByInternal(sourceTravs).map { result =>
      Path(result.path.filter(_.visible == true).map(_.node))
    }
    new Steps(__(paths: _*))
  }

  private def reachableByInternal[NodeType <: nodes.TrackingPoint](sourceTravs: Seq[Steps[NodeType]])(
      implicit semantics: Semantics): List[ReachableByResult] = {

    val sources: List[nodes.TrackingPoint] = sourceTravs
      .flatMap(_.raw.clone.toList)
      .collect { case n: nodes.TrackingPoint => n }
      .toList

    val sinks = raw.clone.dedup.toList.sortBy(_.id2)

    new Engine().determineFlowsBackwards(sinks, sources)
  }

}

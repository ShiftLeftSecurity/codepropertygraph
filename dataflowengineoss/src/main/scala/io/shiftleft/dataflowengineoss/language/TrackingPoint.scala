package io.shiftleft.dataflowengineoss.language

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.dataflowengineoss.queryengine.{Engine, EngineContext, ReachableByResult}
import io.shiftleft.semanticcpg.language._
import overflowdb.traversal._

/**
  * Base class for nodes that can occur in data flows
  * */
class TrackingPoint(val traversal: Traversal[nodes.TrackingPoint]) extends AnyVal {

  /**
    * The enclosing method of the tracking point
    * */
  def method: Traversal[nodes.Method] =
    traversal.map(_.method)

  /**
    * Convert to nearest CFG node
    * */
  def cfgNode: Traversal[nodes.CfgNode] =
    traversal.map(_.cfgNode)

  def reachableBy[NodeType <: nodes.TrackingPoint](sourceTravs: Traversal[NodeType]*)(
      implicit context: EngineContext): Traversal[NodeType] = {
    val reachedSources = reachableByInternal(sourceTravs).map(_.source)
    Traversal.from(reachedSources).cast[NodeType]
  }

  def reachableByFlows[A <: nodes.TrackingPoint](sourceTravs: Traversal[A]*)(
      implicit context: EngineContext): Traversal[Path] = {
    val paths = reachableByInternal(sourceTravs).map { result =>
      Path(removeConsecutiveDuplicates(result.path.filter(_.visible == true).map(_.node)))
    }.dedup
    paths.to(Traversal)
  }

  private def removeConsecutiveDuplicates[T](l: List[T]): List[T] = {
    l.headOption.map(x => x :: l.sliding(2).collect { case Seq(a, b) if a != b => b }.toList).getOrElse(List())
  }

  private def reachableByInternal[NodeType <: nodes.TrackingPoint](sourceTravs: Seq[Traversal[NodeType]])(
      implicit context: EngineContext): List[ReachableByResult] = {
    val sources: List[nodes.TrackingPoint] =
      sourceTravs
        .flatMap(_.toList)
        .collect { case n: nodes.TrackingPoint => n }
        .toList

    val sinks = traversal.dedup.toList.sortBy(_.id)

    new Engine(context).backwards(sinks, sources)
  }

}

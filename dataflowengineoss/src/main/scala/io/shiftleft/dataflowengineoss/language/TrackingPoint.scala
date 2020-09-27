package io.shiftleft.dataflowengineoss.language

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.dataflowengineoss.queryengine.{Engine, EngineContext, PathElement, ReachableByResult}
import io.shiftleft.dataflowengineoss.semanticsloader.Semantics
import io.shiftleft.semanticcpg.language._
import overflowdb.traversal._

import scala.collection.mutable

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

  def ddgIn(implicit semantics: Semantics): Traversal[nodes.TrackingPoint] = {
    val cache = mutable.HashMap[nodes.TrackingPoint, List[PathElement]]()
    val result = traversal.flatMap(x => x.ddgIn(List(PathElement(x)), withInvisible = false, cache))
    cache.clear
    result
  }

  def ddgInPathElem(implicit semantics: Semantics): Traversal[PathElement] = {
    val cache = mutable.HashMap[nodes.TrackingPoint, List[PathElement]]()
    val result = traversal.flatMap(x => x.ddgInPathElem(List(PathElement(x)), withInvisible = false, cache))
    cache.clear
    result
  }

  def reachableBy[NodeType <: nodes.TrackingPoint](sourceTravs: Traversal[NodeType]*)(
      implicit context: EngineContext): Traversal[NodeType] = {
    val reachedSources = reachableByInternal(sourceTravs).map(_.source)
    Traversal.from(reachedSources).cast[NodeType]
  }

  def reachableByFlows[A <: nodes.TrackingPoint](sourceTravs: Traversal[A]*)(
      implicit context: EngineContext): Traversal[Path] = {
    val paths = reachableByInternal(sourceTravs)
      .map { result =>
        // We can get back results that start in nodes that are invisible
        // according to the semantic, e.g., arguments that are only used
        // but not defined. We filter these results here prior to returning
        val first = result.path.headOption
        if (first.isDefined && !first.get.visible) {
          None
        } else {
          val visiblePathElements = result.path.filter(_.visible)
          Some(Path(removeConsecutiveDuplicates(visiblePathElements.map(_.node))))
        }
      }
      .filter(_.isDefined)
      .dedup
      .flatten
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

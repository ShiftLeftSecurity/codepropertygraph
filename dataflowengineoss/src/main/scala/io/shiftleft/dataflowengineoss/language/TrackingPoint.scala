package io.shiftleft.dataflowengineoss.language

import gremlin.scala._
import io.shiftleft.codepropertygraph.generated.nodes
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

  def reachableBy[NodeType <: nodes.TrackingPoint](sourceTravs: Steps[NodeType]*): NodeSteps[NodeType] = {
    val reachedSources = reachableByInternal(sourceTravs).map(_.reachedSource)
    new NodeSteps[NodeType](__(reachedSources: _*).asInstanceOf[GremlinScala[NodeType]])
  }

  def reachableByFlows[A <: nodes.TrackingPoint](sourceTravs: NodeSteps[A]*): Steps[Path] = {
    val paths = reachableByInternal(sourceTravs).map { result =>
      Path(result.path)
    }
    new Steps(__(paths: _*))
  }

  private def reachableByInternal[NodeType <: nodes.TrackingPoint](
      sourceTravs: Seq[Steps[NodeType]]): List[ReachableByResult] = {
    val sourceSymbols = sourceTravs
      .flatMap(_.raw.clone.toList)
      .map(_.asInstanceOf[nodes.TrackingPoint])
      .toSet

    val sinkSymbols = raw.clone.dedup.toList.sortBy { _.id.asInstanceOf[java.lang.Long] }

    def traverseDdgBack(path: List[nodes.TrackingPoint]): List[ReachableByResult] = {
      val node = path.head
      val resultsForNode = if (sourceSymbols.contains(node)) {
        List(new ReachableByResult(node, path))
      } else {
        List[ReachableByResult]()
      }

      val resultsForParents = incomingDdgNodes(node)
        .filter(x => !path.contains(x))
        .flatMap { p =>
          traverseDdgBack(p :: node :: path.tail)
        }
        .toList
      resultsForParents ++ resultsForNode
    }

    sinkSymbols.flatMap(s => traverseDdgBack(List(s)))
  }

  private def incomingDdgNodes(node: nodes.TrackingPoint): Iterator[nodes.TrackingPoint] = {
    node
      ._reachingDefIn()
      .asScala
      .filter(_.isInstanceOf[nodes.TrackingPoint])
      .map(_.asInstanceOf[nodes.TrackingPoint])
  }

}

private class ReachableByResult(val reachedSource: nodes.TrackingPoint, val path: List[nodes.TrackingPoint]) {
  override def clone(): ReachableByResult = {
    new ReachableByResult(reachedSource, path)
  }
}

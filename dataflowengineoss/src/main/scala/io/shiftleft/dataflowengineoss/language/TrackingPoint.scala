package io.shiftleft.dataflowengineoss.language

import io.shiftleft.Implicits.JavaIteratorDeco
import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.semanticcpg.language._
import io.shiftleft.semanticcpg.utils.MemberAccess
import overflowdb.traversal._

import scala.jdk.CollectionConverters._

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

  def reachableBy[NodeType <: nodes.TrackingPoint](sourceTravs: Traversal[NodeType]*): Traversal[NodeType] = {
    val pathReachables = reachableByInternal(sourceTravs)
    val reachedSources = pathReachables.map(_.reachedSource)
    Traversal.from(reachedSources).cast[NodeType]
  }

  def reachableByFlows[A <: nodes.TrackingPoint](sourceTravs: Traversal[A]*): Traversal[Path] = {
    val pathReachables = reachableByInternal(sourceTravs)
    val paths = pathReachables.map { reachableByContainer =>
      Path(reachableByContainer.path)
    }
    paths.to(Traversal)
  }

  private def reachableByInternal[NodeType <: nodes.TrackingPoint](
      sourceTravs: Seq[Traversal[NodeType]]): List[ReachableByContainer] = {
    val sourceSymbols = sourceTravs
      .flatMap(_.toList)
      .flatMap { elem =>
        getTrackingPoint(elem.asInstanceOf[nodes.TrackingPoint])
      }
      .toSet

    val sinkSymbols = traversal.dedup.toList.sortBy(_.id2)

    var pathReachables = List.empty[ReachableByContainer]

    def traverseDDGBack(path: List[nodes.TrackingPoint]): Unit = {
      val node = path.head
      if (sourceSymbols.contains(node)) {
        val sack = new ReachableByContainer(node, path)
        pathReachables = sack :: pathReachables
      }

      for {
        ddgPredecessor <- node._reachingDefIn.asScala
        predTrackingPoint <- getTrackingPoint(ddgPredecessor)
        if !path.contains(predTrackingPoint)
      } traverseDDGBack(predTrackingPoint :: node :: path.tail)
    }

    sinkSymbols.map(getTrackingPoint).foreach {
      case Some(trackingPoing) => traverseDDGBack(List(trackingPoing))
      case None                =>
    }

    pathReachables
  }

  private def getTrackingPoint(node: nodes.StoredNode): Option[nodes.TrackingPoint] =
    node match {
      case identifier: nodes.Identifier           => Some(identifier)
      case call: nodes.Call                       => Some(call)
      case ret: nodes.Return                      => Some(ret)
      case methodReturn: nodes.MethodReturn       => Some(methodReturn)
      case methodParamIn: nodes.MethodParameterIn => Some(methodParamIn)
      case literal: nodes.Literal                 => getTrackingPoint(literal._argumentIn.onlyChecked)
      case _                                      => None
    }

}

private class ReachableByContainer(val reachedSource: nodes.TrackingPoint, val path: List[nodes.TrackingPoint]) {
  override def clone(): ReachableByContainer = {
    new ReachableByContainer(reachedSource, path)
  }
}

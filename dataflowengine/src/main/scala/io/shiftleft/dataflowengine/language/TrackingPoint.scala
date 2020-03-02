package io.shiftleft.dataflowengine.language

import gremlin.scala._
import io.shiftleft.codepropertygraph.generated._
import io.shiftleft.semanticcpg.language.{NodeSteps, Steps}
import io.shiftleft.Implicits.JavaIteratorDeco
import io.shiftleft.semanticcpg.utils.{ExpandTo, MemberAccess}
import org.apache.tinkerpop.gremlin.structure.Vertex
import scala.jdk.CollectionConverters._

/**
  * Base class for nodes that can occur in data flows
  * */
class TrackingPoint(val wrapped: NodeSteps[nodes.TrackingPoint]) extends AnyVal {
  private def raw: GremlinScala[nodes.TrackingPoint] = wrapped.raw

  /**
    * The enclosing method of the tracking point
    * */
  def method: NodeSteps[nodes.Method] = wrapped.map(methodFast)

  /**
    * Convert to nearest CFG node
    * */
  def cfgNode: NodeSteps[nodes.CfgNode] = wrapped.map(_.cfgNode)

  def reachableBy[NodeType <: nodes.TrackingPoint](sourceTravs: Steps[NodeType]*): NodeSteps[NodeType] = {
    val pathReachables = reachableByInternal(sourceTravs)
    val reachedSources = pathReachables.map(_.reachedSource)
    new NodeSteps[NodeType](__(reachedSources: _*).asInstanceOf[GremlinScala[NodeType]])
  }

  def reachableByFlows[A <: nodes.TrackingPoint](sourceTravs: NodeSteps[A]*): Flows = {
    val pathReachables = reachableByInternal(sourceTravs)
    val paths = pathReachables.map(_.path)
    new Flows(new Steps[List[nodes.TrackingPoint]](__(paths: _*)))
  }

  private def reachableByInternal[NodeType <: nodes.TrackingPoint](
      sourceTravs: Seq[Steps[NodeType]]): List[ReachableByContainer] = {
    val sourceSymbols = sourceTravs
      .flatMap(_.raw.clone.toList)
      .flatMap { elem =>
        getTrackingPoint(elem.asInstanceOf[nodes.TrackingPoint])
      }
      .toSet

    val sinkSymbols = raw.clone.dedup.toList.sortBy { _.id.asInstanceOf[java.lang.Long] }

    var pathReachables = List.empty[ReachableByContainer]

    def traverseDDGBack(path: List[nodes.TrackingPoint]): Unit = {
      val node = path.head
      if (!indirectAccess(node) && sourceSymbols.contains(node)) {
        val sack = new ReachableByContainer(node, path)
        pathReachables = sack :: pathReachables
      }

      for {
        ddgPredecessor <- node._reachingDefIn.asScala
        predTrackingPoint <- getTrackingPoint(ddgPredecessor)
        if !path.contains(predTrackingPoint)
      } if (indirectAccess(node)) traverseDDGBack(predTrackingPoint :: path.tail)
      else traverseDDGBack(predTrackingPoint :: node :: path.tail)
    }

    sinkSymbols.map(getTrackingPoint).foreach {
      case Some(trackingPoing) => traverseDDGBack(List(trackingPoing))
      case None                =>
    }

    pathReachables
  }

  private def getTrackingPoint(node: nodes.StoredNode): Option[nodes.TrackingPoint] =
    node match {
      case identifier: nodes.Identifier =>
        getTrackingPoint(identifier._argumentIn().nextChecked)
      case call: nodes.Call                       => Some(call)
      case ret: nodes.Return                      => Some(ret)
      case methodReturn: nodes.MethodReturn       => Some(methodReturn)
      case methodParamIn: nodes.MethodParameterIn => Some(methodParamIn)
      case literal: nodes.Literal                 => getTrackingPoint(literal._argumentIn().nextChecked)
      case _                                      => None
    }

  private def methodFast(dataFlowObject: Vertex): nodes.Method = {
    val method =
      dataFlowObject.label match {
        case NodeTypes.METHOD_RETURN =>
          ExpandTo.methodReturnToMethod(dataFlowObject)
        case NodeTypes.METHOD_PARAMETER_IN =>
          ExpandTo.parameterInToMethod(dataFlowObject)
        case NodeTypes.METHOD_PARAMETER_OUT =>
          ExpandTo.parameterInToMethod(dataFlowObject)
        case NodeTypes.LITERAL | NodeTypes.CALL | NodeTypes.IDENTIFIER | NodeTypes.RETURN | NodeTypes.UNKNOWN =>
          ExpandTo.expressionToMethod(dataFlowObject.asInstanceOf[nodes.Expression])
      }
    method.asInstanceOf[nodes.Method]
  }

  private def indirectAccess(node: nodes.StoredNode): Boolean =
    node match {
      case call: nodes.Call =>
        val callName = call.value2(NodeKeys.NAME)
        MemberAccess.isGenericMemberAccessName(callName)
      case _ => false
    }

}

private class ReachableByContainer(val reachedSource: nodes.TrackingPoint, val path: List[nodes.TrackingPoint]) {
  override def clone(): ReachableByContainer = {
    new ReachableByContainer(reachedSource, path)
  }
}

package io.shiftleft.dataflowengine.language

import gremlin.scala._
import io.shiftleft.codepropertygraph.generated._
import io.shiftleft.semanticcpg.language.{NodeSteps, Steps}
import io.shiftleft.semanticcpg.language.types.structure.Method
import io.shiftleft.Implicits.JavaIteratorDeco
import io.shiftleft.semanticcpg.utils.{ExpandTo, MemberAccess}
import org.apache.tinkerpop.gremlin.structure.Direction

import scala.collection.JavaConverters._

/**
  * Base class for nodes that can occur in data flows
  * */
class TrackingPoint(raw: GremlinScala[nodes.TrackingPoint]) extends NodeSteps[nodes.TrackingPoint](raw) {

  private class ReachableByContainer(val reachedSource: nodes.TrackingPoint, val path: List[nodes.TrackingPoint]) {
    override def clone(): ReachableByContainer = {
      new ReachableByContainer(reachedSource, path)
    }
  }

  /**
    * The enclosing method of the tracking point
    * */
  def method: Method = {
    new Method(
      raw.map { dataFlowObject =>
        methodFast(dataFlowObject)
      }
    )
  }

  def reachableBy(sourceTravs: NodeSteps[nodes.TrackingPoint]*): TrackingPoint = {
    val pathReachables = reachableByInternal(sourceTravs)
    val reachedSources = pathReachables.map(_.reachedSource)
    new TrackingPoint(graph.asScala().inject(reachedSources: _*).asInstanceOf[GremlinScala[nodes.TrackingPoint]])
  }

  def reachableByFlows(sourceTravs: NodeSteps[nodes.TrackingPoint]*): Flows = {

    val pathReachables = reachableByInternal(sourceTravs)
    val paths = pathReachables.map(_.path)
    new Flows(
      new Steps[List[nodes.TrackingPoint]](
        graph.asScala().inject(paths: _*).asInstanceOf[GremlinScala[List[nodes.TrackingPoint]]]))
  }

  private def reachableByInternal(sourceTravs: Seq[NodeSteps[nodes.TrackingPoint]]): List[ReachableByContainer] = {
    val sourceSymbols = sourceTravs
      .flatMap(_.raw.clone.toList)
      .flatMap { elem =>
        getTrackingPoint(elem)
      }
      .toSet

    val sinkSymbols = raw.clone.dedup().toList.sortBy { _.id.asInstanceOf[java.lang.Long] }

    var pathReachables = List[ReachableByContainer]()

    def traverseDDGBack(path: List[nodes.TrackingPoint]): Unit = {
      val node = path.head
      if (!indirectAccess(node) && sourceSymbols.contains(node)) {
        val sack = new ReachableByContainer(node, path)
        pathReachables = sack :: pathReachables
      }

      val ddgPredecessors = node.vertices(Direction.IN, EdgeTypes.REACHING_DEF).asScala
      ddgPredecessors.foreach { pred =>
        getTrackingPoint(pred) match {
          case Some(predTrackingPoint) =>
            if (!path.contains(predTrackingPoint)) {
              if (indirectAccess(node)) {
                traverseDDGBack(predTrackingPoint :: path.tail)
              } else {
                traverseDDGBack(predTrackingPoint :: node :: path.tail)
              }
            }
          case None =>
        }
      }
    }

    sinkSymbols.foreach { sym =>
      getTrackingPoint(sym) match {
        case Some(vertex) => traverseDDGBack(List(vertex))
        case None         =>
      }
    }
    pathReachables
  }

  private def getTrackingPoint(vertex: Vertex): Option[nodes.TrackingPoint] = {
    vertex match {
      case identifier: nodes.Identifier =>
        getTrackingPoint(identifier.vertices(Direction.IN, EdgeTypes.AST).nextChecked)
      case call: nodes.Call                       => Some(call)
      case ret: nodes.Return                      => Some(ret)
      case methodReturn: nodes.MethodReturn       => Some(methodReturn)
      case methodParamIn: nodes.MethodParameterIn => Some(methodParamIn)
      case literal: nodes.Literal                 => getTrackingPoint(literal.vertices(Direction.IN, EdgeTypes.AST).nextChecked)
      case _                                      => None
    }
  }

  private def methodFast(dataFlowObject: nodes.TrackingPoint): nodes.Method = {
    val method =
      dataFlowObject.label match {
        case NodeTypes.METHOD_RETURN =>
          ExpandTo.methodReturnToMethod(dataFlowObject)
        case NodeTypes.METHOD_PARAMETER_IN =>
          ExpandTo.parameterInToMethod(dataFlowObject)
        case NodeTypes.METHOD_PARAMETER_OUT =>
          ExpandTo.parameterInToMethod(dataFlowObject)
        case NodeTypes.LITERAL | NodeTypes.CALL | NodeTypes.IDENTIFIER | NodeTypes.RETURN | NodeTypes.UNKNOWN =>
          ExpandTo.expressionToMethod(dataFlowObject)
      }
    method.asInstanceOf[nodes.Method]
  }

  private def indirectAccess(vertex: Vertex): Boolean = {
    if (!vertex.isInstanceOf[nodes.Call]) {
      return false
    }

    val callName = vertex.value2(NodeKeys.NAME)
    MemberAccess.isGenericMemberAccessName(callName)
  }

}

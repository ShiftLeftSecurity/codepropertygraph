package io.shiftleft.passes.dataflows.steps

import gremlin.scala._
import io.shiftleft.codepropertygraph.generated._
import io.shiftleft.queryprimitives.steps.CpgSteps
import org.apache.logging.log4j.LogManager
import shapeless.HList
import DataFlowObject._
import gremlin.scala.dsl.Steps
import io.shiftleft.passes.reachingdef.DataFlowFrameworkHelper
import io.shiftleft.queryprimitives.steps.types.structure.Method
import io.shiftleft.queryprimitives.utils.ExpandTo
import org.apache.tinkerpop.gremlin.structure.Direction

import scala.collection.JavaConverters._

object DataFlowObject {
  protected val logger = LogManager.getLogger(getClass)
  
  implicit val marshaller: Marshallable[nodes.DataFlowObject] = new Marshallable[nodes.DataFlowObject] {

    /* TODO MP: generate in DomainClassCreator */
    override def toCC(element: Element): nodes.DataFlowObject =
      element.label match {
        case NodeTypes.IDENTIFIER => implicitly[Marshallable[nodes.Identifier]].toCC(element)
        case NodeTypes.LITERAL => implicitly[Marshallable[nodes.Literal]].toCC(element)
        case NodeTypes.METHOD_PARAMETER_IN => implicitly[Marshallable[nodes.MethodParameterIn]].toCC(element)
        case NodeTypes.METHOD_PARAMETER_OUT => implicitly[Marshallable[nodes.MethodParameterOut]].toCC(element)
        case NodeTypes.METHOD_RETURN => implicitly[Marshallable[nodes.MethodReturn]].toCC(element)

        // Expressions are DataFlowObjects
        case NodeTypes.BLOCK => implicitly[Marshallable[nodes.Block]].toCC(element)
        case NodeTypes.CALL => implicitly[Marshallable[nodes.Call]].toCC(element)
        case NodeTypes.METHOD_REF => implicitly[Marshallable[nodes.MethodRef]].toCC(element)
        case NodeTypes.RETURN => implicitly[Marshallable[nodes.Return]].toCC(element)
        case NodeTypes.UNKNOWN => implicitly[Marshallable[nodes.Unknown]].toCC(element)
      }

    // not really needed AFAIK
    override def fromCC(cc: nodes.DataFlowObject) = ???
  }
}

/**
  * Base class for nodes that can occur in data flows
  * */
class DataFlowObject[Labels <: HList](raw: GremlinScala[Vertex])
    extends CpgSteps[nodes.DataFlowObject, Labels](raw) {

  private class ReachableByContainer(val reachedSource: Vertex, val path: List[Vertex]) {
    override def clone(): ReachableByContainer = {
      new ReachableByContainer(reachedSource, path)
    }
  }

  def method: Method[Labels] = {
    new Method[Labels](
      raw.map { dataFlowObject =>
        methodFast(dataFlowObject)
      }
    )
  }

  def reachableBy(sourceTravs: CpgSteps[nodes.DataFlowObject, _]*): DataFlowObject[Labels] = {
    val pathReachables = reachableByInternal(sourceTravs)
    val reachedSources = pathReachables.map(_.reachedSource)
    new DataFlowObject(graph.asScala().inject(reachedSources:_*))
  }

  def reachableByFlows(sourceTravs: CpgSteps[nodes.DataFlowObject, _]*)
  : Flows = {

    val pathReachables = reachableByInternal(sourceTravs)
    val paths = pathReachables.map(_.path)
    new Flows(new Steps[List[nodes.DataFlowObject], List[Vertex], Labels](
      graph.asScala().inject[List[Vertex]](paths: _*)))
  }

  private def reachableByInternal(sourceTravs: Seq[CpgSteps[nodes.DataFlowObject, _]])
  : List[ReachableByContainer] = {
    val sourceSymbols = sourceTravs.flatMap(_.raw.clone.toList)
      .flatMap { elem => getOperation(elem) }
      .toSet

    val sinkSymbols   = raw.clone.dedup().toList.sortBy { _.id.asInstanceOf[java.lang.Long] }

    var pathReachables = List[ReachableByContainer]()

    def traverseDDGBack(path: List[Vertex]): Unit = {
      val node = path.head
      if(sourceSymbols.contains(node)) {
        val sack = new ReachableByContainer(node, path)
        pathReachables = sack :: pathReachables
      }

      val ddgPredecessors = node.vertices(Direction.IN, EdgeTypes.REACHING_DEF).asScala
      ddgPredecessors.foreach { pred =>
        traverseDDGBack(pred :: node :: path.tail)
      }
    }

    sinkSymbols.foreach { sym =>
      getOperation(sym) match {
        case Some(vertex) => traverseDDGBack(List(vertex))
        case None =>
      }
    }
    pathReachables
  }

  private def getOperation(vertex: Vertex): Option[Vertex] = {
    vertex.label match {
      case NodeTypes.IDENTIFIER => getOperation(vertex.vertices(Direction.IN, EdgeTypes.AST).next)
      case NodeTypes.CALL => Some(vertex)
      case NodeTypes.RETURN => Some(vertex)
      case _ => None
    }
  }

  private def methodFast(dataFlowObject: Vertex): Vertex = {
    dataFlowObject.label match {
      case NodeTypes.METHOD_RETURN =>
        ExpandTo.formalReturnToMethod(dataFlowObject)
      case NodeTypes.METHOD_PARAMETER_IN =>
        ExpandTo.parameterToMethod(dataFlowObject)
      case NodeTypes.METHOD_PARAMETER_OUT =>
        ExpandTo.parameterToMethod(dataFlowObject)
      case NodeTypes.LITERAL |
           NodeTypes.CALL |
           NodeTypes.IDENTIFIER |
           NodeTypes.RETURN |
           NodeTypes.UNKNOWN =>
        ExpandTo.expressionToMethod(dataFlowObject)
    }
  }

}

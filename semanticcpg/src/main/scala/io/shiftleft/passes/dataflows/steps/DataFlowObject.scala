package io.shiftleft.passes.dataflows.steps

import gremlin.scala._
import io.shiftleft.codepropertygraph.generated._
import io.shiftleft.queryprimitives.steps.{NodeSteps, Steps}
import org.apache.logging.log4j.LogManager
import shapeless.HList
import DataFlowObject._
import io.shiftleft.queryprimitives.steps.types.structure.Method
import io.shiftleft.queryprimitives.utils.ExpandTo
import org.apache.tinkerpop.gremlin.structure.Direction
import scala.collection.JavaConverters._
import shapeless.HNil

object DataFlowObject {
  protected val logger = LogManager.getLogger(getClass)

  implicit val marshaller: Marshallable[nodes.DataFlowObject] = new Marshallable[nodes.DataFlowObject] {

    /* TODO MP: generate in DomainClassCreator */
    override def toCC(element: Element): nodes.DataFlowObject =
      element.label match {
        case NodeTypes.IDENTIFIER           => implicitly[Marshallable[nodes.Identifier]].toCC(element)
        case NodeTypes.LITERAL              => implicitly[Marshallable[nodes.Literal]].toCC(element)
        case NodeTypes.METHOD_PARAMETER_IN  => implicitly[Marshallable[nodes.MethodParameterIn]].toCC(element)
        case NodeTypes.METHOD_PARAMETER_OUT => implicitly[Marshallable[nodes.MethodParameterOut]].toCC(element)
        case NodeTypes.METHOD_RETURN        => implicitly[Marshallable[nodes.MethodReturn]].toCC(element)

        // Expressions are DataFlowObjects
        case NodeTypes.BLOCK      => implicitly[Marshallable[nodes.Block]].toCC(element)
        case NodeTypes.CALL       => implicitly[Marshallable[nodes.Call]].toCC(element)
        case NodeTypes.METHOD_REF => implicitly[Marshallable[nodes.MethodRef]].toCC(element)
        case NodeTypes.RETURN     => implicitly[Marshallable[nodes.Return]].toCC(element)
        case NodeTypes.UNKNOWN    => implicitly[Marshallable[nodes.Unknown]].toCC(element)
      }

    // not really needed AFAIK
    override def fromCC(cc: nodes.DataFlowObject) = ???
  }
}

/**
  * Base class for nodes that can occur in data flows
  * */
class DataFlowObject[Labels <: HList](raw: GremlinScala.Aux[nodes.DataFlowObject, Labels])
    extends NodeSteps(raw) {

  private class ReachableByContainer(
    val reachedSource: nodes.DataFlowObject,
    val path: List[nodes.DataFlowObject]) {
    override def clone(): ReachableByContainer = {
      new ReachableByContainer(reachedSource, path)
    }
  }

  def method: Method[Labels] = {
    new Method[Labels](raw.map(methodFast))
  }

  def reachableBy(sourceTravs: NodeSteps[nodes.DataFlowObject, _]*): DataFlowObject[_] = {
    val pathReachables = reachableByInternal(sourceTravs)
    val reachedSources = pathReachables.map(_.reachedSource)
    new DataFlowObject(graph.asScala.inject(reachedSources: _*))
  }

  def reachableByFlows(sourceTravs: NodeSteps[nodes.DataFlowObject, _]*): Flows = {

    val pathReachables = reachableByInternal(sourceTravs)
    val paths = pathReachables.map(_.path)
    new Flows(
      new Steps[List[nodes.DataFlowObject], HNil](graph.asScala.inject[List[nodes.DataFlowObject]](paths: _*)))
  }

  private def reachableByInternal(sourceTravs: Seq[NodeSteps[nodes.DataFlowObject, _]]): List[ReachableByContainer] = {
    val sourceSymbols = sourceTravs
      .flatMap(_.raw.clone.toList)
      .flatMap { elem =>
        getOperation(elem)
      }
      .toSet

    val sinkSymbols = raw.clone.dedup().toList.sortBy { _.id.asInstanceOf[java.lang.Long] }

    var pathReachables = List[ReachableByContainer]()

    def traverseDDGBack(path: List[nodes.DataFlowObject]): Unit = {
      val node = path.head
      if (sourceSymbols.contains(node)) {
        val sack = new ReachableByContainer(node, path)
        pathReachables = sack :: pathReachables
      }

      val ddgPredecessors = node.vertices(Direction.IN, EdgeTypes.REACHING_DEF).asScala
      ddgPredecessors.foreach { pred =>
        if(!path.contains(pred)) {
          traverseDDGBack(pred.asInstanceOf[nodes.DataFlowObject] :: node :: path.tail)
        }
      }
    }

    sinkSymbols.foreach { sym =>
      getOperation(sym) match {
        case Some(vertex) => traverseDDGBack(List(vertex.asInstanceOf[nodes.DataFlowObject]))
        case None         =>
      }
    }
    pathReachables
  }

  private def getOperation(vertex: Vertex): Option[Vertex] = {
    vertex.label match {
      case NodeTypes.IDENTIFIER          => getOperation(vertex.vertices(Direction.IN, EdgeTypes.AST).next)
      case NodeTypes.CALL                => Some(vertex)
      case NodeTypes.RETURN              => Some(vertex)
      case NodeTypes.METHOD_RETURN       => Some(vertex)
      case _                             => None
    }
  }

  private def methodFast(dataFlowObject: nodes.DataFlowObject): nodes.Method = {
    dataFlowObject.label match {
      case NodeTypes.METHOD_RETURN =>
        ExpandTo.formalReturnToMethod(dataFlowObject).asInstanceOf[nodes.Method]
      case NodeTypes.METHOD_PARAMETER_IN =>
        ExpandTo.parameterToMethod(dataFlowObject).asInstanceOf[nodes.Method]
      case NodeTypes.METHOD_PARAMETER_OUT =>
        ExpandTo.parameterToMethod(dataFlowObject).asInstanceOf[nodes.Method]
      case NodeTypes.LITERAL | NodeTypes.CALL | NodeTypes.IDENTIFIER | NodeTypes.RETURN | NodeTypes.UNKNOWN =>
        ExpandTo.expressionToMethod(dataFlowObject).asInstanceOf[nodes.Method]
    }
  }

}

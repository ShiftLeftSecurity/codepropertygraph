package io.shiftleft.cpgenhancements.generic.calldecorator

import gremlin.scala._
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeKeys, NodeTypes}
import io.shiftleft.cpgenhancements.CpgEnhancement
import io.shiftleft.diffgraph.DiffGraph
import org.apache.tinkerpop.gremlin.structure.Direction
import scala.collection.JavaConverters._

/**
  * Create CALL_ARG, CALL_ARG_OUT and CALL_RET edges for each call site.
  */
class CallDecorator(graph: ScalaGraph) extends CpgEnhancement(graph) {

  override def run(): Unit = {
    graph.V
      .hasLabel(NodeTypes.CALL)
      .sideEffect { callsite =>
        try {
          val perCallsiteDstGraph = new DiffGraph()
          val sortedArguments = callsite
            .vertices(Direction.OUT, EdgeTypes.AST)
            .asScala
            .toSeq
            .sortBy(_.value2(NodeKeys.ARGUMENT_INDEX))

          callsite.vertices(Direction.OUT, EdgeTypes.CALL).asScala.foreach { methodInstance =>
            methodInstance.vertices(Direction.OUT, EdgeTypes.REF).asScala.foreach { method =>
              generateCallArgEdges(perCallsiteDstGraph, method, sortedArguments)
              generateCallArgOutEdges(perCallsiteDstGraph, method, sortedArguments)
              generateCallRetEdge(perCallsiteDstGraph, method, callsite)
            }
          }
          dstGraph.mergeFrom(perCallsiteDstGraph)
        } catch {
          case exception: Exception =>
            logger.warn(s"Unable to decorate callsite: ${callsite.value2(NodeKeys.CODE)}")
        }
      }
      .iterate
  }

  private def generateCallArgEdges(perCallsiteDstGraph: DiffGraph,
                                   method: Vertex,
                                   sortedArguments: Seq[Vertex]): Unit = {
    val sortedParameters = method
      .vertices(Direction.OUT, EdgeTypes.AST)
      .asScala
      .filter(_.label == NodeTypes.METHOD_PARAMETER_IN)
      .toSeq
      .sortBy(_.value2(NodeKeys.ORDER))

    sortedArguments.zip(sortedParameters).foreach {
      case (argument: Vertex, parameter: Vertex) =>
        perCallsiteDstGraph.addEdgeInOriginal(argument, parameter, EdgeTypes.CALL_ARG)
      case other =>
        logger.error(s"Failed to generate arg edges for $other")
    }
  }

  private def generateCallArgOutEdges(perCallsiteDstGraph: DiffGraph,
                                      method: Vertex,
                                      sortedArguments: Seq[Vertex]): Unit = {
    val sortedParameters = method
      .vertices(Direction.OUT, EdgeTypes.AST)
      .asScala
      .filter(_.label == NodeTypes.METHOD_PARAMETER_OUT)
      .toSeq
      .sortBy(_.value2(NodeKeys.ORDER))

    sortedArguments.zip(sortedParameters).foreach {
      case (argument: Vertex, parameter: Vertex) =>
        perCallsiteDstGraph.addEdgeInOriginal(parameter, argument, EdgeTypes.CALL_ARG_OUT)
      case other =>
        logger.error(s"Failed to generate out arg edges for $other")
    }
  }

  private def generateCallRetEdge(perCallsiteDstGraph: DiffGraph, method: Vertex, callsite: Vertex): Unit = {
    val methodReturn = method
      .vertices(Direction.OUT, EdgeTypes.AST)
      .asScala
      .find(_.label == NodeTypes.METHOD_RETURN)
      .get
    perCallsiteDstGraph.addEdgeInOriginal(methodReturn, callsite, EdgeTypes.CALL_RET)
  }
}

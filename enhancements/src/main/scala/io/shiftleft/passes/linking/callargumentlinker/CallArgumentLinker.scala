package io.shiftleft.passes.linking.callargumentlinker

import gremlin.scala._
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeKeys, NodeKeyNames, NodeTypes}
import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.diffgraph.DiffGraph
import io.shiftleft.passes.CpgPass
import io.shiftleft.queryprimitives.steps.Implicits._
import io.shiftleft.queryprimitives.steps.starters.Cpg
import io.shiftleft.queryprimitives.steps.types.expressions.generalizations.Expression
import org.apache.tinkerpop.gremlin.structure.Direction

import scala.collection.JavaConverters._

class CallArgumentLinker(graph: ScalaGraph) extends CpgPass(graph) {

  override def run() = {
    graph.V
      .hasLabel(NodeTypes.CALL)
      .sideEffect { callsite =>
        callsite.vertices(Direction.OUT, EdgeTypes.CALL).asScala.foreach { methodInst =>
          val sortedArguments = callsite
            .vertices(Direction.OUT, EdgeTypes.AST)
            .asScala
            .toSeq
            .sortBy(_.value2(NodeKeys.ARGUMENT_INDEX))

          methodInst.vertices(Direction.OUT, EdgeTypes.REF).asScala.foreach { method =>
            generateCallArgEdges(dstGraph, method, sortedArguments)
            generateCallArgOutEdges(dstGraph, method, sortedArguments)
            generateCallRetEdge(dstGraph, method, callsite)
          }
        }
      }
      .iterate
    dstGraph
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
      case (argument, parameter)
        if Expression.isExpression(argument.label) &&
        parameter.label == NodeTypes.METHOD_PARAMETER_IN =>
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
      case (argument, parameter)
        if Expression.isExpression(argument.label) &&
        parameter.label == NodeTypes.METHOD_PARAMETER_OUT =>
        perCallsiteDstGraph.addEdgeInOriginal(parameter, argument, EdgeTypes.CALL_ARG_OUT)
      case other =>
        logger.error(s"Failed to generate out arg edges for $other")
    }
  }

  private def generateCallRetEdge(perCallsiteDstGraph: DiffGraph,
                                  method: Vertex,
                                  callsite: Vertex): Unit = {
    val methodReturn = method
      .vertices(Direction.OUT, EdgeTypes.AST)
      .asScala
      .find(_.label == NodeTypes.METHOD_RETURN)
      .get
    perCallsiteDstGraph.addEdgeInOriginal(methodReturn, callsite, EdgeTypes.CALL_RET)
  }
}

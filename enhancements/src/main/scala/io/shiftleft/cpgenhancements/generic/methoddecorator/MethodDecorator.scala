package io.shiftleft.cpgenhancements.generic.methoddecorator

import gremlin.scala._
import io.shiftleft.codepropertygraph.generated.{nodes, EdgeTypes, NodeKeys, NodeTypes}
import io.shiftleft.cpgenhancements.CpgEnhancement
import org.apache.tinkerpop.gremlin.structure.Direction

/**
  * Adds a METHOD_PARAMETER_OUT for each METHOD_PARAMETER_IN to the graph and
  * connects those with a PARAMETER_LINK edge.
  * It also creates an AST edge from METHOD to the new METHOD_PARAMETER_OUT nodes
  * and EVAL_TYPE edges to the same type which METHOD_PARAMETER_IN points to.
  */
class MethodDecorator(graph: ScalaGraph) extends CpgEnhancement(graph) {
  override def run(): Unit = {
    graph.V
      .hasLabel(NodeTypes.METHOD_PARAMETER_IN)
      .sideEffect { parameterIn =>
        if (!parameterIn.vertices(Direction.OUT, EdgeTypes.PARAMETER_LINK).hasNext) {
          val parameterOut = nodes.NewMethodParameterOut(
            parameterIn.value2(NodeKeys.CODE),
            parameterIn.value2(NodeKeys.ORDER),
            parameterIn.value2(NodeKeys.NAME),
            parameterIn.value2(NodeKeys.EVALUATION_STRATEGY),
            parameterIn.value2(NodeKeys.TYPE_FULL_NAME),
            parameterIn.valueOption(NodeKeys.LINE_NUMBER),
            parameterIn.valueOption(NodeKeys.LINE_NUMBER_END),
            parameterIn.valueOption(NodeKeys.COLUMN_NUMBER),
            parameterIn.valueOption(NodeKeys.COLUMN_NUMBER_END)
          )

          val method = parameterIn.vertices(Direction.IN, EdgeTypes.AST).next
          val evalType = parameterIn.vertices(Direction.OUT, EdgeTypes.EVAL_TYPE).next

          dstGraph.addNode(parameterOut)
          dstGraph.addEdgeFromOriginal(method, parameterOut, EdgeTypes.AST)
          dstGraph.addEdgeFromOriginal(parameterIn, parameterOut, EdgeTypes.PARAMETER_LINK)
          dstGraph.addEdgeToOriginal(parameterOut, evalType, EdgeTypes.EVAL_TYPE)
        }
      }
      .iterate
  }
}

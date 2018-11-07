package io.shiftleft.passes.methoddecorator

import gremlin.scala._
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeKeys, NodeTypes, nodes}
import io.shiftleft.passes.CpgPass
import io.shiftleft.queryprimitives.steps.Implicits._
import org.apache.tinkerpop.gremlin.structure.Direction

object MethodDecorator {
  private var loggedDeprecatedWarning = false
  private var loggedMissingTypeFullName = false
}

/**
  * Adds a METHOD_PARAMETER_OUT for each METHOD_PARAMETER_IN to the graph and
  * connects those with a PARAMETER_LINK edge.
  * It also creates an AST edge from METHOD to the new METHOD_PARAMETER_OUT nodes.
  */
class MethodDecorator(graph: ScalaGraph) extends CpgPass(graph) {
  override def run(): Unit = {
    graph.V
      .hasLabel(NodeTypes.METHOD_PARAMETER_IN)
      .sideEffect { parameterIn =>
        if (!parameterIn.vertices(Direction.OUT, EdgeTypes.PARAMETER_LINK).hasNext) {
          val parameterOut = new nodes.NewMethodParameterOut(
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

          val method = parameterIn.vertices(Direction.IN, EdgeTypes.AST).nextChecked
          if (parameterIn.valueOption(NodeKeys.TYPE_FULL_NAME) == None) {
            val evalType = parameterIn.vertices(Direction.OUT, EdgeTypes.EVAL_TYPE).nextChecked
            dstGraph.addEdgeToOriginal(parameterOut, evalType, EdgeTypes.EVAL_TYPE)
            if (!MethodDecorator.loggedMissingTypeFullName) {
              logger.warn("Using deprecated CPG format with missing TYPE_FULL_NAME on METHOD_PARAMETER_IN nodes.")
              MethodDecorator.loggedMissingTypeFullName = true
            }
          }

          dstGraph.addNode(parameterOut)
          dstGraph.addEdgeFromOriginal(method, parameterOut, EdgeTypes.AST)
          dstGraph.addEdgeFromOriginal(parameterIn, parameterOut, EdgeTypes.PARAMETER_LINK)
        } else if (!MethodDecorator.loggedDeprecatedWarning) {
          logger.warn("Using deprecated CPG format with PARAMETER_LINK egdes")
          MethodDecorator.loggedDeprecatedWarning = true
        }
      }
      .iterate
  }
}

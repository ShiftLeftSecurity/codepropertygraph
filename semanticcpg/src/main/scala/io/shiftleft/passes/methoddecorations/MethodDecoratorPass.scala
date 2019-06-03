package io.shiftleft.passes.methoddecorations

import gremlin.scala._
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeTypes, nodes}
import io.shiftleft.diffgraph.DiffGraph
import io.shiftleft.passes.CpgPass
import io.shiftleft.queryprimitives.steps.Implicits.JavaIteratorDeco
import org.apache.logging.log4j.{LogManager, Logger}
import org.apache.tinkerpop.gremlin.structure.Direction

object MethodDecoratorPass {
  private var loggedDeprecatedWarning = false
  private var loggedMissingTypeFullName = false
  private val logger: Logger = LogManager.getLogger(classOf[MethodDecoratorPass])
}

/**
  * Adds a METHOD_PARAMETER_OUT for each METHOD_PARAMETER_IN to the graph and
  * connects those with a PARAMETER_LINK edge.
  * It also creates an AST edge from METHOD to the new METHOD_PARAMETER_OUT nodes.
  */
class MethodDecoratorPass(graph: ScalaGraph) extends CpgPass(graph) {
  import MethodDecoratorPass.logger

  override def run() = {
    val dstGraph = new DiffGraph

    graph.V
      .hasLabel(NodeTypes.METHOD_PARAMETER_IN)
      .sideEffect { node =>
        val parameterIn = node.asInstanceOf[nodes.MethodParameterIn]
        if (!parameterIn.vertices(Direction.OUT, EdgeTypes.PARAMETER_LINK).hasNext) {
          val parameterOut = new nodes.NewMethodParameterOut(
            parameterIn.code,
            parameterIn.order,
            parameterIn.name,
            parameterIn.evaluationStrategy,
            parameterIn.typeFullName,
            parameterIn.lineNumber,
            parameterIn.lineNumberEnd,
            parameterIn.columnNumber,
            parameterIn.columnNumberEnd
          )

          val method =
            parameterIn.vertices(Direction.IN, EdgeTypes.AST).nextChecked.asInstanceOf[nodes.Method]
          if (parameterIn.typeFullName == null) {
            val evalType = parameterIn
              .vertices(Direction.OUT, EdgeTypes.EVAL_TYPE)
              .nextChecked
              .asInstanceOf[nodes.Type]
            dstGraph.addEdgeToOriginal(parameterOut, evalType, EdgeTypes.EVAL_TYPE)
            if (!MethodDecoratorPass.loggedMissingTypeFullName) {
              logger.warn("Using deprecated CPG format with missing TYPE_FULL_NAME on METHOD_PARAMETER_IN nodes.")
              MethodDecoratorPass.loggedMissingTypeFullName = true
            }
          }

          dstGraph.addNode(parameterOut)
          dstGraph.addEdgeFromOriginal(method, parameterOut, EdgeTypes.AST)
          dstGraph.addEdgeFromOriginal(parameterIn, parameterOut, EdgeTypes.PARAMETER_LINK)
        } else if (!MethodDecoratorPass.loggedDeprecatedWarning) {
          logger.warn("Using deprecated CPG format with PARAMETER_LINK egdes")
          MethodDecoratorPass.loggedDeprecatedWarning = true
        }
      }
      .iterate
    Iterator(dstGraph)
  }
}

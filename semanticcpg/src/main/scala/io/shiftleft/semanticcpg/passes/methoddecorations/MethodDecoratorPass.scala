package io.shiftleft.semanticcpg.passes.methoddecorations

import gremlin.scala._
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeTypes, nodes}
import io.shiftleft.passes.{CpgPass, DiffGraph}
import io.shiftleft.Implicits.JavaIteratorDeco
import io.shiftleft.codepropertygraph.Cpg
import org.apache.logging.log4j.{LogManager, Logger}
import org.apache.tinkerpop.gremlin.structure.Direction

/**
  * Adds a METHOD_PARAMETER_OUT for each METHOD_PARAMETER_IN to the graph and
  * connects those with a PARAMETER_LINK edge.
  * It also creates an AST edge from METHOD to the new METHOD_PARAMETER_OUT nodes.
  *
  * This pass has MethodStubCreator as prerequisite for language frontends which do
  * not provide method stubs.
  *
  */
class MethodDecoratorPass(cpg: Cpg) extends CpgPass(cpg) {
  import MethodDecoratorPass.logger

  private[this] var loggedDeprecatedWarning = false
  private[this] var loggedMissingTypeFullName = false

  override def run() = {
    val dstGraph = DiffGraph.newBuilder

    cpg.graph.V
      .hasLabel(NodeTypes.METHOD_PARAMETER_IN)
      .sideEffect {
        case parameterIn: nodes.MethodParameterIn =>
          if (!parameterIn.vertices(Direction.OUT, EdgeTypes.PARAMETER_LINK).hasNext) {
            val parameterOut = new nodes.NewMethodParameterOut(
              parameterIn.code,
              parameterIn.order,
              parameterIn.name,
              parameterIn.evaluationStrategy,
              parameterIn.typeFullName,
              parameterIn.lineNumber,
              parameterIn.columnNumber,
            )

            val method =
              parameterIn.vertices(Direction.IN, EdgeTypes.AST).nextChecked.asInstanceOf[nodes.Method]
            if (parameterIn.typeFullName == null) {
              val evalType = parameterIn
                .vertices(Direction.OUT, EdgeTypes.EVAL_TYPE)
                .nextChecked
                .asInstanceOf[nodes.Type]
              dstGraph.addEdgeToOriginal(parameterOut, evalType, EdgeTypes.EVAL_TYPE)
              if (!loggedMissingTypeFullName) {
                logger.warn("Using deprecated CPG format with missing TYPE_FULL_NAME on METHOD_PARAMETER_IN nodes.")
                loggedMissingTypeFullName = true
              }
            }

            dstGraph.addNode(parameterOut)
            dstGraph.addEdgeFromOriginal(method, parameterOut, EdgeTypes.AST)
            dstGraph.addEdgeFromOriginal(parameterIn, parameterOut, EdgeTypes.PARAMETER_LINK)
          } else if (!loggedDeprecatedWarning) {
            logger.warn("Using deprecated CPG format with PARAMETER_LINK edges")
            loggedDeprecatedWarning = true
          }
      }
      .iterate
    Iterator(dstGraph.build())
  }
}

object MethodDecoratorPass {
  private val logger: Logger = LogManager.getLogger(classOf[MethodDecoratorPass])
}

package io.shiftleft.cpgenhancements.generic.methodinstlinker

import gremlin.scala._
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeKeyNames, NodeKeys, NodeTypes}
import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.cpgenhancements.CpgEnhancement
import io.shiftleft.diffgraph.DiffGraph
import io.shiftleft.queryprimitives.steps.Implicits._
import io.shiftleft.queryprimitives.steps.starters.Cpg
import org.apache.tinkerpop.gremlin.structure.Direction

import scala.collection.JavaConverters._

object MethodInstLinker {
  private var loggedDeprecatedWarning = false
}

/**
  * Link CALL nodes to METHOD_INST and create
  * CALL_ARG, CALL_ARG_OUT and CALL_RET edges for each call site.
  */
class MethodInstLinker(graph: ScalaGraph) extends CpgEnhancement(graph) {
  private var fullNameToMethodInst = Map.empty[String, Vertex]

  override def run(): Unit = {
    val cpg = Cpg(graph.graph)

    cpg.methodInst
      .sideEffect { methodInstNode =>
        fullNameToMethodInst += methodInstNode.fullName -> methodInstNode.underlying
      }
      .iterate()

    graph.V
      .hasLabel(NodeTypes.CALL)
      .sideEffect { callsite =>
        try {
          val perCallsiteDstGraph = new DiffGraph()

          val methodInstOption: Option[Vertex] =
            callsite.vertices(Direction.OUT, EdgeTypes.CALL).nextOption match {
              case Some(methodInst) =>
                // Deprecated frontend handling.
                callsite.property(NodeKeyNames.METHOD_INST_FULL_NAME, methodInst.value2(NodeKeys.FULL_NAME))
                if (!MethodInstLinker.loggedDeprecatedWarning) {
                  logger.warn(
                    "Using deprecated CPG format with already existing CALL edge" +
                      " between CALL and METHOD_INST node.")
                  MethodInstLinker.loggedDeprecatedWarning = true
                }
                Some(methodInst)
              case None =>
                fullNameToMethodInst.get(callsite.value2(NodeKeys.METHOD_INST_FULL_NAME)) match {
                  case Some(methodInst) =>
                    dstGraph.addEdgeInOriginal(callsite, methodInst, EdgeTypes.CALL)
                    Some(methodInst)
                  case None =>
                    logger.error(
                      "Missing METHOD_INST node or invalid methodInstFullName=${callsite.value2(NodeKeys.METHOD_INST_FULL_NAME)}")
                    None
                }
            }

          if (methodInstOption.isDefined) {
            val sortedArguments = callsite
              .vertices(Direction.OUT, EdgeTypes.AST)
              .asScala
              .toSeq
              .sortBy(_.value2(NodeKeys.ARGUMENT_INDEX))

            methodInstOption.get.vertices(Direction.OUT, EdgeTypes.REF).asScala.foreach { method =>
              generateCallArgEdges(perCallsiteDstGraph, method, sortedArguments)
              generateCallArgOutEdges(perCallsiteDstGraph, method, sortedArguments)
              generateCallRetEdge(perCallsiteDstGraph, method, callsite)
            }
          }
          dstGraph.mergeFrom(perCallsiteDstGraph)
        } catch {
          case exception: Exception =>
            logger.warn(s"Unable to decorate callsite: ${callsite.value2(NodeKeys.CODE)}", exception)
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
      case (argument, parameter) =>
        perCallsiteDstGraph.addEdgeInOriginal(argument, parameter, EdgeTypes.CALL_ARG)
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
      case (argument, parameter) =>
        perCallsiteDstGraph.addEdgeInOriginal(parameter, argument, EdgeTypes.CALL_ARG_OUT)
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

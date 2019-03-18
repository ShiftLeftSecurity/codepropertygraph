package io.shiftleft.passes.languagespecific.fuzzyc

import gremlin.scala._
import io.shiftleft.codepropertygraph.generated.nodes.{NewBlock, NewMethodReturn}
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, EvaluationStrategies, NodeTypes, nodes}
import io.shiftleft.diffgraph.DiffGraph
import io.shiftleft.passes.CpgPass
import io.shiftleft.queryprimitives.steps.starters.Cpg
import org.apache.tinkerpop.gremlin.structure.Direction

import scala.collection.JavaConverters._

class MethodStubCreator(graph: ScalaGraph) extends CpgPass(graph) {
  // Since the method fullNames for fuzzyc are not unique, we do not have
  // a 1to1 relation and may overwrite some values. We deem this ok for now.
  private var methodFullNameToNode = Map[String, nodes.MethodBase]()
  private var methodInstFullNameToParameterCount = Map[String, Int]()

  initMap()

  override def run(): Unit = {
    graph.V
      .hasLabel(NodeTypes.METHOD_INST)
      .sideEffect { v =>
        val methodInst = v.toCC[nodes.MethodInst]
        try {
          methodFullNameToNode.get(methodInst.methodFullName) match {
            case Some(method) =>
            case None =>
              val paramterCount = methodInstFullNameToParameterCount(methodInst.fullName)
              val newMethod =
                createMethodStub(methodInst.name, methodInst.fullName, methodInst.signature, paramterCount)
              methodFullNameToNode += methodInst.methodFullName -> newMethod
          }
        } catch {
          case _: Exception =>
            logger.warn(s"Unable to create method stub. methodInstFullName=${methodInst.fullName}")
        }
      }
      .iterate()
    // dstGraph
  }

  private def createMethodStub(name: String,
                               fullName: String,
                               signature: String,
                               parameterCount: Int): nodes.MethodBase = {
    val methodNode = new nodes.NewMethod(
      name,
      fullName,
      signature,
      NodeTypes.NAMESPACE_BLOCK,
      "<global>",
      None,
      None,
      None,
      None
    )
    dstGraph.addNode(methodNode)

    for (paramaterOrder <- 1 to parameterCount) {
      val nameAndCode = s"p$paramaterOrder"

      val methodParameterIn = new nodes.NewMethodParameterIn(
        nameAndCode,
        paramaterOrder,
        nameAndCode,
        EvaluationStrategies.BY_VALUE,
        "ANY",
        None,
        None,
        None,
        None
      )

      dstGraph.addNode(methodParameterIn)
      dstGraph.addEdge(methodNode, methodParameterIn, EdgeTypes.AST)
    }

    val methodReturn = new NewMethodReturn(
      "RET",
      EvaluationStrategies.BY_VALUE,
      "ANY",
      None,
      None,
      None,
      None
    )
    dstGraph.addNode(methodReturn)
    dstGraph.addEdge(methodNode, methodReturn, EdgeTypes.AST)

    val blockNode = new NewBlock("", 1, 1, "ANY", None, None, None, None)
    dstGraph.addNode(blockNode)
    dstGraph.addEdge(methodNode, blockNode, EdgeTypes.AST)

    methodNode
  }

  private def initMap(): Unit = {
    val cpg = Cpg(graph.graph)
    cpg.method
      .sideEffect { method =>
        methodFullNameToNode += method.fullName -> method
      }
      .exec()

    cpg.call.raw
      .sideEffect { v: Vertex =>
        val call = v.toCC[nodes.Call]
        methodInstFullNameToParameterCount +=
          call.methodInstFullName -> v.vertices(Direction.OUT, EdgeTypes.AST).asScala.size
      }
      .iterate()
  }
}

package io.shiftleft.passes.languagespecific.fuzzyc

import gremlin.scala._
import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.nodes.{NewBlock, NewMethodReturn}
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, EvaluationStrategies, NodeTypes, nodes}
import io.shiftleft.diffgraph.DiffGraph
import io.shiftleft.passes.CpgPass
import org.apache.tinkerpop.gremlin.structure.Direction

import scala.collection.JavaConverters._

class MethodStubCreator(graph: ScalaGraph) extends CpgPass(graph) {
  // Since the method fullNames for fuzzyc are not unique, we do not have
  // a 1to1 relation and may overwrite some values. We deem this ok for now.
  private var methodFullNameToNode = Map[String, nodes.MethodBase]()
  private var methodInstFullNameToParameterCount = Map[String, Int]()

  override def run(): Stream[DiffGraph] = {
    val dstGraph = new DiffGraph

    init()

    graph.V
      .hasLabel(NodeTypes.METHOD_INST)
      .sideEffectWithTraverser { traverser =>
        val methodInst = traverser.get.asInstanceOf[nodes.MethodInst]
        try {
          methodFullNameToNode.get(methodInst.methodFullName) match {
            case Some(method) =>
            case None =>
              val paramterCount = methodInstFullNameToParameterCount(methodInst.fullName)
              val newMethod =
                createMethodStub(methodInst.name, methodInst.fullName, methodInst.signature, paramterCount, dstGraph)

              methodFullNameToNode += methodInst.methodFullName -> newMethod
          }
        } catch {
          case _: Exception =>
            logger.warn("Unable to create method stub.methodInstFullName=${methodInst.fullName}")
        }
      }
      .iterate()
    Stream(dstGraph)
  }

  private def createMethodStub(name: String,
                               fullName: String,
                               signature: String,
                               parameterCount: Int,
                               dstGraph: DiffGraph): nodes.MethodBase = {
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
      None
    )
    dstGraph.addNode(methodReturn)
    dstGraph.addEdge(methodNode, methodReturn, EdgeTypes.AST)

    val blockNode = new NewBlock("", 1, 1, "ANY", None, None, None, None)
    dstGraph.addNode(blockNode)
    dstGraph.addEdge(methodNode, blockNode, EdgeTypes.AST)

    methodNode
  }

  private def init(): Unit = {
    val cpg = Cpg(graph.graph)
    cpg.method
      .sideEffect { method =>
        methodFullNameToNode += method.fullName -> method
      }
      .exec()

    cpg.call
      .sideEffect { call =>
        methodInstFullNameToParameterCount +=
          call.methodInstFullName -> call.vertices(Direction.OUT, EdgeTypes.AST).asScala.size
      }
      .exec()
  }
}

package io.shiftleft.semanticcpg.passes.languagespecific.fuzzyc

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.nodes.{NewBlock, NewMethodReturn}
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, EvaluationStrategies, NodeTypes, nodes}
import io.shiftleft.passes.{CpgPass, DiffGraph}
import org.apache.logging.log4j.{LogManager, Logger}
import org.apache.tinkerpop.gremlin.structure.Direction
import io.shiftleft.semanticcpg.language._

import scala.collection.JavaConverters._

/**
  * This pass has no other pass as prerequisite.
  */
class MethodStubCreator(cpg: Cpg) extends CpgPass(cpg) {
  import MethodStubCreator.logger

  private case class NameAndSignature(name: String, signature: String)

  // Since the method fullNames for fuzzyc are not unique, we do not have
  // a 1to1 relation and may overwrite some values. We deem this ok for now.
  private var methodFullNameToNode = Map[String, nodes.MethodBase]()
  private var methodToParameterCount = Map[NameAndSignature, Int]()

  override def run(): Iterator[DiffGraph] = {
    val dstGraph = new DiffGraph

    init()

    // TODO bring in Receiver type. Just working on name and comparing to full name
    // will only work for C because in C, name always equals full name.
    methodToParameterCount.foreach {
      case (NameAndSignature(name, signature), parameterCount) =>
        methodFullNameToNode.get(name) match {
          case None =>
            createMethodStub(name, name, signature, parameterCount, dstGraph)
          case _ =>
        }

    }

    Iterator(dstGraph)
  }

  private def createMethodStub(name: String,
                               fullName: String,
                               signature: String,
                               parameterCount: Int,
                               dstGraph: DiffGraph): nodes.MethodBase = {
    val methodNode = new nodes.NewMethod(
      name,
      fullName,
      true,
      signature,
      NodeTypes.NAMESPACE_BLOCK,
      "<global>",
      None,
      None,
      0,
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
        Nil,
        None,
        None,
      )

      dstGraph.addNode(methodParameterIn)
      dstGraph.addEdge(methodNode, methodParameterIn, EdgeTypes.AST)
    }

    val methodReturn = new NewMethodReturn(
      "RET",
      EvaluationStrategies.BY_VALUE,
      "ANY",
      Nil,
      None,
      None,
    )
    dstGraph.addNode(methodReturn)
    dstGraph.addEdge(methodNode, methodReturn, EdgeTypes.AST)

    val blockNode = new NewBlock("", 1, 1, "ANY", Nil, None, None)
    dstGraph.addNode(blockNode)
    dstGraph.addEdge(methodNode, blockNode, EdgeTypes.AST)

    methodNode
  }

  private def init(): Unit = {
    cpg.method
      .sideEffect { method =>
        methodFullNameToNode += method.fullName -> method
      }
      .exec()

    cpg.call
      .sideEffect { call =>
        methodToParameterCount +=
          NameAndSignature(call.name, call.signature) -> call.vertices(Direction.OUT, EdgeTypes.AST).asScala.size
      }
      .exec()
  }
}

object MethodStubCreator {
  private val logger: Logger = LogManager.getLogger(classOf[MethodStubCreator])
}

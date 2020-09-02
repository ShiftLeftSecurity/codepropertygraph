package io.shiftleft.semanticcpg.passes.languagespecific.fuzzyc

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.nodes.{NewBlock, NewMethodReturn}
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, EvaluationStrategies, NodeTypes, nodes}
import io.shiftleft.passes.{DiffGraph, ParallelCpgPass}
import io.shiftleft.semanticcpg.language._

import scala.jdk.CollectionConverters._

case class NameAndSignature(name: String, signature: String)

/**
  * This pass has no other pass as prerequisite.
  */
class MethodStubCreator(cpg: Cpg) extends ParallelCpgPass[(NameAndSignature, Int)](cpg) {

  // Since the method fullNames for fuzzyc are not unique, we do not have
  // a 1to1 relation and may overwrite some values. We deem this ok for now.
  private var methodFullNameToNode = Map[String, nodes.MethodBase]()
  private var methodToParameterCount = Map[NameAndSignature, Int]()

  override def init(): Unit = {
    cpg.method
      .sideEffect { method =>
        methodFullNameToNode += method.fullName -> method
      }
      .exec()

    cpg.call
      .sideEffect { call =>
        methodToParameterCount +=
          NameAndSignature(call.name, call.signature) -> call._astOut.asScala.size
      }
      .exec()
  }

  override def partIterator: Iterator[(NameAndSignature, Int)] = methodToParameterCount.iterator

  // TODO bring in Receiver type. Just working on name and comparing to full name
  // will only work for C because in C, name always equals full name.
  override def runOnPart(part: (NameAndSignature, Int)): Iterator[DiffGraph] = {
    val name = part._1.name
    val signature = part._1.signature
    val parameterCount = part._2

    implicit val dstGraph: DiffGraph.Builder = DiffGraph.newBuilder
    methodFullNameToNode.get(name) match {
      case None =>
        createMethodStub(name, name, signature, parameterCount, dstGraph)
      case _ =>
    }
    Iterator(dstGraph.build())
  }

  private def createMethodStub(name: String,
                               fullName: String,
                               signature: String,
                               parameterCount: Int,
                               dstGraph: DiffGraph.Builder): nodes.MethodBase = {
    val methodNode = nodes.NewMethod(
      name = name,
      fullName = fullName,
      isExternal = true,
      signature = signature,
      astParentType = NodeTypes.NAMESPACE_BLOCK,
      astParentFullName = "<global>",
      order = 0
    )
    dstGraph.addNode(methodNode)

    for (parameterOrder <- 1 to parameterCount) {
      val nameAndCode = s"p$parameterOrder"

      val methodParameterIn = nodes.NewMethodParameterIn(
        code = nameAndCode,
        order = parameterOrder,
        name = nameAndCode,
        evaluationStrategy = EvaluationStrategies.BY_VALUE,
        typeFullName = "ANY",
        dynamicTypeHintFullName = Nil
      )

      dstGraph.addNode(methodParameterIn)
      dstGraph.addEdge(methodNode, methodParameterIn, EdgeTypes.AST)
    }

    val methodReturn = NewMethodReturn(
      code = "RET",
      evaluationStrategy = EvaluationStrategies.BY_VALUE,
      typeFullName = "ANY",
      dynamicTypeHintFullName = Nil
    )
    dstGraph.addNode(methodReturn)
    dstGraph.addEdge(methodNode, methodReturn, EdgeTypes.AST)

    val blockNode = NewBlock(
      order = 1,
      argumentIndex = 1,
      typeFullName = "ANY",
      dynamicTypeHintFullName = Nil
    )
    dstGraph.addNode(blockNode)
    dstGraph.addEdge(methodNode, blockNode, EdgeTypes.AST)

    methodNode
  }

}

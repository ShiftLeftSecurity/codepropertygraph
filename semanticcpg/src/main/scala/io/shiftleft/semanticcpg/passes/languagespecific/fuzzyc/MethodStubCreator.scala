package io.shiftleft.semanticcpg.passes.languagespecific.fuzzyc

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.nodes.{
  MethodBase,
  NewBlock,
  NewMethod,
  NewMethodParameterIn,
  NewMethodReturn
}
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, EvaluationStrategies, NodeTypes}
import io.shiftleft.passes.{DiffGraph, ParallelCpgPass}
import io.shiftleft.semanticcpg.language._

import scala.jdk.CollectionConverters._

case class NameAndSignature(name: String, signature: String, fullName: String)

/**
  * This pass has no other pass as prerequisite.
  */
class MethodStubCreator(cpg: Cpg) extends ParallelCpgPass[(NameAndSignature, Int)](cpg) {

  // Since the method fullNames for fuzzyc are not unique, we do not have
  // a 1to1 relation and may overwrite some values. We deem this ok for now.
  private var methodFullNameToNode = Map[String, MethodBase]()
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
          NameAndSignature(call.name, call.signature, call.methodFullName) -> call._astOut.asScala.size
      }
      .exec()
  }

  override def partIterator: Iterator[(NameAndSignature, Int)] = methodToParameterCount.iterator

  // TODO bring in Receiver type. Just working on name and comparing to full name
  // will only work for C because in C, name always equals full name.
  override def runOnPart(part: (NameAndSignature, Int)): Iterator[DiffGraph] = {
    val name = part._1.name
    val fullName = part._1.fullName
    val signature = part._1.signature
    val parameterCount = part._2

    implicit val dstGraph: DiffGraph.Builder = DiffGraph.newBuilder
    methodFullNameToNode.get(name) match {
      case None =>
        createMethodStub(name, fullName, signature, parameterCount, dstGraph)
      case _ =>
    }
    Iterator(dstGraph.build())
  }

  private def createMethodStub(name: String,
                               fullName: String,
                               signature: String,
                               parameterCount: Int,
                               dstGraph: DiffGraph.Builder): MethodBase = {
    val methodNode = NewMethod()
      .name(name)
      .fullName(fullName)
      .isExternal(true)
      .signature(signature)
      .astParentType(NodeTypes.NAMESPACE_BLOCK)
      .astParentFullName("<global>")
      .order(0)

    dstGraph.addNode(methodNode)

    for (parameterOrder <- 1 to parameterCount) {
      val nameAndCode = s"p$parameterOrder"

      val methodParameterIn = NewMethodParameterIn()
        .code(nameAndCode)
        .order(parameterOrder)
        .name(nameAndCode)
        .evaluationStrategy(EvaluationStrategies.BY_VALUE)
        .typeFullName("ANY")

      dstGraph.addNode(methodParameterIn)
      dstGraph.addEdge(methodNode, methodParameterIn, EdgeTypes.AST)
    }

    val methodReturn = NewMethodReturn()
      .code("RET")
      .evaluationStrategy(EvaluationStrategies.BY_VALUE)
      .typeFullName("ANY")

    dstGraph.addNode(methodReturn)
    dstGraph.addEdge(methodNode, methodReturn, EdgeTypes.AST)

    val blockNode = NewBlock()
      .order(1)
      .argumentIndex(1)
      .typeFullName("ANY")

    dstGraph.addNode(blockNode)
    dstGraph.addEdge(methodNode, blockNode, EdgeTypes.AST)

    methodNode
  }

}

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

import scala.util.Try

case class NameAndSignature(name: String, signature: String, fullName: String)

/**
  * Determines calls to functions for which there is no corresponding
  * method in the CPG and creates method stubs for these methods. A
  * method stub consists of a METHOD node, one node for each parameter,
  * a METHOD_RETURN node that represents the formal return parameter, and
  * a BLOCK node that represents an empty compound statement.
  *
  * This pass has no other pass as prerequisite.
  */
class MethodStubCreator(cpg: Cpg) extends ParallelCpgPass[(NameAndSignature, Int)](cpg) {

  private var methodFullNameToNode = Map[String, MethodBase]()
  private var methodToParameterCount = Map[NameAndSignature, Int]()

  override def init(): Unit = {
    // Warning: if fullName is not unique (likely the case for fuzzyc),
    // then we may overwrite key-value pairs in this map. However,
    // assuming that `cpg.method` returns nodes in the same order on
    // each run, this is at least deterministic
    methodFullNameToNode = cpg.method.map { method =>
      method.fullName -> method
    }.toMap

    methodToParameterCount = cpg.call.map { call =>
      NameAndSignature(call.name, call.signature, call.methodFullName) -> call.argument.size
    }.toMap
  }

  override def partIterator: Iterator[(NameAndSignature, Int)] = methodToParameterCount.iterator

  override def runOnPart(part: (NameAndSignature, Int)): Iterator[DiffGraph] = {
    val name = part._1.name
    val signature = part._1.signature
    val fullName = part._1.fullName
    val parameterCount = part._2

    implicit val dstGraph: DiffGraph.Builder = DiffGraph.newBuilder
    methodFullNameToNode.get(fullName) match {
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
    val methodNode1 = NewMethod()
      .name(name)
      .fullName(fullName)
      .isExternal(true)
      .signature(signature)
      .astParentType(NodeTypes.NAMESPACE_BLOCK)
      .astParentFullName("<global>")
      .order(0)

    val methodNode = {
      val s = fullName.split(":")
      if (s.size == 4 && Try { s(1).toInt }.isSuccess) {
        val filename = s(0)
        val lineNumber = s(1).toInt
        methodNode1.filename(filename).lineNumber(lineNumber)
      } else {
        methodNode1
      }
    }

    dstGraph.addNode(methodNode)

    (1 to parameterCount).foreach { parameterOrder =>
      val nameAndCode = s"p$parameterOrder"
      val param = NewMethodParameterIn()
        .code(nameAndCode)
        .order(parameterOrder)
        .name(nameAndCode)
        .evaluationStrategy(EvaluationStrategies.BY_VALUE)
        .typeFullName("ANY")

      dstGraph.addNode(param)
      dstGraph.addEdge(methodNode, param, EdgeTypes.AST)
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

package io.shiftleft.semanticcpg.passes.languagespecific.fuzzyc

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.nodes.{NewBlock, NewMethodReturn}
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, EvaluationStrategies, NodeTypes, nodes}
import io.shiftleft.passes.{CpgPass, DiffGraph}
import io.shiftleft.semanticcpg.language._

import scala.jdk.CollectionConverters._

case class NameAndSignature(name: String, signature: String)

/**
  * This pass has no other pass as prerequisite.
  */
class MethodStubCreator(cpg: Cpg) extends CpgPass(cpg) {

  // Since the method fullNames for fuzzyc are not unique, we do not have
  // a 1to1 relation and may overwrite some values. We deem this ok for now.
  private val methodFullNameToNode = scala.collection.mutable.HashMap[String, nodes.MethodBase]()
  private val methodToParameterCount = scala.collection.mutable.HashMap[NameAndSignature, Int]()
  override def run(): Iterator[DiffGraph] = {
    for (method <- cpg.method.toIterator) {
      methodFullNameToNode.put(method.fullName, method)
    }
    for (call <- cpg.call.toIterator) {
      if (!methodFullNameToNode.contains(call.name)) {
        methodToParameterCount.put(NameAndSignature(call.name, call.signature), call._argumentOut.asScala.size)
      }
    }
    val builder = DiffGraph.newBuilder
    for ((NameAndSignature(name, signature), nargs) <- methodToParameterCount) {
      createMethodStub(name = name, fullName = name, signature = signature, parameterCount = nargs, dstGraph = builder)
    }
    Iterator(builder.build)
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

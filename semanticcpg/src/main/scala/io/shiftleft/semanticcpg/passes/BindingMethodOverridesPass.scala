package io.shiftleft.semanticcpg.passes

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.nodes.{Binding, Method, TypeDecl}
import io.shiftleft.codepropertygraph.generated.{NodeKeys, nodes}
import io.shiftleft.passes.{CpgPass, DiffGraph, ParallelIteratorExecutor}
import io.shiftleft.semanticcpg.language._
import org.apache.logging.log4j.LogManager

class BindingMethodOverridesPass(cpg: Cpg) extends CpgPass(cpg) {
  override def run(): Iterator[DiffGraph] = {
    val methodIterator = cpg.method.toIterator().grouped(1024)
    new ParallelIteratorExecutor(methodIterator).map(processMethods)
  }

  private def processMethods(methods: Seq[Method]): DiffGraph = {
    val diff: DiffGraph = new DiffGraph
    methods.foreach(method => processSingleMethod(diff, method))
    diff
  }

  private def processSingleMethod(diff: DiffGraph, method: nodes.Method): Unit = {
      method.start.referencingBinding.toIterator()
        .foreach(binding => processBinding(method, diff, binding))
  }

  private def processBinding(method: Method, diff: DiffGraph, binding: Binding) = {
    val typeDecl: Option[TypeDecl] = binding.start.bindingTypeDecl.headOption()
    assert(typeDecl.isDefined,
      "No binding typeDecl found in BindingMethodOverridesPass: " + method.name + " " + method.signature)
    val neverOverriddenFlag = isMethodNeverOverridden(typeDecl.get, method.name, method.signature)
    diff.addNodeProperty(
      binding,
      NodeKeys.IS_METHOD_NEVER_OVERRIDDEN.name,
      neverOverriddenFlag.asInstanceOf[AnyRef]
    )
  }

  def isMethodNeverOverridden(typeDecl: nodes.TypeDecl, methodName: String, methodSignature: String): Boolean = {
    typeDecl.start.derivedTypeDeclTransitive.method
      .filter(_.nameExact(methodName).signatureExact(methodSignature))
      .headOption
      .isEmpty
  }
}

object BindingMethodOverridesPass {
  private val logger = LogManager.getLogger(getClass)
}

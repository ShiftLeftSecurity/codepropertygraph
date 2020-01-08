package io.shiftleft.semanticcpg.passes

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.{NodeKeys, nodes}
import io.shiftleft.passes.{CpgPass, DiffGraph}
import io.shiftleft.semanticcpg.language._
import org.apache.logging.log4j.LogManager

class BindingMethodOverridesPass(cpg: Cpg) extends CpgPass(cpg) {
  import BindingMethodOverridesPass._

  override def run(): Iterator[DiffGraph] = {
    cpg.method.map(processMethod).toList().iterator
  }

  def processMethod(method: nodes.Method): DiffGraph = {
    val diff: DiffGraph = new DiffGraph
    method.start.referencingBinding
      .toIterator()
      .foreach(binding => {
        val typeDecl: Option[nodes.TypeDecl] = binding.start.bindingTypeDecl.headOption()
        if (typeDecl.isDefined) {
          val neverOverriddenFlag = isMethodNeverOverridden(typeDecl.get, binding.name, binding.signature)
          diff.addNodeProperty(
            binding,
            NodeKeys.IS_METHOD_NEVER_OVERRIDDEN.name,
            neverOverriddenFlag.asInstanceOf[AnyRef]
          )
        } else {
          logger.error(
            "No binding typeDecl found in BindingMethodOverridesPass: " + method.name + " " + method.signature)
        }
      })
    diff
  }

  def isMethodNeverOverridden(typeDecl: nodes.TypeDecl, bindingName: String, bindingSignature: String): Boolean = {
    typeDecl.start.derivedTypeDeclTransitive.methodBinding
      .filter(_.nameExact(bindingName).signatureExact(bindingSignature))
      .headOption
      .isEmpty
  }
}

object BindingMethodOverridesPass {
  private val logger = LogManager.getLogger(getClass)
}

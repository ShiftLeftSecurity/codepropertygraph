package io.shiftleft.semanticcpg.passes

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.{NodeKeys, nodes}
import io.shiftleft.passes.{CpgPass, DiffGraph}
import io.shiftleft.semanticcpg.language._
import org.apache.logging.log4j.LogManager

class BindingMethodOverridesPass(cpg: Cpg) extends CpgPass(cpg) {
  override def run(): Iterator[DiffGraph] = {
    cpg.method.map(processMethod).toList().iterator
  }

  def processMethod(m: nodes.Method): DiffGraph = {
    val diff: DiffGraph = new DiffGraph
    m.start.referencingBinding
      .toIterator()
      .foreach(binding => {
        val typeDecl: Option[nodes.TypeDecl] = binding.start.bindingTypeDecl.headOption()
        assert(typeDecl.isDefined,
               "No binding typeDecl found in BindingMethodOverridesPass: " + m.name + " " + m.signature)
        val neverOverriddenFlag = isMethodNeverOverridden(typeDecl.get, m.name, m.signature)
        diff
          .addNodeProperty(binding, NodeKeys.IS_METHOD_NEVER_OVERRIDDEN.name, neverOverriddenFlag.asInstanceOf[AnyRef])
      })
    diff
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

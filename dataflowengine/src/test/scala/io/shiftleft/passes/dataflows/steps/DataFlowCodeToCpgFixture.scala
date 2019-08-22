package io.shiftleft.passes.dataflows.steps

import io.shiftleft.SerializedCpg
import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.passes.dataflows.DataFlowRunner
import io.shiftleft.semanticcpg.layers.EnhancementRunner
import io.shiftleft.semanticcpg.testfixtures.{CodeToCpgFixture, LanguageFrontend}
import io.shiftleft.semanticsloader.SemanticsLoader

object DataFlowCodeToCpgFixture {

  def apply(): DataFlowCodeToCpgFixture = {
    new DataFlowCodeToCpgFixture(LanguageFrontend.Fuzzyc)
  }

  def passes(cpg: Cpg): Unit = {
    new EnhancementRunner().run(cpg, new SerializedCpg())
    val semantics = new SemanticsLoader("dataflowengine/src/test/resources/default.semantics").load()
    new DataFlowRunner(semantics).run(cpg, new SerializedCpg())
  }

}

class DataFlowCodeToCpgFixture(frontend: LanguageFrontend) extends CodeToCpgFixture(frontend) {

  override def buildCpg[T](sourceCode: String, passes: (Cpg => Unit))(fun: Cpg => T): T = {
    super.buildCpg(sourceCode, DataFlowCodeToCpgFixture.passes)(fun)
  }

}

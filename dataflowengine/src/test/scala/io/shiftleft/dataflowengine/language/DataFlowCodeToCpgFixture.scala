package io.shiftleft.dataflowengine.language

import io.shiftleft.SerializedCpg
import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.dataflowengine.layers.dataflows.DataFlowRunner
import io.shiftleft.semanticcpg.layers.EnhancementRunner
import io.shiftleft.semanticcpg.testfixtures.{CodeToCpgFixture, LanguageFrontend}
import io.shiftleft.dataflowengine.semanticsloader.SemanticsLoader

object DataFlowCodeToCpgFixture {

  def apply[T](sourceCode: String,
               passes: (Cpg => Unit) = passes,
               frontend: LanguageFrontend = LanguageFrontend.Fuzzyc)(fun: Cpg => T): T =
    new CodeToCpgFixture(frontend).buildCpg(sourceCode, passes)(fun)

  private def passes(cpg: Cpg): Unit = {
    new EnhancementRunner().run(cpg)
    val semantics = new SemanticsLoader("dataflowengine/src/test/resources/default.semantics").load()
    new DataFlowRunner(semantics).run(cpg, new SerializedCpg())
  }

}

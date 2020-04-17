package io.shiftleft.dataflowengine.language

import io.shiftleft.SerializedCpg
import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.dataflowengine.layers.dataflows.DataFlowRunner
import io.shiftleft.dataflowengine.semanticsloader.SemanticsLoader
import io.shiftleft.semanticcpg.layers.EnhancementRunner
import io.shiftleft.semanticcpg.testfixtures.{CodeToCpgFixture, LanguageFrontend}

object DataFlowFileToCpgFixture {

  def apply[T](sourceCode: String,
               passes: (Cpg => Unit) = DataFlowFileToCpgFixture.passes,
               frontend: LanguageFrontend = LanguageFrontend.Fuzzyc)(fun: Cpg => T): T =
    new CodeToCpgFixture(frontend).buildCpg(sourceCode, passes)(fun)

  def passes(cpg: Cpg): Unit = {
    new EnhancementRunner().run(cpg, new SerializedCpg())
    val semantics = new SemanticsLoader("dataflowengine/src/test/resources/default.semantics").load()
    new DataFlowRunner(semantics).run(cpg, new SerializedCpg())
  }

}

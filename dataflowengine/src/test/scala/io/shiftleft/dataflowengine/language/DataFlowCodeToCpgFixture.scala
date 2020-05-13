package io.shiftleft.dataflowengine.language

import io.shiftleft.SerializedCpg
import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.dataflowengine.layers.dataflows.{OssDataFlow, OssDataFlowOptions}
import io.shiftleft.semanticcpg.layers.{LayerCreatorContext, Scpg}
import io.shiftleft.dataflowengine.semanticsloader.SemanticsLoader
import io.shiftleft.semanticcpg.testfixtures.{CodeToCpgFixture, LanguageFrontend}

object DataFlowCodeToCpgFixture {

  def apply[T](sourceCode: String,
               passes: (Cpg => Unit) = passes,
               frontend: LanguageFrontend = LanguageFrontend.Fuzzyc)(fun: Cpg => T): T =
    new CodeToCpgFixture(frontend).buildCpg(sourceCode, passes)(fun)

  private def passes(cpg: Cpg): Unit = {
    val context = new LayerCreatorContext(cpg, new SerializedCpg())
    new Scpg().run(context)
    val semantics = new SemanticsLoader("dataflowengine/src/test/resources/default.semantics").load()
    val options = new OssDataFlowOptions(semantics)
    new OssDataFlow().run(context, Some(options))
  }

}

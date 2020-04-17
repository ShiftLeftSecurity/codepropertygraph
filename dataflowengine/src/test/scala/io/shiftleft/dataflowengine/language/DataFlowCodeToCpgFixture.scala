package io.shiftleft.dataflowengine.language

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.semanticcpg.testfixtures.{CodeToCpgFixture, LanguageFrontend}

object DataFlowCodeToCpgFixture {

  def apply[T](sourceCode: String,
               passes: (Cpg => Unit) = DataFlowFileToCpgFixture.passes,
               frontend: LanguageFrontend = LanguageFrontend.Fuzzyc)(fun: Cpg => T): T =
    new CodeToCpgFixture(frontend).buildCpg(sourceCode, passes)(fun)

}

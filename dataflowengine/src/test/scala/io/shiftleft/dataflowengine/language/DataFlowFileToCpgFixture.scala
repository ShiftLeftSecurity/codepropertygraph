package io.shiftleft.dataflowengine.language

import java.io.File

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.dataflowengine.language.DataFlowCodeToCpgFixture.passes
import io.shiftleft.semanticcpg.testfixtures.{CodeToCpgFixture, LanguageFrontend}

object DataFlowFileToCpgFixture {

  def apply[T](file: File,
               passes: (Cpg => Unit) = passes,
               frontend: LanguageFrontend = LanguageFrontend.Fuzzyc)(fun: Cpg => T): T =
    new CodeToCpgFixture(frontend).buildCpgForFile(file, passes)(fun)

}

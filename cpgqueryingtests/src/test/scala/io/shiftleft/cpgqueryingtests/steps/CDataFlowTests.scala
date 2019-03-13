package io.shiftleft.cpgqueryingtests.steps

import io.shiftleft.cpgqueryingtests.codepropertygraph.{CpgFactory, LanguageFrontend}
import org.scalatest.{Matchers, WordSpec}

class CDataFlowTests extends WordSpec with Matchers {
  val cpgFactory = new CpgFactory(LanguageFrontend.Fuzzyc)

  "Example test setup." in {
    val cpg = cpgFactory.buildCpg(
      """
        |void method(int x) {
        |}
      """.stripMargin
    )
    val foo = cpg.method.name("method").l
    foo.size shouldBe 1
  }
}

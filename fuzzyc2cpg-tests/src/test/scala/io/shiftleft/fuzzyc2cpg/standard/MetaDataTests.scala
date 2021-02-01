package io.shiftleft.fuzzyc2cpg.standard

import io.shiftleft.fuzzyc2cpg.testfixtures.FuzzyCCodeToCpgSuite
import io.shiftleft.semanticcpg.language._

class MetaDataTests extends FuzzyCCodeToCpgSuite {

  override val code: String =
    """
      |
      |""".stripMargin

  "should contain exactly one node with all mandatory fields set" in {
    cpg.metaData.l match {
      case List(x) =>
        x.language shouldBe "C"
        x.version shouldBe "0.1"
        x.overlays shouldBe List("semanticcpg")
      case _ => fail()
    }
  }

}

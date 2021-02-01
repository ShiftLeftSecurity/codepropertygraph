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
        // C-frontend does not set hash for entire CPG.
        // Change this assertion if it is supposed to.
        x.hash shouldBe None
      case _ => fail()
    }
  }

  "should not have any incoming or outgoing edges" in {
    cpg.metaData.size shouldBe 1
    cpg.metaData.in.l shouldBe List()
    cpg.metaData.out.l shouldBe List()
  }

}

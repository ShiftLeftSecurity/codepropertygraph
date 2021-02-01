package io.shiftleft.fuzzyc2cpg.standard

import io.shiftleft.semanticcpg.language._
import io.shiftleft.fuzzyc2cpg.testfixtures.FuzzyCCodeToCpgSuite

class MethodReturnTests extends FuzzyCCodeToCpgSuite {

  override val code =
    """
      |int *foo() { return x; }
      |""".stripMargin

  "should" in {
    cpg.methodReturn.l match {
      case List(x) =>
        x.code shouldBe "RET"
        x.typeFullName shouldBe "int *"
      case _ => fail()
    }
  }

}

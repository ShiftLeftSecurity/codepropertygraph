package io.shiftleft.cpgqueryingtests.steps

import io.shiftleft.cpgqueryingtests.codepropertygraph.{CpgFactory, LanguageFrontend}

class CAstTests extends CpgDataFlowTests {
  val cpgFactory = new CpgFactory(LanguageFrontend.Fuzzyc, "cpgqueryingtests/src/test/resources/default.semantics")

  val code =
    """
      | int foo(int y) {
      |   int x = 10;
      |   if (x > 10) {
      |     return bar(x + 10);
      |   } else {
      |     if (y > x) {
      |       printf("reached");
      |     }
      |   }
      | }
    """.stripMargin

  "should identify four blocks" in {
    cpgFactory.buildCpg(code) { cpg =>
      cpg.method.name("foo").ast.isBlock.l.size shouldBe 4
    }
  }

}

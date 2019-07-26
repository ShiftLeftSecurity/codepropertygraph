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

  "should identify three control structures" in {
    cpgFactory.buildCpg(code) { cpg =>
      cpg.method
        .name("foo")
        .ast
        .isControlStructure
        .parserTypeName("IfStatement")
        .l
        .size shouldBe 2

      cpg.method
        .name("foo")
        .ast
        .isControlStructure
        .parserTypeName("ElseStatement")
        .l
        .size shouldBe 1
    }
  }

  "should identify conditions" in {
    cpgFactory.buildCpg(code) { cpg =>
      cpg.method.name("foo").ast.isControlStructure.condition.code.l shouldBe List("x > 10", "", "y > x")
    }
  }

  "should allow parserTypeName filtering and then ast" in {
    cpgFactory.buildCpg(code) { cpg =>
      val query1Size = cpg.method.name("foo").ast.isControlStructure.ast.l.size
      query1Size should be > 0

      val query2Size = cpg.method
        .name("foo")
        .ast
        .isControlStructure
        .parserTypeName(".*")
        .ast
        .l
        .size
      query1Size shouldBe query2Size
    }
  }

  "should allow filtering on conditions" in {
    cpgFactory.buildCpg(code) { cpg =>
      cpg.method
        .name("foo")
        .condition(".*x > 10.*")
        .l
        .size shouldBe 1

      cpg.method
        .name("foo")
        .condition(".*x > 10.*")
        .whenTrue
        .ast
        .isReturnNode
        .code
        .l shouldBe List("return bar(x + 10);")

      cpg.method
        .name("foo")
        .condition(".*x > 10.*")
        .whenFalse
        .ast
        .isCall
        .code(".*printf.*")
        .code
        .l shouldBe List("printf(\"reached\")")
    }
  }

}

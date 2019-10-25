package io.shiftleft.semanticcpg.language.types.expressions.generalizations

import org.scalatest.{Matchers, WordSpec}
import io.shiftleft.semanticcpg.language._
import io.shiftleft.semanticcpg.testfixtures.CodeToCpgFixture

class CAstTests extends WordSpec with Matchers {

  val code =
    """
      | int foo(int y) {
      |   int x = 10;
      |   if (x > 10) {
      |     moo(boo(1+2));
      |     return bar(x + 10);
      |   } else {
      |     if (y > x) {
      |       printf("reached");
      |     }
      |   }
      | }
    """.stripMargin

  CodeToCpgFixture(code) { cpg =>
    "should identify four blocks" in {
      cpg.method.name("foo").ast.isBlock.l.size shouldBe 4
    }

    "should allow finding addition in argument to bar" in {
      implicit val resolver: ICallResolver = NoResolve
      cpg.method
        .name("bar")
        .callIn
        .argument(1)
        .containsCallTo("<operator>.(addition|multiplication)")
        .code
        .l shouldBe List("x + 10")
    }

    "should allow finding that addition is not a direct argument of moo" in {
      implicit val resolver: ICallResolver = NoResolve

      cpg.method
        .name("moo")
        .callIn
        .argument(1)
        .containsCallTo("<operator>.(addition|multiplication)")
        .code
        .l shouldBe List("boo(1+2)")

      cpg.method
        .name("moo")
        .callIn
        .argument(1)
        .filterOnEnd(
          arg =>
            arg.start.ast
              .isCallTo("<operator>.(addition|multiplication)")
              .filterNot(_.inAstMinusLeaf(arg).isCall)
              .l
              .nonEmpty)
        .code
        .l shouldBe List()
    }

    "should identify three control structures" in {
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

    "should identify conditions" in {
      cpg.method.name("foo").ast.isControlStructure.condition.code.l shouldBe List("x > 10", "y > x")
    }

    "should allow parserTypeName filtering and then ast" in {
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

    "should allow filtering on conditions" in {
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

  val bufInLoopCode =
    """
      |void foo(int bar) {
      | char buf[10];
      | int i;
      | for (int i = 0; i < bar; i++) {
      |   buf[i] = 42;
      | }
      |}
    """.stripMargin

  CodeToCpgFixture().buildCpg(bufInLoopCode) { cpg =>
    "should find index `i` used for buf" in {

      cpg.call
        .name("<operator>.computedMemberAccess")
        .argument
        .argIndex(2)
        .code
        .l shouldBe List("i")
    }

    "should find that i is assigned as part of loop header" in {

      cpg.call
        .name("<operator>.computedMemberAccess")
        .argument
        .argIndex(2)
        .inAstMinusLeaf
        .isControlStructure
        .code
        .l shouldBe List("for (int i = 0; i < bar; i++)")

    }

    "should correctly identify condition of for loop" in {
      cpg.call
        .name("<operator>.computedMemberAccess")
        .argument
        .argIndex(2)
        .inAstMinusLeaf
        .isControlStructure
        .condition
        .code
        .l shouldBe List("i < bar")
    }

  }

}

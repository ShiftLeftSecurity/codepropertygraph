package io.shiftleft.semanticcpg.samplequeries

import io.shiftleft.semanticcpg.testfixtures.CodeToCpgFixture
import org.scalatest.{Matchers, WordSpec}
import io.shiftleft.semanticcpg.language._

class FunctionComplexityMetrics extends WordSpec with Matchers {

  val code = """
    int too_many_params(int a, int b, int c, int d, int e) {
    }

    int high_cyclomatic_complexity(int x) {
      while(true) {
        for(int i = 0; i < 10; i++) {
        }
        if(foo()) {}
      }
      if (x > 10) {
        for(int i = 0; i < 10; i++) {

         }
      }
    }

    int func_with_many_lines(int x) {
      x++;
      x++;
      x++;
      x++;
      x++;
      x++;
      x++;
      x++;
      x++;
      x++;
      x++;
      x++;
    }

    int func_with_multiple_returns (int x) {
      if (x > 10) {
        return 0;
      } else {
        return 1;
      }
    }

    int func_with_nesting_level_of_3(int foo, int bar) {
      if (foo > 10) {
        if (bar > foo) {
          for(int i = 0; i < bar ;i++) {

          }
        }
      }
    }

    int high_number_of_loops () {
      for(int i = 0; i < 10; i++){
      }
      int j = 0;
      do {
        j++
      } while(j < 10);
      while(foo()) {}
      while(bar()){}
    }

    """

  CodeToCpgFixture(code) { cpg =>
    "find functions with too many parameters" in {
      cpg.method.where(_.parameter.size > 4).name.l shouldBe List("too_many_params")
      cpg.method.where(_.parameter.size < 4).name.l should not contain "too_many_params"
    }

    "find functions with high cyclomatic complexity" in {
      cpg.method.where(_.controlStructure.size > 4).name.l shouldBe List("high_cyclomatic_complexity")
      cpg.method.where(_.controlStructure.size <= 4).name.l should not contain ("high_cyclomatic_complexity")
    }

    "find functions that are long (in terms of line numbers)" in {
      cpg.method.where(_.numberOfLines >= 14).name.l shouldBe List("func_with_many_lines")
      cpg.method.where(_.numberOfLines < 14).name.l should not contain "func_with_many_lines"
    }

    "find functions with multiple returns" in {
      cpg.method.where(_.ast.isReturn.l.size > 1).name.l shouldBe List("func_with_multiple_returns")
      cpg.method.where(_.ast.isReturn.l.size == 1).name.l should not contain "func_with_multiple_returns"
    }

    "find functions with high number of loops" in {
      cpg.method.where(_.ast.isControlStructure.parserTypeName("(For|Do|While).*").size >= 4).name.l shouldBe List(
        "high_number_of_loops")
      cpg.method
        .where(_.ast.isControlStructure.parserTypeName("(For|Do|While).*").size < 4)
        .name
        .l should not contain "high_number_of_loops"
    }

    "find deeply nested functions" in {
      cpg.method.where(_.depth(_.isControlStructure) == 3).name.l shouldBe List("func_with_nesting_level_of_3")
    }

  }

}

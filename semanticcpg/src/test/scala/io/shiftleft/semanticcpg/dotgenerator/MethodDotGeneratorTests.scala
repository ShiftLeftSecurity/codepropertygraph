package io.shiftleft.semanticcpg.dotgenerator

import org.scalatest.{Matchers, WordSpec}

import io.shiftleft.semanticcpg.testfixtures.CodeToCpgFixture
import io.shiftleft.semanticcpg.language._

class MethodDotGeneratorTests extends WordSpec with Matchers {

  private val code =
    """| // A comment
       |int my_func(int x)
       |{
       |  int y = x * 2;
       |  if (y > 42) {
       |    return y;
       |  } else {
       |    return sqrt(y);
       |  }
       |}
       |
       |void boop() {
       |  printf("Boop!");
       |  return;
       |}
       |""".stripMargin

  private val expectedMyFuncRegex =
    """|digraph my_func \{
       | "\d+" -> "\d+" \[label="y = x \* 2"\];
       | "\d+" -> "\d+" \[label="if \(y > 42\)"\];
       | "\d+" -> "\d+" \[label="y > 42"\];
       | "\d+" -> "\d+" \[label="BLOCK"\];
       | "\d+" -> "\d+" \[label="return y;"\];
       | "\d+" -> "\d+" \[label="else"\];
       | "\d+" -> "\d+" \[label="BLOCK"\];
       | "\d+" -> "\d+" \[label="return sqrt\(y\);"\];
       |\}
       |""".stripMargin

  "A MethodDotGenerator" should {
    CodeToCpgFixture(code) { cpg =>
      "return an empty list for an empty method set" in {
        MethodDotGenerator.toDotGraph(cpg.method.name("ohnoes")) shouldBe empty
      }

      "return a dot representation of all methods" in {
        val List(boopGraph, myFuncGraph) = MethodDotGenerator.toDotGraph(cpg.method).sortBy(_.length)

        val expectedBoopRegex =
          """|digraph boop \{
             | "\d+" -> "\d+" \[label="printf\("Boop!"\)"\];
             | "\d+" -> "\d+" \[label="return;"\];
             |\}
             |""".stripMargin

        myFuncGraph should fullyMatch regex expectedMyFuncRegex
        boopGraph should fullyMatch regex expectedBoopRegex
      }

      "return a dot representation of all methods matching the query criterion" in {
        val List(dotGraph) = MethodDotGenerator.toDotGraph(cpg.method.nameExact("my_func"))

        dotGraph should fullyMatch regex expectedMyFuncRegex
      }

      "return the dot representation of a method using the query language extensions" in {
        val List(dotGraph) = cpg.method.nameExact("my_func").dot

        dotGraph should fullyMatch regex expectedMyFuncRegex
      }
    }
  }
}

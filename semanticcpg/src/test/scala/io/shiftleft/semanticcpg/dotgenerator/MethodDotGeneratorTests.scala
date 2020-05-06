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

  "A MethodDotGenerator" should {
    CodeToCpgFixture(code) { cpg =>
      "generate dot graph" in {
        val dotLines = cpg.method.name("my_func").dot
        dotLines.size shouldBe 1
        val myFuncDot = dotLines.head
        myFuncDot.startsWith("digraph my_func") shouldBe true
        myFuncDot.contains("""[label = "(CONTROL_STRUCTURE,if (y > 42))" ]""") shouldBe true
        myFuncDot.endsWith("}")
      }

      "allow selection method" in {
        val dotLines = cpg.method.name("boop").dot
        dotLines.size shouldBe 1
        dotLines.head.startsWith("digraph boop")
      }

    }
  }
}

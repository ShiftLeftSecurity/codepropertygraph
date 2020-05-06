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
        cpg.method.name("my_func").dot match {
          case x :: _ =>
            x.startsWith("digraph my_func") shouldBe true
            x.contains("""[label = "(CONTROL_STRUCTURE,if (y > 42))" ]""") shouldBe true
            x.endsWith("}") shouldBe true
        }
      }

      "allow selection method" in {
        cpg.method.name("boop").dot match {
          case x :: _ => x.startsWith("digraph boop") shouldBe true
          case _ => fail
        }
      }

      "not include MethodParameterOut nodes" in {
        cpg.method.name("my_func").dot match {
          case x :: _ => x.contains("PARAM_OUT") shouldBe false
          case _ => fail
        }
      }

    }
  }
}

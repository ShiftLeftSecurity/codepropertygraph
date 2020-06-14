package io.shiftleft.semanticcpg.language.types.expressions.generalizations

import io.shiftleft.semanticcpg.testfixtures.CodeToCpgFixture
import org.scalatest.{Matchers, WordSpec}
import io.shiftleft.semanticcpg.language._

class CfgTests extends WordSpec with Matchers {

  val code =
    """
      | int foo(int y, int y) {
      |  if (y < 10)
      |    goto end;
      |  if (x < 10) {
      |    sink(x);
      |  }
      |  end:
      |  printf("foo");
      | }
    """.stripMargin

  CodeToCpgFixture(code) { cpg =>
    "should find that sink is control dependent on condition" in {
      val controllers = cpg.call("sink").controlledBy.isCall.toSet
      controllers.map(_.code) should contain("y < 10")
      controllers.map(_.code) should contain("x < 10")
    }
    "should find that first if controls `sink`" in {
      cpg.controlStructure.condition.code("y < 10").controls.isCall.name("sink").l.size shouldBe 1
    }

  }

}

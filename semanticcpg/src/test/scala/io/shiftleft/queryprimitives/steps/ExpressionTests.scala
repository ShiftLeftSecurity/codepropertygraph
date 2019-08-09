package io.shiftleft.queryprimitives.steps

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.semanticcpg.Fixture
import org.scalatest.{Matchers, WordSpec}

class ExpressionTests extends WordSpec with Matchers {

  "generic cpg" should Fixture("expression") { fixture =>
    "expand to next expression in CFG" in {
      val expressions: List[nodes.Expression] =
        fixture.cpg.method.name("methodForCfgTest").cfgFirst.cfgNext.toList

      expressions.size shouldBe 1
      expressions.head.code shouldBe "aaa"
    }

    "dont expand to previous of first expression in CFG. Aka it should be empty" in {
      val expressions: List[nodes.Expression] =
        fixture.cpg.method.name("methodForCfgTest").cfgFirst.cfgPrev.toList

      expressions.size shouldBe 0
    }

    "expand to previous expression in CFG" in {
      val expressions: List[nodes.Expression] =
        fixture.cpg.method.name("methodForCfgTest").cfgLast.cfgPrev.toList

      expressions.size shouldBe 1
      expressions.head.code shouldBe "temp"
    }

    "dont expand to next of last expression in CFG. Aka it should be empty" in {
      val expressions: List[nodes.Expression] =
        fixture.cpg.method.name("methodForCfgTest").cfgLast.cfgNext.toList

      expressions.size shouldBe 0
    }
  }
}

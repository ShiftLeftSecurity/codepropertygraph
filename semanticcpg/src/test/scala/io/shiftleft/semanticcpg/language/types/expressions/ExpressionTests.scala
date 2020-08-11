package io.shiftleft.semanticcpg.language.types.expressions

import io.shiftleft.codepropertygraph.generated.nodes

import io.shiftleft.semanticcpg.language._
import io.shiftleft.semanticcpg.testfixtures.ExistingCpgFixture
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class ExpressionTests extends AnyWordSpec with Matchers {

  "generic cpg" should ExistingCpgFixture("expression") { fixture =>
    "expand to next expression in CFG" in {
      val expressions: List[nodes.Expression] =
        fixture.cpg.method.name("methodForCfgTest").cfgFirst.cfgNext.isExpression.toList

      expressions.size shouldBe 1
      expressions.head.code shouldBe "aaa"
    }

    "don't expand to previous of first expression in CFG. Aka it should be empty" in {
      val expressions: List[nodes.Expression] =
        fixture.cpg.method.name("methodForCfgTest").cfgFirst.cfgPrev.isExpression.toList

      expressions.size shouldBe 0
    }

    "expand to previous expression in CFG" in {
      val expressions: List[nodes.Expression] =
        fixture.cpg.method.name("methodForCfgTest").cfgLast.cfgPrev.isExpression.toList

      expressions.size shouldBe 1
      expressions.head.code shouldBe "temp"
    }

    "don't expand to next of last expression in CFG. Aka it should be empty" in {
      val expressions: List[nodes.Expression] =
        fixture.cpg.method.name("methodForCfgTest").cfgLast.cfgNext.isExpression.toList

      expressions.size shouldBe 0
    }
  }
}

package io.shiftleft.semanticcpg.utils

import io.shiftleft.semanticcpg.testfixtures.ExistingCpgFixture
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec


class CountStatementsTests extends AnyWordSpec with Matchers {

  "Class Statements" should ExistingCpgFixture("method") { fixture =>
    "count statements correctly" in {
      Statements.countAll(fixture.cpg) should be(13)
    }
  }
}

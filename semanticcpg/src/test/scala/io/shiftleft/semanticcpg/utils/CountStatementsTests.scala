package io.shiftleft.semanticcpg.utils

import io.shiftleft.testfixtures.ExistingCpgFixture
import org.scalatest.{Matchers, WordSpec}

class CountStatementsTests extends WordSpec with Matchers {

  "Class Statements" should ExistingCpgFixture("method") { fixture =>
    "count statements correctly" in {
      Statements.countAll(fixture.cpg) should be(13)
    }
  }
}

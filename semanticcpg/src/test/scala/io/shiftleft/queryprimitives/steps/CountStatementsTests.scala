package io.shiftleft.queryprimitives.steps

import io.shiftleft.queryprimitives.utils.Statements
import io.shiftleft.semanticcpg.Fixture
import org.scalatest.{Matchers, WordSpec}

class CountStatementsTests extends WordSpec with Matchers {

  "Class Statements" should Fixture("method") { fixture =>
    "count statements correctly" in {
      Statements.countAll(fixture.cpg) should be(13)
    }
  }
}

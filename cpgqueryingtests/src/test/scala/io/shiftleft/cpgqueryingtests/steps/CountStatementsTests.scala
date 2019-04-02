package io.shiftleft.cpgqueryingtests.steps

import io.shiftleft.cpgqueryingtests.codepropertygraph.CpgTestFixture
import io.shiftleft.queryprimitives.utils.Statements
import org.scalatest.{Matchers, WordSpec}

class CountStatementsTests extends WordSpec with Matchers {
  val fixture = CpgTestFixture("method")

  "Class Statements" should {
    "count statements correctly" in {
      Statements.countAll(fixture.cpg) should be(9)
    }
  }
}

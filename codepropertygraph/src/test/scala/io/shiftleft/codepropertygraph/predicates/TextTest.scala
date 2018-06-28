package io.shiftleft.codepropertygraph.predicates

import org.apache.tinkerpop.gremlin.process.traversal.P
import org.scalatest.{Matchers, WordSpec}

class TextTest extends WordSpec with Matchers {
  val name = "fully funny"

  "Text.textRegex" should {
    "find matching regex" in {
      Text.textRegex("(fu[ln]*y) (fu[ln]*y)").test(name) shouldBe true
      Text.textRegex("(fu[l]*y) .*").test(name) shouldBe true
    }

    "find non-matching regex" in {
      Text.textRegex("(fu[l]*y) (fu[l]*y)").test(name) shouldBe false
    }
    "empty regex" in {
      Text.textRegex("").test(name) shouldBe false
    }
  }
}


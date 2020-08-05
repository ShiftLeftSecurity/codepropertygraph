package io.shiftleft.semanticcpg.language.types.structure

import io.shiftleft.codepropertygraph.generated.ModifierTypes
import io.shiftleft.semanticcpg.language._
import io.shiftleft.semanticcpg.testfixtures.ExistingCpgFixture
import org.scalatest.{Matchers, WordSpec}

class MemberTests extends WordSpec with Matchers {

  "Member traversals" should ExistingCpgFixture("type") { fixture =>
    "should find two members: `member` and `static_member`" in {
      fixture.cpg.member.name.toSet shouldBe Set("member", "static_member")
    }

    "filter by modifier" in {
      val member = fixture.cpg.member.hasModifier(ModifierTypes.STATIC).name.toSet
      member shouldBe Set("static_member")
    }
  }

}

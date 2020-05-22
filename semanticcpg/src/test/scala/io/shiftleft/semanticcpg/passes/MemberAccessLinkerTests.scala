package io.shiftleft.semanticcpg.passes

import io.shiftleft.codepropertygraph.generated._
import io.shiftleft.semanticcpg.language._
import io.shiftleft.semanticcpg.testfixtures.ExistingCpgFixture
import org.scalatest.{Matchers, WordSpec}

class MemberAccessLinkerTests extends WordSpec with Matchers {

  "have a reference to correct member" in ExistingCpgFixture("memberaccesslinker") { fixture =>
    val members = fixture.cpg.call(Operators.indirectMemberAccess).referencedMember.l
    members.size shouldBe 1
    members.head.name shouldBe "aaa"
  }

}

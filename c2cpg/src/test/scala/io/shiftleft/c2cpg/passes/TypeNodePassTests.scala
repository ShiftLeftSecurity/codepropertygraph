package io.shiftleft.c2cpg.passes

import io.shiftleft.c2cpg.fixtures.CpgTypeNodeFixture
import io.shiftleft.semanticcpg.language._
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class TypeNodePassTests extends AnyWordSpec with Matchers with CpgTypeNodeFixture {

  "TypeNodePass" should {
    "create TYPE nodes for used types" in CpgTypeNodeFixture("int main() { int x; }") { cpg =>
      cpg.typ.name.toSet shouldBe Set("int", "void", "ANY")
    }
  }

}

package io.shiftleft.c2cpg.passes

import io.shiftleft.c2cpg.fixtures.CpgStubRemovalFixture
import io.shiftleft.semanticcpg.language._
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class StubRemovalPassTests extends AnyWordSpec with Matchers with CpgStubRemovalFixture {

  "StubRemovalPass" should {
    "remove stub if non-stub with same signature exists" in CpgStubRemovalFixture("""
        |int foo(int x);
        |int foo(int x) {
        | return 0;
        |}
        |""".stripMargin) { cpg =>
      cpg.method.name.l shouldBe List("foo")
      cpg.method.isStub.l shouldBe List()
      cpg.parameter.name.l shouldBe List("x")
      cpg.methodReturn.l.size shouldBe 1
    }

    "remove stub even if even parameter names differ" in CpgStubRemovalFixture("""
        |int foo(int another_name);
        |int foo(int x) {
        | return 0;
        |}
        |""".stripMargin) { cpg =>
      cpg.method.name.l shouldBe List("foo")
      cpg.method.isStub.l shouldBe List()
      cpg.parameter.name.l shouldBe List("x")
      cpg.methodReturn.l.size shouldBe 1
    }

    "keep multiple implementations" in CpgStubRemovalFixture("""
        |int foo(int x) { return x; }
        |int foo(int x) {
        | return 0;
        |}
        |""".stripMargin) { cpg =>
      cpg.method.name.l shouldBe List("foo", "foo")
    }

  }

}

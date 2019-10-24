package io.shiftleft.semanticcpg.language

import org.scalatest.{Matchers, WordSpec}
import io.shiftleft.semanticcpg.language._
import io.shiftleft.semanticcpg.testfixtures.CodeToCpgFixture

class LocationTests extends WordSpec with Matchers {

  val code = """
   int my_func(int param1) {
      int x = foo(param1);
   }"""

  "should return location for method" in {
    CodeToCpgFixture().buildCpg(code) { cpg =>
      val locations = cpg.method.name("my_func").location.l
      locations.size shouldBe 1

      val loc = locations.head
      loc.methodFullName shouldBe "my_func"
      loc.methodShortName shouldBe "my_func"
      loc.symbol shouldBe "my_func"
      loc.lineNumber shouldBe Some(2)
      loc.filename should endWith(".c")
      loc.nodeLabel shouldBe "METHOD"
      loc.lineNumberEnd shouldBe Some(4)
    }
  }

  "should return location for parameter" in {
    CodeToCpgFixture().buildCpg(code) { cpg =>
      val locations = cpg.parameter.name("param1").location.l
      locations.size shouldBe 1

      val loc = locations.head
      loc.methodFullName shouldBe "my_func"
      loc.methodShortName shouldBe "my_func"
      loc.symbol shouldBe "param1"
      loc.lineNumber shouldBe Some(2)
      loc.filename should endWith(".c")
      loc.nodeLabel shouldBe "METHOD_PARAMETER_IN"
    }
  }

  "should return location for return parameter" in {
    CodeToCpgFixture().buildCpg(code) { cpg =>
      val locations = cpg.method.name("my_func").methodReturn.location.l
      locations.size shouldBe 1

      val loc = locations.head
      loc.methodFullName shouldBe "my_func"
      loc.methodShortName shouldBe "my_func"
      loc.symbol shouldBe "$ret"
      loc.lineNumber shouldBe Some(2)
      loc.filename should endWith(".c")
      loc.nodeLabel shouldBe "METHOD_RETURN"
    }
  }

  "should return location for call" in {
    CodeToCpgFixture().buildCpg(code) { cpg =>
      val locations = cpg.call.name("foo").location.l
      locations.size shouldBe 1

      val loc = locations.head
      loc.methodFullName shouldBe "my_func"
      loc.methodShortName shouldBe "my_func"
      loc.symbol shouldBe "foo(param1)"
      loc.lineNumber shouldBe Some(3)
      loc.filename should endWith(".c")
      loc.nodeLabel shouldBe "CALL"
    }
  }

  "should return location for identifier" in {
    CodeToCpgFixture().buildCpg(code) { cpg =>
      val locations = cpg.identifier.name("x").location.l
      locations.size shouldBe 1

      val loc = locations.head
      loc.methodFullName shouldBe "my_func"
      loc.methodShortName shouldBe "my_func"
      loc.symbol shouldBe "x"
      loc.lineNumber shouldBe Some(3)
      loc.filename should endWith(".c")
      loc.nodeLabel shouldBe "IDENTIFIER"
    }
  }

}

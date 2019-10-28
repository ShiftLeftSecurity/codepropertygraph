package io.shiftleft.semanticcpg.codedumper

import io.shiftleft.semanticcpg.testfixtures.CodeToCpgFixture
import org.scalatest.{Matchers, WordSpec}
import io.shiftleft.semanticcpg.language._

class CodeDumperTests extends WordSpec with Matchers {

  val code = """
                |int my_func(int param1) {
                |   int x = foo(param1);
                |}""".stripMargin

  CodeToCpgFixture(code) { cpg =>
    "should return empty string for empty traversal" in {
      CodeDumper.dump(cpg.method.name("notinthere")) shouldBe ""
    }

    "should be able to dump complete function" in {
      val query = cpg.method.name("my_func")
      val code = CodeDumper.dump(query)
      code should startWith(CodeDumper.arrow.toString)
      code.contains("foo(param1)") shouldBe true
      code should endWith("}")
    }

    "should dump method with arrow for expression (a call)" in {
      val query = cpg.call.name("foo")
      val code = CodeDumper.dump(query)
      code should startWith("int")
      code.matches(".*" + CodeDumper.arrow + ".*" + "int x = foo" + ".*")
      code should endWith("}")
    }

    "methodCode should return nothing on invalid filename" in {
      CodeDumper.code("foo", 1, 2) shouldBe ""
    }

  }

}

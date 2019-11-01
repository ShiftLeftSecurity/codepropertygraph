package io.shiftleft.semanticcpg.codedumper

import java.util.regex.Pattern

import io.shiftleft.semanticcpg.testfixtures.CodeToCpgFixture
import org.scalatest.{Matchers, WordSpec}
import io.shiftleft.semanticcpg.language._

class CodeDumperTests extends WordSpec with Matchers {

  val code = """
                | // A comment
                |int my_func(int param1) {
                |   int x = foo(param1);
                |}""".stripMargin

  CodeToCpgFixture(code) { cpg =>
    "should return empty string for empty traversal" in {
      CodeDumper
        .dump(cpg.method.name("notinthere"), false)
        .mkString("\n") shouldBe ""
    }

    "should be able to dump complete function" in {
      val query = cpg.method.name("my_func")
      val code = CodeDumper.dump(query, false).mkString("\n")
      code should startWith("int my_func")
      code should include("foo(param1)")
      code should endWith("}")
    }

    "should dump method with arrow for expression (a call)" in {
      val query = cpg.call.name("foo")
      val code = CodeDumper.dump(query, false).mkString("\n")
      code should startWith("int")
      code should include regex (".*" + "int x = foo" + ".*" + Pattern.quote(CodeDumper.arrow.toString) + ".*")
      code should endWith("}")
    }

    "methodCode should return nothing on invalid filename" in {
      CodeDumper.code("foo", 1, 2) shouldBe ""
    }

    "should allow dumping via .dump" in {
      val code = cpg.method.name("my_func").dumpRaw.mkString("\n")
      code should startWith("int my_func")
    }

    "should allow dumping callIn" in {
      implicit val resolver: ICallResolver = NoResolve
      val code = cpg.method.name("foo").callIn.dumpRaw.mkString("\n")
      code should startWith("int")
    }

  }

}

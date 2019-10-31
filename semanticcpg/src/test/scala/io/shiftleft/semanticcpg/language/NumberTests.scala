package io.shiftleft.semanticcpg.language

import io.shiftleft.semanticcpg.testfixtures.CodeToCpgFixture
import org.scalatest.{Matchers, WordSpec}

class NumberTests extends WordSpec with Matchers {

  val code = """
       int main(int argc, char **argv) { }
       int foo(int x) {}
    """

  CodeToCpgFixture(code) { cpg =>

    "should allow retrieving number of parameters for methods" in {
      cpg.method.map(_.start.parameter.num.head).toSet shouldBe Set(1,2)
    }

    "should allow retrieving methods by number of parameters" in {
      cpg.method.filter(_.parameter.num(1)).name.l shouldBe List("foo")
      cpg.method.filter(_.parameter.num(2)).name.l shouldBe List("main")
    }
  }

}

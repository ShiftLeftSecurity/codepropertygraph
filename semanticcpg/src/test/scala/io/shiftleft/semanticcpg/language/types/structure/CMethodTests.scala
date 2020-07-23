package io.shiftleft.semanticcpg.language.types.structure

import org.scalatest.{Matchers, WordSpec}
import io.shiftleft.semanticcpg.language._
import io.shiftleft.semanticcpg.testfixtures.CodeToCpgFixture

/**
  * Language primitives for navigating method stubs
  * */
class CMethodTests extends WordSpec with Matchers {

  CodeToCpgFixture("""
      |int main(int argc, char **argv) {
      |}""".stripMargin) { cpg =>
    "should return correct function/method name" in {
      cpg.method.name.toSet shouldBe Set("main")
    }

    "should return correct line number" in {
      cpg.method.lineNumber.l shouldBe List(2)
    }

    "should return correct end line number" in {
      cpg.method.lineNumberEnd.l shouldBe List(3)
    }

    "should return correct number of lines" in {
      cpg.method.numberOfLines.l shouldBe List(2)
    }

    "should have correct type signature" in {
      cpg.method.signature.toSet shouldBe Set("int(int,char * *)")
    }

    "should return correct number of parameters" in {
      cpg.parameter.name.toSet shouldBe Set("argc", "argv")
      cpg.method.name("main").parameter.name.toSet shouldBe Set("argc", "argv")
    }

    "should return correct parameter types" in {
      cpg.parameter.name("argc").evalType.l shouldBe List("int")
      cpg.parameter.name("argv").evalType.l shouldBe List("char * *")
    }

    "should return correct return type" in {
      cpg.methodReturn.evalType.l shouldBe List("int")
      cpg.method.name("main").methodReturn.evalType.l shouldBe List("int")
      cpg.parameter.name("argc").method.methodReturn.evalType.l shouldBe List("int")
    }

    "should return a filename for method 'main'" in {
      cpg.method.name("main").file.name.l should not be empty
    }

    "should allow filtering by number of parameters" in {
      cpg.method.where(_.parameter.size == 2).name.l shouldBe List("main")
      cpg.method.where(_.parameter.size == 1).name.l shouldBe List()
    }
  }

  CodeToCpgFixture("int foo(); int bar() { return woo(); }") { cpg =>
    "should identify method as stub" in {
      cpg.method.isStub.name.toSet shouldBe Set("foo", "woo")
      cpg.method.isNotStub.name.l shouldBe List("bar")
    }
  }

}

package io.shiftleft.fuzzyc2cpg.standard

import io.shiftleft.fuzzyc2cpg.testfixtures.FuzzyCCodeToCpgSuite
import io.shiftleft.semanticcpg.language._

class MethodTests extends FuzzyCCodeToCpgSuite {

  override val code =
    """
      |int main(int argc, char **argv) {
      |}""".stripMargin

  "should contain exactly one method node with correct fields" in {
    cpg.method.l match {
      case List(x) =>
        x.name shouldBe "main"
        x.fullName shouldBe "main"
        x.code shouldBe "int main (int argc,char **argv)"
        x.signature shouldBe "int main (int,char * *)"
      case _ => fail()
    }
  }

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

  "should have correct method signature" in {
    cpg.method.signature.toSet shouldBe Set("int main (int,char * *)")
  }

  "should return correct number of parameters" in {
    cpg.parameter.name.toSet shouldBe Set("argc", "argv")
    cpg.method.name("main").parameter.name.toSet shouldBe Set("argc", "argv")
  }

  "should return correct parameter types" in {
    cpg.parameter.name("argc").typeFullName.l shouldBe List("int")
    cpg.parameter.name("argv").typeFullName.l shouldBe List("char * *")
  }

  "should return correct return type" in {
    cpg.methodReturn.typeFullName.l shouldBe List("int")
    cpg.method.name("main").methodReturn.typeFullName.l shouldBe List("int")
    cpg.parameter.name("argc").method.methodReturn.typeFullName.l shouldBe List("int")
  }

  "should return a filename for method 'main'" in {
    cpg.method.name("main").file.name.l should not be empty
  }

  "should allow filtering by number of parameters" in {
    cpg.method.filter(_.parameter.size == 2).name.l shouldBe List("main")
    cpg.method.filter(_.parameter.size == 1).name.l shouldBe List()
  }
}

class CMethodTests2 extends FuzzyCCodeToCpgSuite {
  override val code = "int foo(); int bar() { return woo(); }"

  "should identify method as stub" in {
    cpg.method.isStub.name.toSet shouldBe Set("foo", "woo")
    cpg.method.isNotStub.name.l shouldBe List("bar")
  }
}

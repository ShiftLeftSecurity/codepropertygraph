package io.shiftleft.queryprimitives.steps

import org.scalatest.{Matchers, WordSpec}

/**
  * Language primitives for navigating method stubs
  * */
class MethodTests2 extends WordSpec with Matchers {

  val code = """
       int main(int argc, char **argv) { }
    """

  "should return correct function/method name" in {
    CodeToCpgFixture().buildCpg(code) { cpg =>
      cpg.method.name.toSet shouldBe Set("main")
    }
  }

  "should have correct type signature" in
    CodeToCpgFixture().buildCpg(code) { cpg =>
      cpg.method.signature.toSet shouldBe Set("int(int,char * *)")
    }

  "should return correct number of parameters" in
    CodeToCpgFixture().buildCpg(code) { cpg =>
      cpg.parameter.name.toSet shouldBe Set("argc", "argv")
      cpg.method.name("main").parameter.name.toSet shouldBe Set("argc", "argv")
    }

  "should return correct parameter types" in
    CodeToCpgFixture().buildCpg(code) { cpg =>
      cpg.parameter.name("argc").evalType.l shouldBe List("int")
      cpg.parameter.name("argv").evalType.l shouldBe List("char * *")
    }

  "should return correct return type" in
    CodeToCpgFixture().buildCpg(code) { cpg =>
      cpg.methodReturn.evalType.l shouldBe List("int")
      cpg.method.name("main").methodReturn.evalType.l shouldBe List("int")
      cpg.parameter.name("argc").method.methodReturn.evalType.l shouldBe List("int")
    }

  "should return a filename for method 'main'" in
    CodeToCpgFixture().buildCpg(code) { cpg =>
      cpg.method.name("main").file.name.l should not be empty
    }

}

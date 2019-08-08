package io.shiftleft.queryprimitives.steps

import org.scalatest.{Matchers, WordSpec}

class CallGraphTests extends WordSpec with Matchers {

  implicit val resolver = NoResolve

  val code = """
       int add(int x, int y) {
         return x + y;
       }
       int main(int argc, char **argv) {
         printf("%d\n", add((1+2), 3));
       }
    """

  "should find that add is called by main" in {
    CpgFactory().buildCpg(code) { cpg =>
      cpg.method.name("add").caller.name.toSet shouldBe Set("main")
    }
  }

  "should find that main calls add and others" in {
    CpgFactory().buildCpg(code) { cpg =>
      cpg.method.name("main").callee.name.toSet shouldBe Set("add", "printf", "<operator>.addition")
    }
  }

  "should find three outgoing calls for main" in {
    CpgFactory().buildCpg(code) { cpg =>
      cpg.method.name("main").callOut.code.toSet shouldBe
        Set("1+2", "add((1+2), 3)", "printf(\"%d\\n\", add((1+2), 3))")
    }
  }

  "should find one callsite for add" in {
    CpgFactory().buildCpg(code) { cpg =>
      cpg.method.name("add").callIn.code.toSet shouldBe Set("add((1+2), 3)")
    }
  }

  "should find that argument '1+2' is passed to parameter 'x'" in {
    CpgFactory().buildCpg(code) { cpg =>
      cpg.parameter.name("x").argument().code.toSet shouldBe Set("1+2")
    }
  }

  "should allow traversing from argument to formal parameter" in {
    CpgFactory().buildCpg(code) { cpg =>
      cpg.argument.toParameter.name.toSet should not be empty
    }
  }
}

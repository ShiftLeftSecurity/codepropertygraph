package io.shiftleft.queryprimitives.steps

import org.scalatest.{Matchers, WordSpec}

class SampleDocuTest extends WordSpec with Matchers {

  val code = """
       int main(int argc, char **argv) { }
    """

  CpgFactory().buildCpg(code) { cpg =>
    "should return `main` as the only method" in {
      cpg.method.name.toSet shouldBe Set("main")
    }
  }

}

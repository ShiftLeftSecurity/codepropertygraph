package io.shiftleft.semanticcpg.language

import io.shiftleft.semanticcpg.testfixtures.CodeToCpgFixture
import org.scalatest.{Matchers, WordSpec}

class SampleDocuTest extends WordSpec with Matchers {

  val code = """
       int main(int argc, char **argv) { }
    """

  CodeToCpgFixture().buildCpg(code) { cpg =>
    "should return `main` as the only method" in {
      cpg.method.name.toSet shouldBe Set("main")
    }
  }

}

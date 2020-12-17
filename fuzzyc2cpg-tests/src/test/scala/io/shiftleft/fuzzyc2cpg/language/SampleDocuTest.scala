package io.shiftleft.fuzzyc2cpg.language

import io.shiftleft.fuzzyc2cpg.testfixtures.CodeToCpgSuite
import io.shiftleft.semanticcpg.language._

class SampleDocuTest extends CodeToCpgSuite {

  override val code = """
       int main(int argc, char **argv) { }
    """

  "should return `main` as the only method" in {
    cpg.method.name.toSet shouldBe Set("main")
  }

}

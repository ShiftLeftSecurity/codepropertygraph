package io.shiftleft.semanticcpg.language

import io.shiftleft.semanticcpg.testfixtures.CodeToCpgSuite

class SampleDocuTest extends CodeToCpgSuite {

  override val code = """
       int main(int argc, char **argv) { }
    """

  "should return `main` as the only method" in {
    cpg.method.name.toSet shouldBe Set("main")
  }

}

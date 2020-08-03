package io.shiftleft.dataflowengineoss.dotgenerator

import io.shiftleft.semanticcpg.testfixtures.CodeToCpgSuite

class DotPdgGeneratorTests extends CodeToCpgSuite {

  override val code =
    """
      |int foo(int param1, char *param2) {
      |   int i = 0;
      |   while(i < 10) {
      |     printf("Hello World");
      |     i++;
      |   }
      |   return 0;
      |}
      |""".stripMargin

  "A CfgDotGenerator" should {

    "create a dot graph" in {}
  }

}

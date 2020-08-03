package io.shiftleft.dataflowengineoss.dotgenerator

import io.shiftleft.dataflowengineoss.language.DataFlowCodeToCpgSuite
import io.shiftleft.semanticcpg.language._
import io.shiftleft.dataflowengineoss.language._

class DotPdgGeneratorTests extends DataFlowCodeToCpgSuite {

  override val code =
    """
      |int foo(int param1, char *param2) {
      |   int i = 0;
      |   while(i < 10) {
      |     char *boo = moo("%d\n", i + bar(i));
      |     printf(boo);
      |     i++;
      |   }
      |   return 0;
      |}
      |""".stripMargin

  "A CfgDotGenerator" should {

    "create a dot graph" in {

      cpg.method.name("foo").dotPdg.l.foreach(println)

    }
  }

}

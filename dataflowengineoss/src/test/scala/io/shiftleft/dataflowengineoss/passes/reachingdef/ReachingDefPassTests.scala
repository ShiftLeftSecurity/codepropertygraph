package io.shiftleft.dataflowengineoss.passes.reachingdef

import io.shiftleft.codepropertygraph.generated.EdgeKeys
import io.shiftleft.dataflowengineoss.language.DataFlowCodeToCpgSuite
import io.shiftleft.semanticcpg.language._
import overflowdb.traversal._

class ReachingDefPassTests extends DataFlowCodeToCpgSuite {

  override val code =
    """
      |int foo(int param1, char *param2) {
      |int i = 0;
      |while(i < 10) {
      |char *boo = moo("%d\n", i + bar(i));
      |printf(boo);
      |i++;
      |}
      |return 0;
      |}
      |""".stripMargin

  "should VARIABLE edge labels on REACHING_DEF edges" in {
    cpg
      .call("printf")
      .argument(1)
      .inE("REACHING_DEF")
      .property(EdgeKeys.VARIABLE)
      .l
      .contains("boo") shouldBe true
  }

}

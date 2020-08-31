package io.shiftleft.dataflowengineoss.passes.reachingdef

import io.shiftleft.dataflowengineoss.language.DataFlowCodeToCpgSuite
import io.shiftleft.semanticcpg.language._
import overflowdb._
import overflowdb.traversal._
import gremlin.scala._

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
      .raw
      .inE("REACHING_DEF")
      .value[String]("VARIABLE")
      .toList
      .contains("boo") shouldBe true
  }

}

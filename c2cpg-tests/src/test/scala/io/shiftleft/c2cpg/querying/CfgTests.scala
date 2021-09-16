package io.shiftleft.c2cpg.querying

import io.shiftleft.c2cpg.testfixtures.{CCodeToCpgSuite, DataFlowCodeToCpgSuite}
import io.shiftleft.codepropertygraph.generated.DispatchTypes
import io.shiftleft.codepropertygraph.generated.nodes.Call
import io.shiftleft.dataflowengineoss.language.toExtendedCfgNode
import io.shiftleft.semanticcpg.language._

class CfgTests extends CCodeToCpgSuite {

  override val code: String =
    """
      | int foo(int x, int y) {
      |  if (y < 10)
      |    goto end;
      |  if (x < 10) {
      |    sink(x);
      |  }
      |  end:
      |  printf("foo");
      | }
    """.stripMargin

  "should find that sink is control dependent on condition" in {
    val controllers = cpg.call("sink").controlledBy.isCall.toSet
    controllers.map(_.code) should contain("y < 10")
    controllers.map(_.code) should contain("x < 10")
  }

  "should find that first if controls `sink`" in {
    cpg.controlStructure.condition.code("y < 10").controls.isCall.name("sink").l.size shouldBe 1
  }

  "should find sink(x) does not dominate anything" in {
    cpg.call("sink").dominates.l.size shouldBe 0
  }

  "should find sink(x) is dominated by `x < 10` and `y < 10`" in {
    cpg.call("sink").dominatedBy.isCall.code.toSet shouldBe Set("x < 10", "y < 10")
  }

  "should find that printf post dominates all" in {
    cpg.call("printf").postDominates.size shouldBe 12
  }

  "should find that method does not post dominate anything" in {
    cpg.method("foo").postDominates.l.size shouldBe 0
  }
}

class CfgMacroTests extends DataFlowCodeToCpgSuite {
  override val code: String =
    """
       #define MP4_GET4BYTES( dst ) MP4_GETX_PRIVATE( dst, GetDWBE(p_peek), 4 )
       #define MP4_GETX_PRIVATE(dst, code, size) \
    do \
    { \
        if( (i_read) >= (size) ) \
        { \
            dst = (code); \
            p_peek += (size); \
            i_read -= (size); \
        } \
        else \
        { \
            dst = 0; \
            i_read = 0; \
        } \
    } while(0)

    int foo() {
       unsigned int x;
       MP4_GET4BYTES(x);
       sink(x);
    }

    """.stripMargin

  "should create correct CFG for macro expansion and find data flow" in {
    val List(callToMacro: Call) = cpg.method("foo").call.dispatchType(DispatchTypes.INLINED).l
    callToMacro.argument.code.l shouldBe List("x")
    callToMacro.cfgNext.code.toSet shouldBe Set("x", "i_read")
    val source = cpg.method("foo").call.name("MP4_GET4BYTES").argument(1).l
    val sink = cpg.method("foo").call.name("sink").argument(1).l
    sink.reachableByFlows(source).l.foreach(println)
  }
}

package io.shiftleft.dataflowengineoss.passes

import io.shiftleft.dataflowengineoss.language.DataFlowCodeToCpgSuite
import io.shiftleft.semanticcpg.language._
import io.shiftleft.dataflowengineoss.language._
import io.shiftleft.codepropertygraph.generated.nodes

class ReachingDefPassTests extends DataFlowCodeToCpgSuite {
  override val code =
    """
      | int foo(int x) {
      | sink(x);
      | }
      |""".stripMargin

  "Test0 : should find flow from parameter to argument" in {
    val src = cpg.parameter.name("x")
    val snk = cpg.call("sink").argument(1)
    val flows = snk.reachableByFlows(src).l
    flows.size shouldBe 1
    flows.head.elements match {
      case List(first: nodes.MethodParameterIn, arg: nodes.Identifier) =>
        first.name shouldBe "x"
        arg.code shouldBe "x"
      case _ => fail
    }

    cpg.parameter.name("x").ddgNext.l match {
      case List(x: nodes.Identifier) => x.code shouldBe "x"
      case _                         => fail
    }
  }
}

class ReachingDefPassTests1 extends DataFlowCodeToCpgSuite {

  override val code =
    """
      | int foo(int x) {
      | x = 10;
      | sink(x);
      | }
      |""".stripMargin

  "Test 1.1: should return no flow when variable is overwritten" in {
    val src = cpg.parameter.name("x").l
    src.size shouldBe 1
    src.start.ddgNext.size shouldBe 0
    val snk = cpg.call("sink").argument(1).l
    snk.size shouldBe 1
    snk.start.reachableByFlows(src.start).l.size shouldBe 0
    snk.start.reachableByFlows(cpg.call.name("sink")).size shouldBe 0
  }

  "Test 1.2: should return a flow from assignment's `x` to sink" in {
    val src = cpg.assignment.target.l
    src.size shouldBe 1
    val snk = cpg.call("sink").argument(1).l
    snk.start.reachableByFlows(src.start).size shouldBe 1
  }

  "Test 1.3: should also identify flow if sink is the call" in {
    val src = cpg.assignment.target.l
    src.size shouldBe 1
    val snk = cpg.call("sink")
    println(snk.reachableByFlows(src.start).l)
  }

  "Test 1.4 should find flow from identifier to itself" in {
    val src = cpg.assignment.target.l
    val snk = src

    snk.start.reachableByFlows(src.start).l match {
      case List(p) =>
        p.elements.size shouldBe 1
        p.elements.head.asInstanceOf[nodes.Identifier].name shouldBe "x"
      case _ => fail
    }
  }

  "Test 1.5 should find flow from identifier to parent call" in {
    val src = cpg.assignment.source.l
    val snk = cpg.assignment.l
    snk.start.reachableByFlows(src.start).size shouldBe 1
  }

}

class ReachingDefPassTests2 extends DataFlowCodeToCpgSuite {

  override val code =
    """
      |int foo() {
      | int x = 0x10;
      | int y = x + 2;
      | sink(y);
      |}
      |""".stripMargin

  "should find flow from declaration to sink" in {
    val src = cpg.identifier.name("x").filter(_.inCall.code(".*0x10.*"))
    val snk = cpg.call("sink").argument
    snk.reachableByFlows(src).foreach { flow =>
      flow.elements.foreach(println); println("===")
    }
  }

}

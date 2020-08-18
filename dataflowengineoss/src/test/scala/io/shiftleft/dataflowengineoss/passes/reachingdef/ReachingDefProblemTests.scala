package io.shiftleft.dataflowengineoss.passes.reachingdef

import io.shiftleft.dataflowengineoss.passes.propagateedges.PropagateEdgeTestSuite
import io.shiftleft.dataflowengineoss.semanticsloader.{FlowSemantic, Semantics}
import io.shiftleft.semanticcpg.language._
import io.shiftleft.codepropertygraph.generated.{Operators, nodes}

class ReachingDefProblemTests1 extends ReachingDefProblemSuite {
  override val code =
    """
      |int foo(int x, int y) {}
      |""".stripMargin

  "ReachingDefFlowGraph" should {
    "be correct for method with params and no method body" in {
      method.parameter.l.flatMap(flowGraph.succ).toSet shouldBe Set(flowGraph.exitNode)
      method.start.cfgFirst.head shouldBe method.methodReturn
      method.parameter.l.flatMap(flowGraph.pred).toSet shouldBe Set(flowGraph.entryNode)
      val file = method.start.file.head
      assertThrows[NoSuchElementException] { flowGraph.succ(file) }
      assertThrows[NoSuchElementException] { flowGraph.pred(file) }
    }
  }
}

class ReachingDefProblemTests2 extends ReachingDefProblemSuite {
  override val semantics: Semantics = Semantics(
    List(
      FlowSemantic(Operators.assignment, List((2, 1)))
    ))

  override val code =
    """
      |int foo(int x, int y) {
      |  int x = 10;
      |  int ret = escape(x,y);
      |}
      |""".stripMargin

  "ReachingDefFlowGraph" should {
    "be correct for method with params and method body" in {
      val firstNonParamNode = method.start.cfgFirst.head
      method.parameter.l.flatMap(flowGraph.pred).toSet shouldBe Set(flowGraph.entryNode)
      method.parameter.l.flatMap(flowGraph.succ).toSet shouldBe Set(firstNonParamNode)
      flowGraph.pred(method.methodReturn) match {
        case List(c: nodes.Call) => c.code shouldBe "ret = escape(x,y)"
        case _                   => fail
      }
    }
  }

  "ReachingDefTransferFunction's gen set" should {
    "contain definitions of parameters for each parameter" in {
      method.parameter.l.map(transfer.gen(_)) shouldBe method.parameter.map(Set(_)).l
    }
    "contain definition of return value for unannotated method" in {
      val call = method.start.call.name("escape").head
      transfer.gen(call) shouldBe Set(call)
    }

    "contain only correct argument for annotated method" in {
      val call = method.start.call.name(Operators.assignment).head
      transfer.gen(call) shouldBe Set(call.argument(1))
    }

  }

}

class ReachingDefProblemSuite extends PropagateEdgeTestSuite {
  override val semantics: Semantics = Semantics(List())

  var method: nodes.Method = _
  var flowGraph: ReachingDefFlowGraph = _
  var transfer: ReachingDefTransferFunction = _

  override def beforeAll(): Unit = {
    super.beforeAll()
    method = cpg.method.name("foo").head
    flowGraph = new ReachingDefFlowGraph(method)
    transfer = new ReachingDefTransferFunction(method)
    flowGraph.entryNode shouldBe method
    flowGraph.exitNode shouldBe method.methodReturn
  }
}

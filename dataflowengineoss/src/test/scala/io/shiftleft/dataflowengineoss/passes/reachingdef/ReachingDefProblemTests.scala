package io.shiftleft.dataflowengineoss.passes.reachingdef

import io.shiftleft.codepropertygraph.generated.{Operators, nodes}
import io.shiftleft.dataflowengineoss.semanticsloader.{FlowSemantic, Semantics}
import io.shiftleft.semanticcpg.language._
import io.shiftleft.semanticcpg.testfixtures.CodeToCpgSuite

class ReachingDefProblemTests1 extends ReachingDefProblemSuite {
  override val code =
    """
      |int foo(int x, int y) {}
      |""".stripMargin

  "ReachingDefFlowGraph" should {
    "be correct for method with params and no method body" in {
      method.parameter.l.flatMap(flowGraph.succ).toSet shouldBe Set(flowGraph.exitNode)
      method.cfgFirst.head shouldBe method.methodReturn
      method.parameter.l.flatMap(flowGraph.pred).toSet shouldBe Set(flowGraph.entryNode)
      val file = method.file.head
      assertThrows[NoSuchElementException] { flowGraph.succ(file) }
      assertThrows[NoSuchElementException] { flowGraph.pred(file) }
    }
  }
}

class ReachingDefProblemTests2 extends ReachingDefProblemSuite {
  override val semantics: Semantics = Semantics
    .fromList(
      List(
        FlowSemantic(Operators.assignment, List((2, 1)))
      ))

  override val code =
    """
      |int foo(int x, int y) {
      |  x = 10;
      |  int ret = escape(x,y);
      |}
      |""".stripMargin

  "ReachingDefFlowGraph" should {
    "be correct for method with params and method body" in {
      val firstNonParamNode = method.cfgFirst.head
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
      method.parameter.l.map(transfer.gen(_)) shouldBe method.parameter.map(x => Set(Definition.fromNode(x))).l
    }
    "contain definition of return value and all arguments for unannotated method" in {
      val call = method.call.name("escape").head
      transfer.gen(call) shouldBe Set(Definition.fromNode(call)) ++ method.call
        .name("escape")
        .argument
        .map(Definition.fromNode)
        .toSet
    }

  }

  "ReachingDefTransferFunction's kill set" should {
    "contain kill for parameter at assignment to x" in {
      val call = method.call.name(Operators.assignment).head
      transfer.kill(call).contains(Definition.fromNode(method.parameter.name("x").head))
    }
  }

}

class ReachingDefProblemSuite extends CodeToCpgSuite {

  val semantics: Semantics = Semantics.empty

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

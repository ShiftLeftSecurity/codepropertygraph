package io.shiftleft.dataflowengineoss.language
import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.semanticcpg.language._
import io.shiftleft.dataflowengineoss.semanticsloader.Semantics

class TrackingPointTests extends DataFlowCodeToCpgSuite {

  implicit val resolver: NoResolve.type = NoResolve
  implicit val s: Semantics = semantics

  override val code =
    """
      |int foo(int y) {
      | int x = source();
      | x += y;
      | sink(y);
      |}
      |""".stripMargin

  "allow traversing from argument of sink back to param via `ddgIn`" in {
    cpg.method("sink").parameter.argument.ddgIn.l match {
      case List(param: nodes.MethodParameterIn) =>
        param.name shouldBe "y"
      case _ => fail
    }
  }

  "allow traversing from argument node to param via `ddgIn`" in {
    cpg.method("sink").parameter.argument.l match {
      case List(t: nodes.TrackingPoint) =>
        t.code shouldBe "y"
        t.ddgIn.l match {
          case List(param: nodes.MethodParameterIn) =>
            param.name shouldBe "y"
        }
      case _ => fail
    }
  }

  "allow traversing from argument back to param while inspecting edge" in {
    cpg.method("sink").parameter.argument.ddgInPathElem.l match {
      case List(pathElem) =>
        pathElem.inEdgeLabel shouldBe "y"
        pathElem.node.isInstanceOf[nodes.MethodParameterIn] shouldBe true
      case _ => fail
    }
  }

}

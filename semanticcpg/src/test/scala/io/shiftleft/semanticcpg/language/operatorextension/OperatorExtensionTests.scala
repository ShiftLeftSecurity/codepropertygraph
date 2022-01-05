package io.shiftleft.semanticcpg.language.operatorextension

import io.shiftleft.codepropertygraph.generated.nodes.{Identifier, NewIdentifier}
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, Operators}
import io.shiftleft.semanticcpg.language._
import io.shiftleft.semanticcpg.testing.MockCpg
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class OperatorExtensionTests extends AnyWordSpec with Matchers {

  private val methodName = "method"

  "NodeTypeStarters" should {
    "allow retrieving assignments" in {
      val cpg = mockCpgWithCallAndCode(Operators.assignment, "x = 10")
      val List(x: OpNodes.Assignment) = cpg.assignment.l
      x.name shouldBe Operators.assignment
      x.code shouldBe "x = 10"
    }

    "allow retrieving arithmetic expressions" in {
      val cpg = mockCpgWithCallAndCode(Operators.addition, "10 + 20")
      val List(x: OpNodes.Arithmetic) = cpg.arithmetic.l
      x.name shouldBe Operators.addition
      x.code shouldBe "10 + 20"
    }

    "include '+=' in both assignments and arithmetics" in {
      val cpg = mockCpgWithCallAndCode(Operators.assignmentPlus, "x += 10")
      val List(y: OpNodes.Arithmetic) = cpg.arithmetic.l
      val List(x: OpNodes.Assignment) = cpg.assignment.l
      x.id shouldBe y.id
      x.name shouldBe Operators.assignmentPlus
      x.code shouldBe "x += 10"
    }

    "allow retrieving array accesses" in {
      val cpg = mockCpgWithCallAndCode(Operators.indexAccess, "x[i]")
      val List(x: OpNodes.ArrayAccess) = cpg.arrayAccess.l
      x.name shouldBe Operators.indexAccess
      x.code shouldBe "x[i]"
    }
  }

  "Assignment" should {
    "allow traversing to target/source for assignment with two arguments" in {
      val cpg = MockCpg()
        .withMethod(methodName)
        .withCallInMethod(methodName, Operators.assignment, Some("x = y"))
        .withCustom { (graph, cpg) =>
          val List(call) = cpg.call(Operators.assignment).l
          val arg1 = NewIdentifier().name("x").argumentIndex(1)
          val arg2 = NewIdentifier().name("y").argumentIndex(2)
          graph.addNode(arg1)
          graph.addNode(arg2)
          graph.addEdge(call, arg1, EdgeTypes.ARGUMENT)
          graph.addEdge(call, arg2, EdgeTypes.ARGUMENT)
        }
        .cpg

      val List(target: Identifier) = cpg.assignment.target.l
      target.name shouldBe "x"
      val List(source: Identifier) = cpg.assignment.source.l
      source.name shouldBe "y"

      val List((x: Identifier, y: Identifier)) = cpg.assignment.map(x => (x.target, x.source)).l
      x.name shouldBe "x"
      y.name shouldBe "y"
    }
  }

  private def mockCpgWithCallAndCode(name: String, code: String) =
    MockCpg()
      .withMethod(methodName)
      .withCallInMethod(methodName, name, Some(code))
      .cpg

}

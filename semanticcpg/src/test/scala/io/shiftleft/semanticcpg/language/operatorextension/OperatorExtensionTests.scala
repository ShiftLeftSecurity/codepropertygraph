package io.shiftleft.semanticcpg.language.operatorextension

import io.shiftleft.codepropertygraph.generated.Operators
import io.shiftleft.semanticcpg.language._
import io.shiftleft.semanticcpg.testing.MockCpg
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class OperatorExtensionTests extends AnyWordSpec with Matchers {

  private val methodName = "method"

  "NodeTypeStarters" should {

    "allow retrieving assignments" in {
      val cpg = MockCpg()
        .withMethod(methodName)
        .withCallInMethod(methodName, Operators.assignment, Some("x = 10"))
        .cpg

      val List(x: opnodes.Assignment) = cpg.assignment.l
      x.name shouldBe Operators.assignment
      x.code shouldBe "x = 10"
    }

    "allow retrieving arithmetic expressions" in {
      val cpg = MockCpg()
        .withMethod(methodName)
        .withCallInMethod(methodName, Operators.addition, Some("10 + 20"))
        .cpg

      val List(x) = cpg.arithmetic.l
      x.name shouldBe Operators.addition
      x.code shouldBe "10 + 20"
    }

    "include '+=' in both assignments and arithmetics" in {
      val cpg = MockCpg()
        .withMethod(methodName)
        .withCallInMethod(methodName, Operators.assignmentPlus, Some("x += 10"))
        .cpg

      val List(y: opnodes.Arithmetic) = cpg.arithmetic.l
      val List(x: opnodes.Assignment) = cpg.assignment.l

      x.id shouldBe y.id
      x.name shouldBe Operators.assignmentPlus
      x.code shouldBe "x += 10"
    }

  }

}

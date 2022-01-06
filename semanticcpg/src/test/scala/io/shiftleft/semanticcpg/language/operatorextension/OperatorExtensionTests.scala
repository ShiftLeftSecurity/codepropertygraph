package io.shiftleft.semanticcpg.language.operatorextension

import io.shiftleft.codepropertygraph.generated.Operators
import io.shiftleft.codepropertygraph.generated.nodes.Identifier
import io.shiftleft.semanticcpg.language.operatorextension.OpNodes.ArrayAccess
import io.shiftleft.semanticcpg.testing.MockCpg
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import io.shiftleft.semanticcpg.language._

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

    "allow retrieving field accesses" in {
      val cpg = mockCpgWithCallAndCode(Operators.fieldAccess, "x.y")
      val List(x: OpNodes.ArrayAccess) = cpg.arrayAccess.l
      x.name shouldBe Operators.fieldAccess
      x.code shouldBe "x.y"
    }

    def mockCpgWithCallAndCode(name: String, code: String) =
      MockCpg()
        .withMethod(methodName)
        .withCallInMethod(methodName, name, Some(code))
        .cpg

  }

  "Assignment" should {
    "allow traversing to target/source for assignment with two arguments" in {
      val cpg = MockCpg()
        .withMethod(methodName)
        .withCallInMethod(methodName, Operators.assignment)
        .withIdentifierArgument(Operators.assignment, "x", 1)
        .withIdentifierArgument(Operators.assignment, "y", 2)
        .cpg

      val List(target: Identifier) = cpg.assignment.target.l
      target.name shouldBe "x"
      val List(source: Identifier) = cpg.assignment.source.l
      source.name shouldBe "y"
      val List((x: Identifier, y: Identifier)) = cpg.assignment.map(x => (x.target, x.source)).l
      x.name shouldBe "x"
      y.name shouldBe "y"
    }

    "allow traversing to target/source from assignment with one argument (e.g., 'i++')" in {
      val cpg = MockCpg()
        .withMethod(methodName)
        .withCallInMethod(methodName, Operators.postIncrement)
        .withIdentifierArgument(Operators.postIncrement, "x", 1)
        .cpg

      val List(target: Identifier) = cpg.assignment.target.l
      target.name shouldBe "x"
      val List(source: Identifier) = cpg.assignment.source.l
      source.name shouldBe "x"
      val List((x: Identifier, y: Identifier)) = cpg.assignment.map(x => (x.target, x.source)).l
      x.name shouldBe "x"
      y.name shouldBe "x"
    }

  }

  "Target" should {
    "only traverse to outer most array access for a target.arrayAccess" in {
      val cpg = MockCpg()
        .withMethod(methodName)
        .withCallInMethod(methodName, Operators.assignment)
        .withCallArgument(Operators.assignment, Operators.indexAccess, "x[y[1]]", 1)
        .withIdentifierArgument(Operators.indexAccess, "x", 1)
        .withCallArgument(Operators.indexAccess, Operators.indexAccess, "y[1]", 2)
        .withIdentifierArgument(Operators.assignment, "y", 2)
        .cpg

      val List(x: ArrayAccess) = cpg.assignment.target.arrayAccess.l
      x.name shouldBe Operators.indexAccess
      x.code shouldBe "x[y[1]]"
    }
  }

}

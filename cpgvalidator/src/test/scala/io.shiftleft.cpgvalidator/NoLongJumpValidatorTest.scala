package io.shiftleft.cpgvalidator

import io.shiftleft.OverflowDbTestInstance
import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, Properties, NodeTypes}
import io.shiftleft.cpgvalidator.validators.cfg.NoLongJumpValidator
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import overflowdb._

class NoLongJumpValidatorTest extends AnyWordSpec with Matchers {
  private def withNewBaseCpg[T](fun: Cpg => T): T = {
    val graph = OverflowDbTestInstance.create
    val cpg = Cpg(graph)
    try fun(cpg)
    finally cpg.close()
  }

  "report no cfg edge errors for empty graph" in {
    withNewBaseCpg { cpg =>
      val validator = new NoLongJumpValidator(new ValidationErrorRegistry)
      validator.validate(cpg) shouldBe true
    }
  }

  "report no cfg edge errors for simple correct graph" in {
    withNewBaseCpg { cpg =>
      val validator = new NoLongJumpValidator(new ValidationErrorRegistry)
      val method1 = cpg.graph + (NodeTypes.METHOD, Properties.FULL_NAME -> "method1")
      val method2 = cpg.graph + (NodeTypes.METHOD, Properties.FULL_NAME -> "method2")

      val nodeInMethod1 = cpg.graph + NodeTypes.CALL
      val nodeInMethod2 = cpg.graph + NodeTypes.CALL
      method1 --- EdgeTypes.AST --> nodeInMethod1
      method2 --- EdgeTypes.AST --> nodeInMethod2

      method1 --- EdgeTypes.CFG --> nodeInMethod1
      method2 --- EdgeTypes.CFG --> nodeInMethod2

      validator.validate(cpg) shouldBe true
    }
  }

  "report cfg edge errors for simple incorrect graph" in {
    withNewBaseCpg { cpg =>
      val validator = new NoLongJumpValidator(new ValidationErrorRegistry)
      val method1 = cpg.graph + (NodeTypes.METHOD, Properties.FULL_NAME -> "method1")
      val method2 = cpg.graph + (NodeTypes.METHOD, Properties.FULL_NAME -> "method2")

      val nodeInMethod1 = cpg.graph + NodeTypes.CALL
      val nodeInMethod2 = cpg.graph + NodeTypes.CALL
      method1 --- EdgeTypes.AST --> nodeInMethod1
      method2 --- EdgeTypes.AST --> nodeInMethod2

      method1 --- EdgeTypes.CFG --> nodeInMethod2

      validator.validate(cpg) shouldBe false
    }
  }
}

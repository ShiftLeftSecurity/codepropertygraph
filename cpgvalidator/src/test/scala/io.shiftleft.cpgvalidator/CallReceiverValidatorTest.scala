package io.shiftleft.cpgvalidator

import io.shiftleft.OverflowDbTestInstance
import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.{DispatchTypes, EdgeTypes, NodeTypes, Properties}
import io.shiftleft.cpgvalidator.validators.CallReceiverValidator
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import overflowdb._

class CallReceiverValidatorTest extends AnyWordSpec with Matchers {
  private def withNewBaseCpg[T](fun: Cpg => T): T = {
    val graph = OverflowDbTestInstance.create
    val cpg = Cpg(graph)
    try fun(cpg)
    finally cpg.close()
  }

  "report no call receiver errors for empty graph" in {
    withNewBaseCpg { cpg =>
      val validator = new CallReceiverValidator(new ValidationErrorRegistry)
      validator.validate(cpg) shouldBe true
    }
  }

  "report no call receiver errors for simple correct graph" in {
    withNewBaseCpg { cpg =>
      val validator = new CallReceiverValidator(new ValidationErrorRegistry)
      val rec = cpg.graph + (NodeTypes.IDENTIFIER, Properties.NAME -> "rec")
      val call1 = cpg.graph + (NodeTypes.CALL, Properties.DISPATCH_TYPE -> DispatchTypes.DYNAMIC_DISPATCH)
      val call2 = cpg.graph + (NodeTypes.CALL, Properties.DISPATCH_TYPE -> DispatchTypes.STATIC_DISPATCH)

      call1 --- EdgeTypes.RECEIVER --> rec

      validator.validate(cpg) shouldBe true
    }
  }

  "report no call receiver errors for simple correct graph (with AST edge and argument index)" in {
    withNewBaseCpg { cpg =>
      val validator = new CallReceiverValidator(new ValidationErrorRegistry)
      val rec = cpg.graph + (NodeTypes.IDENTIFIER, Properties.NAME -> "rec", Properties.ARGUMENT_INDEX -> 0)
      val call1 = cpg.graph + (NodeTypes.CALL, Properties.DISPATCH_TYPE -> DispatchTypes.DYNAMIC_DISPATCH)
      val call2 = cpg.graph + (NodeTypes.CALL, Properties.DISPATCH_TYPE -> DispatchTypes.STATIC_DISPATCH)

      call1 --- EdgeTypes.AST --> rec

      validator.validate(cpg) shouldBe true
    }
  }

  "report call receiver errors for simple incorrect graph" in {
    withNewBaseCpg { cpg =>
      val validator = new CallReceiverValidator(new ValidationErrorRegistry)
      val rec = cpg.graph + (NodeTypes.IDENTIFIER, Properties.NAME -> "rec")
      val call1 = cpg.graph + (NodeTypes.CALL, Properties.DISPATCH_TYPE -> DispatchTypes.DYNAMIC_DISPATCH)
      val call2 = cpg.graph + (NodeTypes.CALL, Properties.DISPATCH_TYPE -> DispatchTypes.STATIC_DISPATCH)

      call2 --- EdgeTypes.RECEIVER --> rec

      validator.validate(cpg) shouldBe false
    }
  }
}

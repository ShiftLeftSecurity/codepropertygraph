package io.shiftleft.cpgvalidator

import io.shiftleft.OverflowDbTestInstance
import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.NodeTypes
import io.shiftleft.cpgvalidator.validators.CpgValidator
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import overflowdb._

class CpgValidatorTest extends AnyWordSpec with Matchers {
  private def withNewBaseCpg[T](fun: Cpg => T): T = {
    val graph = OverflowDbTestInstance.create
    val cpg = Cpg(graph)
    try fun(cpg)
    finally cpg.close()
  }

  "CpgValidator reports errors from nested validators" in {
    withNewBaseCpg { cpg =>
      val errorRegistry = new ValidationErrorRegistry()
      val validator = new CpgValidator(errorRegistry)
      cpg.graph + NodeTypes.METHOD
      validator.validate(cpg) shouldBe false
      errorRegistry.getErrorCount shouldBe 11
    }
  }
}

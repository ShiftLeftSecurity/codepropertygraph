package io.shiftleft.semanticcpg.language.types.structure

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.semanticcpg.testfixtures.ExistingCpgFixture
import io.shiftleft.semanticcpg.language._
import org.scalatest.{Matchers, WordSpec}

class BindingTests extends WordSpec with Matchers {

  "Binding steps" should ExistingCpgFixture("binding") { fixture =>
    "expand from BindingTest class to one method binding" in {
      val queryResult: List[nodes.Binding] =
        fixture.cpg.typeDecl.name("BindingTest").methodBinding.l

      queryResult.size shouldBe 1
      queryResult.map(_.name).toSet shouldBe Set("boundMethod")
    }

    "expand from method binding to bound method" in {
      val queryResult: List[nodes.Method] =
        fixture.cpg.typeDecl.name("BindingTest").methodBinding.boundMethod.l

      queryResult.size shouldBe 1
      queryResult.map(_.name).toSet shouldBe Set("boundMethod")
    }

    "expand from bound method to method binding" in {
      val queryResult: List[nodes.Binding] =
        fixture.cpg.method.name("boundMethod").referencingBinding.l

      queryResult.size shouldBe 1
      queryResult.map(_.name).toSet shouldBe Set("boundMethod")
    }

    "expand from method binding to binding type decl" in {
      val queryResult: List[nodes.TypeDecl] =
        fixture.cpg.method.name("boundMethod").referencingBinding.bindingTypeDecl.l

      queryResult.size shouldBe 1
      queryResult.map(_.name).toSet shouldBe Set("BindingTest")
    }

    "expand from BindingTest class to bound method" in {
      val queryResult: List[nodes.Method] =
        fixture.cpg.typeDecl.name("BindingTest").boundMethod.l

      queryResult.size shouldBe 1
      queryResult.map(_.name).toSet shouldBe Set("boundMethod")
    }

    "expand from bound method to binding class" in {
      val queryResult: List[nodes.TypeDecl] =
        fixture.cpg.method.name("boundMethod").bindingTypeDecl.l

      queryResult.size shouldBe 1
      queryResult.map(_.name).toSet shouldBe Set("BindingTest")
    }
  }
}

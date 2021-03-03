package io.shiftleft.semanticcpg.language.types.structure

import io.shiftleft.codepropertygraph.generated.{EdgeTypes, nodes}
import io.shiftleft.semanticcpg.language._
import io.shiftleft.semanticcpg.testing.MockCpg
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class BindingTests extends AnyWordSpec with Matchers {

  val cpg = MockCpg()
    .withTypeDecl("BindingTest")
    .withMethod("<init>")
    .withMethod("boundMethod")
    .withCustom { (graph, cpg) =>
      val typeDecl = cpg.typeDecl.name("BindingTest").head
      val binding1 = nodes.NewBinding().name("<init>")
      val binding2 = nodes.NewBinding().name("boundMethod")
      graph.addEdge(typeDecl, binding1, EdgeTypes.BINDS)
      graph.addEdge(typeDecl, binding2, EdgeTypes.BINDS)
      graph.addEdge(binding1, cpg.method("<init>").head, EdgeTypes.REF)
      graph.addEdge(binding2, cpg.method("boundMethod").head, EdgeTypes.REF)
    }
    .cpg

  "Binding steps" should {
    "expand from BindingTest class to one method binding" in {
      val queryResult: List[nodes.Binding] =
        cpg.typeDecl.name("BindingTest").methodBinding.l

      queryResult.map(_.name) should contain theSameElementsAs List("<init>", "boundMethod")
    }

    "expand from method binding to bound method" in {
      val queryResult: List[nodes.Method] =
        cpg.typeDecl.name("BindingTest").methodBinding.boundMethod.l

      queryResult.map(_.name) should contain theSameElementsAs List("<init>", "boundMethod")
    }

    "expand from bound method to method binding" in {
      val queryResult: List[nodes.Binding] =
        cpg.method.name("boundMethod").referencingBinding.l

      queryResult.map(_.name) should contain theSameElementsAs List("boundMethod")
    }

    "expand from method binding to binding type decl" in {
      val queryResult: List[nodes.TypeDecl] =
        cpg.method.name("boundMethod").referencingBinding.bindingTypeDecl.l

      queryResult.map(_.name) should contain theSameElementsAs List("BindingTest")
    }

    "expand from BindingTest class to bound method" in {
      val queryResult: List[nodes.Method] =
        cpg.typeDecl.name("BindingTest").boundMethod.l

      queryResult.map(_.name) should contain theSameElementsAs List("<init>", "boundMethod")
    }

    "expand from bound method to binding class" in {
      val queryResult: List[nodes.TypeDecl] =
        cpg.method.name("boundMethod").bindingTypeDecl.l

      queryResult.map(_.name) should contain theSameElementsAs List("BindingTest")
    }
  }
}

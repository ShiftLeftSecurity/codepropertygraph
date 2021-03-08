package io.shiftleft.semanticcpg.language.types.structure

import io.shiftleft.codepropertygraph.generated.{EdgeTypes, nodes}
import io.shiftleft.semanticcpg.language._
import io.shiftleft.semanticcpg.testing.MockCpg
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class MethodTests extends AnyWordSpec with Matchers {

  val cpg = MockCpg()
    .withNamespace("namespace")
    .withTypeDecl("TypeDecl", inNamespace = Some("namespace"))
    .withMethod("foo", inTypeDecl = Some("TypeDecl"), external = false)
    .withMethod("bar", inTypeDecl = Some("TypeDecl"), external = true)
    .withCallInMethod("foo", "call")
    .withCallInMethod("foo", "call2")
    .withLiteralArgument("call", "literal")
    .withCustom {
      case (graph, cpg) =>
        val method = cpg.method("foo").head
        val call = cpg.call.name("call").head
        val methodReturn = cpg.method("foo").methodReturn.head
        graph.addEdge(method, call, EdgeTypes.CFG)
        graph.addEdge(call, methodReturn, EdgeTypes.CFG)
    }
    .cpg

  "Method traversals" should {
    "expand to type declaration" in {
      val queryResult: List[nodes.TypeDecl] =
        cpg.method.name("foo").definingTypeDecl.toList

      queryResult.size shouldBe 1
      queryResult.head.name shouldBe "TypeDecl"
    }

    "expand to literal" in {
      val queryResult: List[nodes.Literal] =
        cpg.method.name("foo").literal.toList

      queryResult.size shouldBe 1
      queryResult.head.code shouldBe "literal"
    }

    "expand to namespace" in {
      val queryResult: Set[String] = cpg.method.namespace.name.l.distinct.toSet
      queryResult shouldBe Set("namespace")
    }

    "filter by name" in {
      val methods: List[nodes.Method] =
        cpg.method.name("foo").toList
      methods.size shouldBe 1
      verifyMainMethod(methods.head)
    }

    "filter by name with regex" in {
      val methods: List[nodes.Method] =
        cpg.method.name(".*foo.*").toList
      methods.size shouldBe 1
      verifyMainMethod(methods.head)
    }

    def verifyMainMethod(main: nodes.Method) = {
      main.name shouldBe "foo"
      main.fullName shouldBe "foo"
    }

    "expand to top level expressions" in {
      val expressions: List[nodes.Expression] =
        cpg.method.name("foo").topLevelExpressions.toList

      expressions.size shouldBe 2
      expressions.map(_.code).toSet shouldBe
        Set("call", "call2")
    }

    "expand to first expression" in {
      val expressions: List[nodes.Expression] =
        cpg.method.name("foo").cfgFirst.toList

      expressions.size shouldBe 1
      expressions.head.code shouldBe "call"
    }

    "expand to last expression" in {
      val expressions: List[nodes.Expression] =
        cpg.method.name("foo").cfgLast.toList

      expressions.size shouldBe 1
      expressions.head.code shouldBe "call"
    }

    "filter for external/internal methods" in {
      val externals = cpg.method.external.fullName.l
      externals.size should be > 0
      externals should contain allElementsOf Seq(
        "bar"
      )

      val internals = cpg.method.internal.fullName.l
      internals.size should be > 0
      internals should not contain "bar"

      val allMethods = cpg.method.fullName.l
      allMethods should contain allElementsOf internals
      allMethods should contain allElementsOf externals
    }

    "get the isExternal property (true) for external method calls" in {
      val methods: List[nodes.Method] =
        cpg.method.name("bar").toList

      methods.size shouldBe 1
      methods.head.isExternal shouldBe true
    }

    "get the isExternal property (false) for non-external method calls" in {
      val methods: List[nodes.Method] =
        cpg.method.name("foo").toList

      methods.size shouldBe 1
      methods.head.isExternal shouldBe false
    }

    "filter by modifier" in {
      val internalMethod: List[nodes.Method] =
        cpg.method.name("foo").hasModifier("modifiertype").toList
      internalMethod.size shouldBe 1
    }
  }

}

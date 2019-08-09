package io.shiftleft.queryprimitives.steps

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.testfixtures.ExistingCpgFixture
import org.scalatest.{Matchers, WordSpec}

class NamespaceTests extends WordSpec with Matchers {

  "generic cpg" should ExistingCpgFixture("namespace") { fixture =>
    "find package io.shiftleft.testcode.namespace" in {
      val queryResult: List[nodes.Namespace] = fixture.cpg.namespace.toList

      queryResult.map(_.name) should contain("io.shiftleft.testcode.namespace")
    }

    "find a namespace block for io.shiftleft.testcode.namespace" in {
      val queryResult: List[nodes.NamespaceBlock] = fixture.cpg.namespaceBlock.toList
      queryResult.map(_.name) should contain("io.shiftleft.testcode.namespace")
    }

    "be able to traverse from namespace block to type decl" in {
      val queryResult: List[nodes.TypeDecl] = fixture.cpg.namespaceBlock
        .nameExact("io.shiftleft.testcode.namespace")
        .typeDecl
        .toList
      queryResult.map(_.name) should contain("NamespaceTest")
    }

    "be able to expand to class NamespaceTest from via its namespace." in {
      val queryResult: List[nodes.TypeDecl] =
        fixture.cpg.namespace.name("io.shiftleft.testcode.namespace").typeDecl.toList

      queryResult.size shouldBe 1
      queryResult.head.name shouldBe "NamespaceTest"
    }

    "be able to expand to file which defines namespace." in {
      val queryResult: List[nodes.File] =
        fixture.cpg.namespace.name("io.shiftleft.testcode.namespace").file.toList

      queryResult.size shouldBe 1
      queryResult.head.name shouldBe "io/shiftleft/testcode/namespace/NamespaceTest.java"
    }
  }

}

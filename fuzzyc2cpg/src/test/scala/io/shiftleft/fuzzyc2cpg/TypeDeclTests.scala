package io.shiftleft.fuzzyc2cpg

import io.shiftleft.codepropertygraph.generated.NodeKeys
import io.shiftleft.proto.cpg.Cpg.CpgStruct.Node.NodeType
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import overflowdb._

class TypeDeclTests extends AnyWordSpec with Matchers {
  val fixture = CpgTestFixture("typedecl")

  "Type decl test project" should {
    "contain one internal type decl node for Foo" in {
      val typeDeclNodes = fixture
        .traversalSource
        .label(NodeType.TYPE_DECL.toString)
        .has(NodeKeys.NAME -> "Foo")
        .l
      typeDeclNodes.size shouldBe 1
      typeDeclNodes.head.property(NodeKeys.IS_EXTERNAL) shouldBe false
    }

    "contain three member nodes" in {
      fixture.traversalSource.label(NodeType.MEMBER.toString).l.size shouldBe 3
    }

    "contain edges from Foo to three members" in {
      val members = fixture
        .traversalSource
        .label(NodeType.TYPE_DECL.toString)
        .out("AST")
        .hasLabel(NodeType.MEMBER.toString)
        .l
      members.size shouldBe 3
    }

    "contain correct code fields for all members" in {
      fixture.traversalSource.label(NodeType.MEMBER.toString).property(NodeKeys.CODE).toSet shouldBe Set("x", "y", "*foo")
    }

  }
}

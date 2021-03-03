package io.shiftleft.semanticcpg.language.types.structure

import io.shiftleft.codepropertygraph.generated.{EdgeTypes, ModifierTypes, nodes}
import io.shiftleft.semanticcpg.language._
import io.shiftleft.semanticcpg.testing.MockCpg
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class MemberTests extends AnyWordSpec with Matchers {

  val cpg = MockCpg()
    .withTypeDecl("foo")
    .withCustom { (graph, _) =>
      val staticMember = nodes.NewMember().name("static_member")
      val modifier = nodes.NewModifier().modifierType(ModifierTypes.STATIC)
      graph.addNode(staticMember)
      graph.addNode(modifier)
      graph.addEdge(staticMember, modifier, EdgeTypes.AST)
    }
    .cpg

  "Member traversals" should {
    "should find two members: `member` and `static_member`" in {
      cpg.member.name.toSet shouldBe Set("amember", "static_member")
    }

    "filter by modifier" in {
      val member = cpg.member.hasModifier(ModifierTypes.STATIC).name.toSet
      member shouldBe Set("static_member")
    }
  }

}

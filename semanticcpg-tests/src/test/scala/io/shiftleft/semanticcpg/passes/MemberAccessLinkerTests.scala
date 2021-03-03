package io.shiftleft.semanticcpg.passes

import io.shiftleft.codepropertygraph.generated._
import io.shiftleft.semanticcpg.language._
import io.shiftleft.semanticcpg.testing.MockCpg
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class MemberAccessLinkerTests extends AnyWordSpec with Matchers {

  val cpg = MockCpg().withCustom { (graph, _) =>
    val call = nodes.NewCall().name(Operators.indirectMemberAccess)
    val member = nodes.NewMember().name("aaa")
    graph.addNode(call)
    graph.addNode(member)
    graph.addEdge(call, member, EdgeTypes.REF)
  }.cpg

  "have a reference to correct member" in {
    val List(m) = cpg.call(Operators.indirectMemberAccess).referencedMember.l
    m.name shouldBe "aaa"
  }

}

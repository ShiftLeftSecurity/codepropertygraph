package io.shiftleft.passes.memberaccesslinker

import gremlin.scala._
import io.shiftleft.codepropertygraph.generated._
import io.shiftleft.semanticcpg.Fixture
import org.scalatest.{Matchers, WordSpec}

class MemberAccessLinkerTests extends WordSpec with Matchers {
  val fixture = new Fixture("memberaccesslinker")

  "have a reference to correct member" in {
    val queryResult: List[Vertex] = fixture.scalaGraph.V
      .hasLabel(NodeTypes.CALL)
      .has(NodeKeys.NAME -> Operators.indirectMemberAccess)
      .out(EdgeTypes.REF)
      .toList()

    queryResult.size shouldBe 1
    queryResult.head.label() shouldBe NodeTypes.MEMBER
    queryResult.head.value2(NodeKeys.NAME) shouldBe "aaa"
  }

}

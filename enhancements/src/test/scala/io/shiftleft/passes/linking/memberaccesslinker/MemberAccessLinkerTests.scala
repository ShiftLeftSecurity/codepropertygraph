package io.shiftleft.passes.memberaccesslinker

/* TODO fix compilation issues: resolve cyclic dependency in project setup */

// import gremlin.scala._
// import io.shiftleft.codepropertygraph.generated._
// import io.shiftleft.cpgqueryingtests.codepropertygraph.CpgTestFixture
// import org.scalatest.{Matchers, WordSpec}

// class MemberAccessLinkerTests extends WordSpec with Matchers {

// "have a reference to correct member" in new CpgTestFixture("memberaccesslinker") {
//   val queryResult: List[Vertex] = scalaGraph.V
//     .hasLabel(NodeTypes.CALL)
//     .has(NodeKeys.NAME -> Operators.indirectMemberAccess)
//     .out(EdgeTypes.REF)
//     .toList()

//   queryResult.size shouldBe 1
//   queryResult.head.label() shouldBe NodeTypes.MEMBER
//   queryResult.head.value2(NodeKeys.NAME) shouldBe "aaa"
// }

// }

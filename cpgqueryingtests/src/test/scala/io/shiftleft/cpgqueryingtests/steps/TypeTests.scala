package io.shiftleft.cpgqueryingtests.steps

import gremlin.scala._
import io.shiftleft.cpgqueryingtests.codepropertygraph.CpgTestFixture
import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.queryprimitives.steps.Implicits._
import org.scalatest.{Matchers, WordSpec}

class TypeTests extends WordSpec with Matchers {
  val fixture = CpgTestFixture("type")

  "ClassMemberTest" should {

    "have ClassMemberTest as internal class" in {
      def queryResult: List[nodes.TypeDecl] =
        fixture.cpg.typeDecl.name("ClassMemberTest").internal.toList
      queryResult.size shouldBe 1
    }

    "have Object as external class" in {
      def queryResult: List[nodes.TypeDecl] =
        fixture.cpg.typeDecl.name("Object").external.toList

      queryResult.size shouldBe 1
    }

    "have a member called member" in {
      def queryResult: List[nodes.Member] =
        fixture.cpg.typeDecl.member.nameExact("member").toList

      queryResult.size shouldBe 1
    }

    "have a static member called static_member" in {
      def queryResult: List[nodes.Member] =
        fixture.cpg.typeDecl.member.nameExact("static_member").isStatic.toList

      queryResult.size shouldBe 1
    }

    "more than 0 members found by regex" in {
      def queryResult: List[nodes.Member] =
        fixture.cpg.typeDecl.member.name(".*").toList

      queryResult.size should be > 0
    }
  }

}

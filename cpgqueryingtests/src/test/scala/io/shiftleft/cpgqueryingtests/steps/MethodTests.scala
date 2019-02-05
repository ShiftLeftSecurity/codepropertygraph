package io.shiftleft.cpgqueryingtests.steps

import io.shiftleft.cpgqueryingtests.codepropertygraph.CpgTestFixture
import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.codepropertygraph.generated.{Languages, NodeKeys, NodeTypes}
import io.shiftleft.queryprimitives.steps.Implicits._
import org.scalatest.{Matchers, WordSpec}
import java.io._
import org.json4s._
import org.json4s.native.JsonMethods._

class MethodTests extends WordSpec with Matchers {
  val fixture = CpgTestFixture("method")

  "Method traversals" should {
    "expand to type declaration" in {
      val queryResult: List[nodes.TypeDecl] =
        fixture.cpg.method.name("methodWithLiteral").definingTypeDecl.toList

      queryResult.size shouldBe 1
      queryResult.head.name shouldBe "TestGraph"
    }

    "expand to literal" ignore {
      val queryResult: List[nodes.Literal] =
        fixture.cpg.method.name("methodWithLiteral").literal.toList

      queryResult.size shouldBe 1
      queryResult.head.code shouldBe "\"myLiteral\""
    }

    "filter by name" in {
      val methods: List[nodes.Method] =
        fixture.cpg.method.name("methodWithLiteral").toList
      methods.size shouldBe 1
      verifyMainMethod(methods.head)
    }

    "filter by name with regex" in {
      val methods: List[nodes.Method] =
        fixture.cpg.method.name(".*methodWithLiteral.*").toList
      methods.size shouldBe 1
      verifyMainMethod(methods.head)
    }

    def verifyMainMethod(main: nodes.Method) = {
      main.name shouldBe "methodWithLiteral"
      main.fullName shouldBe
        "io.shiftleft.testcode.method.TestGraph.methodWithLiteral:java.lang.String()"
    }

    "expand to top level expressions" in {
      val expressions: List[nodes.Expression] =
        fixture.cpg.method.name("multipleTopLevelExpressionMethod").topLevelExpressions.toList

      expressions.size shouldBe 3
      expressions.map(_.code).toSet shouldBe
        Set("this.someFunction(\"FOO\")", "this.someFunction(\"BAR\")", "return")
    }

    "expand to first expression" in {
      val expressions: List[nodes.Expression] =
        fixture.cpg.method.name("methodForCfgTest").cfgFirst.toList

      expressions.size shouldBe 1
      expressions.head.code shouldBe "temp"
    }

    "expand to last expression" in {
      val expressions: List[nodes.Expression] =
        fixture.cpg.method.name("methodForCfgTest").cfgLast.toList

      expressions.size shouldBe 1
      expressions.head.code shouldBe "return"
    }
  }

}

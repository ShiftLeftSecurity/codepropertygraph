package io.shiftleft.cpgqueryingtests.steps

import io.shiftleft.cpgqueryingtests.codepropertygraph.CpgTestFixture
import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.queryprimitives.steps.Implicits._
import org.scalatest.{Matchers, WordSpec}

class MethodParameterTests extends WordSpec with Matchers {
  val fixture = CpgTestFixture("methodparameter")
  import gremlin.scala.Graph
  implicit val graph: Graph = fixture.cpg.graph

  "generic cpg" should {

    "find parameters" when {
      "asking for all parameters" in {
        val args: List[nodes.MethodParameterIn] =
          fixture.cpg.method.name("manyArgs").parameter.toList

        args.size shouldBe 4
        args.sortBy(_.order).map(_.start.typ.head.fullName) shouldBe
          List("java.lang.String", "java.lang.Integer", "java.lang.Long", "java.lang.Double")
      }

      "filtering by name" in {
        val queryResult: List[nodes.MethodParameterIn] =
          fixture.cpg.method.parameter.name(".*").toList

        queryResult.size should be > 1
      }

      "finding parameter by index" when {
        "specifying number" in {
          val args: List[nodes.MethodParameterIn] =
            fixture.cpg.method.name("manyArgs").parameter.index(num = 1).toList

          args.size shouldBe 1
          args.head.start.typ.head.fullName shouldBe "java.lang.String"
        }

        "specifying index >= x" in {
          val args: List[nodes.MethodParameterIn] =
            fixture.cpg.method.name("manyArgs").parameter.indexFrom(2).toList

          args.map(_.start.typ.head.fullName).toSet shouldBe Set("java.lang.Integer",
                                                                 "java.lang.Long",
                                                                 "java.lang.Double")
        }

        "specifying index <= x" in {
          val args: List[nodes.MethodParameterIn] =
            fixture.cpg.method.name("manyArgs").parameter.indexTo(2).toList

          args.map(_.start.typ.head.fullName).toSet shouldBe Set("java.lang.String",
                                                                 "java.lang.Integer")
        }
      }
    }

    "find method that a MethodParameter belongs to" in {
      val methods: List[nodes.Method] =
        fixture.cpg.method.name("manyArgs").parameter.index(num = 1).method.toList

      methods.size shouldBe 1
      methods.head.fullName shouldBe
        "io.shiftleft.testcode.methodparameter.MethodParameterTest.manyArgs:java.lang.String" +
          "(java.lang.String,java.lang.Integer,java.lang.Long,java.lang.Double)"
    }
  }
}

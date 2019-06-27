package io.shiftleft.cpgqueryingtests.steps

import gremlin.scala.{Edge, GremlinScala, StepLabel, Vertex}
import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.cpgqueryingtests.codepropertygraph.CpgTestFixture
import org.json4s.JString
import org.json4s.native.JsonMethods.parse
import org.scalatest.{Matchers, WordSpec}

import scala.collection.mutable
import shapeless.HNil

class StepsTest extends WordSpec with Matchers {

  "generic cpg" should {

    "filter by regex" in CpgTestFixture("splitmeup") { fixture =>
      val queryResult: List[nodes.Literal] =
        fixture.cpg.literal.code(".*").toList

      queryResult.size should be > 1
    }

    "filter on cpg type" in CpgTestFixture("splitmeup") { fixture =>
      val mainMethods: List[nodes.Method] =
        fixture.cpg.method
          .name(".*")
          .filterOnEnd(_.fullName.matches(".*main.*"))
          .toList

      mainMethods.size shouldBe 1
    }

    "filter with traversal on cpg type" in CpgTestFixture("splitmeup") { fixture =>
      def allMethods = fixture.cpg.method
      val publicMethods = allMethods.filter(_.isPublic)

      allMethods.toList.size should be > publicMethods.toList.size
    }

    "filter on id" when {
      "providing one" in CpgTestFixture("splitmeup") { fixture =>
        // find an arbitrary method so we can find it again in the next step
        val method: nodes.Method = fixture.cpg.method.toList.head
        val results: List[nodes.Method] = fixture.cpg.method.id(method.underlying.id).toList

        results.size shouldBe 1
        results.head.underlying.id
      }

      "providing multiple" in CpgTestFixture("splitmeup") { fixture =>
        // find two arbitrary methods so we can find it again in the next step
        val methods: Set[nodes.Method] = fixture.cpg.method.toList.take(2).toSet
        val results: List[nodes.Method] = fixture.cpg.method.id(methods.map(_.id())).toList

        results.size shouldBe 2
        results.toSet shouldBe methods.toSet
      }
    }

    "aggregate intermediary results into a given collection" in CpgTestFixture("splitmeup") { fixture =>
      val allMethods = mutable.ArrayBuffer.empty[nodes.Method]

      val mainMethods: List[nodes.Method] =
        fixture.cpg.method
          .name(".*")
          .aggregate(allMethods)
          .filterOnEnd(_.fullName.matches(".*main.*"))
          .toList

      mainMethods.size shouldBe 1
      allMethods.size should be > 1
    }
  }

  "find that all method returns are linked to a method" in CpgTestFixture("splitmeup") { fixture =>
    val returnsWithMethods = fixture.cpg.method.methodReturn.l
    val returns = fixture.cpg.methodReturn.l
    returnsWithMethods.size shouldBe returns.size
  }

  "allow for comprehensions" in CpgTestFixture("splitmeup") { fixture =>
    case class MethodParamPairs(methodName: String, paramName: String)

    val query = for {
      method <- fixture.cpg.method
      param <- method.start.parameter
    } yield MethodParamPairs(method.name, param.name)

    val pairs: List[MethodParamPairs] = query.toList
    pairs.size should be > 0
  }

  "allow lists in map/flatMap/forComprehension" in CpgTestFixture("splitmeup") { fixture =>
    val query = fixture.cpg.method.isPublic.map { method =>
      (method.name, method.start.parameter.toList)
    }
    val results: List[(String, List[nodes.MethodParameterIn])] = query.toList
    results.size should be > 1
  }

  "allow side effects" in CpgTestFixture("splitmeup") { fixture =>
    var i = 0
    fixture.cpg.method.sideEffect(_ => i = i + 1).exec
    i should be > 0
  }

  "as/select" should {
    "select all by default" in CpgTestFixture("splitmeup") { fixture =>
      val results: List[(nodes.Method, nodes.MethodReturn)] =
        fixture.cpg.method.as("method").methodReturn.as("methodReturn").select.toList

      results.size should be > 0
    }

    "allow to select a single label" in CpgTestFixture("splitmeup") { fixture =>
      val methodReturnLabel = StepLabel[nodes.MethodReturn]("methodReturn")
      val methodLabel = StepLabel[nodes.Method]("method")
      val results: List[nodes.MethodReturn] =
        fixture.cpg.method.as(methodLabel).methodReturn.as(methodReturnLabel).select(methodReturnLabel).toList

      results.size should be > 0
    }

    "allow to select multiple labels" in CpgTestFixture("splitmeup") { fixture =>
      val methodReturnLabel = StepLabel[nodes.MethodReturn]("methodReturn")
      val methodLabel = StepLabel[nodes.Method]("method")
      val results: List[(nodes.MethodReturn, nodes.Method)] =
        fixture.cpg.method
          .as(methodLabel)
          .methodReturn
          .as(methodReturnLabel)
          .select((methodReturnLabel, methodLabel))
          .toList

      results.size should be > 0
    }
  }

  "raw traversals" should {
    "allow typed as/select" in CpgTestFixture("splitmeup") { fixture =>
      val raw = fixture.cpg.namespace.raw.asInstanceOf[GremlinScala.Aux[Vertex, HNil]]
      val _: List[(Vertex, Edge)] = raw.as("a").outE.as("b").select.toList
    // all that matters is that the result type is (Vertex, Edge)
    // for more options with as/select on raw, see https://github.com/mpollmeier/gremlin-scala/blob/master/gremlin-scala/src/test/scala/gremlin/scala/SelectSpec.scala
    }
  }

  "toJson" in CpgTestFixture("splitmeup") { fixture =>
    val json = fixture.cpg.namespace.nameExact("io.shiftleft.testcode.splitmeup").toJson
    val parsed = parse(json).children.head //exactly one result for the above query
    (parsed \ "NAME") shouldBe JString("io.shiftleft.testcode.splitmeup")
    (parsed \ "_label") shouldBe JString("NAMESPACE")
  }

}

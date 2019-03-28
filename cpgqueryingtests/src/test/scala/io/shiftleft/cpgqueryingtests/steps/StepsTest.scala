package io.shiftleft.cpgqueryingtests.steps

import io.shiftleft.cpgqueryingtests.codepropertygraph.CpgTestFixture
import gremlin.scala._
import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.codepropertygraph.generated.nodes.StoredNode
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeTypes}
import io.shiftleft.diffgraph.{DiffGraph, DiffGraphApplier}
import io.shiftleft.queryprimitives.steps.Implicits._
import org.json4s.JString
import org.json4s.native.JsonMethods.parse
import org.scalatest.{Matchers, WordSpec}

import scala.collection.mutable
import shapeless.HNil

class StepsTest extends WordSpec with Matchers {

  "generic cpg" should {

    "filter by regex" in new CpgTestFixture("splitmeup") {
      val queryResult: List[nodes.Literal] =
        cpg.literal.code(".*").toList

      queryResult.size should be > 1
    }

    "filter on cpg type" in new CpgTestFixture("splitmeup") {
      val mainMethods: List[nodes.Method] =
        cpg.method
          .name(".*")
          .filterOnEnd(_.fullName.matches(".*main.*"))
          .toList

      mainMethods.size shouldBe 1
    }

    "filter with traversal on cpg type" in new CpgTestFixture("splitmeup") {
      def allMethods = cpg.method
      val publicMethods = allMethods.filter(_.isPublic)

      allMethods.toList.size should be > publicMethods.toList.size
    }

    "filter on id" when {
      "providing one" in new CpgTestFixture("splitmeup") {
        // find an arbitrary method so we can find it again in the next step
        val method: nodes.Method = cpg.method.toList.head
        val results: List[nodes.Method] = cpg.method.id(method.underlying.id).toList

        results.size shouldBe 1
        results.head.underlying.id
      }

      "providing multiple" in new CpgTestFixture("splitmeup") {
        // find two arbitrary methods so we can find it again in the next step
        val methods: Set[nodes.Method] = cpg.method.toList.take(2).toSet
        val results: List[nodes.Method] = cpg.method.id(methods.map(_.id)).toList

        results.size shouldBe 2
        results.toSet shouldBe methods.toSet
      }
    }

    "aggregate intermediary results into a given collection" in new CpgTestFixture("splitmeup") {
      val allMethods = mutable.ArrayBuffer.empty[nodes.Method]

      val mainMethods: List[nodes.Method] =
        cpg.method
          .name(".*")
          .aggregate(allMethods)
          .filterOnEnd(_.fullName.matches(".*main.*"))
          .toList

      mainMethods.size shouldBe 1
      allMethods.size should be > 1
    }
  }

  "find that all method returns are linked to a method" in new CpgTestFixture("splitmeup") {
    val returnsWithMethods = cpg.method.methodReturn.l
    val returns = cpg.methodReturn.l
    returnsWithMethods.size shouldBe returns.size
  }

  "allow for comprehensions" in new CpgTestFixture("splitmeup") {
    case class MethodParamPairs(methodName: String, paramName: String)

    val query = for {
      method <- cpg.method
      param <- method.start.parameter
    } yield MethodParamPairs(method.name, param.name)

    val pairs: List[MethodParamPairs] = query.toList
    pairs.size should be > 0
  }

  "allow lists in map/flatMap/forComprehension" in new CpgTestFixture("splitmeup") {
    val query = cpg.method.isPublic.map { method =>
      (method.name, method.start.parameter.toList)
    }
    val results: List[(String, List[nodes.MethodParameterIn])] = query.toList
    results.size should be > 1
  }

  "allow side effects" in new CpgTestFixture("splitmeup") {
    var i = 0
    cpg.method.sideEffect(_ => i = i + 1).exec
    i should be > 0
  }

  "as/select" should {
    "select all by default" in new CpgTestFixture("splitmeup") {
      val results: List[(nodes.Method, nodes.MethodReturn)] =
        cpg.method.as("method").methodReturn.as("methodReturn").select.toList

      results.size should be > 0
    }

    "allow to select a single label" in new CpgTestFixture("splitmeup") {
      val methodReturnLabel = StepLabel[nodes.MethodReturn]("methodReturn")
      val methodLabel = StepLabel[nodes.Method]("method")
      val results: List[nodes.MethodReturn] =
        cpg.method.as(methodLabel).methodReturn.as(methodReturnLabel).select(methodReturnLabel).toList

      results.size should be > 0
    }

    "allow to select multiple labels" in new CpgTestFixture("splitmeup") {
      val methodReturnLabel = StepLabel[nodes.MethodReturn]("methodReturn")
      val methodLabel = StepLabel[nodes.Method]("method")
      val results: List[(nodes.MethodReturn, nodes.Method)] =
        cpg.method
          .as(methodLabel)
          .methodReturn
          .as(methodReturnLabel)
          .select((methodReturnLabel, methodLabel))
          .toList

      results.size should be > 0
    }
  }

  "raw traversals" should {
    "allow typed as/select" in new CpgTestFixture("splitmeup") {
      val raw = cpg.namespace.raw.asInstanceOf[GremlinScala.Aux[Vertex, HNil]]
      val _: List[(Vertex, Edge)] = raw.as("a").outE.as("b").select.toList
      // all that matters is that the result type is (Vertex, Edge)
      // for more options with as/select on raw, see https://github.com/mpollmeier/gremlin-scala/blob/master/gremlin-scala/src/test/scala/gremlin/scala/SelectSpec.scala
    }
  }

  "toJson" in new CpgTestFixture("splitmeup") {
    val json = cpg.namespace.nameExact("io.shiftleft.testcode.splitmeup").toJson
    val parsed = parse(json).children.head //exactly one result for the above query
    (parsed \ "NAME") shouldBe JString("io.shiftleft.testcode.splitmeup")
    (parsed \ "_label") shouldBe JString("NAMESPACE")
  }

}

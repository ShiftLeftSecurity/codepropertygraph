package io.shiftleft.cpgqueryingtests.steps

import io.shiftleft.cpgqueryingtests.codepropertygraph.CpgTestFixture
import gremlin.scala._
import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.codepropertygraph.generated.nodes.StoredNode
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeTypes}
import io.shiftleft.diffgraph.{DiffGraph, DiffGraphApplier}
import io.shiftleft.queryprimitives.steps.Implicits._
import io.shiftleft.queryprimitives.steps.CpgSteps
import org.json4s.JString
import org.json4s.native.JsonMethods.parse
import org.scalatest.{Matchers, WordSpec}

import scala.collection.mutable
import shapeless.HNil

class StepsTest extends WordSpec with Matchers {

  "generic cpg" should {
    "find a field by type regex" in new CpgTestFixture("splitmeup") {
      val queryResult: List[nodes.Member] =
        cpg.member.evalType(".*").toList

      queryResult.size should be > 1
    }

    "find a literal by regex" in new CpgTestFixture("splitmeup") {
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
      def allMethods    = cpg.method
      val publicMethods = allMethods.filter(_.isPublic)

      allMethods.toList.size should be > publicMethods.toList.size
    }

    "filter on id" when {
      "providing one" in new CpgTestFixture("splitmeup") {
        // find an arbitrary method so we can find it again in the next step
        val method: nodes.Method        = cpg.method.toList.head
        val results: List[nodes.Method] = cpg.method.id(method.underlying.id).toList

        results.size shouldBe 1
        results.head.underlying.id
      }

      "providing multiple" in new CpgTestFixture("splitmeup") {
        // find two arbitrary methods so we can find it again in the next step
        val methods: Set[nodes.Method]  = cpg.method.toList.take(2).toSet
        val ids = methods.map(_.underlying.id).toSeq
        val results: List[nodes.Method] = cpg.method.id(ids: _*).toList

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
    val returns            = cpg.methodReturn.l
    returnsWithMethods.size shouldBe returns.size
  }

  "allow for comprehensions" in new CpgTestFixture("splitmeup") {
    case class MethodParamPairs(methodName: String, paramName: String)

    val query = for {
      method <- cpg.method
      param  <- method.start.parameter
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
      val methodReturnLabel  = StepLabel[nodes.MethodReturn]("methodReturn")
      val methodLabel = StepLabel[nodes.Method]("method")
      val results: List[nodes.MethodReturn] =
        cpg.method.as(methodLabel).methodReturn.as(methodReturnLabel).select(methodReturnLabel).toList

      results.size should be > 0
    }

    "allow to select multiple labels" in new CpgTestFixture("splitmeup") {
      val methodReturnLabel  = StepLabel[nodes.MethodReturn]("methodReturn")
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
      val raw                     = cpg.namespace.raw.asInstanceOf[GremlinScala.Aux[Vertex, HNil]]
      val _: List[(Vertex, Edge)] = raw.as("a").outE.as("b").select.toList
      // all that matters is that the result type is (Vertex, Edge)
      // for more options with as/select on raw, see https://github.com/mpollmeier/gremlin-scala/blob/master/gremlin-scala/src/test/scala/gremlin/scala/SelectSpec.scala
    }
  }

  "toJson" in new CpgTestFixture("splitmeup") {
    val json = cpg.namespace.nameExact("io.shiftleft.testcode.splitmeup").toJson

    val parsed = parse(json).children.head //exactly one result for the above query
    (parsed \ "_label") shouldBe JString("NAMESPACE")
    (parsed \ "NAME") shouldBe JString("io.shiftleft.testcode.splitmeup")
  }

  "repeat, emit, until, times test" should {
    "find all base types of Derived2" in new CpgTestFixture("steps") {
      val results = cpg.typeDecl
        .nameExact("TestGraph$Derived2")
        .repeat(_.baseTypeDecl)
        .emit()
        .toList

      results.size shouldBe 3
      results.map(_.name).toSet shouldBe Set("TestGraph$Derived1", "TestGraph$Base", "Object")
    }

    "find all base types of Derived2 whos name contains 'Derived1'" in new CpgTestFixture("steps") {
      val results = cpg.typeDecl
        .nameExact("TestGraph$Derived2")
        .repeat(_.baseTypeDecl)
        .emit(_.name(".*Derived1.*"))
        .toList

      results.size shouldBe 1
      results.map(_.name).toSet shouldBe Set("TestGraph$Derived1")
    }

    "find base type of Derived2 whos name contains 'Base'" in new CpgTestFixture("steps") {
      val results = cpg.typeDecl
        .nameExact("TestGraph$Derived2")
        .repeat(_.baseTypeDecl)
        .until(_.name(".*Base.*"))
        .toList

      results.size shouldBe 1
      results.map(_.name).toSet shouldBe Set("TestGraph$Base")
    }

    "find seconde level ancestor class of Derived2 " in new CpgTestFixture("steps") {
      val results = cpg.typeDecl
        .nameExact("TestGraph$Derived2")
        .repeat(_.baseTypeDecl)
        .times(2)
        .toList

      results.size shouldBe 1
      results.map(_.name).toSet shouldBe Set("TestGraph$Base")
    }
  }

  "or test" should {
    "find types Base and Derived1" in new CpgTestFixture("steps") {
      val results = cpg.typeDecl
        .or(
          _.nameExact("TestGraph$Base"),
          _.nameExact("TestGraph$Derived1"),
        )
        .toList

      results.size shouldBe 2
      results.map(_.name).toSet shouldBe Set("TestGraph$Base", "TestGraph$Derived1")
    }
  }

  "and with complete traversal test" should {
    "find ..." in new CpgTestFixture("steps") {
      // val results = cpg.typeDecl.and(cpg.method).l
      // results.size should be > 0
      // results.head.getClass.getSimpleName shouldBe "Method"
      // TODO fix in gremlin-scala
      ???
    }

  }

}

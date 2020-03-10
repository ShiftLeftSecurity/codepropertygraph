package io.shiftleft.semanticcpg.language

import gremlin.scala.__
import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.semanticcpg.testfixtures.ExistingCpgFixture
import org.json4s.JString
import org.json4s.native.JsonMethods.parse
import org.scalatest.{Matchers, WordSpec}
import scala.collection.mutable

class StepsTest extends WordSpec with Matchers {

  "generic cpg" should {

    "filter by regex" in ExistingCpgFixture("splitmeup") { fixture =>
      val queryResult: List[nodes.Literal] =
        fixture.cpg.literal.code(".*").toList

      queryResult.size should be > 1
    }

    "filter on cpg type" in ExistingCpgFixture("splitmeup") { fixture =>
      val mainMethods: List[nodes.Method] =
        fixture.cpg.method
          .name(".*")
          .where(_.fullName.matches(".*main.*"))
          .toList

      mainMethods.size shouldBe 1
    }

    "filter with traversal on cpg type" in ExistingCpgFixture("splitmeup") { fixture =>
      def allMethods = fixture.cpg.method
      val publicMethods = allMethods.filter(_.isPublic)

      allMethods.toList.size should be > publicMethods.toList.size
    }

    "filter on id" when {
      "providing one" in ExistingCpgFixture("splitmeup") { fixture =>
        // find an arbitrary method so we can find it again in the next step
        val method: nodes.Method = fixture.cpg.method.toList.head
        val results: List[nodes.Method] = fixture.cpg.method.id(method.underlying.id).toList

        results.size shouldBe 1
        results.head.underlying.id
      }

      "providing multiple" in ExistingCpgFixture("splitmeup") { fixture =>
        // find two arbitrary methods so we can find it again in the next step
        val methods: Set[nodes.Method] = fixture.cpg.method.toList.take(2).toSet
        val results: List[nodes.Method] = fixture.cpg.method.id(methods.map(_.id())).toList

        results.size shouldBe 2
        results.toSet shouldBe methods.toSet
      }
    }

    "aggregate intermediary results into a given collection" in ExistingCpgFixture("splitmeup") { fixture =>
      val allMethods = mutable.ArrayBuffer.empty[nodes.Method]

      val mainMethods: List[nodes.Method] =
        fixture.cpg.method
          .name(".*")
          .aggregate(allMethods)
          .where(_.fullName.matches(".*main.*"))
          .toList

      mainMethods.size shouldBe 1
      allMethods.size should be > 1
    }
  }

  "find that all method returns are linked to a method" in ExistingCpgFixture("splitmeup") { fixture =>
    val returnsWithMethods = fixture.cpg.method.methodReturn.l
    val returns = fixture.cpg.methodReturn.l
    returnsWithMethods.size shouldBe returns.size
  }

  "allow for comprehensions" in ExistingCpgFixture("splitmeup") { fixture =>
    case class MethodParamPairs(methodName: String, paramName: String)

    val query = for {
      method <- fixture.cpg.method
      param <- method.start.parameter
    } yield MethodParamPairs(method.name, param.name)

    val pairs: List[MethodParamPairs] = query.toList
    pairs.size should be > 0
  }

  "allow lists in map/flatMap/forComprehension" in ExistingCpgFixture("splitmeup") { fixture =>
    val query = fixture.cpg.method.isPublic.map { method =>
      (method.name, method.start.parameter.toList)
    }
    val results: List[(String, List[nodes.MethodParameterIn])] = query.toList
    results.size should be > 1
  }

  "allow side effects" in ExistingCpgFixture("splitmeup") { fixture =>
    var i = 0
    fixture.cpg.method.sideEffect(_ => i = i + 1).exec
    i should be > 0
  }

  "allow retrieving ids" in ExistingCpgFixture("splitmeup") { fixture =>
    fixture.cpg.method.id.l should not be empty
  }

  "toJson" when ExistingCpgFixture("splitmeup") { fixture =>
    "operating on StoredNode" in {
      val json = fixture.cpg.namespace.nameExact("io.shiftleft.testcode.splitmeup").toJson
      val parsed = parse(json).children.head //exactly one result for the above query
      (parsed \ "_label") shouldBe JString("NAMESPACE")
      (parsed \ "NAME") shouldBe JString("io.shiftleft.testcode.splitmeup")
    }

    "operating on NewNode" in {
      val json = fixture.cpg.method.name(".*manyArgs.*").location.toJson
      val parsed = parse(json).children.head //exactly one result for the above query
      (parsed \ "SYMBOL") shouldBe JString("manyArgs")
      (parsed \ "CLASS_NAME") shouldBe JString("io.shiftleft.testcode.splitmeup.TestGraph")
      (parsed \ "FILENAME") shouldBe JString("io/shiftleft/testcode/splitmeup/TestGraph.java")
    }

    "operating on primitive" in {
      val json = fixture.cpg.method.name(".*manyArgs.*").signature.toJson
      val parsed = parse(json).children.head //exactly one result for the above query
      parsed shouldBe JString("java.lang.String(java.lang.String,java.lang.Integer,java.lang.Long,java.lang.Double)")
    }
  }

  ".p for pretty printing" should {

    "use Show.Default instance if nothing else applies" in {
      case class Foo(i: Int)
      val steps: Steps[Foo] = new Steps(__(Foo(42)))
      steps.p2.head shouldBe "Foo(42)"
    }

    "use Show.ForNode instance for NodeSteps" in ExistingCpgFixture("splitmeup") { fixture =>
      def mainMethods: Steps[nodes.Method] =
        fixture.cpg.method.name("main")

      val nodeId = mainMethods.head.id
      mainMethods.p2.head shouldBe s"""(METHOD,$nodeId): AST_PARENT_FULL_NAME: io.shiftleft.testcode.splitmeup.TestGraph, AST_PARENT_TYPE: TYPE_DECL, BINARY_SIGNATURE: ([Ljava/lang/String;)V, FULL_NAME: io.shiftleft.testcode.splitmeup.TestGraph.main:void(java.lang.String[]), IS_EXTERNAL: false, LINE_NUMBER: 12, NAME: main, SIGNATURE: void(java.lang.String[])"""
    }

    "allows to provide custom Show instance" in ExistingCpgFixture("splitmeup") { fixture =>
      def mainMethods: Steps[nodes.Method] =
        fixture.cpg.method.name("main")

      implicit val customShowInstance = new Show[nodes.Method] {
        override def apply(node: nodes.Method): String = "my custom pretty printer"
      }

      mainMethods.p2.head shouldBe "my custom pretty printer"
    }

    "uses Show instance from package" in ExistingCpgFixture("splitmeup") { fixture =>
      object SomePackage {
        implicit def packageShowInstance: Show[nodes.Method] = { method: nodes.Method =>
          "package defined pretty printer"
        }
      }

      import SomePackage._
      def mainMethods: Steps[nodes.Method] =
        fixture.cpg.method.name("main")

      mainMethods.p2.head shouldBe "package defined pretty printer"
    }
  }

}

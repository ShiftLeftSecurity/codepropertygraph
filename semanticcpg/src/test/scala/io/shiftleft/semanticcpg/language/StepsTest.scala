package io.shiftleft.semanticcpg.language

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.semanticcpg.testfixtures.ExistingCpgFixture
import org.json4s.JString
import org.json4s.native.JsonMethods.parse
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import overflowdb.traversal._

class StepsTest extends AnyWordSpec with Matchers {

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
          .filter(_.fullName.matches(".*main.*"))
          .toList

      mainMethods.size shouldBe 1
    }

    "filter with traversal on cpg type" in ExistingCpgFixture("splitmeup") { fixture =>
      def allMethods = fixture.cpg.method
      val publicMethods = allMethods.where(_.isPublic)

      allMethods.toList.size should be > publicMethods.toList.size
    }

    "filter on id" when {
      "providing one" in ExistingCpgFixture("splitmeup") { fixture =>
        // find an arbitrary method so we can find it again in the next step
        val method: nodes.Method = fixture.cpg.method.head
        val results: List[nodes.Method] = fixture.cpg.method.id(method.id).toList

        results.size shouldBe 1
        results.head.underlying.id
      }

      "providing multiple" in ExistingCpgFixture("splitmeup") { fixture =>
        // find two arbitrary methods so we can find it again in the next step
        val methods = fixture.cpg.method.toList.take(2)
        val results: List[nodes.Method] = fixture.cpg.method.id(methods.map(_.id): _*).toList

        results.size shouldBe 2
        results.toSet shouldBe methods.toSet
      }
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
      (method.name, method.parameter.l)
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
      (parsed \ "name") shouldBe JString("io.shiftleft.testcode.splitmeup")
    }

    "operating on NewNode" in {
      val json = fixture.cpg.method.name(".*manyArgs.*").location.toJson
      val parsed = parse(json).children.head //exactly one result for the above query
      (parsed \ "symbol") shouldBe JString("manyArgs")
      (parsed \ "className") shouldBe JString("io.shiftleft.testcode.splitmeup.TestGraph")
      (parsed \ "filename") shouldBe JString("io/shiftleft/testcode/splitmeup/TestGraph.java")
    }

    "operating on primitive" in {
      val json = fixture.cpg.method.name(".*manyArgs.*").signature.toJson
      val parsed = parse(json).children.head //exactly one result for the above query
      parsed shouldBe JString("java.lang.String(java.lang.String,java.lang.Integer,java.lang.Long,java.lang.Double)")
    }
  }

  ".p for pretty printing" should {

    "use default `toString` if nothing else applies" in {
      case class Foo(i: Int)
      val steps: Steps[Foo] = new Steps(Traversal.fromSingle(Foo(42)))
      steps.p.head shouldBe "Foo(42)"
    }

    "render nodes as `(label,id): properties`" in ExistingCpgFixture("splitmeup") { fixture =>
      def mainMethods: Traversal[nodes.Method] =
        fixture.cpg.method.name("main")

      val nodeId = mainMethods.head.id
      val printed = mainMethods.p.head
      printed.should(startWith(s"""(METHOD,$nodeId):"""))
      printed.should(include("AST_PARENT_FULL_NAME: io.shiftleft.testcode.splitmeup.TestGraph"))
      printed.should(include("FULL_NAME: io.shiftleft.testcode.splitmeup.TestGraph.main:void(java.lang.String[])"))
    }

    "allows to provide custom Show instance" in ExistingCpgFixture("splitmeup") { fixture =>
      def mainMethods: Steps[nodes.Method] =
        fixture.cpg.method.name("main")

      implicit val customShowInstance = new Show[nodes.Method] {
        override def apply(node: nodes.Method): String = "my custom pretty printer"
      }

      mainMethods.p.head shouldBe "my custom pretty printer"
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

      mainMethods.p.head shouldBe "package defined pretty printer"
    }
  }

  ".help step" should {

    "show domain overview" in {
      val cpg = Cpg.emptyCpg
      cpg.help should include(".comment")
      cpg.help should include("All comments in source-based CPGs")
      cpg.help should include(".arithmetic")
      cpg.help should include("All arithmetic operations")
    }

    "provide node-specific overview" in {
      val methodSteps = new Steps[nodes.Method](null)
      methodSteps.help should include("Available steps for Method")
      methodSteps.help should include(".namespace")
      methodSteps.help should include(".depth") //from AstNode

      methodSteps.helpVerbose should include("traversal name")
      methodSteps.helpVerbose should include("io.shiftleft.semanticcpg.language.types.structure.Method")
    }

    "provides generic help" when {
      "using verbose mode" when {
        "traversing nodes" in {
          val methodTraversal = Traversal.empty[nodes.Method]
          methodTraversal.helpVerbose should include(".l")
          methodTraversal.helpVerbose should include(".label")
        }

        "traversing non-nodes" in {
          val stringTraversal = Traversal.empty[String]
          stringTraversal.helpVerbose should include(".l")
          stringTraversal.helpVerbose should not include ".label"
        }
      }
    }
  }

}

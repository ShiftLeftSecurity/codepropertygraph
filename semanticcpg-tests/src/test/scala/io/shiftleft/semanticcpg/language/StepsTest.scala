package io.shiftleft.semanticcpg.language

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.{NodeKeys, NodeTypes, nodes}
import io.shiftleft.semanticcpg.testfixtures.ExistingCpgFixture
import org.json4s.JString
import org.json4s.native.JsonMethods.parse
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import overflowdb.traversal.{Traversal, jIteratortoTraversal}

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
      param <- method.parameter
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

  "provides extension steps for Traversals and Nodes" in ExistingCpgFixture("splitmeup") { fixture =>
    /* n.b. interestingly, intellij puts some red squiggles on `Traversal.file` etc. if one imports
     * `overflowdb.traversal.iterableToTraversal`,  e.g. via `import overflowdb.traversal._`
     * Looks like thats a bug in intellij's presentation compiler, esp. given that both sbt and intellij compile this
     * code without errors, and intellij's autocomplete works.
     */
    val cpg = fixture.cpg
    def literal = cpg.literal.code(".*wow.*")
    literal.file.name.head shouldBe "io/shiftleft/testcode/splitmeup/TestGraph.java"
    literal.head.file.name.head shouldBe "io/shiftleft/testcode/splitmeup/TestGraph.java"
    literal.method.name.head shouldBe "manyArgs"
    literal.head.method.name shouldBe "manyArgs"

    def typ = cpg.typ.nameExact("TestGraph")
    typ.namespace.name.head shouldBe "io.shiftleft.testcode.splitmeup"
    typ.head.namespace.name.head shouldBe "io.shiftleft.testcode.splitmeup"

    def typeDecl = cpg.typeDecl.nameExact("TestGraph")
    typeDecl.namespace.name.head shouldBe "io.shiftleft.testcode.splitmeup"
    typeDecl.head.namespace.name.head shouldBe "io.shiftleft.testcode.splitmeup"

    def call = cpg.call.nameExact("add")
    call.method.name.size shouldBe 3
    call.map(_.method.name).size shouldBe 3

    // not testable in this cpg, but if it compiles it's probably fine
    def controlStructure = cpg.controlStructure
    controlStructure.condition
    controlStructure.headOption.map(_.condition)

    def identifier = cpg.identifier.name("val1")
    identifier.refsTo.property(NodeKeys.LINE_NUMBER).head shouldBe 18
    identifier.head.refsTo.property(NodeKeys.LINE_NUMBER).head shouldBe 18

    def member = cpg.member.name("foo")
    member.typeDecl.name.head shouldBe "TestGraph"
    member.head.typeDecl.name.head shouldBe "TestGraph"

    def local = cpg.local.name("two")
    local.typ.name.head shouldBe "Integer"
    local.head.typ.name.head shouldBe "Integer"

    def method = cpg.method.name("add")
    method.parameter.size shouldBe 2
    method.head.parameter.size shouldBe 2

    def methodParameterIn = cpg.parameter.name("one")
    methodParameterIn.typ.name.head shouldBe "String"
    methodParameterIn.head.typ.name.head shouldBe "String"

    def methodParameterOut = cpg.graph.nodes(NodeTypes.METHOD_PARAMETER_OUT).cast[nodes.MethodParameterOut].name("two")
    methodParameterOut.typ.name.head shouldBe "Integer"
    methodParameterOut.head.typ.name.head shouldBe "Integer"

    def methodReturn = cpg.methodReturn.typeFullNameExact("int")
    methodReturn.method.name.toSet shouldBe Set("methodWithCycle", "add")
    methodReturn.map(_.method.name).toSet shouldBe Set("methodWithCycle", "add")

    def namespace = cpg.namespace.name("io.shiftleft.testcode.splitmeup")
    namespace.typeDecl.name.toSet shouldBe Set("TestGraph", "InlineArguments")
    namespace.head.typeDecl.name.toSet shouldBe Set("TestGraph", "InlineArguments")

    def namespaceBlock = cpg.namespaceBlock.name("io.shiftleft.testcode.splitmeup")
    namespaceBlock.typeDecl.name.toSet shouldBe Set("TestGraph", "InlineArguments")
    namespaceBlock.flatMap(_.typeDecl.name).toSet shouldBe Set("TestGraph", "InlineArguments")

    def file = cpg.file.name("io/shiftleft/testcode/splitmeup/TestGraph.java")
    file.typeDecl.name.head shouldBe "TestGraph"
    file.head.typeDecl.name.head shouldBe "TestGraph"

    def block = cpg.graph.nodes(NodeTypes.BLOCK).cast[nodes.Block].typeFullName("int")
    block.local.name.size shouldBe 6
    block.flatMap(_.local.name).size shouldBe 6

    // not testable in this cpg, but if it compiles it's probably fine
    def methodRef = cpg.methodRef
    methodRef.referencedMethod
    methodRef.headOption.map(_.referencedMethod)

    // not testable in this cpg, but if it compiles it's probably fine
    def binding = cpg.graph.nodes(NodeTypes.BINDING).cast[nodes.Binding]
    binding.boundMethod
    binding.headOption.map(_.boundMethod)
  }

}

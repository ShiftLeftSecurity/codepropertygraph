package io.shiftleft.semanticcpg.language

import io.shiftleft.codepropertygraph.generated.NodeTypes
import io.shiftleft.semanticcpg.testfixtures.CodeToCpgFixture
import org.scalatest.{Matchers, WordSpec}

class NodeTypeStartersTests extends WordSpec with Matchers {

  val code = """
       int main(int argc, char **argv) { }
       struct foo { int x; };
    """

  CodeToCpgFixture().buildCpg(code) { cpg =>
    "should allow retrieving files" in {
      cpg.file.name.l.head should endWith(".c")
    }

    "should allow retrieving methods" in {
      cpg.method.name.l shouldBe List("main")
    }

    "should allow retrieving parameters" in {
      cpg.parameter.name.toSet shouldBe Set("argc", "argv")
    }

    "should allow retrieving type declarations" in {
      cpg.typeDecl.name.toSet shouldBe Set("foo", "int", "void", "char * *")
    }

    "should allow retrieving members" in {
      cpg.member.name.l shouldBe List("x")
    }

    "should allow retrieving (used) types" in {
      cpg.types.name.toSet shouldBe Set("int", "void", "char * *")
    }

    "should allow retrieving namespaces" in {
      cpg.namespace.name.l shouldBe List("<global>")
    }

    "should allow retrieving namespace blocks" in {
      cpg.namespaceBlock.name.toSet shouldBe Set("<global>")
    }

    "should allow retrieving of method returns" in {
      cpg.methodReturn.code.l shouldBe List("RET")
    }

    "should allow retrieving of meta data" in {
      cpg.metaData.language.l shouldBe List("C")
    }

    "should allow retrieving all nodes" in {
      val allNodesLabels = cpg.all.label.toSet

      allNodesLabels shouldBe Set(
        NodeTypes.NAMESPACE_BLOCK,
        NodeTypes.MEMBER,
        NodeTypes.TYPE_DECL,
        NodeTypes.METHOD_PARAMETER_IN,
        NodeTypes.METHOD_PARAMETER_OUT,
        NodeTypes.NAMESPACE,
        NodeTypes.META_DATA,
        NodeTypes.METHOD,
        NodeTypes.FILE,
        NodeTypes.METHOD_RETURN,
        NodeTypes.TYPE,
        NodeTypes.BLOCK
      )
    }

  }

}

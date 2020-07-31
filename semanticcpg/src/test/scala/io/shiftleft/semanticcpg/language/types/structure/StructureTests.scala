package io.shiftleft.semanticcpg.language.types.structure

import org.scalatest.{Matchers, WordSpec}
import io.shiftleft.semanticcpg.language._
import io.shiftleft.semanticcpg.testfixtures.CodeToCpgSuite

class StructureTests extends CodeToCpgSuite {

  override val code = """
                   | struct foo {};
                   | int main() {}
                 """.stripMargin

  "should return one source file" in {
    cpg.file.name.l.count(_.endsWith(".c")) shouldBe 1
  }

  "should allow traversing from file to namespace blocks" in {
    cpg.file.namespaceBlock.name.toSet shouldBe Set("<global>")
  }

  "should allow traversing to namespaces" in {
    cpg.file.namespace.name("<global>").l.size shouldBe 1
  }

  "should allow traversing to type declarations" in {
    cpg.file.typeDecl.name.toSet shouldBe Set("foo", "void", "int")
  }

  "should allow traversing to methods in namespaces" in {
    cpg.file.namespace.method.name.toSet shouldBe Set("main")
  }

}

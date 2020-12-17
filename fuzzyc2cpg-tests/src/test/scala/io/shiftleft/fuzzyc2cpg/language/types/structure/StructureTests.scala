package io.shiftleft.fuzzyc2cpg.language.types.structure

import io.shiftleft.fuzzyc2cpg.testfixtures.CodeToCpgSuite
import io.shiftleft.semanticcpg.language._

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
    cpg.file.namespace.name("<global>").l.size shouldBe 2
  }

  "should allow traversing to type declarations" in {
    cpg.file.typeDecl.name.toSet shouldBe Set("foo", "void", "int")
  }

  "should allow traversing to methods in namespaces" in {
    cpg.file.namespace.method.name.toSet shouldBe Set("main")
  }

}

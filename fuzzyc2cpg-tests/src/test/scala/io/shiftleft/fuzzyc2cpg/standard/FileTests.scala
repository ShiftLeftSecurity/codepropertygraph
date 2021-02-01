package io.shiftleft.fuzzyc2cpg.standard

import io.shiftleft.fuzzyc2cpg.testfixtures.FuzzyCCodeToCpgSuite
import io.shiftleft.semanticcpg.language._
import io.shiftleft.semanticcpg.language.types.structure.File

class FileTests extends FuzzyCCodeToCpgSuite {

  override val code: String =
    """
      | int foo() {}
      | int bar() {}
      | struct my_struct { int x; };
      |""".stripMargin

  "should contain two file nodes in total, both with order=0" in {
    cpg.file.order.l shouldBe List(0, 0)
    cpg.file.name(File.UNKNOWN).size shouldBe 1
    cpg.file.nameNot(File.UNKNOWN).size shouldBe 1
  }

  "should contain exactly one placeholder file node with `name=\"<unknown>\"/order=0`" in {
    cpg.file(File.UNKNOWN).order.l shouldBe List(0)
    cpg.file(File.UNKNOWN).hash.l shouldBe List()
  }

  "should contain exactly one non-placeholder file with absolute path in `name`" in {
    cpg.file.nameNot(File.UNKNOWN).l match {
      case List(x) =>
        x.name should startWith("/")
        // C-frontend currently does not set hash but should do so
        // in the future
        x.hash shouldBe None
      case _ => fail()
    }
  }

  "should allow traversing from file to its namespace blocks" in {
    cpg.file.nameNot(File.UNKNOWN).namespaceBlock.name.toSet shouldBe Set("<global>")
  }

  "should allow traversing from file to its methods via namespace block" in {
    cpg.file.nameNot(File.UNKNOWN).method.name.toSet shouldBe Set("foo", "bar")
  }

  "should allow traversing from file to its type declarations via namespace block" in {
    cpg.file.nameNot(File.UNKNOWN).typeDecl.name.toSet shouldBe Set("my_struct")
  }

}

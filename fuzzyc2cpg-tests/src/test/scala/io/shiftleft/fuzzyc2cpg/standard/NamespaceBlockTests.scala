package io.shiftleft.fuzzyc2cpg.standard

import io.shiftleft.fuzzyc2cpg.testfixtures.FuzzyCCodeToCpgSuite
import io.shiftleft.semanticcpg.language._
import io.shiftleft.semanticcpg.language.types.structure.{File, Namespace}

class NamespaceBlockTests extends FuzzyCCodeToCpgSuite {

  // The fuzzyC parser currently just ignores namespaces. We place symbols
  // that can't be associated in a file into the namespace "<global>", and
  // those which can in `filename:<global>`

  override val code: String =
    """
      |int foo() {}
      |struct my_struct{};
      |""".stripMargin

  "should contain two namespace blocks in total" in {
    cpg.namespaceBlock.size shouldBe 2
  }

  "should contain a correct global namespace block for the `<unknown>` file" in {
    cpg.namespaceBlock.filename(File.UNKNOWN).l match {
      case List(x) =>
        x.name shouldBe Namespace.globalNamespaceName
        x.fullName shouldBe Namespace.globalNamespaceName
        x.order shouldBe 0
      case _ => fail()
    }
  }

  "should contain correct namespace block for known file" in {
    cpg.namespaceBlock.filenameNot(File.UNKNOWN).l match {
      case List(x) =>
        x.name shouldBe Namespace.globalNamespaceName
        x.fullName shouldBe s"${x.filename}:${Namespace.globalNamespaceName}"
        x.order shouldBe 0
      case _ => fail()
    }
  }

  "should allow traversing from namespace block to method" in {
    cpg.namespaceBlock.filenameNot(File.UNKNOWN).method.name.l shouldBe List("foo")
  }

  "should allow traversing from namespace block to type declaration" in {
    cpg.namespaceBlock.filenameNot(File.UNKNOWN).typeDecl.name.l shouldBe List("my_struct")
  }

  "should allow traversing from namespace block to namespace" in {
    cpg.namespaceBlock.filenameNot(File.UNKNOWN).namespace.name.l shouldBe List(Namespace.globalNamespaceName)
  }

}

package io.shiftleft.fuzzyc2cpg.querying

import io.shiftleft.fuzzyc2cpg.testfixtures.FuzzyCCodeToCpgSuite
import io.shiftleft.semanticcpg.language._
import io.shiftleft.semanticcpg.language.types.structure.File

class FileNodeTests extends FuzzyCCodeToCpgSuite {

  override val code: String =
    """
      |
      |""".stripMargin

  "should contain two file nodes in total, both with order=0" in {
    cpg.file.order.l shouldBe List(0, 0)
    cpg.file.name(File.UNKNOWN).size shouldBe 1
    cpg.file.nameNot(File.UNKNOWN).size shouldBe 1
  }

  "should contain exactly one placeholder file node with `name=\"<unknown>\"/order=0`" in {
    cpg.file(File.UNKNOWN).l match {
      case List(x) =>
        x.order shouldBe 0
      case _ => fail
    }
  }

  "should contain exactly one non-placeholder file with absolute path in `name`" in {
    cpg.file.nameNot(File.UNKNOWN).l match {
      case List(x) =>
        x.name should startWith("/")
      case _ => fail
    }
  }

}

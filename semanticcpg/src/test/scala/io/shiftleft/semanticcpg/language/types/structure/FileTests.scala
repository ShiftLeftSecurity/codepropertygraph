package io.shiftleft.semanticcpg.language.types.structure

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.semanticcpg.testfixtures.ExistingCpgFixture
import org.scalatest.{LoneElement, Matchers, WordSpec}
import io.shiftleft.semanticcpg.language._

class FileTests extends WordSpec with Matchers with LoneElement {
  val fileName = "io/shiftleft/testcode/file/FileTest.java"

  "generic cpg" should ExistingCpgFixture("file") { fixture =>
    s"find file $fileName" in {
      val queryResult: List[nodes.File] = fixture.cpg.file.l

      queryResult.map(_.name) should contain(fileName)
    }

    "be able to expand to class FileTest" in {
      val queryResult: List[nodes.TypeDecl] =
        fixture.cpg.file.name(fileName).typeDecl.l

      queryResult.loneElement.name shouldBe "FileTest"
    }

    "be able to expand to namespace" in {
      val queryResult: List[nodes.Namespace] =
        fixture.cpg.file.name(fileName).namespace.l

      queryResult.loneElement.name shouldBe "io.shiftleft.testcode.file"
    }

    "be able to get file in which a formal method return is defined" in {
      val queryResult: List[nodes.File] =
        fixture.cpg.method.name("method").methodReturn.file.l

      queryResult.loneElement.name shouldBe fileName
    }
  }

}

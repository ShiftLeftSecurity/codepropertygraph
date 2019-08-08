package io.shiftleft.queryprimitives.steps

import io.shiftleft.codepropertygraph.generated.nodes
import org.scalatest.{Matchers, WordSpec}

class FileTests extends WordSpec with Matchers {

  "generic cpg" should ExistingCpgFixture("file") { fixture =>
    "find file io/shiftleft/testcode/file/FileTest" in {
      val queryResult: List[nodes.File] = fixture.cpg.file.toList

      queryResult.map(_.name) should contain("io/shiftleft/testcode/file/FileTest.java")
    }

    "be able to expand to class FileTest" in {
      val queryResult: List[nodes.TypeDecl] =
        fixture.cpg.file.name("io/shiftleft/testcode/file/FileTest.java").typeDecl.toList

      queryResult.size shouldBe 1
      queryResult.head.name shouldBe "FileTest"
    }

    "be able to expand to namespace" in {
      val queryResult: List[nodes.Namespace] =
        fixture.cpg.file.name("io/shiftleft/testcode/file/FileTest.java").namespace.toList

      queryResult.size shouldBe 1
      queryResult.head.name shouldBe "io.shiftleft.testcode.file"
    }

    "be able to get file in which a formal method return is defined" in {
      val queryResult: List[nodes.File] =
        fixture.cpg.method.name("method").methodReturn.file.toList

      queryResult.size shouldBe 1
    }
  }

}

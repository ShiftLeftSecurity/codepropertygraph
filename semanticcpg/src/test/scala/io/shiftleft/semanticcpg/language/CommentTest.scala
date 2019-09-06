package io.shiftleft.semanticcpg.language

import io.shiftleft.semanticcpg.testfixtures.CodeToCpgFixture
import org.scalatest.{Matchers, WordSpec}

class CommentTest extends WordSpec with Matchers {

  val code =
    """ // A comment
      | int main(int argc, char **argv) {}
    """.stripMargin

  "should find comment linked to file" in {
    CodeToCpgFixture().buildCpg(code) { cpg =>
      println(cpg.file.comment.code.l shouldBe List("// A comment\n"))
    }
  }

}

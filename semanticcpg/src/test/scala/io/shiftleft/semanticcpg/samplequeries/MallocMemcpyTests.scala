package io.shiftleft.semanticcpg.samplequeries

import io.shiftleft.semanticcpg.testfixtures.CodeToCpgFixture
import org.scalatest.{Matchers, WordSpec}

class MallocMemcpyTests extends WordSpec with Matchers {

  val code =
    """
      | int func(size_t len, char *src) {
      | char *dst = malloc(len + 8);
      | memcpy(dst, src, len + 7);
      | }
    """.stripMargin

  CodeToCpgFixture(code) { cpg =>
    }

}

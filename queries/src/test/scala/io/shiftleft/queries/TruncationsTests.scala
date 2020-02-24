package io.shiftleft.queries

import io.shiftleft.dataflowengine.language.DataFlowCodeToCpgFixture
import org.scalatest.{Matchers, WordSpec}
import io.shiftleft.semanticcpg.language._

class TruncationsTests extends WordSpec with Matchers {

  val code: String =
    """
      int vulnerable(char *str) {
        int len;
        len = strlen(str);
      }

      int non_vulnerable(char *str) {
        size_t len;
        len = strlen(str);
      }
    """

  DataFlowCodeToCpgFixture(code) { cpg =>
    cpg.call("strlen").inAssignment.target.evalType("(g?)int").method.name.l shouldBe List("vulnerable")
  }

}

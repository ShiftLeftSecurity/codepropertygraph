package io.shiftleft.queries

import io.shiftleft.dataflowengineoss.language.DataFlowCodeToCpgSuite
import io.shiftleft.semanticcpg.language._

class TruncationsTests extends DataFlowCodeToCpgSuite {

  override val code: String =
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

  "truncation test should work" in {
    cpg.call("strlen").inAssignment.target.evalType("(g?)int").method.name.l shouldBe List("vulnerable")
  }

}

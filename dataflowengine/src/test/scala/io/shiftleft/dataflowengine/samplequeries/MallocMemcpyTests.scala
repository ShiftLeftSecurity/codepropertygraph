package io.shiftleft.dataflowengine.samplequeries

import io.shiftleft.dataflowengine.language.DataFlowCodeToCpgFixture
import org.scalatest.{Matchers, WordSpec}
import io.shiftleft.semanticcpg.language._
import io.shiftleft.dataflowengine.language._

class MallocMemcpyTests extends WordSpec with Matchers {

  val code: String =
    """
      int vulnerable(size_t len, char *src) {
        char *dst = malloc(len + 8);
        memcpy(dst, src, len + 7);
      }

      int non_vulnerable(size_t len, char *src) {
       char *dst = malloc(len + 8);
       memcpy(dst, src,len + 8);
      }

    """

  DataFlowCodeToCpgFixture(code) { cpg =>
    "find calls to malloc where first argument contains addition" in {

      val src = cpg.call("malloc").filter(_.argument(1).containsCallTo("<operator>.add.*"))

      cpg
        .call("memcpy")
        .whereNonEmpty { call =>
          val codeInThirdArg = call.argument(3).code
          call.argument(1).reachableBy(src).filterNot(_.argument(1).codeExact(codeInThirdArg))
        }
        .code
        .l shouldBe List("memcpy(dst, src, len + 7)")
    }

  }

}

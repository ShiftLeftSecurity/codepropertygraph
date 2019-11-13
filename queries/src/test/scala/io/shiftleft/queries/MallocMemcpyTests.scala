package io.shiftleft.queries

import io.shiftleft.dataflowengine.language.{DataFlowCodeToCpgFixture, _}
import io.shiftleft.semanticcpg.language._
import org.scalatest.{Matchers, WordSpec}

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

    /**
      * Find calls to malloc where the first argument contains an addition,
      * and the return value flows into memcpy as the third argument, and
      * the third argument of that memcpy is unequal to the first argument
      * of malloc. This is an adaption of the old-joern query first shown
      * at 31C3 that found a buffer overflow in VLC's MP4 demuxer (CVE-2014-9626).
      * */

    "find calls to malloc/memcpy system with different additions in arguments" in {

      val src = cpg.call("malloc")
        .filter(_.argument(1).containsCallTo("<operator>.add.*"))

      cpg
        .call("memcpy")
        .whereNonEmpty { call =>
          val codeInThirdArg = call.argument(3).code
          call.argument(1).reachableBy(src)
            .filterNot(_.argument(1).codeExact(codeInThirdArg))
        }
        .code
        .l shouldBe List("memcpy(dst, src, len + 7)")
    }

  }

}

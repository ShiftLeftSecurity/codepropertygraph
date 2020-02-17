package io.shiftleft.queries

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.dataflowengine.language.{DataFlowCodeToCpgFixture, _}
import io.shiftleft.semanticcpg.language._
import io.shiftleft.semanticcpg.language.operatorextension._
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
      * Find calls to malloc where the first argument contains an arithmetic expression,
      * the allocated buffer flows into memcpy as the first argument, and the third
      * argument of that memcpy is unequal to the first argument of malloc. This is
      * an adaption of the old-joern query first shown at 31C3 that found a
      * buffer overflow in VLC's MP4 demuxer (CVE-2014-9626).
      * */
    "find calls to malloc/memcpy system with different expressions in arguments" in {
      val src = cpg.call("malloc").filter(_.argument(1).arithmetics).l

      cpg
        .call("memcpy")
        .whereNonEmpty { call =>
          call
            .argument(1)
            .reachableBy(src.start)
            .filterNot(_.argument(1).codeExact(call.argument(3).code))
        }
        .code
        .l shouldBe List("memcpy(dst, src, len + 7)")
    }

  }

}

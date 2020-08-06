package io.shiftleft.queries

import io.shiftleft.dataflowengineoss.language.{DataFlowCodeToCpgSuite, _}
import io.shiftleft.semanticcpg.language._
import org.scalatest.{Matchers, WordSpec}
import overflowdb.traversal.Traversal

class MallocMemcpyTests extends DataFlowCodeToCpgSuite {

  override val code: String =
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

  /**
    * Find calls to malloc where the first argument contains an arithmetic expression,
    * the allocated buffer flows into memcpy as the first argument, and the third
    * argument of that memcpy is unequal to the first argument of malloc. This is
    * an adaption of the old-joern query first shown at 31C3 that found a
    * buffer overflow in VLC's MP4 demuxer (CVE-2014-9626).
    * */
  "find calls to malloc/memcpy system with different expressions in arguments" in {
    val src = cpg.call("malloc").where(_.argument(1).arithmetics)

    cpg
      .call("memcpy")
      .filter { call =>
        call
          .argument(1)
          .reachableBy(src)
          .not(_.argument(1).codeExact(call.argument(3).code))
          .hasNext
      }.code.l shouldBe List("memcpy(dst, src, len + 7)")
  }

}

package io.shiftleft.dataflowengine.samplequeries

import io.shiftleft.dataflowengine.language.DataFlowCodeToCpgFixture
import org.scalatest.{Matchers, WordSpec}
import io.shiftleft.semanticcpg.language._
import io.shiftleft.dataflowengine.language._

class MallocMemcpyTests extends WordSpec with Matchers {

  val code: String =
    """
      | int func(size_t len, char *src) {
      | char *dst = malloc(len + 8);
      | memcpy(dst, src, len + 7);
      | }
    """.stripMargin

  DataFlowCodeToCpgFixture(code) { cpg =>
    "find calls to malloc where first argument contains addition" in {
      cpg.call.name("memcpy")
        .argument(3)
        .whereNonEmpty( arg =>
          arg.reachableBy(
            cpg.call.name("malloc").argument(1).containsCallTo("<operator>.add.*")
          ).filterNot(_.cfgNode.codeExact(arg.code))
        )
    }

  }

}

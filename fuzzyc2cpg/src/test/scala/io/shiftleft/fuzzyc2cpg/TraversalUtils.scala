package io.shiftleft.fuzzyc2cpg

import io.shiftleft.codepropertygraph.generated.{NodeKeys, NodeTypes}
import org.scalatest.Matchers
import overflowdb._

trait TraversalUtils extends Matchers {
  val fixture: CpgTestFixture

  def getMethod(name: String): List[Node] = {
    val result =
      fixture
        .traversalSource
        .label(NodeTypes.METHOD)
        .has(NodeKeys.NAME -> name)
        .l

    result.size shouldBe 1
    result
  }

}

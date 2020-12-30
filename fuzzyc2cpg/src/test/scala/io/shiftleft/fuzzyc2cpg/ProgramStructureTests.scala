package io.shiftleft.fuzzyc2cpg

import io.shiftleft.codepropertygraph.generated.NodeKeys
import io.shiftleft.proto.cpg.Cpg.CpgStruct.Node.NodeType
import io.shiftleft.semanticcpg.language.types.structure.Namespace
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import overflowdb._

class ProgramStructureTests extends AnyWordSpec with Matchers {
  val fixture = CpgTestFixture("structure")

  "Program structure of test project" should {

    "contain <global> namespace block node" in {
      val namespaceBlocks =
        fixture.traversalSource
          .label(NodeType.NAMESPACE_BLOCK.toString)
          .has(NodeKeys.FULL_NAME -> Namespace.globalNamespaceName)
          .l

      namespaceBlocks.size shouldBe 1
    }

    "contain type-decl node" in {
      val nodes = fixture.traversalSource.label(NodeType.TYPE_DECL.toString).l
      nodes.size should be > 0
    }

  }

}

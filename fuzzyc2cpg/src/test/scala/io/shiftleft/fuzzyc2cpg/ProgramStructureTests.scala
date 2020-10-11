package io.shiftleft.fuzzyc2cpg

import java.nio.file.Paths

import io.shiftleft.codepropertygraph.generated.NodeKeys
import io.shiftleft.proto.cpg.Cpg.CpgStruct.Node.NodeType
import org.scalatest.{Matchers, WordSpec}
import overflowdb._

class ProgramStructureTests extends WordSpec with Matchers {
  val fixture = CpgTestFixture("structure")

  "Program structure of test project" should {

    "contain <global> namespace block node" in {
      val namespaceBlocks =
        fixture
          .traversalSource
          .label(NodeType.NAMESPACE_BLOCK.toString)
          .has(NodeKeys.FULL_NAME -> Defines.globalNamespaceName)
          .l

      namespaceBlocks.size shouldBe 1
    }

    "contain one file node" in {
      val fileName = fixture
        .traversalSource
        .label(NodeType.FILE.toString)
        .property(NodeKeys.NAME)
        .headOption
      fileName.isDefined shouldBe true
      fileName.head should not contain ".."

      // Construct a platform-independent path.
      val path = Paths
        .get("src", "test", "resources", "testcode", "structure", "structure.c")
        .toString

      fileName.head should not be path
      fileName.head should endWith(path)
    }

    "contain AST edge from file node to namespace block" in {
      val nodes = fixture
        .traversalSource
        .label(NodeType.FILE.toString)
        .out("AST")
        .hasLabel(NodeType.NAMESPACE_BLOCK.toString)
        .l
      nodes.size shouldBe 1
    }

    "contain type-decl node" in {
      val nodes = fixture.traversalSource.label(NodeType.TYPE_DECL.toString).l
      nodes.size should be > 0
    }

  }

}

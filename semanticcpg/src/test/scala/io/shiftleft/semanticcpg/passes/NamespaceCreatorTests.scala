package io.shiftleft.semanticcpg.passes

import gremlin.scala._
import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeKeys, NodeTypes}
import io.shiftleft.semanticcpg.passes.namespacecreator.NamespaceCreator
import io.shiftleft.semanticcpg.testfixtures.EmptyScalaGraphFixture
import org.scalatest.{Matchers, WordSpec}

class NamespaceCreatorTests extends WordSpec with Matchers {
  "NamespaceCreateor test " in EmptyScalaGraphFixture { graph =>
    val block1 = graph + (NodeTypes.NAMESPACE_BLOCK, NodeKeys.NAME -> "namespace1")
    val block2 = graph + (NodeTypes.NAMESPACE_BLOCK, NodeKeys.NAME -> "namespace1")
    val block3 = graph + (NodeTypes.NAMESPACE_BLOCK, NodeKeys.NAME -> "namespace2")

    val namespaceCreator = new NamespaceCreator(new Cpg(graph.graph))
    namespaceCreator.createAndApply()

    val namespaces = graph.V().hasLabel(NodeTypes.NAMESPACE).toBuffer
    namespaces.size shouldBe 2
    namespaces.map(_.value2(NodeKeys.NAME)).toSet shouldBe Set("namespace1", "namespace2")

    val namspaceBlocks = graph.V().hasLabel(NodeTypes.NAMESPACE).in(EdgeTypes.REF).toSet
    namspaceBlocks shouldBe Set(block1, block2, block3)
  }
}

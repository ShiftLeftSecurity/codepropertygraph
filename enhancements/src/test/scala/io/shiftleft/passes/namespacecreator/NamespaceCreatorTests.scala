package io.shiftleft.passes.namespacecreator

import gremlin.scala._
import io.shiftleft.codepropertygraph.generated.{EdgeKeyNames, EdgeTypes, NodeKeys, NodeTypes}
import org.apache.tinkerpop.gremlin.tinkergraph.structure.TinkerGraph
import org.scalatest.{Matchers, WordSpec}

class NamespaceCreatorTests extends WordSpec with Matchers {
  "NamespaceCreateor test " in {
    implicit val graph: ScalaGraph = TinkerGraph.open.asScala

    val block1 = graph + (NodeTypes.NAMESPACE_BLOCK, NodeKeys.NAME -> "namespace1")
    val block2 = graph + (NodeTypes.NAMESPACE_BLOCK, NodeKeys.NAME -> "namespace1")
    val block3 = graph + (NodeTypes.NAMESPACE_BLOCK, NodeKeys.NAME -> "namespace2")

    val namespaceCreator = new NamespaceCreator(graph)
    namespaceCreator.executeAndApply()

    val namespaces = graph.V().hasLabel(NodeTypes.NAMESPACE).toBuffer
    namespaces.size shouldBe 2
    namespaces.map(_.value2(NodeKeys.NAME)).toSet shouldBe Set("namespace1", "namespace2")

    val namspaceBlocks = graph.V().hasLabel(NodeTypes.NAMESPACE).in(EdgeTypes.REF).toSet
    namspaceBlocks shouldBe Set(block1, block2, block3)
  }
}

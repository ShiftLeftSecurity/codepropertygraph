package io.shiftleft.semanticcpg.passes

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.{NodeKeysOdb, NodeTypes}
import io.shiftleft.semanticcpg.language._
import io.shiftleft.semanticcpg.passes.namespacecreator.NamespaceCreator
import io.shiftleft.semanticcpg.testfixtures.EmptyGraphFixture
import org.scalatest.{Matchers, WordSpec}
import overflowdb._

class NamespaceCreatorTests extends WordSpec with Matchers {
  "NamespaceCreateor test " in EmptyGraphFixture { graph =>
    val cpg = new Cpg(graph)
    val block1 = graph + (NodeTypes.NAMESPACE_BLOCK, NodeKeysOdb.NAME -> "namespace1")
    val block2 = graph + (NodeTypes.NAMESPACE_BLOCK, NodeKeysOdb.NAME -> "namespace1")
    val block3 = graph + (NodeTypes.NAMESPACE_BLOCK, NodeKeysOdb.NAME -> "namespace2")

    val namespaceCreator = new NamespaceCreator(new Cpg(graph))
    namespaceCreator.createAndApply()

    val namespaces = cpg.namespace.l
    namespaces.size shouldBe 2
    namespaces.map(_.name).toSet shouldBe Set("namespace1", "namespace2")

    val namspaceBlocks = cpg.namespace.flatMap(_._namespaceBlockViaRefIn).toSet
    namspaceBlocks shouldBe Set(block1, block2, block3)
  }
}

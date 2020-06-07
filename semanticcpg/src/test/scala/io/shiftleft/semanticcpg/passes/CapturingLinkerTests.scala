package io.shiftleft.semanticcpg.passes

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeKeysOdb, NodeTypes}
import overflowdb._
import io.shiftleft.semanticcpg.passes.linking.capturinglinker.CapturingLinker
import io.shiftleft.semanticcpg.testfixtures.EmptyGraphFixture
import org.scalatest.{Matchers, WordSpec}

import scala.jdk.CollectionConverters._

class CapturingLinkerTests extends WordSpec with Matchers {

  "link CLOSURE_BINDING and LOCALS with same CLOSURE_BINDING_IDs" in EmptyGraphFixture { graph =>
    val closureBinding1 = graph + (NodeTypes.CLOSURE_BINDING, NodeKeysOdb.CLOSURE_BINDING_ID -> "id1")
    val closureBinding2 = graph + (NodeTypes.CLOSURE_BINDING, NodeKeysOdb.CLOSURE_BINDING_ID -> "id2")
    val local1 = graph + (NodeTypes.LOCAL, NodeKeysOdb.CLOSURE_BINDING_ID -> "id1")
    val local2 = graph + (NodeTypes.LOCAL, NodeKeysOdb.CLOSURE_BINDING_ID -> "id2")

    val capturingLinker = new CapturingLinker(new Cpg(graph))
    capturingLinker.createAndApply()

    val capturedByDest1 = local1.out(EdgeTypes.CAPTURED_BY).asScala.toList
    capturedByDest1 shouldBe List(closureBinding1)

    val capturedByDest2 = local2.out(EdgeTypes.CAPTURED_BY).asScala.toList
    capturedByDest2 shouldBe List(closureBinding2)
  }
}

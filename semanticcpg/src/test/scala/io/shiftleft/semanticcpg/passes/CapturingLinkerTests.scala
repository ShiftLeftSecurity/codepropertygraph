package io.shiftleft.semanticcpg.passes

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeKeyNames, NodeTypes}
import io.shiftleft.overflowdb._
import io.shiftleft.semanticcpg.passes.linking.capturinglinker.CapturingLinker
import io.shiftleft.semanticcpg.testfixtures.EmptyGraphFixture
import org.scalatest.{Matchers, WordSpec}

import scala.jdk.CollectionConverters._

class CapturingLinkerTests extends WordSpec with Matchers {

  "link CLOSURE_BINDING and LOCALS with same CLOSURE_BINDING_IDs" in EmptyGraphFixture { graph =>
    // TODO generate in codegen
    val closureBindingId = io.shiftleft.overflowdb.traversal.PropertyKey[String](NodeKeyNames.CLOSURE_BINDING_ID)

    val closureBinding1 = graph + (NodeTypes.CLOSURE_BINDING, closureBindingId -> "id1")
    val closureBinding2 = graph + (NodeTypes.CLOSURE_BINDING, closureBindingId -> "id2")
    val local1 = graph + (NodeTypes.LOCAL, closureBindingId -> "id1")
    val local2 = graph + (NodeTypes.LOCAL, closureBindingId -> "id2")

    val capturingLinker = new CapturingLinker(new Cpg(graph))
    capturingLinker.createAndApply()

    val capturedByDest1 = local1.nodesOut(EdgeTypes.CAPTURED_BY).asScala.toList
    capturedByDest1 shouldBe List(closureBinding1)

    val capturedByDest2 = local2.nodesOut(EdgeTypes.CAPTURED_BY).asScala.toList
    capturedByDest2 shouldBe List(closureBinding2)
  }
}

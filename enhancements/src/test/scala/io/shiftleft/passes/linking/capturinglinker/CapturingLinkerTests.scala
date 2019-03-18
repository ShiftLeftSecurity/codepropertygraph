package io.shiftleft.passes.linking.capturinglinker

import gremlin.scala._
import io.shiftleft.codepropertygraph.generated.{EdgeKeyNames, EdgeTypes, NodeKeys, NodeTypes}
import org.apache.tinkerpop.gremlin.tinkergraph.structure.TinkerGraph
import org.scalatest.{Matchers, WordSpec}

class CapturingLinkerTests extends WordSpec with Matchers {

  "link CLOSURE_BINDING and LOCALS with same CLOSURE_BINDING_IDs" in {
    implicit val graph: ScalaGraph = TinkerGraph.open.asScala

    val closureBinding1 = graph + (NodeTypes.CLOSURE_BINDING, NodeKeys.CLOSURE_BINDING_ID -> "id1")
    val closureBinding2 = graph + (NodeTypes.CLOSURE_BINDING, NodeKeys.CLOSURE_BINDING_ID -> "id2")
    val local1 = graph + (NodeTypes.LOCAL, NodeKeys.CLOSURE_BINDING_ID -> "id1")
    val local2 = graph + (NodeTypes.LOCAL, NodeKeys.CLOSURE_BINDING_ID -> "id2")

    val capturingLinker = new CapturingLinker(graph)
    capturingLinker.executeAndApply()

    val capturedByDest1 = local1.out(EdgeTypes.CAPTURED_BY).toList()
    capturedByDest1.size shouldBe 1
    capturedByDest1.head shouldBe closureBinding1

    val capturedByDest2 = local2.out(EdgeTypes.CAPTURED_BY).toList()
    capturedByDest2.size shouldBe 1
    capturedByDest2.head shouldBe closureBinding2
  }
}

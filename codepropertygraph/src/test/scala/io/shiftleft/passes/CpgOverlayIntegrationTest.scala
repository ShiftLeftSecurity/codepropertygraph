package io.shiftleft.passes

import io.shiftleft.OverflowDbTestInstance
import overflowdb._
import overflowdb.traversal._
import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated._
import org.scalatest.{Matchers, WordSpec}

class CpgOverlayIntegrationTest extends WordSpec with Matchers {

  val InitialNodeCode = "initialNode"
  val Pass1NewNodeCode = "pass1NewNodeCode"
  val Pass2NewNodeCode = "pass2NewNodeCode"

  "cpg passes being serialised to and from overlay protobuf via DiffGraph" in {
    withNewBaseCpg { cpg =>
      val initialNode = cpg.graph.V.has(NodeKeysOdb.CODE -> InitialNodeCode).head
      val pass1 = passAddsEdgeTo(initialNode.asInstanceOf[nodes.StoredNode], Pass1NewNodeCode, cpg)

      val overlay1 = pass1.createApplyAndSerialize()
      fullyConsume(overlay1)
      cpg.graph.nodeCount shouldBe 2
      initialNode.out.property(NodeKeysOdb.CODE).toList shouldBe List(Pass1NewNodeCode)

      val pass1NewNode = cpg.graph.V.has(NodeKeysOdb.CODE -> Pass1NewNodeCode).head
      val pass2 = passAddsEdgeTo(pass1NewNode.asInstanceOf[nodes.StoredNode], Pass2NewNodeCode, cpg)

      val overlay2 = pass2.createApplyAndSerialize()
      fullyConsume(overlay2)
      cpg.graph.nodeCount shouldBe 3
      pass1NewNode.out.property(NodeKeysOdb.CODE).toList shouldBe List(Pass2NewNodeCode)
    }
  }

  "apply cpg pass, serialize the inverse DiffGraph, and apply the inverse to undo" in {
    withNewBaseCpg { cpg =>
      cpg.graph.nodeCount shouldBe 1
      val initialNode = cpg.graph.V.has(NodeKeysOdb.CODE, InitialNodeCode).head

      // 1) add a new node
      val addNodeInverse = applyDiffAndGetInverse(cpg)(
        _.addNode(
          nodes.NewUnknown(code = null)
        ))
      cpg.graph.nodeCount shouldBe 2
      val additionalNode = cpg.graph.V.label("UNKNOWN").head

      // 2) add node property
      val addNodePropertyInverse =
        applyDiffAndGetInverse(cpg)(
          _.addNodeProperty(additionalNode.asInstanceOf[nodes.StoredNode], NodeKeyNames.CODE, "Node2Code"))
      additionalNode.property(NodeKeysOdb.CODE) shouldBe "Node2Code"

      // 2) remove node property
      DiffGraph.Applier.applyDiff(addNodePropertyInverse, cpg)
      additionalNode.propertyOption(NodeKeysOdb.CODE) shouldBe None

      // 1) remove node
      DiffGraph.Applier.applyDiff(addNodeInverse, cpg)
      cpg.graph.nodeCount shouldBe 1
    }
  }

  /* like a freshly deserialized cpg.bin.zip without any overlays applied */
  def withNewBaseCpg[T](fun: Cpg => T): T = {
    val graph = OverflowDbTestInstance.create
    graph + (NodeTypes.METHOD, NodeKeysOdb.CODE -> InitialNodeCode)
    val cpg = Cpg(graph)
    try fun(cpg)
    finally cpg.close()
  }

  /* applies DiffGraph, gets the inverse, serializes and deserializes the inverse (to verify round-trip works) */
  def applyDiffAndGetInverse(cpg: Cpg)(fun: DiffGraph.Builder => Unit): DiffGraph = {
    val builder = DiffGraph.newBuilder
    fun(builder)
    val diff = builder.build
    val applied = DiffGraph.Applier.applyDiff(diff, cpg, undoable = true)
    val inverse = applied.inverseDiffGraph.get
    val inverseProto = new DiffGraphProtoSerializer().serialize(inverse)
    DiffGraph.fromProto(inverseProto, cpg)
  }

  def passAddsEdgeTo(from: nodes.StoredNode, propValue: String, cpg: Cpg): CpgPass = {

    val newNode = nodes.NewUnknown(code = propValue)

    new CpgPass(cpg) {
      override def run(): Iterator[DiffGraph] = {
        val dstGraph = DiffGraph.newBuilder
        dstGraph.addNode(newNode)
        dstGraph.addEdgeFromOriginal(srcNode = from, dstNode = newNode, edgeLabel = EdgeTypes.AST)
        Iterator(dstGraph.build())
      }
    }
  }

  /** equivalent of what happens in `CpgPassRunner.createStoreAndApplyOverlay` */
  def fullyConsume(overlay: Iterator[_]): Unit =
    while (overlay.hasNext) overlay.next()
}

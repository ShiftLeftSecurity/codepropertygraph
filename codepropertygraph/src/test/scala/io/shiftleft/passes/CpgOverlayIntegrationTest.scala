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
      val additionalNode = cpg.graph.V.label("UNKNOWN").l.filter { _.asInstanceOf[nodes.Unknown].code == null }.head

      // 2) add two edges with the same label but different properties (they'll later be disambiguated by their property hash, since edges don't have IDs
      val addEdge1Inverse = applyDiffAndGetInverse(cpg)(
        _.addEdge(
          src = initialNode.asInstanceOf[nodes.StoredNode],
          dst = additionalNode.asInstanceOf[nodes.StoredNode],
          edgeLabel = edges.ContainsNode.Label,
          properties = Seq(EdgeKeyNames.INDEX -> Int.box(1))
        ))
      val addEdge2Inverse = applyDiffAndGetInverse(cpg)(
        _.addEdge(
          src = initialNode.asInstanceOf[nodes.StoredNode],
          dst = additionalNode.asInstanceOf[nodes.StoredNode],
          edgeLabel = edges.ContainsNode.Label,
          properties = Seq(EdgeKeyNames.INDEX -> Int.box(2))
        ))
      def initialNodeOutEdges = initialNode.outE.toList
      initialNodeOutEdges.size shouldBe 2

      // 3) add node property
      val addNodePropertyInverse =
        applyDiffAndGetInverse(cpg)(
          _.addNodeProperty(additionalNode.asInstanceOf[nodes.StoredNode], NodeKeyNames.CODE, "Node2Code"))
      additionalNode.property(NodeKeysOdb.CODE) shouldBe "Node2Code"

      // TODO 4) add edge property - not needed for now?
//      val addEdgePropertyInverse = applyDiffAndGetInverse(cpg)(_.addEdgeProperty(initialNodeOutEdges.head, EdgeKeyNames.INDEX, Int.box(1)))
//      initialNode.start.outE.value(EdgeKeyNames.INDEX).toList shouldBe List(1)

      // now apply all inverse diffgraphs in the reverse order...
      // TODO 4) remove edge property - not needed for now?
//      DiffGraph.Applier.applyDiff(addEdgePropertyInverse, cpg)
//      initialNode.start.outE.value(EdgeKeyNames.INDEX).toList shouldBe List.empty

      // 3) remove node property
      DiffGraph.Applier.applyDiff(addNodePropertyInverse, cpg)
      additionalNode.propertyOption(NodeKeysOdb.CODE) shouldBe None

      // 2) remove edges - they don't have ids and are therefor disambiguated by their property hash
      DiffGraph.Applier.applyDiff(addEdge2Inverse, cpg)
      initialNodeOutEdges.size shouldBe 1
      initialNode.outE.property(EdgeKeysOdb.INDEX).toList shouldBe List(Int.box(1))
      DiffGraph.Applier.applyDiff(addEdge1Inverse, cpg)
      initialNodeOutEdges.size shouldBe 0

      // 1) remove node
      DiffGraph.Applier.applyDiff(addNodeInverse, cpg)
      cpg.graph.nodeCount shouldBe 1
    }
  }

  /* like a freshly deserialized cpg.bin.zip without any overlays applied */
  def withNewBaseCpg[T](fun: Cpg => T): T = {
    val graph = OverflowDbTestInstance.create
    graph + (NodeTypes.UNKNOWN, NodeKeysOdb.CODE -> InitialNodeCode)
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

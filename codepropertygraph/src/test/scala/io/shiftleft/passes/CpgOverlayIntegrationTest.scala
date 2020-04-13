package io.shiftleft.passes

import gremlin.scala._
import io.shiftleft.OverflowDbTestInstance
import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated._
import org.scalatest.{Matchers, WordSpec}

class CpgOverlayIntegrationTest extends WordSpec with Matchers {
  import CpgOverlayIntegrationTest.DummyProduct

  val InitialNodeCode = "initialNode"
  val Pass1NewNodeCode = "pass1NewNodeCode"
  val Pass2NewNodeCode = "pass2NewNodeCode"

  "cpg passes being serialised to and from overlay protobuf via DiffGraph" in {
    withNewBaseCpg { cpg =>
      val initialNode = cpg.graph.V.has(NodeKeys.CODE, InitialNodeCode).head
      val pass1 = passAddsEdgeTo(initialNode.asInstanceOf[nodes.StoredNode], Pass1NewNodeCode, cpg)

      val overlay1 = pass1.createApplyAndSerialize()
      fullyConsume(overlay1)
      cpg.graph.V.count.head shouldBe 2
      initialNode.start.out.value(NodeKeys.CODE).toList shouldBe List(Pass1NewNodeCode)

      val pass1NewNode = cpg.graph.V.has(NodeKeys.CODE, Pass1NewNodeCode).head
      val pass2 = passAddsEdgeTo(pass1NewNode.asInstanceOf[nodes.StoredNode], Pass2NewNodeCode, cpg)

      val overlay2 = pass2.createApplyAndSerialize()
      fullyConsume(overlay2)
      cpg.graph.V.count.head shouldBe 3
      pass1NewNode.start.out.value(NodeKeys.CODE).toList shouldBe List(Pass2NewNodeCode)
    }
  }

  "apply cpg pass, serialize the inverse DiffGraph, and apply the inverse to undo" in {
    withNewBaseCpg { cpg =>
      cpg.graph.V.count.head shouldBe 1
      val initialNode = cpg.graph.V.has(NodeKeys.CODE, InitialNodeCode).head.asInstanceOf[nodes.StoredNode]

      // 1a) add a new node
      val addNodeInverse = applyDiffAndGetInverse(cpg)(_.addNode(
        new nodes.NewNode with DummyProduct {
          override def containedNodesByLocalName = ???
          override def label = NodeTypes.UNKNOWN
          override def properties = Map.empty
        }
      ))
      cpg.graph.V.count.head shouldBe 2
      val additionalNode = cpg.graph.V.hasNot(NodeKeys.CODE).head.asInstanceOf[nodes.StoredNode]

      // 2a) add edge
      val addEdgeInverse = applyDiffAndGetInverse(cpg)(_.addEdge(src = initialNode, dst = additionalNode, edgeLabel = edges.ContainsNode.Label))
      val initialNodeOutEdges = initialNode.outE.toList
      initialNodeOutEdges.size shouldBe 1

      // 3a) add node property
      val addNodePropertyInverse = applyDiffAndGetInverse(cpg)(_.addNodeProperty(additionalNode, NodeKeyNames.CODE, "Node2Code"))
      additionalNode.value2(NodeKeys.CODE) shouldBe "Node2Code"

      // TODO 4a) add edge property - not needed for now?
//      val addEdgePropertyInverse = applyDiffAndGetInverse(cpg)(_.addEdgeProperty(initialNodeOutEdges.head, EdgeKeyNames.INDEX, Int.box(1)))
//      initialNode.start.outE.value(EdgeKeyNames.INDEX).toList shouldBe List(1)

      // now apply all inverse diffgraphs in the reverse order...
      // TODO 4b) remove edge property - not needed for now?
//      DiffGraph.Applier.applyDiff(addEdgePropertyInverse, cpg)
//      initialNode.start.outE.value(EdgeKeyNames.INDEX).toList shouldBe List.empty

      // 3b) remove node property
      DiffGraph.Applier.applyDiff(addNodePropertyInverse, cpg)
      additionalNode.valueOption(NodeKeys.CODE) shouldBe None

      // TODO 2b) remove edge

      // 1b) remove node
      DiffGraph.Applier.applyDiff(addNodeInverse, cpg)
      cpg.graph.V.count.head shouldBe 1
    }
  }

  /* like a freshly deserialized cpg.bin.zip without any overlays applied */
  def withNewBaseCpg[T](fun: Cpg => T): T = {
    val graph: ScalaGraph = OverflowDbTestInstance.create
    val initialNode = graph + NodeTypes.UNKNOWN
    initialNode.setProperty(NodeKeys.CODE, InitialNodeCode)
    val cpg = Cpg(graph.asJava())
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
    val newNode = new nodes.NewNode with DummyProduct {
      override def containedNodesByLocalName = ???
      override def label = NodeTypes.UNKNOWN
      override def properties = Map(NodeKeyNames.CODE -> propValue)
    }
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

object CpgOverlayIntegrationTest {
  private trait DummyProduct {
    def getId: java.lang.Long = ???
    def canEqual(that: Any): Boolean = ???
    def productArity: Int = ???
    def productElement(n: Int): Any = ???
    def productElementLabel(n: Int): String = ???
  }
}

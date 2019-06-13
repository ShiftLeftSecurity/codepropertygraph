package io.shiftleft.passes

import gremlin.scala._
import io.shiftleft.TinkerGraphTestInstance
import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.nodes.NodeVisitor
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeKeyNames, NodeKeys, NodeTypes, edges, nodes}
import io.shiftleft.diffgraph.DiffGraph
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

      val overlay1 = pass1.executeAndCreateOverlay()
      fullyConsume(overlay1)
      cpg.graph.V.count.head shouldBe 2
      initialNode.start.out.value(NodeKeys.CODE).toList shouldBe List(Pass1NewNodeCode)

      val pass1NewNode = cpg.graph.V.has(NodeKeys.CODE, Pass1NewNodeCode).head
      val pass2 = passAddsEdgeTo(pass1NewNode.asInstanceOf[nodes.StoredNode], Pass2NewNodeCode, cpg)

      val overlay2 = pass2.executeAndCreateOverlay()
      fullyConsume(overlay2)
      cpg.graph.V.count.head shouldBe 3
      pass1NewNode.start.out.value(NodeKeys.CODE).toList shouldBe List(Pass2NewNodeCode)
    }
  }

  /* like a freshly deserialized cpg.bin.zip without any overlays applied */
  def withNewBaseCpg[T](fun: Cpg => T): T = {
    val graph: ScalaGraph = TinkerGraphTestInstance.create
    val initialNode = graph + NodeTypes.UNKNOWN
    initialNode.setProperty(NodeKeys.CODE, InitialNodeCode)
    val cpg = Cpg(graph.asJava())
    try fun(cpg)
    finally cpg.close()
  }

  def passAddsEdgeTo(from: nodes.StoredNode, propValue: String, cpg: Cpg): CpgPass = {
    val newNode = new nodes.NewNode with DummyProduct {
      override def containedNodesByLocalName = ???
      override def label = NodeTypes.UNKNOWN
      override def properties = Map(NodeKeyNames.CODE -> propValue)
      override def accept[T](visitor: NodeVisitor[T]): T = ???
    }
    new CpgPass(cpg.graph) {

      /**
        * Main method of enhancement - to be implemented by child class
        **/
      override def run(): Iterator[DiffGraph] = {
        val dstGraph = new DiffGraph
        dstGraph.addNode(newNode)
        dstGraph.addEdgeFromOriginal(srcNode = from, dstNode = newNode, edgeLabel = EdgeTypes.AST)
        Iterator(dstGraph)
      }
    }
  }

  /** equivalent of what happens in `CpgPassRunner.createStoreAndApplyOverlay` */
  def fullyConsume(overlay: Iterator[_]): Unit =
    while (overlay.hasNext) overlay.next()
}

object CpgOverlayIntegrationTest {
  private trait DummyProduct {
    def canEqual(that: Any): Boolean = ???
    def productArity: Int = ???
    def productElement(n: Int): Any = ???
  }
}

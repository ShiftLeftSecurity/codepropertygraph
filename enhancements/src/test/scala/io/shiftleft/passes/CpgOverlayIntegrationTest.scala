package io.shiftleft.passes

import gremlin.scala._
import io.shiftleft.codepropertygraph.generated.nodes.NodeVisitor
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeKeyNames, NodeKeys, NodeTypes, edges, nodes}
import io.shiftleft.diffgraph.DiffGraph
import io.shiftleft.queryprimitives.steps.starters.Cpg
import org.apache.tinkerpop.gremlin.tinkergraph.structure.TinkerGraph
import org.scalatest.{Matchers, WordSpec}

class CpgOverlayIntegrationTest extends WordSpec with Matchers {

  "cpg passes being serialised to and from overlay protobuf via DiffGraph" in {
    val cpg = createNewBaseCpg()

    val initialNode = cpg.graph.V.has(NodeKeys.CODE, InitialNodeCode).head
    val pass1 = new CpgPass(cpg.graph) {
      val newNode = new nodes.NewNode with DummyProduct {
        override def containedNodesByLocalName = ???
        override def label                     = NodeTypes.UNKNOWN
        override def properties                = Map(NodeKeyNames.CODE -> Pass1NewNodeCode)
        override def accept[T](visitor: NodeVisitor[T]): T = ???
      }
      override def run() = {
        val dstGraph = new DiffGraph
        dstGraph.addNode(newNode)
        dstGraph.addEdgeFromOriginal(srcNode = initialNode.asInstanceOf[nodes.StoredNode],
                                     dstNode = newNode,
                                     edgeLabel = EdgeTypes.AST)
        Stream(dstGraph)
      }
    }

    val overlay1 = pass1.executeAndCreateOverlay()
    cpg.graph.V.count.head shouldBe 2
    initialNode.start.out.value(NodeKeys.CODE).toList shouldBe List(Pass1NewNodeCode)

    val pass1NewNode = cpg.graph.V.has(NodeKeys.CODE, Pass1NewNodeCode).head
    val pass2 = new CpgPass(cpg.graph) {
      val newNode = new nodes.NewNode with DummyProduct {
        override def containedNodesByLocalName = ???
        override def label                     = NodeTypes.UNKNOWN
        override def properties                = Map(NodeKeyNames.CODE -> Pass2NewNodeCode)
        override def accept[T](visitor: NodeVisitor[T]): T = ???
      }
      override def run() = {
        val dstGraph = new DiffGraph
        dstGraph.addNode(newNode)
        dstGraph.addEdgeFromOriginal(srcNode = pass1NewNode.asInstanceOf[nodes.StoredNode],
                                     dstNode = newNode,
                                     edgeLabel = EdgeTypes.AST)
        Stream(dstGraph)
      }
    }

    val overlay2 = pass2.executeAndCreateOverlay()
    cpg.graph.V.count.head shouldBe 3
    pass1NewNode.start.out.value(NodeKeys.CODE).toList shouldBe List(Pass2NewNodeCode)
  }

  val InitialNodeCode  = "initialNode"
  val Pass1NewNodeCode = "pass1NewNodeCode"
  val Pass2NewNodeCode = "pass2NewNodeCode"

  /* like a freshly deserialized cpg.bin.zip without any overlays applied */
  def createNewBaseCpg(): Cpg = {
    val graph: Graph = TinkerGraph.open(nodes.Factories.AllAsJava, edges.Factories.AllAsJava)
    val initialNode  = graph + NodeTypes.UNKNOWN
    initialNode.setProperty(NodeKeys.CODE, InitialNodeCode)
    Cpg(graph)
  }
}

trait DummyProduct {
  def canEqual(that: Any): Boolean = ???
  def productArity: Int            = ???
  def productElement(n: Int): Any  = ???
}

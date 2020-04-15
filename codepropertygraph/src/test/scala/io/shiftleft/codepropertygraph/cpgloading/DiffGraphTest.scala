package io.shiftleft.codepropertygraph.cpgloading

import gremlin.scala._
import io.shiftleft.OverflowDbTestInstance
import io.shiftleft.codepropertygraph.generated._
import io.shiftleft.codepropertygraph.generated.nodes.{NewNode, StoredNode}
import io.shiftleft.passes.DiffGraph
import org.scalatest.{Matchers, WordSpec}

class DiffGraphTest extends WordSpec with Matchers {
  "should be able to build an inverse DiffGraph" in {
    withTestOdb { g =>
      // setup existing graph
      // add x and y nodes to graph
      val x = g.addVertex(NodeTypes.UNKNOWN)
      val y = g.addVertex(NodeTypes.UNKNOWN)
      x.property(NodeKeyNames.CODE, "x")
      y.property(NodeKeyNames.CODE, "old y code")
      val x2y = x.addEdge(EdgeTypes.CONTAINS_NODE, y, EdgeKeyNames.LOCAL_NAME, "old edge attr")
      // make diffgraph
      val diffBuilder = DiffGraph.newBuilder
      // add a b c nodes to the DiffGraph.Builder
      val a = makeNode("a")
      diffBuilder.addNode(a)
      val b = makeNode("b")
      diffBuilder.addNode(b)
      val c = makeNode("c")
      diffBuilder.addNode(c)
      // add edge a -> b -> c to the DiffGraph.Builder
      makeEdgeBetweenNewNodes(diffBuilder, a, b)
      makeEdgeBetweenNewNodes(diffBuilder, b, c)
      // add edge from existing node "x" to new node "a" to the builder
      diffBuilder.addEdgeFromOriginal(x.asInstanceOf[StoredNode], a, EdgeTypes.AST)
      // modify property of existing node "y"
      diffBuilder.addNodeProperty(y.asInstanceOf[StoredNode], NodeKeyNames.ORDER, Int.box(123))
      diffBuilder.addNodeProperty(y.asInstanceOf[StoredNode], NodeKeyNames.CODE, "new y code")

      diffBuilder.addEdgeProperty(x2y, EdgeKeyNames.LOCAL_NAME, "new edge attr")
      val diffGraph = diffBuilder.build()
      // apply diffgraph with undoable = true
      val appliedDiffGraph = DiffGraph.Applier.applyDiff(diffGraph, g.graph, true)
      val inverseDiffGraph = appliedDiffGraph.inverseDiffGraph.get
      val changes = inverseDiffGraph.iterator.toList
      import DiffGraph.Change._
      val List(
        SetEdgeProperty(_, EdgeKeyNames.LOCAL_NAME, "old edge attr"), // restore old edge property value
        SetNodeProperty(_, NodeKeyNames.CODE, "old y code"), // restore old Y property value
        RemoveNodeProperty(_, NodeKeyNames.ORDER), // remove newly added property
        RemoveEdge(_), // remove x -> a
        RemoveEdge(_), // remove b -> c
        RemoveEdge(_), // remove a -> b
        RemoveNode(_), // remove c
        RemoveNode(_), // remove b
        RemoveNode(_) // remove a
      ) = changes
    }
  }

  "should be able to revert DiffGraph and reapply again" in {
    withTestOdb { g =>
      var diffBuilder = DiffGraph.newBuilder
      diffBuilder.addNode(makeNode("a"))
      diffBuilder.addNode(makeNode("b"))
      diffBuilder.addNode(makeNode("c"))
      val threeNodes = diffBuilder.build()
      val appliedDiff1 = DiffGraph.Applier.applyDiff(threeNodes, g.graph, true)
      assert(g.V.count.head == 3)
      DiffGraph.Applier.unapplyDiff(g.graph, appliedDiff1.inverseDiffGraph.get)
      assert(g.V.count.head == 0)
      DiffGraph.Applier.applyDiff(threeNodes, g.graph, false)
      diffBuilder = DiffGraph.newBuilder
      makeEdgeBetweenExistingNodes(g, diffBuilder, "a", "b")
      makeEdgeBetweenExistingNodes(g, diffBuilder, "b", "c")
      val appliedDiff2 = DiffGraph.Applier.applyDiff(diffBuilder.build(), g.graph, true)
      assert(g.V.has(NodeKeys.CODE, "a").head.out(EdgeTypes.AST).head().property(NodeKeys.CODE).value() == "b")
      DiffGraph.Applier.unapplyDiff(g.graph, appliedDiff2.inverseDiffGraph.get)
      assert(g.V.has(NodeKeys.CODE, "a").head.out(EdgeTypes.AST).notExists())
      assert(g.V.has(NodeKeys.CODE, "b").head.out(EdgeTypes.AST).notExists())
    }
  }

  "apply and revert DiffGraph with nodes _and_ edges" when {

    "testing simple scenario" in withTestOdb { g =>
      val diffBuilder = DiffGraph.newBuilder
      val newNodeA = makeNode("a")
      val newNodeB = makeNode("b")
      diffBuilder.addNode(newNodeA)
      diffBuilder.addNode(newNodeB)
      diffBuilder.addEdge(newNodeA, newNodeB, EdgeTypes.AST)
      val diff = diffBuilder.build()
      val appliedDiff = DiffGraph.Applier.applyDiff(diff, g.graph, true)
      assert(g.V.count.head == 2)
      assert(g.E.count.head == 1)
      DiffGraph.Applier.unapplyDiff(g.graph, appliedDiff.inverseDiffGraph.get)
      assert(g.V.count.head == 0)
    }

    "testing more complex scenario" in withTestOdb { g =>
      val diffBuilder = DiffGraph.newBuilder
      val newNodeA = makeNode("a")
      val newNodeB = makeNode("b")
      diffBuilder.addNode(newNodeA)
      diffBuilder.addNode(newNodeB)
      diffBuilder.addEdge(newNodeA, newNodeB, EdgeTypes.AST)
      val newNodeC = makeNode("c")
      diffBuilder.addNode(newNodeC)
      diffBuilder.addEdge(newNodeB, newNodeC, EdgeTypes.AST)
      val diff = diffBuilder.build()
      val appliedDiff = DiffGraph.Applier.applyDiff(diff, g.graph, true)
      assert(g.V.count.head == 3)
      assert(g.E.count.head == 2)
      println(appliedDiff.inverseDiffGraph.get.iterator.toList)
      DiffGraph.Applier.unapplyDiff(g.graph, appliedDiff.inverseDiffGraph.get)
      assert(g.V.count.head == 0)
    }
  }

  def withTestOdb[T](f: ScalaGraph => T): T = {
    val graph: ScalaGraph = OverflowDbTestInstance.create
    try f(graph)
    finally graph.close()
  }

  def makeNode(code: String) = new nodes.NewNode {
    override def containedNodesByLocalName = ???
    override def label = NodeTypes.UNKNOWN
    override def properties = Map(NodeKeyNames.CODE -> code)
    def getId: java.lang.Long = ???
    def canEqual(that: Any): Boolean = ???
    def productArity: Int = ???
    def productElement(n: Int): Any = ???
    def productElementLabel(n: Int): String = ???
  }

  def makeEdgeBetweenExistingNodes(g: ScalaGraph, diff: DiffGraph.Builder, codeA: String, codeB: String) = {
    val a = g.V.has(NodeKeys.CODE, codeA).head
    val b = g.V.has(NodeKeys.CODE, codeB).head
    diff.addEdge(a.asInstanceOf[nodes.StoredNode], b.asInstanceOf[nodes.StoredNode], EdgeTypes.AST)
  }

  def makeEdgeBetweenNewNodes(diff: DiffGraph.Builder, a: NewNode, b: NewNode) =
    diff.addEdge(a, b, EdgeTypes.AST)
}

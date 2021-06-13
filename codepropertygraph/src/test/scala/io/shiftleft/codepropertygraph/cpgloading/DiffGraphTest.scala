package io.shiftleft.codepropertygraph.cpgloading

import io.shiftleft.OverflowDbTestInstance
import io.shiftleft.codepropertygraph.generated._
import io.shiftleft.codepropertygraph.generated.edges.ReachingDef
import io.shiftleft.codepropertygraph.generated.nodes.{Identifier, MethodParameterIn, NewNode, NewUnknown, StoredNode}
import io.shiftleft.passes.{DiffGraph, IntervalKeyPool}
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import overflowdb._
import overflowdb.traversal._

import java.lang.{Boolean => JBoolean}

class DiffGraphTest extends AnyWordSpec with Matchers {
  "should be able to build an inverse DiffGraph" in {
    withTestOdb { graph =>
      // setup existing graph
      // add x and y nodes to graph
      val x = graph + (MethodParameterIn.Label, MethodParameterIn.Properties.Code -> "x")
      val y = graph + (Identifier.Label, Identifier.Properties.Code -> "old y code")
      val x2y = x --- (ReachingDef.Label, ReachingDef.Properties.Variable -> "true") --> y

      // make diffgraph
      val diffBuilder = DiffGraph.newBuilder
      // add a b c nodes to the DiffGraph.Builder
      val a = createNewNode("a")
      diffBuilder.addNode(a)
      val b = createNewNode("b")
      diffBuilder.addNode(b)
      val c = createNewNode("c")
      diffBuilder.addNode(c)
      // add edge a -> b -> c to the DiffGraph.Builder
      makeEdgeBetweenNewNodes(diffBuilder, a, b)
      makeEdgeBetweenNewNodes(diffBuilder, b, c)
      // add edge from existing node "x" to new node "a" to the builder
      diffBuilder.addEdgeFromOriginal(x.asInstanceOf[StoredNode], a, EdgeTypes.AST)
      // modify property of existing node "y"
      diffBuilder.addNodeProperty(y.asInstanceOf[StoredNode], PropertyNames.ORDER, Int.box(123))
      diffBuilder.addNodeProperty(y.asInstanceOf[StoredNode], PropertyNames.CODE, "new y code")

      diffBuilder.addEdgeProperty(x2y, PropertyNames.VARIABLE, JBoolean.FALSE)
      val diffGraph = diffBuilder.build()
      // apply diffgraph with undoable = true
      val appliedDiffGraph = DiffGraph.Applier.applyDiff(diffGraph, graph, true, None)
      val inverseDiffGraph = appliedDiffGraph.inverseDiffGraph.get
      val changes = inverseDiffGraph.iterator.toList
      import DiffGraph.Change._
      val List(
        SetEdgeProperty(_, PropertyNames.VARIABLE, "true"), // restore old edge property value
        SetNodeProperty(_, PropertyNames.CODE, "old y code"), // restore old Y property value
        RemoveNodeProperty(_, PropertyNames.ORDER), // remove newly added property
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
    withTestOdb { graph =>
      var diffBuilder = DiffGraph.newBuilder
      diffBuilder.addNode(createNewNode("a"))
      diffBuilder.addNode(createNewNode("b"))
      diffBuilder.addNode(createNewNode("c"))
      val threeNodes = diffBuilder.build()
      val appliedDiff1 = DiffGraph.Applier.applyDiff(threeNodes, graph, true, None)
      graph.nodeCount shouldBe 3
      DiffGraph.Applier.unapplyDiff(graph, appliedDiff1.inverseDiffGraph.get)
      graph.nodeCount shouldBe 0
      DiffGraph.Applier.applyDiff(threeNodes, graph, false, None)
      diffBuilder = DiffGraph.newBuilder
      makeEdgeBetweenExistingNodes(graph, diffBuilder, "a", "b")
      makeEdgeBetweenExistingNodes(graph, diffBuilder, "b", "c")
      val appliedDiff2 = DiffGraph.Applier.applyDiff(diffBuilder.build(), graph, true, None)
      graph.V.has(Properties.CODE -> "a").head.out(EdgeTypes.AST).property(Properties.CODE).head shouldBe "b"
      DiffGraph.Applier.unapplyDiff(graph, appliedDiff2.inverseDiffGraph.get)
      graph.V.has(Properties.CODE -> "a").head.out(EdgeTypes.AST).l shouldBe Nil
      graph.V.has(Properties.CODE, "b").head.out(EdgeTypes.AST).l shouldBe Nil
    }
  }

  "should choose keys from provided KeyPool" in {
    withTestOdb { graph =>
      val builder = DiffGraph.newBuilder
      val firstNode: NewNode = createNewNode("a")
      val secondNode: NewNode = createNewNode("b")
      val thirdNode: NewNode = createNewNode("c")
      builder.addNode(firstNode)
      builder.addNode(secondNode)
      builder.addNode(thirdNode)
      val keyPool = Some(new IntervalKeyPool(20, 30))
      val appliedGraph = DiffGraph.Applier.applyDiff(builder.build(), graph, true, keyPool)
      appliedGraph.nodeToGraphId(firstNode) shouldBe 20
      appliedGraph.nodeToGraphId(secondNode) shouldBe 21
      appliedGraph.nodeToGraphId(thirdNode) shouldBe 22
    }
  }

  "apply and revert DiffGraph with nodes _and_ edges" when {

    "testing simple scenario" in withTestOdb { graph =>
      val diffBuilder = DiffGraph.newBuilder
      val newNodeA = createNewNode("a")
      val newNodeB = createNewNode("b")
      diffBuilder.addNode(newNodeA)
      diffBuilder.addNode(newNodeB)
      diffBuilder.addEdge(newNodeA, newNodeB, EdgeTypes.AST)
      val diff = diffBuilder.build()
      val appliedDiff = DiffGraph.Applier.applyDiff(diff, graph, true, None)
      graph.nodeCount shouldBe 2
      graph.edgeCount shouldBe 1
      DiffGraph.Applier.unapplyDiff(graph, appliedDiff.inverseDiffGraph.get)
      graph.nodeCount shouldBe 0
    }

    "testing more complex scenario" in withTestOdb { graph =>
      val diffBuilder = DiffGraph.newBuilder
      val newNodeA = createNewNode("a")
      val newNodeB = createNewNode("b")
      diffBuilder.addNode(newNodeA)
      diffBuilder.addNode(newNodeB)
      diffBuilder.addEdge(newNodeA, newNodeB, EdgeTypes.AST)
      val newNodeC = createNewNode("c")
      diffBuilder.addNode(newNodeC)
      diffBuilder.addEdge(newNodeB, newNodeC, EdgeTypes.AST)
      val diff = diffBuilder.build()
      val appliedDiff = DiffGraph.Applier.applyDiff(diff, graph, true, None)
      graph.nodeCount shouldBe 3
      graph.edgeCount shouldBe 2
      DiffGraph.Applier.unapplyDiff(graph, appliedDiff.inverseDiffGraph.get)
      graph.nodeCount shouldBe 0
    }
  }

  def withTestOdb[T](f: Graph => T): T = {
    val graph = OverflowDbTestInstance.create
    try f(graph)
    finally graph.close()
  }

  def createNewNode(code: String): NewUnknown = NewUnknown().code(code)

  def makeEdgeBetweenExistingNodes(graph: Graph, diff: DiffGraph.Builder, codeA: String, codeB: String) = {
    val a = graph.V.has(Properties.CODE, codeA).head
    val b = graph.V.has(Properties.CODE, codeB).head
    diff.addEdge(a.asInstanceOf[StoredNode], b.asInstanceOf[StoredNode], EdgeTypes.AST)
  }

  def makeEdgeBetweenNewNodes(diff: DiffGraph.Builder, a: NewNode, b: NewNode) =
    diff.addEdge(a, b, EdgeTypes.AST)
}

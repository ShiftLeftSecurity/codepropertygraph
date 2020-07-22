package io.shiftleft.semanticcpg.language

import gremlin.scala._
import io.shiftleft.OverflowDbTestInstance
import io.shiftleft.codepropertygraph.generated.edges.ContainsNode
import io.shiftleft.codepropertygraph.generated.nodes._
import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.codepropertygraph.generated.{EdgeKeyNames, ModifierTypes}
import io.shiftleft.passes.DiffGraph
import io.shiftleft.passes.DiffGraph.{EdgeInDiffGraph, EdgeToOriginal}
import org.scalatest.{Matchers, WordSpec}

class NewNodeStepsTest extends WordSpec with Matchers {
  import NewNodeNodeStepsTest._

  "stores NewNodes" in {
    implicit val diffGraphBuilder = DiffGraph.newBuilder
    val newNode = newTestNode()
    new NewNodeSteps(__(newNode)).store
    val diffGraph = diffGraphBuilder.build()
    diffGraph.nodes.toList shouldBe List(newNode)
  }

  "can access the node label" in {
    implicit val diffGraphBuilder = DiffGraph.newBuilder
    val newNode = newTestNode()
    new NewNodeSteps(__(newNode)).label.l shouldBe List(newNode.label)
  }

  "stores containedNodes and connecting edge" when {

    "embedding a StoredNode and a NewNode" in {
      implicit val diffGraphBuilder = DiffGraph.newBuilder
      val existingContainedNode = Modifier.factory.createNode(OverflowDbTestInstance.create, 42L)
      existingContainedNode.property(Modifier.PropertyNames.ModifierType, ModifierTypes.NATIVE)

      val newContainedNode = newTestNode()
      val newNode = newTestNode(containedNodes = List(existingContainedNode, newContainedNode))
      new NewNodeSteps(__(newNode)).store
      val diffGraph = diffGraphBuilder.build
      diffGraph.nodes.toSet shouldBe Set(newNode, newContainedNode)

      diffGraph.edges shouldBe Nil
    }

    "embedding a NewNode recursively" in {
      implicit val diffGraphBuilder = DiffGraph.newBuilder
      val newContainedNodeL1 = newTestNode()
      val newContainedNodeL0 = newTestNode(containedNodes = List(newContainedNodeL1))
      val newNode = newTestNode(containedNodes = List(newContainedNodeL0))
      new NewNodeSteps(__(newNode)).store
      val diffGraph = diffGraphBuilder.build

      diffGraph.nodes.toSet shouldBe Set(newNode, newContainedNodeL0, newContainedNodeL1)
      diffGraph.edges.toSet shouldBe Set.empty
    }

  }
}

object NewNodeNodeStepsTest {
  def newTestNode(containedNodes: List[CpgNode] = Nil) = nodes.NewFinding(evidence = containedNodes)
}

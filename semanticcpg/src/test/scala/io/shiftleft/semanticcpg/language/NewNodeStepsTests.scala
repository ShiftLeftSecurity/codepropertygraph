package io.shiftleft.semanticcpg.language

import gremlin.scala._
import io.shiftleft.OverflowDbTestInstance
import io.shiftleft.codepropertygraph.generated.edges.ContainsNode
import io.shiftleft.codepropertygraph.generated.nodes._
import io.shiftleft.codepropertygraph.generated.{EdgeKeyNames, ModifierTypes}
import io.shiftleft.passes.{DiffGraph}
import io.shiftleft.passes.DiffGraph.{EdgeInDiffGraph, EdgeToOriginal}
import overflowdb._
import org.scalatest.{Matchers, WordSpec}

class NewNodeStepsTest extends WordSpec with Matchers {
  import NewNodeNodeStepsTest._

  "stores NewNodes" in {
    implicit val diffGraphBuilder = DiffGraph.newBuilder
    val newNode = new TestNewNode
    new NewNodeSteps(__(newNode)).store
    val diffGraph = diffGraphBuilder.build()
    diffGraph.nodes.toList shouldBe List(newNode)
  }

  "stores containedNodes and connecting edge" when {

    "embedding a StoredNode and a NewNode" in {
      implicit val diffGraphBuilder = DiffGraph.newBuilder
      val existingContainedNode = Modifier.factory.createNode(OverflowDbTestInstance.create, 42L)
      existingContainedNode.property(Modifier.PropertyNames.ModifierType, ModifierTypes.NATIVE)

      val newContainedNode = new TestNewNode
      val newNode = TestNewNode(containedNodes = List(existingContainedNode, newContainedNode))
      new NewNodeSteps(__(newNode)).store
      val diffGraph = diffGraphBuilder.build
      diffGraph.nodes.toSet shouldBe Set(newNode, newContainedNode)
      diffGraph.edgesToOriginal shouldBe List(
        EdgeToOriginal(
          src = newNode,
          dst = existingContainedNode,
          label = ContainsNode.Label,
          properties = Seq(
            (EdgeKeyNames.LOCAL_NAME -> newNode.testContainedLabel),
            (EdgeKeyNames.INDEX -> (0: Integer))
          )
        )
      )

      diffGraph.edges shouldBe List(
        EdgeInDiffGraph(
          src = newNode,
          dst = newContainedNode,
          label = ContainsNode.Label,
          properties = Seq(
            (EdgeKeyNames.LOCAL_NAME -> newNode.testContainedLabel),
            (EdgeKeyNames.INDEX -> (1: Integer))
          )
        )
      )
    }

    "embedding a NewNode recursively" in {
      implicit val diffGraphBuilder = DiffGraph.newBuilder
      val newContainedNodeL1 = new TestNewNode
      val newContainedNodeL0 = TestNewNode(containedNodes = List(newContainedNodeL1))
      val newNode = TestNewNode(containedNodes = List(newContainedNodeL0))
      new NewNodeSteps(__(newNode)).store
      val diffGraph = diffGraphBuilder.build

      diffGraph.nodes.toSet shouldBe Set(newNode, newContainedNodeL0, newContainedNodeL1)
      diffGraph.edges.toSet shouldBe Set(
        EdgeInDiffGraph(
          src = newNode,
          dst = newContainedNodeL0,
          label = ContainsNode.Label,
          properties = Seq(
            (EdgeKeyNames.LOCAL_NAME -> newNode.testContainedLabel),
            (EdgeKeyNames.INDEX -> (0: Integer))
          )
        ),
        EdgeInDiffGraph(
          src = newContainedNodeL0,
          dst = newContainedNodeL1,
          label = ContainsNode.Label,
          properties = Seq(
            (EdgeKeyNames.LOCAL_NAME -> newNode.testContainedLabel),
            (EdgeKeyNames.INDEX -> (0: Integer))
          )
        )
      )
    }

  }
}

object NewNodeNodeStepsTest {

  case class TestNewNode(containedNodes: List[Node] = Nil) extends NewNode {
    override val label = "TEST_LABEL"
    override val properties: Map[String, Any] = Map.empty
    val testContainedLabel = "testContains"
    override def containedNodesByLocalName: Map[String, List[Node]] =
      Map(testContainedLabel -> containedNodes)
  }
}

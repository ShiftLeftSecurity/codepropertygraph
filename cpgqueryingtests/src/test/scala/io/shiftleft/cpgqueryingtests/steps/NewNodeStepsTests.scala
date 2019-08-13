package io.shiftleft.cpgqueryingtests.steps

import gremlin.scala._
import io.shiftleft.OverflowDbTestInstance
import io.shiftleft.codepropertygraph.generated.nodes._
import io.shiftleft.codepropertygraph.generated.EdgeKeyNames
import io.shiftleft.codepropertygraph.generated.edges.ContainsNode
import io.shiftleft.queryprimitives.steps.NewNodeSteps
import io.shiftleft.codepropertygraph.generated.ModifierTypes
import io.shiftleft.passes.DiffGraph
import io.shiftleft.passes.DiffGraph.{EdgeInDiffGraph, EdgeToOriginal}
import io.shiftleft.overflowdb.OdbGraph
import org.scalatest.{Matchers, WordSpec}

class NewNodeStepsTest extends WordSpec with Matchers {
  import NewNodeNodeStepsTest._

  "stores NewNodes" in {
    implicit val diffGraph = new DiffGraph
    val newNode = new TestNewNode
    new NewNodeSteps(__(newNode)).store

    diffGraph.nodes shouldBe List(newNode)
  }

  "stores containedNodes and connecting edge" when {

    "embedding a StoredNode and a NewNode" in {
      implicit val diffGraph = new DiffGraph
      val existingContainedNode = Modifier.Factory.createNode(OverflowDbTestInstance.create, 42L)
      existingContainedNode.property(Modifier.Keys.ModifierType, ModifierTypes.NATIVE)

      val newContainedNode = new TestNewNode
      val newNode = new TestNewNode(containedNodes = List(existingContainedNode, newContainedNode))
      new NewNodeSteps(__(newNode)).store

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
      implicit val diffGraph = new DiffGraph
      val newContainedNodeL1 = new TestNewNode
      val newContainedNodeL0 = new TestNewNode(containedNodes = List(newContainedNodeL1))
      val newNode = new TestNewNode(containedNodes = List(newContainedNodeL0))
      new NewNodeSteps(__(newNode)).store

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
    override def accept[T](visitor: NodeVisitor[T]): T = ???
  }
}

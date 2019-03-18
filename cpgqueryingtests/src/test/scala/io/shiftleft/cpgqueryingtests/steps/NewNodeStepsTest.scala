package io.shiftleft.cpgqueryingtests.steps

import gremlin.scala._
import io.shiftleft.codepropertygraph.generated.nodes._
import io.shiftleft.codepropertygraph.generated.EdgeKeyNames
import io.shiftleft.codepropertygraph.generated.EdgeTypes
import io.shiftleft.diffgraph.DiffGraph
import io.shiftleft.diffgraph.DiffGraph._
import io.shiftleft.queryprimitives.steps.NewNodeSteps
import java.lang.{Long => JLong}

import io.shiftleft.codepropertygraph.generated.ModifierTypes
import org.apache.tinkerpop.gremlin.tinkergraph.structure.TinkerGraph
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

    /* TODO: MP reenable when compatible with cpg-internal */
    // "embedding a StoredNode and a NewNode" in {
    //   implicit val diffGraph = new DiffGraph
    //   val graph: Graph = TinkerGraph.open
    //   val existingContainedNodeUnderlying: Vertex = graph.addVertex("foo")

    //   val existingContainedNode =
    //     new Modifier(_underlying = Some(existingContainedNodeUnderlying), MODIFIER_TYPE = ModifierTypes.NATIVE)
    //   val newContainedNode = new TestNewNode
    //   val newNode          = new TestNewNode(containedNodes = List(existingContainedNode, newContainedNode))
    //   new NewNodeSteps(__(newNode)).store

    //   diffGraph.nodes shouldBe List(newNode, newContainedNode)
    //   diffGraph.edgesToOriginal shouldBe List(
    //     EdgeToOriginal(
    //       src = newNode,
    //       dst = existingContainedNodeUnderlying,
    //       label = EdgeTypes.CONTAINS,
    //       properties = Seq(
    //         (EdgeKeyNames.LOCAL_NAME -> newNode.testContainedLabel),
    //         (EdgeKeyNames.INDEX      -> (0: Integer))
    //       )
    //     )
    //   )

    //   diffGraph.edges shouldBe List(
    //     EdgeInDiffGraph(
    //       src = newNode,
    //       dst = newContainedNode,
    //       label = EdgeTypes.CONTAINS,
    //       properties = Seq(
    //         (EdgeKeyNames.LOCAL_NAME -> newNode.testContainedLabel),
    //         (EdgeKeyNames.INDEX      -> (1: Integer))
    //       )
    //     )
    //   )
    // }

    "embedding a NewNode recursively" in {
      implicit val diffGraph = new DiffGraph
      val newContainedNodeL1 = new TestNewNode { override val label = "newContainedNodeL1" }
      val newContainedNodeL0 = new TestNewNode(containedNodes = List(newContainedNodeL1)) {
        override val label = "newContainedNodeL0"
      }
      val newNode = new TestNewNode(containedNodes = List(newContainedNodeL0)) { override val label = "newNode" }
      new NewNodeSteps(__(newNode)).store

      diffGraph.nodes shouldBe List(newNode, newContainedNodeL0, newContainedNodeL1)
      diffGraph.edges.toSet shouldBe Set(
        EdgeInDiffGraph(
          src = newNode,
          dst = newContainedNodeL0,
          label = EdgeTypes.CONTAINS,
          properties = Seq(
            (EdgeKeyNames.LOCAL_NAME -> newNode.testContainedLabel),
            (EdgeKeyNames.INDEX -> (0: Integer))
          )
        ),
        EdgeInDiffGraph(
          src = newContainedNodeL0,
          dst = newContainedNodeL1,
          label = EdgeTypes.CONTAINS,
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

  implicit val marshaller: Marshallable[TestNewNode] = new Marshallable[TestNewNode] {
    def fromCC(cc: TestNewNode) = ???
    def toCC(element: Element) = ???
  }

  class TestNewNode(containedNodes: List[Node] = Nil) extends NewNode {
    override val label = "TEST_LABEL"
    override val properties: Map[String, Any] = Map.empty
    val testContainedLabel = "testContains"
    override def containedNodesByLocalName: Map[String, List[Node]] =
      Map(testContainedLabel -> containedNodes)

    // Members declared in scala.Equals
    def canEqual(that: Any): Boolean = true
    // Members declared in scala.Product
    def productArity: Int = 0
    def productElement(n: Int): Any = ???

  }
}

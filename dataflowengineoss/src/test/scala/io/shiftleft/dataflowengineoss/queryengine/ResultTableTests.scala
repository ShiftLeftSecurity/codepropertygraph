package io.shiftleft.dataflowengineoss.queryengine

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.semanticcpg.testing.MockCpg
import io.shiftleft.semanticcpg.language._

class ResultTableTests extends AnyWordSpec with Matchers {

  "ResultTable::add" should {

    val cpg = MockCpg()
      .withCustom({ (diffGraph, _) =>
        diffGraph.addNode(nodes.NewLiteral("foo"))
        diffGraph.addNode(nodes.NewLiteral("bar"))
      })
      .cpg

    "append to existing results on multiple inserts for same key" in {
      val node1 = cpg.literal.head
      val node2 = cpg.literal.last
      val table = new ResultTable
      val res1 = List(ReachableByResult(List(PathElement(node1))))
      val res2 = List(ReachableByResult(List(PathElement(node2))))
      table.add(node1, res1)
      table.add(node1, res2)
      table.get(node1) match {
        case Some(results) =>
          results.flatMap(_.path.map(_.node.id2)) shouldBe List(node1.id2, node2.id2)
        case None => fail
      }
    }

  }

  "ResultTable::createFromTable" should {

    val cpg = MockCpg()
      .withCustom({ (diffGraph, _) =>
        diffGraph.addNode(nodes.NewLiteral("foo"))
        diffGraph.addNode(nodes.NewLiteral("bar"))
        diffGraph.addNode(nodes.NewLiteral("woo"))
        diffGraph.addNode(nodes.NewLiteral("moo"))
      })
      .cpg

    "correctly combine path and path stored in table on createFromTable" in {
      val node1 = cpg.literal.code("foo").head
      val pivotNode = cpg.literal.code("bar").head
      val node3 = cpg.literal.code("woo").head
      val node4 = cpg.literal.code("moo").head
      val pathFromPivot = List(PathElement(pivotNode), PathElement(node1))
      val pathContainingPivot = List(PathElement(node4), PathElement(pivotNode), PathElement(node3))
      val table = new ResultTable
      table.add(pivotNode, List(ReachableByResult(pathContainingPivot)))
      table.createFromTable(pathFromPivot) match {
        case Some(List(ReachableByResult(path, _))) =>
          path.map(_.node.id2) shouldBe List(node4.id2, pivotNode.id2, node1.id2)
        case None => fail
      }
    }
  }

}

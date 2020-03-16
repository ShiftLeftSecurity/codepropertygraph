package io.shiftleft.semanticcpg.passes

import gremlin.scala._
import io.shiftleft.OverflowDbTestInstance
import io.shiftleft.codepropertygraph.generated.nodes.StoredNode
import io.shiftleft.semanticcpg.passes.cfgdominator.{CfgAdapter, CfgDominator, CfgDominatorFrontier, DomTreeAdapter}
import org.scalatest.{Matchers, WordSpec}

import scala.jdk.CollectionConverters._

class CfgDominatorFrontierTests extends WordSpec with Matchers {

  private class TestCfgAdapter extends CfgAdapter[StoredNode] {
    override def successors(node: StoredNode): IterableOnce[StoredNode] =
      node._cfgOut.asScala

    override def predecessors(node: StoredNode): IterableOnce[StoredNode] =
      node._cfgIn.asScala
  }

  private class TestDomTreeAdapter(immediateDominators: Map[StoredNode, StoredNode])
      extends DomTreeAdapter[StoredNode] {
    override def immediateDominator(cfgNode: StoredNode): Option[StoredNode] = {
      immediateDominators.get(cfgNode)
    }
  }

  "Cfg dominance frontier test" in {
    implicit val graph: ScalaGraph = OverflowDbTestInstance.create
    def addNode: StoredNode = (graph + "UNKNOWN").asInstanceOf[StoredNode]

    val v0 = addNode
    val v1 = addNode
    val v2 = addNode
    val v3 = addNode
    val v4 = addNode
    val v5 = addNode
    val v6 = addNode

    v0 --- "CFG" --> v1
    v1 --- "CFG" --> v2
    v2 --- "CFG" --> v3
    v2 --- "CFG" --> v5
    v3 --- "CFG" --> v4
    v4 --- "CFG" --> v2
    v4 --- "CFG" --> v5
    v5 --- "CFG" --> v6

    val cfgAdapter = new TestCfgAdapter
    val cfgDominatorCalculator = new CfgDominator(cfgAdapter)
    val immediateDominators = cfgDominatorCalculator.calculate(v0)

    val domTreeAdapter = new TestDomTreeAdapter(immediateDominators)
    val cfgDominatorFrontier = new CfgDominatorFrontier(cfgAdapter, domTreeAdapter)
    val dominanceFrontier = cfgDominatorFrontier.calculate(graph.V.l.asInstanceOf[List[StoredNode]])

    dominanceFrontier.get(v0) shouldBe Set.empty
    dominanceFrontier.get(v1) shouldBe Set.empty
    dominanceFrontier.get(v2) shouldBe Set(v2)
    dominanceFrontier.get(v3) shouldBe Set(v2, v5)
    dominanceFrontier.get(v4) shouldBe Set(v2, v5)
    dominanceFrontier.get(v5) shouldBe Set.empty
    dominanceFrontier.get(v6) shouldBe Set.empty
  }

  "Cfg domiance frontier with dead code test" in {
    implicit val graph: ScalaGraph = OverflowDbTestInstance.create
    def addNode: StoredNode = (graph + "UNKNOWN").asInstanceOf[StoredNode]

    val v0 = addNode
    val v1 = addNode // This node simulates dead code as it is not reachable from the entry v0.
    val v2 = addNode

    v0 --- "CFG" --> v2
    v1 --- "CFG" --> v2

    val cfgAdapter = new TestCfgAdapter
    val cfgDominatorCalculator = new CfgDominator(cfgAdapter)
    val immediateDominators = cfgDominatorCalculator.calculate(v0)

    val domTreeAdapter = new TestDomTreeAdapter(immediateDominators)
    val cfgDominatorFrontier = new CfgDominatorFrontier(cfgAdapter, domTreeAdapter)
    val dominanceFrontier = cfgDominatorFrontier.calculate(graph.V.l.asInstanceOf[List[StoredNode]])

    dominanceFrontier.get(v0) shouldBe Set.empty
    dominanceFrontier.get(v1) shouldBe Set(v2)
    dominanceFrontier.get(v2) shouldBe Set.empty
  }

}

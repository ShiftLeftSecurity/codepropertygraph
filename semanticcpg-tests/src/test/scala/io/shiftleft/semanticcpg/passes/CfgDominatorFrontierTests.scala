package io.shiftleft.semanticcpg.passes

import io.shiftleft.OverflowDbTestInstance
import io.shiftleft.semanticcpg.passes.cfgdominator.{CfgAdapter, CfgDominator, CfgDominatorFrontier, DomTreeAdapter}
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import overflowdb._

import scala.collection.mutable
import scala.jdk.CollectionConverters._

class CfgDominatorFrontierTests extends AnyWordSpec with Matchers {

  private class TestCfgAdapter extends CfgAdapter[Node] {
    override def successors(node: Node): IterableOnce[Node] =
      node.out("CFG").asScala

    override def predecessors(node: Node): IterableOnce[Node] =
      node.in("CFG").asScala
  }

  private class TestDomTreeAdapter(immediateDominators: mutable.Map[Node, Node]) extends DomTreeAdapter[Node] {
    override def immediateDominator(cfgNode: Node): Option[Node] = {
      immediateDominators.get(cfgNode)
    }
  }

  "Cfg dominance frontier test" in {
    val graph = OverflowDbTestInstance.create

    val v0 = graph + "UNKNOWN"
    val v1 = graph + "UNKNOWN"
    val v2 = graph + "UNKNOWN"
    val v3 = graph + "UNKNOWN"
    val v4 = graph + "UNKNOWN"
    val v5 = graph + "UNKNOWN"
    val v6 = graph + "UNKNOWN"

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
    val dominanceFrontier = cfgDominatorFrontier.calculate(graph.nodes.asScala.toList)

    dominanceFrontier.get(v0) shouldBe Set.empty
    dominanceFrontier.get(v1) shouldBe Set.empty
    dominanceFrontier.get(v2) shouldBe Set(v2)
    dominanceFrontier.get(v3) shouldBe Set(v2, v5)
    dominanceFrontier.get(v4) shouldBe Set(v2, v5)
    dominanceFrontier.get(v5) shouldBe Set.empty
    dominanceFrontier.get(v6) shouldBe Set.empty
  }

  "Cfg domiance frontier with dead code test" in {
    val graph = OverflowDbTestInstance.create

    val v0 = graph + "UNKNOWN"
    val v1 = graph + "UNKNOWN" // This node simulates dead code as it is not reachable from the entry v0.
    val v2 = graph + "UNKNOWN"

    v0 --- "CFG" --> v2
    v1 --- "CFG" --> v2

    val cfgAdapter = new TestCfgAdapter
    val cfgDominatorCalculator = new CfgDominator(cfgAdapter)
    val immediateDominators = cfgDominatorCalculator.calculate(v0)

    val domTreeAdapter = new TestDomTreeAdapter(immediateDominators)
    val cfgDominatorFrontier = new CfgDominatorFrontier(cfgAdapter, domTreeAdapter)
    val dominanceFrontier = cfgDominatorFrontier.calculate(graph.nodes.asScala.toList)

    dominanceFrontier.get(v0) shouldBe Set.empty
    dominanceFrontier.get(v1) shouldBe Set(v2)
    dominanceFrontier.get(v2) shouldBe Set.empty
  }

}

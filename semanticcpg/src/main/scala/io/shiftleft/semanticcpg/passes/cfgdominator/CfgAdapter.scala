package io.shiftleft.semanticcpg.passes.cfgdominator

trait CfgAdapter[Node] {
  def successors(node: Node): TraversableOnce[Node]
  def predecessors(node: Node): TraversableOnce[Node]
}

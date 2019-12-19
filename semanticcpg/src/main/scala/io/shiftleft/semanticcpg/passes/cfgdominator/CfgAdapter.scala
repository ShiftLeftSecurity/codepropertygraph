package io.shiftleft.semanticcpg.passes.cfgdominator

trait CfgAdapter[Node] {
  def successors(node: Node): IterableOnce[Node]
  def predecessors(node: Node): IterableOnce[Node]
}

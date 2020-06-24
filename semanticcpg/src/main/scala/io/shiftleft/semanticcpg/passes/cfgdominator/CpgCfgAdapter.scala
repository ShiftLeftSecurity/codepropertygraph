package io.shiftleft.semanticcpg.passes.cfgdominator

import io.shiftleft.codepropertygraph.generated.EdgeTypes
import overflowdb.Node

import scala.jdk.CollectionConverters._

class CpgCfgAdapter extends CfgAdapter[Node] {
  override def successors(node: Node): IterableOnce[Node] = {
    node.out(EdgeTypes.CFG).asScala
  }

  override def predecessors(node: Node): IterableOnce[Node] = {
    node.in(EdgeTypes.CFG).asScala
  }
}

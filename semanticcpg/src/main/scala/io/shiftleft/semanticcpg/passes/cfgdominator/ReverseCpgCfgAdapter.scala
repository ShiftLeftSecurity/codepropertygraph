package io.shiftleft.semanticcpg.passes.cfgdominator

import io.shiftleft.codepropertygraph.generated.{EdgeTypes, nodes}

import scala.jdk.CollectionConverters._

class ReverseCpgCfgAdapter extends CfgAdapter[nodes.StoredNode] {
  override def successors(node: nodes.StoredNode): IterableOnce[nodes.StoredNode] = {
    node._cfgIn.asScala
  }

  override def predecessors(node: nodes.StoredNode): IterableOnce[nodes.StoredNode] = {
    node._cfgOut.asScala
  }
}

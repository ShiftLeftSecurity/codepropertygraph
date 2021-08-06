package io.shiftleft.semanticcpg.passes.cfgdominator

import io.shiftleft.codepropertygraph.generated.EdgeTypes
import io.shiftleft.codepropertygraph.generated.nodes

import scala.jdk.CollectionConverters._

class CpgCfgAdapter extends CfgAdapter[nodes.StoredNode] {
  override def successors(node: nodes.StoredNode): IterableOnce[nodes.StoredNode] = {
    node._cfgOut.asScala
  }

  override def predecessors(node: nodes.StoredNode): IterableOnce[nodes.StoredNode] = {
    node._cfgIn.asScala
  }
}

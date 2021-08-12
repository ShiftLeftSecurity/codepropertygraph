package io.shiftleft.semanticcpg.passes.codepencegraph

import io.shiftleft.codepropertygraph.generated.nodes.StoredNode
import io.shiftleft.semanticcpg.passes.cfgdominator.DomTreeAdapter
import overflowdb.traversal._

class CpgPostDomTreeAdapter extends DomTreeAdapter[StoredNode] {

  override def immediateDominator(cfgNode: StoredNode): Option[StoredNode] = {
    cfgNode._postDominateIn.nextOption()
  }
}

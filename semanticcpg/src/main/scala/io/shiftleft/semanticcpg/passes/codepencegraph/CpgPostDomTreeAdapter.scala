package io.shiftleft.semanticcpg.passes.codepencegraph

import io.shiftleft.codepropertygraph.generated.EdgeTypes
import io.shiftleft.semanticcpg.passes.cfgdominator.DomTreeAdapter
import overflowdb.Node
import overflowdb.traversal._

class CpgPostDomTreeAdapter extends DomTreeAdapter[Node] {

  override def immediateDominator(cfgNode: Node): Option[Node] = {
    cfgNode.in(EdgeTypes.POST_DOMINATE).nextOption
  }
}

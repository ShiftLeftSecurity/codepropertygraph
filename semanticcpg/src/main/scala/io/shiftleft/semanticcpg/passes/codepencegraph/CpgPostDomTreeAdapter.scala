package io.shiftleft.semanticcpg.passes.codepencegraph

import gremlin.scala.Vertex
import io.shiftleft.codepropertygraph.generated.EdgeTypes
import io.shiftleft.semanticcpg.passes.cfgdominator.DomTreeAdapter
import org.apache.tinkerpop.gremlin.structure.Direction

class CpgPostDomTreeAdapter extends DomTreeAdapter[Vertex] {

  override def immediateDominator(cfgNode: Vertex): Option[Vertex] = {
    val iterator = cfgNode.vertices(Direction.IN, EdgeTypes.POST_DOMINATE)
    if (iterator.hasNext) {
      Some(iterator.next)
    } else {
      None
    }
  }
}

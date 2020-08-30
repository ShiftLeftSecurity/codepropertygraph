package io.shiftleft.dataflowengineoss.dotgenerator

import io.shiftleft.codepropertygraph.generated.{EdgeKeys, EdgeTypes, nodes}
import io.shiftleft.semanticcpg.dotgenerator.Shared
import io.shiftleft.semanticcpg.dotgenerator.Shared.Edge
import overflowdb.Node
import overflowdb.traversal._

object DotDdgGenerator {

  def expand(v: nodes.StoredNode): Iterator[Edge] = {
    v.start
      .outE(EdgeTypes.REACHING_DEF)
      .map(x => Edge(v, x.inNode.asInstanceOf[nodes.StoredNode], x.property(EdgeKeys.VARIABLE)))
      .iterator
  }

  def cfgNodeShouldBeDisplayed(v: Node): Boolean = !(
    v.isInstanceOf[nodes.Block] ||
      v.isInstanceOf[nodes.ControlStructure] ||
      v.isInstanceOf[nodes.JumpTarget]
  )

  def toDotDdg(traversal: Traversal[nodes.Method]): Traversal[String] =
    traversal.map(Shared.dotGraph(_, expand, cfgNodeShouldBeDisplayed))

}

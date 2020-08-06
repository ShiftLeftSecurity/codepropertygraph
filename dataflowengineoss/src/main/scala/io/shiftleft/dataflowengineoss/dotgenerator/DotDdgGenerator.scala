package io.shiftleft.dataflowengineoss.dotgenerator

import io.shiftleft.codepropertygraph.generated.nodes.CfgNode
import io.shiftleft.codepropertygraph.generated.{EdgeKeyNames, EdgeTypes, nodes}
import io.shiftleft.semanticcpg.dotgenerator.Shared
import io.shiftleft.semanticcpg.dotgenerator.Shared.Edge
import overflowdb.traversal.Traversal

object DotDdgGenerator {

  def expand(v: CfgNode): Iterator[Edge] = {
    (Traversal.fromSingle(v)
      .outE(EdgeTypes.REACHING_DEF)
      .map(x => Edge(v, x.inNode.asInstanceOf[nodes.CfgNode], x.property2[String](EdgeKeyNames.VARIABLE))))
      .toList
      .iterator
  }

  def toDotDdg(traversal: Traversal[nodes.Method]): Traversal[String] =
    traversal.map(Shared.dotGraph(_, expand))

}

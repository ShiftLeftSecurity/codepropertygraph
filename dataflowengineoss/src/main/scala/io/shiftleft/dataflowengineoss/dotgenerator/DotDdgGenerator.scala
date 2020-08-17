package io.shiftleft.dataflowengineoss.dotgenerator

import io.shiftleft.codepropertygraph.generated.{EdgeKeyNames, EdgeTypes, nodes}
import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.semanticcpg.dotgenerator.Shared
import io.shiftleft.semanticcpg.dotgenerator.Shared.Edge
import io.shiftleft.semanticcpg.language._
import gremlin.scala._

object DotDdgGenerator {

  def expand(v: nodes.StoredNode): Iterator[Edge] = {
    (v.start.raw
      .outE(EdgeTypes.REACHING_DEF)
      .map(x => Edge(v, x.inVertex().asInstanceOf[nodes.StoredNode], x.value[String](EdgeKeyNames.VARIABLE))))
      .toList
      .iterator
  }

  def toDotDdg(step: NodeSteps[nodes.Method]): Steps[String] = step.map(Shared.dotGraph(_, expand))

}

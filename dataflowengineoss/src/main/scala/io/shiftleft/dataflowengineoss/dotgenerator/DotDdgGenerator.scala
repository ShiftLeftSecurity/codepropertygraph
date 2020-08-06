package io.shiftleft.dataflowengineoss.dotgenerator

import io.shiftleft.codepropertygraph.generated.{EdgeKeyNames, EdgeTypes, nodes}
import io.shiftleft.codepropertygraph.generated.nodes.CfgNode
import io.shiftleft.semanticcpg.dotgenerator.Shared
import io.shiftleft.semanticcpg.dotgenerator.Shared.Edge
import io.shiftleft.semanticcpg.language._
import gremlin.scala._

object DotDdgGenerator {

  def expand(v: CfgNode): Iterator[Edge] = {
    (v.start.raw
      .outE(EdgeTypes.REACHING_DEF)
      .map(x => Edge(v, x.inVertex().asInstanceOf[nodes.CfgNode], x.value[String](EdgeKeyNames.VARIABLE))))
      .toList
      .iterator
  }

  def toDotDdg(step: NodeSteps[nodes.Method]): Steps[String] = step.map(Shared.dotGraph(_, expand))

}

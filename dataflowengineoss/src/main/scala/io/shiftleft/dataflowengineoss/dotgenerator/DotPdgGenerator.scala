package io.shiftleft.dataflowengineoss.dotgenerator

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.codepropertygraph.generated.nodes.CfgNode
import io.shiftleft.semanticcpg.dotgenerator.Shared
import io.shiftleft.semanticcpg.language.{NodeSteps, Steps}

import scala.jdk.CollectionConverters._

object DotPdgGenerator {

  def expand(v: CfgNode): Iterator[nodes.CfgNode] = {
    (v._reachingDefOut()
      .asScala)
      .filter(_.isInstanceOf[nodes.CfgNode])
      .map(_.asInstanceOf[nodes.CfgNode])
  }

  def toDotPdg(step: NodeSteps[nodes.Method]): Steps[String] = step.map(Shared.dotGraph(_, expand))

}

package io.shiftleft.semanticcpg.dotgenerator

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.semanticcpg.language._

import scala.jdk.CollectionConverters._

object DotCfgGenerator {

  def toDotCfg(step: NodeSteps[nodes.Method]): Steps[String] = step.map(Shared.dotGraph(_, expand))

  protected def expand(v: nodes.CfgNode): Iterator[nodes.CfgNode] = {
    v._cfgOut()
      .asScala
      .filter(_.isInstanceOf[nodes.CfgNode])
      .map(_.asInstanceOf[nodes.CfgNode])
  }

}

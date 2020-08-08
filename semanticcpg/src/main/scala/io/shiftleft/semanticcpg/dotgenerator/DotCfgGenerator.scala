package io.shiftleft.semanticcpg.dotgenerator

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.semanticcpg.dotgenerator.Shared.Edge
import io.shiftleft.semanticcpg.language._

import scala.jdk.CollectionConverters._

object DotCfgGenerator {

  def toDotCfg(step: NodeSteps[nodes.Method]): Steps[String] = step.map(Shared.dotGraph(_, expand))

  protected def expand(v: nodes.StoredNode): Iterator[Edge] = {
    v._cfgOut()
      .asScala
      .filter(_.isInstanceOf[nodes.CfgNode])
      .map(node => Edge(v, node.asInstanceOf[nodes.CfgNode]))
  }

}

package io.shiftleft.semanticcpg.dotgenerator

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.semanticcpg.language.{NodeSteps, Steps}

object DotCfgGenerator {

  def toDotCfg[T <: nodes.CfgNode](step: NodeSteps[T]): Steps[String] = step.map(dotCfg)

  def dotCfg(cfgRoot: nodes.CfgNode): String = {
    val sb = Shared.namedGraphBegin(cfgRoot)
    sb.append(nodesAndEdges(cfgRoot).mkString("\n"))
    Shared.graphEnd(sb)
  }

  private def nodesAndEdges(cfgStartNode: nodes.CfgNode): List[String] = {

  }

}

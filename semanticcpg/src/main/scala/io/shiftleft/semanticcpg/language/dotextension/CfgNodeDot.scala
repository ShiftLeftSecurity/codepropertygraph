package io.shiftleft.semanticcpg.language.dotextension

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.semanticcpg.dotgenerator.DotCfgGenerator
import io.shiftleft.semanticcpg.language.{NodeSteps, Steps}

class CfgNodeDot[NodeType <: nodes.CfgNode](val wrapped: NodeSteps[NodeType]) extends AnyVal {

  def dotCfg: Steps[String] = DotCfgGenerator.toDotCfg(wrapped)

  def plotDotCfg(implicit viewer: ImageViewer): Unit = {
    Shared.plotAndDisplay(wrapped.dotCfg.l, viewer)
  }

}

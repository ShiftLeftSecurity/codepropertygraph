package io.shiftleft.semanticcpg.language.dotextension

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.semanticcpg.dotgenerator.DotCfgGenerator
import io.shiftleft.semanticcpg.language.{NodeSteps, Steps}

class CfgNodeDot(val wrapped: NodeSteps[nodes.Method]) extends AnyVal {

  def dotCfg: Steps[String] = DotCfgGenerator.toDotCfg(wrapped)

  def plotDotCfg(implicit viewer: ImageViewer): Unit = {
    Shared.plotAndDisplay(wrapped.dotCfg.l, viewer)
  }

}

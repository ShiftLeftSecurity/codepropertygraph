package io.shiftleft.dataflowengineoss.language.dotextension

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.dataflowengineoss.dotgenerator.DotPdgGenerator
import io.shiftleft.semanticcpg.language.dotextension.{ImageViewer, Shared}
import io.shiftleft.semanticcpg.language.{NodeSteps, Steps}

class PdgNodeDot(val wrapped: NodeSteps[nodes.Method]) extends AnyVal {

  def dotPdg: Steps[String] = DotPdgGenerator.toDotPdg(wrapped)

  def plotDotPdg(implicit viewer: ImageViewer): Unit = {
    Shared.plotAndDisplay(wrapped.dotCfg.l, viewer)
  }
}

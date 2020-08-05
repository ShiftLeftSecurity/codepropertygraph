package io.shiftleft.dataflowengineoss.language.dotextension

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.dataflowengineoss.dotgenerator.DotDdgGenerator
import io.shiftleft.semanticcpg.language.dotextension.{ImageViewer, Shared}
import io.shiftleft.semanticcpg.language.{NodeSteps, Steps}
import io.shiftleft.dataflowengineoss.language._

class DdgNodeDot(val wrapped: NodeSteps[nodes.Method]) extends AnyVal {

  def dotDdg: Steps[String] = DotDdgGenerator.toDotDdg(wrapped)

  def plotDotDdg(implicit viewer: ImageViewer): Unit = {
    Shared.plotAndDisplay(wrapped.dotDdg.l, viewer)
  }
}

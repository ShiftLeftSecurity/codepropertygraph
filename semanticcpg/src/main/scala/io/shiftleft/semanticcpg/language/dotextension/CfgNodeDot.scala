package io.shiftleft.semanticcpg.language.dotextension

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.semanticcpg.dotgenerator.DotCfgGenerator
import io.shiftleft.semanticcpg.language.Steps
import overflowdb.traversal.Traversal

class CfgNodeDot(val traversal: Traversal[nodes.Method]) extends AnyVal {
  def dotCfg: Steps[String] = DotCfgGenerator.toDotCfg(traversal)

  def plotDotCfg(implicit viewer: ImageViewer): Unit = {
    Shared.plotAndDisplay(traversal.dotCfg.l, viewer)
  }

}

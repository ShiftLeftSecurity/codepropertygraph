package io.shiftleft.semanticcpg.language.dotextension

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.semanticcpg.dotgenerator.DotCfgGenerator
import overflowdb.traversal.Traversal

class CfgNodeDot(val traversal: Traversal[nodes.Method]) extends AnyVal {

  def dotCfg: Traversal[String] = DotCfgGenerator.dotCfg(traversal)

  def plotDotCfg(implicit viewer: ImageViewer): Unit = {
    Shared.plotAndDisplay(dotCfg.l, viewer)
  }

}

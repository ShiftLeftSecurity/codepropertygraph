package io.shiftleft.dataflowengineoss.language.dotextension

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.dataflowengineoss.dotgenerator.DotDdgGenerator
import io.shiftleft.dataflowengineoss.language._
import io.shiftleft.semanticcpg.language.dotextension.{ImageViewer, Shared}
import overflowdb.traversal.Traversal

class DdgNodeDot(val traversal: Traversal[nodes.Method]) extends AnyVal {

  def dotDdg: Traversal[String] = DotDdgGenerator.toDotDdg(traversal)

  def plotDotDdg(implicit viewer: ImageViewer): Unit = {
    Shared.plotAndDisplay(traversal.dotDdg.l, viewer)
  }
}

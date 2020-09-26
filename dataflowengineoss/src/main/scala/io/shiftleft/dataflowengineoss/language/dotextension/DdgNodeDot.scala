package io.shiftleft.dataflowengineoss.language.dotextension

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.dataflowengineoss.dotgenerator.{DotDdgGenerator, DotPdgGenerator}
import io.shiftleft.dataflowengineoss.language._
import io.shiftleft.dataflowengineoss.semanticsloader.Semantics
import io.shiftleft.semanticcpg.language.dotextension.{ImageViewer, Shared}
import overflowdb.traversal.Traversal

class DdgNodeDot(val traversal: Traversal[nodes.Method]) extends AnyVal {

  def dotDdg(implicit semantics: Semantics): Traversal[String] = DotDdgGenerator.toDotDdg(traversal)

  def dotPdg(implicit semantics: Semantics): Traversal[String] = DotPdgGenerator.toDotPdg(traversal)

  def plotDotPdg(implicit viewer: ImageViewer, semantics: Semantics): Unit = {
    Shared.plotAndDisplay(traversal.dotPdg.l, viewer)
  }
}

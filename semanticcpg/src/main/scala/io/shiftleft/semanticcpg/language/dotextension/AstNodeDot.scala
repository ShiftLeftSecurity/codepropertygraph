package io.shiftleft.semanticcpg.language.dotextension

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.semanticcpg.dotgenerator.DotAstGenerator
import overflowdb.traversal.Traversal

class AstNodeDot[NodeType <: nodes.AstNode](val traversal: Traversal[NodeType]) extends AnyVal {

  def dotAst: Traversal[String] = DotAstGenerator.dotAst(traversal)

  def plotDotAst(implicit viewer: ImageViewer): Unit = {
    Shared.plotAndDisplay(dotAst.l, viewer)
  }

}

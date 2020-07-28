package io.shiftleft.semanticcpg.language.dotextension

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.semanticcpg.dotgenerator.DotAstGenerator
import overflowdb.traversal.Traversal

import scala.language.postfixOps

class AstNodeDot[NodeType <: nodes.AstNode](val traversal: Traversal[NodeType]) extends AnyVal {

  def dotAst: Traversal[String] = DotAstGenerator.toDotAst(traversal)

  def plotDotAst(implicit viewer: ImageViewer): Unit = {
    Shared.plotAndDisplay(dotAst.l, viewer)
  }

}

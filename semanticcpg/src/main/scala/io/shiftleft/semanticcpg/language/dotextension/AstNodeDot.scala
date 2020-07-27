package io.shiftleft.semanticcpg.language.dotextension

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.semanticcpg.dotgenerator.DotAstGenerator
import io.shiftleft.semanticcpg.language._
import overflowdb.traversal.Traversal

import scala.language.postfixOps

class AstNodeDot[NodeType <: nodes.AstNode](val traversal: Traversal[NodeType]) extends AnyVal {

  def dotAst: Steps[String] = DotAstGenerator.toDotAst(traversal)

  def plotDotAst(implicit viewer: ImageViewer): Unit = {
    Shared.plotAndDisplay(traversal.dotAst.l, viewer)
  }

}

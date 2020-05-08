package io.shiftleft.semanticcpg.language.dotextension

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.semanticcpg.dotgenerator.DotAstGenerator
import io.shiftleft.semanticcpg.language._

import scala.language.postfixOps

class AstNodeDot[NodeType <: nodes.AstNode](val wrapped: NodeSteps[NodeType]) extends AnyVal {

  def dotAst: Steps[String] = DotAstGenerator.toDotAst(wrapped)

  def plotDotAst(implicit viewer: ImageViewer): Unit = {
    Shared.plotAndDisplay(wrapped.dotAst.l, viewer)
  }

}

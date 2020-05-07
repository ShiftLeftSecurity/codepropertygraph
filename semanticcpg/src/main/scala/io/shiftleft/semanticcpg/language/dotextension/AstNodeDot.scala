package io.shiftleft.semanticcpg.language.dotextension

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.semanticcpg.dotgenerator.DotAstGenerator
import io.shiftleft.semanticcpg.language._

class AstNodeDot[NodeType <: nodes.AstNode](val wrapped: NodeSteps[NodeType]) extends AnyVal {

  def dotAst: Steps[String] = DotAstGenerator.toDotAst(wrapped)

}

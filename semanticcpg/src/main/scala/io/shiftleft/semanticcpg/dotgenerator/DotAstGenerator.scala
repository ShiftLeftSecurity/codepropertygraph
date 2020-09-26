package io.shiftleft.semanticcpg.dotgenerator

import io.shiftleft.codepropertygraph.generated.nodes
import overflowdb.traversal._

object DotAstGenerator {

  def dotAst[T <: nodes.AstNode](traversal: Traversal[T]): Traversal[String] =
    traversal.map(dotAst)

  def dotAst(astRoot: nodes.AstNode): String = {
    val ast = new AstGenerator().generate(astRoot)
    DotSerializer.dotGraph(astRoot, ast)
  }

}

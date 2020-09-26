package io.shiftleft.semanticcpg.dotgenerator

import io.shiftleft.codepropertygraph.generated.nodes
import overflowdb.traversal._

object DotAstGenerator {

  def dotAst[T <: nodes.AstNode](traversal: Traversal[T]): Traversal[String] =
    traversal.map(dotAst)

  def dotAst(astRoot: nodes.AstNode): String = {
    val sb = Shared.namedGraphBegin(astRoot)
    val ast = new AstGenerator().generate(astRoot)
    val nodeStrings = ast.vertices.map(Shared.nodeToDot)
    val edgeStrings = ast.edges.map(Shared.edgeToDot)
    sb.append((nodeStrings ++ edgeStrings).mkString("\n"))
    Shared.graphEnd(sb)
  }

}

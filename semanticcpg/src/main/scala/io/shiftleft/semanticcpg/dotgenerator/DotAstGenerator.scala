package io.shiftleft.semanticcpg.dotgenerator

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.semanticcpg.language._
import overflowdb.traversal._

object DotAstGenerator {

  def toDotAst[T <: nodes.AstNode](traversal: Traversal[T]): Traversal[String] =
    traversal.map(dotAst)

  def dotAst(astRoot: nodes.AstNode): String = {
    val sb = Shared.namedGraphBegin(astRoot)
    sb.append(nodesAndEdges(astRoot).mkString("\n"))
    Shared.graphEnd(sb)
  }

  private def nodesAndEdges(astRoot: nodes.AstNode): List[String] = {

    def shouldBeDisplayed(v: nodes.AstNode): Boolean = !v.isInstanceOf[nodes.MethodParameterOut]

    val vertices = astRoot.ast.filter(shouldBeDisplayed).l
    val edges = vertices.map(v => (v.id2, v.start.astChildren.filter(shouldBeDisplayed).id.l))

    val nodeStrings = vertices.map { node =>
      s""""${node.id2}" [label = "${Shared.stringRepr(node)}" ]""".stripMargin
    }

    val edgeStrings = edges.flatMap {
      case (id, childIds) =>
        childIds.map(childId => s"""  "$id" -> "$childId"  """)
    }

    nodeStrings ++ edgeStrings
  }

}

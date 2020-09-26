package io.shiftleft.semanticcpg.dotgenerator

import io.shiftleft.codepropertygraph.generated.{EdgeTypes, nodes}
import io.shiftleft.semanticcpg.dotgenerator.DotSerializer.{Edge, Graph}
import io.shiftleft.semanticcpg.language._

class AstGenerator {

  private val edgeType = EdgeTypes.AST

  def generate(astRoot: nodes.AstNode): Graph = {
    def shouldBeDisplayed(v: nodes.AstNode): Boolean = !v.isInstanceOf[nodes.MethodParameterOut]
    val vertices = astRoot.ast.filter(shouldBeDisplayed).l
    val edges = vertices.flatMap(v =>
      v.astChildren.filter(shouldBeDisplayed).map { child =>
        Edge(v, child, edgeType = edgeType)
    })
    Graph(vertices, edges)
  }

}

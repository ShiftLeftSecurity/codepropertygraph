package io.shiftleft.dataflowengineoss.dotgenerator

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.dataflowengineoss.semanticsloader.Semantics
import io.shiftleft.semanticcpg.dotgenerator.Shared
import io.shiftleft.semanticcpg.dotgenerator.Shared.Edge
import overflowdb.traversal._

case class Ddg(vertices: List[nodes.StoredNode], edges: List[Edge])

object DotDdgGenerator {

  def toDotDdg(traversal: Traversal[nodes.Method])(implicit semantics: Semantics): Traversal[String] =
    traversal.map(dotGraphForMethod)

  private def dotGraphForMethod(method: nodes.Method)(implicit semantics: Semantics): String = {
    val sb = Shared.namedGraphBegin(method)
    val ddgGenerator = new DdgGenerator()
    val ddg = ddgGenerator.generate(method)
    val lines = ddg.vertices.map(Shared.nodeToDot) ++ ddg.edges.map(Shared.edgeToDot)
    sb.append(lines.mkString("\n"))
    Shared.graphEnd(sb)
  }

}

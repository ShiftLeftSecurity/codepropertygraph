package io.shiftleft.semanticcpg.dotgenerator

import io.shiftleft.codepropertygraph.generated.nodes
import overflowdb.traversal._

object DotCdgGenerator {

  def dotCdg(traversal: Traversal[nodes.Method]): Traversal[String] =
    traversal.map(dotCdg)

  def dotCdg(method: nodes.Method): String = {
    val sb = Shared.namedGraphBegin(method)
    val cdg = new CdgGenerator().generate(method)
    val nodeStrings = cdg.vertices.map(Shared.nodeToDot)
    val edgeStrings = cdg.edges.map(Shared.edgeToDot)
    sb.append((nodeStrings ++ edgeStrings).mkString("\n"))
    Shared.graphEnd(sb)
  }

}

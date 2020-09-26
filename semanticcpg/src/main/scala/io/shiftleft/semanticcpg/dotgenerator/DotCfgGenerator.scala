package io.shiftleft.semanticcpg.dotgenerator

import io.shiftleft.codepropertygraph.generated.nodes
import overflowdb.traversal._

object DotCfgGenerator {

  def dotCfg(traversal: Traversal[nodes.Method]): Traversal[String] =
    traversal.map(dotCfg)

  def dotCfg(method: nodes.Method): String = {
    val sb = Shared.namedGraphBegin(method)
    val cfg = new CfgGenerator().generate(method)
    val nodeStrings = cfg.vertices.map(Shared.nodeToDot)
    val edgeStrings = cfg.edges.map(Shared.edgeToDot)
    sb.append((nodeStrings ++ edgeStrings).mkString("\n"))
    Shared.graphEnd(sb)
  }

}

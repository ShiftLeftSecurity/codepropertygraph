package io.shiftleft.semanticcpg.dotgenerator

import io.shiftleft.codepropertygraph.generated.nodes
import overflowdb.traversal._

object DotCdgGenerator {

  def dotCdg(traversal: Traversal[nodes.Method]): Traversal[String] =
    traversal.map(dotCdg)

  def dotCdg(method: nodes.Method): String = {
    val cdg = new CdgGenerator().generate(method)
    DotSerializer.dotGraph(method, cdg)
  }

}

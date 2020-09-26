package io.shiftleft.semanticcpg.dotgenerator

import io.shiftleft.codepropertygraph.generated.nodes
import overflowdb.traversal._

object DotCfgGenerator {

  def dotCfg(traversal: Traversal[nodes.Method]): Traversal[String] =
    traversal.map(dotCfg)

  def dotCfg(method: nodes.Method): String = {
    val cfg = new CfgGenerator().generate(method)
    DotSerializer.dotGraph(method, cfg)
  }

}

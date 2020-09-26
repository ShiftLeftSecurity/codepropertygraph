package io.shiftleft.dataflowengineoss.dotgenerator

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.dataflowengineoss.semanticsloader.Semantics
import io.shiftleft.semanticcpg.dotgenerator.DotSerializer
import overflowdb.traversal._

object DotDdgGenerator {

  def toDotDdg(traversal: Traversal[nodes.Method])(implicit semantics: Semantics): Traversal[String] =
    traversal.map(dotGraphForMethod)

  private def dotGraphForMethod(method: nodes.Method)(implicit semantics: Semantics): String = {
    val ddgGenerator = new DdgGenerator()
    val ddg = ddgGenerator.generate(method)
    DotSerializer.dotGraph(method, ddg)
  }

}

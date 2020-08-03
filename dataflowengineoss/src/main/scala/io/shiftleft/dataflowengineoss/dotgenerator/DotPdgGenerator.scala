package io.shiftleft.dataflowengineoss.dotgenerator

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.semanticcpg.dotgenerator.Shared
import io.shiftleft.semanticcpg.language.{NodeSteps, Steps}

object DotPdgGenerator {

  def toDotPdg(step: NodeSteps[nodes.Method]): Steps[String] = step.map(dotPdg)

  def dotPdg(method: nodes.Method): String = {
    val sb = Shared.namedGraphBegin(method)
    sb.append(nodesAndEdges(method).mkString("\n"))
    Shared.graphEnd(sb)
  }

  private def nodesAndEdges(methodNode: nodes.Method): List[String] = {
    List()
  }

}

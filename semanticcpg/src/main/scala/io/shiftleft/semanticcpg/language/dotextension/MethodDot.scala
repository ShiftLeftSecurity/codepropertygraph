package io.shiftleft.semanticcpg.language.dotextension

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.semanticcpg.dotgenerator.MethodDotGenerator
import io.shiftleft.semanticcpg.language._

class MethodDot(val wrapped: NodeSteps[nodes.Method]) extends AnyVal {

  def dot: List[String] =
    MethodDotGenerator.toDotGraph(wrapped)

}

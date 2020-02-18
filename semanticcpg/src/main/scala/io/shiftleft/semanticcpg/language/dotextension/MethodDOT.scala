package io.shiftleft.semanticcpg.language.dotextension

import gremlin.scala.GremlinScala
import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.semanticcpg.dotgenerator.MethodDotGenerator
import io.shiftleft.semanticcpg.language._

class MethodDOT(val wrapped: NodeSteps[nodes.Method]) extends AnyVal {
  private def raw: GremlinScala[nodes.Method] = wrapped.raw

  def dot: List[String] =
    MethodDotGenerator.toDotGraph(wrapped)

}

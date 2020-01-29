package io.shiftleft.semanticcpg.language.dotextension

import gremlin.scala._

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.semanticcpg.dotgenerator.MethodDotGenerator
import io.shiftleft.semanticcpg.language._

class MethodDOT(override val raw: GremlinScala[nodes.Method]) extends Steps[nodes.Method](raw) {

  def dot: List[String] =
    MethodDotGenerator.toDotGraph(this)

}

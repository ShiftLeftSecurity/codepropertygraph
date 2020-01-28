package io.shiftleft.semanticcpg.language.dotextension

import gremlin.scala._

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.semanticcpg.dotgenerator.MethodDotGenerator
import io.shiftleft.semanticcpg.language._

class MethodDOT[A <: nodes.Method](override val raw: GremlinScala[A]) extends Steps[A](raw) {

  def dot: List[String] =
    MethodDotGenerator.toDotGraph(this)

}

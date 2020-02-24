package io.shiftleft.semanticcpg.language.operatorextension

import gremlin.scala.GremlinScala
import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.semanticcpg.language._

class OpAstNode[A <: nodes.AstNode](val wrapped: Steps[A]) extends AnyVal {
  private def raw: GremlinScala[A] = wrapped.raw
  def inAssignment: NodeSteps[opnodes.Assignment] = wrapped.flatMap(_.inAssignment)
  def assignments: NodeSteps[opnodes.Assignment] = wrapped.flatMap(_.assignments)
  def arithmetics: NodeSteps[opnodes.Arithmetic] = wrapped.flatMap(_.arithmetics)
}

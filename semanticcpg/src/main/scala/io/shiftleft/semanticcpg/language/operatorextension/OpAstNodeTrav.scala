package io.shiftleft.semanticcpg.language.operatorextension

import gremlin.scala.GremlinScala
import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.semanticcpg.language._
import io.shiftleft.semanticcpg.language.types.expressions.generalizations.AstNode

class OpAstNodeTrav[NodeType <: nodes.AstNode](raw: GremlinScala[NodeType]) {

  def inAssignment: NodeSteps[nodes.Call] =
    new NodeSteps(astNode.flatMap(_.inAssignment).raw)

  private def astNode: NodeSteps[nodes.AstNode] =
    new NodeSteps[nodes.AstNode](raw.cast[nodes.AstNode])

  def assignments: AssignmentTrav =
    new AssignmentTrav(
      new AstNode(raw.cast[nodes.AstNode])
        .flatMap(_.assignments)
        .raw)

  def arithmetics: ArithmeticTrav =
    new ArithmeticTrav(
      new AstNode(raw.cast[nodes.AstNode])
        .flatMap(_.arithmetics)
        .raw)
}

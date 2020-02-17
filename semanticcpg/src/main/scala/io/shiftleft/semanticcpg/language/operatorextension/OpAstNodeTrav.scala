package io.shiftleft.semanticcpg.language.operatorextension

import gremlin.scala.GremlinScala
import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.semanticcpg.language._

class OpAstNodeTrav[NodeType <: nodes.AstNode](raw: GremlinScala[NodeType]) {

  def inAssignment: NodeSteps[nodes.Call] =
    astNode.flatMap(_.inAssignment)
//    new NodeSteps(astNode.flatMap(_.inAssignment).raw)

  def assignments: AssignmentTrav =
    new NodeSteps(astNode.flatMap(_.assignments).raw)

  def arithmetics: ArithmeticTrav =
    new ArithmeticTrav(
      astNode.flatMap(_.arithmetics).raw
    )

  private def astNode: NodeSteps[nodes.AstNode] =
    new NodeSteps[nodes.AstNode](raw.cast[nodes.AstNode])
}

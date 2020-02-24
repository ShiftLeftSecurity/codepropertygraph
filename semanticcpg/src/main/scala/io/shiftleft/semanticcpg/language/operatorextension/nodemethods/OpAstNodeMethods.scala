package io.shiftleft.semanticcpg.language.operatorextension.nodemethods

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.semanticcpg.language.operatorextension._
import io.shiftleft.semanticcpg.language._

class OpAstNodeMethods[A <: nodes.AstNode](val node: A) extends AnyVal {
  def inAssignment: NodeSteps[opnodes.Assignment] =
    node.start.inAstMinusLeaf.isCall.name(NodeTypeStarters.assignmentPattern).map(new opnodes.Assignment(_))

  def assignments: NodeSteps[opnodes.Assignment] =
    rawTravForPattern(NodeTypeStarters.assignmentPattern).map(new opnodes.Assignment(_))

  def arithmetics: NodeSteps[opnodes.Arithmetic] =
    rawTravForPattern(NodeTypeStarters.arithmeticPattern).map(new opnodes.Arithmetic(_))

  private def rawTravForPattern(pattern: String): NodeSteps[nodes.Call] =
    node.ast.isCall.name(pattern)
}

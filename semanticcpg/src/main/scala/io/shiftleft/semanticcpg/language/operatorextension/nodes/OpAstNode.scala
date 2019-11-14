package io.shiftleft.semanticcpg.language.operatorextension.nodes

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.semanticcpg.language.operatorextension.{ArithmeticTrav, AssignmentTrav, NodeTypeStarters}
import io.shiftleft.semanticcpg.language._

class OpAstNode(val node: nodes.AstNode) extends AnyRef {

  def assignments: AssignmentTrav =
    new AssignmentTrav(rawTravForPattern(NodeTypeStarters.assignmentPattern).map(new Assignment(_)).raw)

  def arithmetics: ArithmeticTrav =
    new ArithmeticTrav(rawTravForPattern(NodeTypeStarters.arithmeticPattern).map(new Arithmetic(_)).raw)

  private def rawTravForPattern(pattern: String) = node.ast.isCall.name(pattern)

}

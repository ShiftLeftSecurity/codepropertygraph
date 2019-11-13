package io.shiftleft.semanticcpg.language.operatorextension.nodes

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.semanticcpg.language.operatorextension.{AssignmentTrav, NodeTypeStarters}
import io.shiftleft.semanticcpg.language._

class OpAstNode(val node: nodes.AstNode) extends AnyRef {

  def assignments: AssignmentTrav =
    new AssignmentTrav(node.ast.isCall.name(NodeTypeStarters.assignmentPattern).map(new Assignment(_)).raw)

}

package io.shiftleft.semanticcpg.language.operatorextension.nodemethods

import io.shiftleft.codepropertygraph.generated.nodes.{AstNode, Call}
import io.shiftleft.semanticcpg.language._
import io.shiftleft.semanticcpg.language.operatorextension.{NodeTypeStarters => ExtStarters, _}
import overflowdb.traversal._

class OpAstNodeMethods[A <: AstNode](val node: A) extends AnyVal {
  def inAssignment: Traversal[opnodes.Assignment] =
    node.start.inAstMinusLeaf.isCall.name(ExtStarters.assignmentPattern).map(new opnodes.Assignment(_))

  def assignments: Traversal[opnodes.Assignment] =
    rawTravForPattern(ExtStarters.assignmentPattern).map(new opnodes.Assignment(_))

  def arithmetics: Traversal[opnodes.Arithmetic] =
    rawTravForPattern(ExtStarters.arithmeticPattern).map(new opnodes.Arithmetic(_))

  private def rawTravForPattern(pattern: String): Traversal[Call] =
    node.ast.isCall.name(pattern)
}

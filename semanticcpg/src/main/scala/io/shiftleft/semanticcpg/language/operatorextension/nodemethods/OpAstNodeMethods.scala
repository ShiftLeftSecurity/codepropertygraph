package io.shiftleft.semanticcpg.language.operatorextension.nodemethods

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.semanticcpg.language._
import io.shiftleft.semanticcpg.language.operatorextension._
import overflowdb.traversal.Traversal

class OpAstNodeMethods[A <: nodes.AstNode](val node: A) extends AnyVal {
  def inAssignment: Traversal[opnodes.Assignment] =
    node.start.inAstMinusLeaf.isCall.name(NodeTypeStarters.assignmentPattern).map(new opnodes.Assignment(_))

  def assignments: Traversal[opnodes.Assignment] =
    rawTravForPattern(NodeTypeStarters.assignmentPattern).map(new opnodes.Assignment(_))

  def arithmetics: Traversal[opnodes.Arithmetic] =
    rawTravForPattern(NodeTypeStarters.arithmeticPattern).map(new opnodes.Arithmetic(_))

  private def rawTravForPattern(pattern: String): Traversal[nodes.Call] =
    node.ast.isCall.name(pattern)
}

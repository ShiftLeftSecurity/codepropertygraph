package io.shiftleft.semanticcpg.language.operatorextension.nodemethods

import io.shiftleft.codepropertygraph.generated.nodes.{AstNode, Call}
import io.shiftleft.semanticcpg.language._
import io.shiftleft.semanticcpg.language.operatorextension.{allArithmeticTypes, allAssignmentTypes, opnodes}
import overflowdb.traversal._

class OpAstNodeMethods[A <: AstNode](val node: A) extends AnyVal {
  def inAssignment: Traversal[opnodes.Assignment] =
    node.start.inAstMinusLeaf.isCall
      .filter(x => allAssignmentTypes.contains(x.name))
      .map(new opnodes.Assignment(_))

  def assignments: Traversal[opnodes.Assignment] =
    rawTravForPattern(allAssignmentTypes).map(new opnodes.Assignment(_))

  def arithmetics: Traversal[opnodes.Arithmetic] =
    rawTravForPattern(allArithmeticTypes).map(new opnodes.Arithmetic(_))

  private def rawTravForPattern(strings: Set[String]): Traversal[Call] =
    node.ast.isCall.filter(x => strings.contains(x.name))
}

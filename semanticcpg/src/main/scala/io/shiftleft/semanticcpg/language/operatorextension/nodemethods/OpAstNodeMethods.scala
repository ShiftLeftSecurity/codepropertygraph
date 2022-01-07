package io.shiftleft.semanticcpg.language.operatorextension.nodemethods

import io.shiftleft.codepropertygraph.generated.nodes.{AstNode, Call}
import io.shiftleft.semanticcpg.language._
import io.shiftleft.semanticcpg.language.operatorextension.{
  OpNodes,
  allArithmeticTypes,
  allAssignmentTypes,
  allFieldAccessTypes
}
import overflowdb.traversal.{NodeOps, Traversal}

class OpAstNodeMethods[A <: AstNode](val node: A) extends AnyVal {

  def inAssignment: Traversal[OpNodes.Assignment] =
    node.start.inAstMinusLeaf.isCall
      .filter(x => allAssignmentTypes.contains(x.name))
      .map(new OpNodes.Assignment(_))

  // TODO I believe these are actually incorrectly named: these methods
  // perform safe down casts, so, they should be called `is$Type`

  def assignment: Traversal[OpNodes.Assignment] =
    rawTravForPattern(allAssignmentTypes).map(new OpNodes.Assignment(_))

  def arithmetic: Traversal[OpNodes.Arithmetic] =
    rawTravForPattern(allArithmeticTypes).map(new OpNodes.Arithmetic(_))

  def fieldAccess: Traversal[OpNodes.FieldAccess] =
    rawTravForPattern(allFieldAccessTypes).map(new OpNodes.FieldAccess(_))

  private def rawTravForPattern(strings: Set[String]): Traversal[Call] =
    node.ast.isCall.filter(x => strings.contains(x.name))
}

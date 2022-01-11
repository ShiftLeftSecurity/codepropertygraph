package io.shiftleft.semanticcpg.language.operatorextension.nodemethods

import io.shiftleft.codepropertygraph.generated.nodes.{AstNode, Call}
import io.shiftleft.semanticcpg.language._
import io.shiftleft.semanticcpg.language.operatorextension.{
  OpNodes,
  allArithmeticTypes,
  allArrayAccessTypes,
  allAssignmentTypes,
  allFieldAccessTypes
}
import overflowdb.traversal.{NodeOps, Traversal}

class OpAstNodeMethods[A <: AstNode](val node: A) extends AnyVal {

  def inAssignment: Traversal[OpNodes.Assignment] =
    node.start.inAstMinusLeaf.isCall
      .filter(x => allAssignmentTypes.contains(x.name))
      .map(new OpNodes.Assignment(_))

  def assignment: Traversal[OpNodes.Assignment] =
    rawTravForPattern(allAssignmentTypes).map(new OpNodes.Assignment(_))

  def arithmetic: Traversal[OpNodes.Arithmetic] =
    rawTravForPattern(allArithmeticTypes).map(new OpNodes.Arithmetic(_))

  def arrayAccess: Traversal[OpNodes.ArrayAccess] =
    rawTravForPattern(allArrayAccessTypes).map(new OpNodes.ArrayAccess(_))

  def fieldAccess: Traversal[OpNodes.FieldAccess] =
    rawTravForPattern(allFieldAccessTypes).map(new OpNodes.FieldAccess(_))

  private def rawTravForPattern(strings: Set[String]): Traversal[Call] =
    node.ast.isCall.filter(x => strings.contains(x.name))
}

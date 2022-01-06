package io.shiftleft.semanticcpg.language.operatorextension.nodemethods

import io.shiftleft.codepropertygraph.generated.nodes.{AstNode, Call}
import io.shiftleft.semanticcpg.language._
import io.shiftleft.semanticcpg.language.operatorextension.{OpNodes, allArithmeticTypes, allAssignmentTypes}
import overflowdb.traversal._

class OpAstNodeMethods[A <: AstNode](val node: A) extends AnyVal {

  def inAssignment: Traversal[OpNodes.Assignment] =
    node.start.inAstMinusLeaf.isCall
      .filter(x => allAssignmentTypes.contains(x.name))
      .map(new OpNodes.Assignment(_))

  def assignment: Traversal[OpNodes.Assignment] =
    rawTravForPattern(allAssignmentTypes).map(new OpNodes.Assignment(_))

  def arithmetic: Traversal[OpNodes.Arithmetic] =
    rawTravForPattern(allArithmeticTypes).map(new OpNodes.Arithmetic(_))

  private def rawTravForPattern(strings: Set[String]): Traversal[Call] =
    node.ast.isCall.filter(x => strings.contains(x.name))
}

package io.shiftleft.semanticcpg.language.operatorextension

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.Operators
import io.shiftleft.semanticcpg.language._
import overflowdb.traversal._
import overflowdb.traversal.help.{Doc, TraversalSource}

object NodeTypeStarters {

  val assignmentAndArithmetic: Set[String] = Set(
    Operators.assignmentDivision,
    Operators.assignmentExponentiation,
    Operators.assignmentPlus,
    Operators.assignmentMinus,
    Operators.assignmentModulo,
    Operators.assignmentMultiplication,
    Operators.preIncrement,
    Operators.preDecrement,
    Operators.postIncrement,
    Operators.postIncrement,
  )

  val allAssignmentTypes: Set[String] = Set(
    Operators.assignment,
    Operators.assignmentOr,
    Operators.assignmentAnd,
    Operators.assignmentXor,
    Operators.assignmentArithmeticShiftRight,
    Operators.assignmentLogicalShiftRight,
    Operators.assignmentShiftLeft,
  ) ++ assignmentAndArithmetic

  val allArithmeticTypes: Set[String] = Set(
    Operators.addition,
    Operators.subtraction,
    Operators.division,
    Operators.multiplication,
    Operators.exponentiation,
    Operators.modulo
  ) ++ assignmentAndArithmetic

}

@TraversalSource
class NodeTypeStarters(cpg: Cpg) {
  import NodeTypeStarters._

  @Doc(info = "All assignments, including shorthand assignments that perform arithmetic (e.g., '+=')")
  def assignment: Traversal[opnodes.Assignment] =
    cpg.call
      .filter { x =>
        allAssignmentTypes.contains(x.name)
      }
      .map(new opnodes.Assignment(_))

  @Doc(info = "All arithmetic operations, including shorthand assignments that perform arithmetic (e.g., '+=')")
  def arithmetic: Traversal[opnodes.Arithmetic] =
    cpg.call
      .filter { x =>
        allArithmeticTypes.contains(x.name)
      }
      .map(new opnodes.Arithmetic(_))

}

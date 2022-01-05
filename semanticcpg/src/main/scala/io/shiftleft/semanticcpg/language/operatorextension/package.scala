package io.shiftleft.semanticcpg.language

import io.shiftleft.codepropertygraph.generated.Operators

package object operatorextension {

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

  val allArrayAccessTypes: Set[String] = Set(
    Operators.computedMemberAccess,
    Operators.indirectComputedMemberAccess,
    Operators.indexAccess,
    Operators.indirectIndexAccess
  )

}

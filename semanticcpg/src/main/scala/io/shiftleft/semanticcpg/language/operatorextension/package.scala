package io.shiftleft.semanticcpg.language

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.{Operators, nodes}
import overflowdb.traversal.help.{Doc, TraversalSource}
import overflowdb.traversal.{NodeOps, Traversal}

package object operatorextension {
  trait AssignmentT
  type Assignment = nodes.Call_Ref[AssignmentT]
  trait ArithmeticT
  type Arithmetic = nodes.Call_Ref[ArithmeticT]
  trait ArrayAccessT
  type ArrayAccess = nodes.Call_Ref[ArrayAccessT]

  def asAssignment(e: nodes.StoredNode): Option[Assignment] = {
    e match {
      case c: nodes.Call =>
        c.methodFullName match {
          case Operators.assignment | Operators.assignmentAnd | Operators.assignmentOr | Operators.assignmentXor |
              Operators.assignmentMinus | Operators.assignmentPlus | Operators.assignmentArithmeticShiftRight |
              Operators.assignmentLogicalShiftRight | Operators.assignmentShiftLeft | Operators.assignmentDivision |
              Operators.assignmentModulo | Operators.assignmentMultiplication | Operators.assignmentExponentiation =>
            Some(c.asInstanceOf[Assignment])
          case _ => None
        }
      case _ => None
    }
  }
  def asArithmetic(e: nodes.StoredNode): Option[Arithmetic] = {
    e match {
      case c: nodes.Call =>
        c.methodFullName match {
          case Operators.addition | Operators.minus | Operators.modulo | Operators.multiplication | Operators.division |
              Operators.exponentiation =>
            Some(c.asInstanceOf[Arithmetic])
          case _ => None
        }
      case _ => None
    }
  }

  def asArrayAccess(e: nodes.StoredNode): Option[ArrayAccess] = {
    e match {
      case c: nodes.Call =>
        c.methodFullName match {
          case Operators.computedMemberAccess | Operators.indirectComputedMemberAccess | Operators.indexAccess |
              Operators.indirectIndexAccess =>
            Some(c.asInstanceOf[ArrayAccess])
          case _ => None
        }
      case _ => None
    }
  }

  def inAssignment(a: nodes.AstNode): Option[Assignment] = {
    var iter = a._astIn
    while (iter.hasNext) {
      val next = iter.next()
      val resOption = asAssignment(next)
      if (resOption.isDefined) return resOption
      iter = next._astIn
    }
    None
  }

  class AssignmentExt(val assignment: Assignment) extends AnyVal {
    def target: nodes.Expression = assignment.argument(1)
    def source: nodes.Expression = assignment.argument(2)
  }

  class ArrayAccessExt(val arrayAccess: ArrayAccess) extends AnyVal {
    def array: nodes.Expression =
      arrayAccess.argument(1)

    def subscripts: Traversal[nodes.Identifier] =
      arrayAccess.argument(2).start.ast.isIdentifier
  }

  class AstNodeExt(val node: nodes.AstNode) extends AnyVal {
    def inAssignment: Traversal[Assignment] = Traversal.from(operatorextension.inAssignment(node))
    def assignments: Traversal[Assignment] = node.start.ast.flatMap(operatorextension.asAssignment)
    def arithmetics: Traversal[Arithmetic] = node.start.ast.flatMap(operatorextension.asArithmetic)
  }

  //this looks seriously wrong, but it is what the old code did
  class ExpressionNodeExt(val node: nodes.Expression) extends AnyVal {
    def isArrayAccess: Traversal[ArrayAccess] = node.start.ast.flatMap(operatorextension.asArrayAccess)
  }

  class AssignmentTraversal[T <: Assignment](val traversal: Traversal[T]) extends AnyVal {
    def target: Traversal[nodes.Expression] = traversal.map(_.target)
    def source: Traversal[nodes.Expression] = traversal.map(_.source)
  }
  class ArrayAccessTraversal[T <: ArrayAccess](val traversal: Traversal[T]) extends AnyVal {
    def array: Traversal[nodes.Expression] = traversal.map(_.array)
    def subscripts: Traversal[nodes.Expression] = traversal.flatMap(_.subscripts)
  }

  class AstTraversal[T <: nodes.AstNode](val traversal: Traversal[T]) extends AnyVal {
    def isAssignment: Traversal[Assignment] = traversal.flatMap(asAssignment)
    def assignments: Traversal[Assignment] = traversal.ast.flatMap(asAssignment)
    def inAssignment: Traversal[Assignment] = traversal.flatMap(operatorextension.inAssignment)

    def isArithmetic: Traversal[Arithmetic] = traversal.flatMap(asArithmetic)
    def arithmetics: Traversal[Arithmetic] = traversal.ast.isArithmetic
  }
  //this looks seriously wrong, but it is what the old code did
  class ExpressionTraversal[T <: nodes.Expression](val traversal: Traversal[T]) extends AnyVal {
    def isArrayAccess: Traversal[ArrayAccess] = traversal.ast.flatMap(asArrayAccess)
  }

  @TraversalSource
  implicit class NodeTypeStarters(val cpg: Cpg) extends AnyVal {

    @Doc("All assignments")
    def assignment: Traversal[Assignment] =
      cpg.call.flatMap(operatorextension.asAssignment)

    @Doc("All arithmetic operations")
    def arithmetic: Traversal[Arithmetic] =
      cpg.call.flatMap(operatorextension.asArithmetic)
  }
}

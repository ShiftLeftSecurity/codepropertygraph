package io.shiftleft.semanticcpg.language

import io.shiftleft.codepropertygraph.generated.Operators
import io.shiftleft.codepropertygraph.generated.nodes.{AstNode, Call, Expression, HasArgumentIndex, StoredNode}
import io.shiftleft.semanticcpg.language.nodemethods.CallMethods
import io.shiftleft.semanticcpg.language.types.expressions.Identifier

package object operatorextension {

  implicit class ArrayAccessExt(val call: Call) extends AnyVal {

    def array: StoredNode with HasArgumentIndex =
      call.argument(1)

    def subscripts: Identifier =
      call.argument(2).ast.isIdentifier
  }

  implicit class AssignmentExt(val call: Call) extends AnyVal {

    def target: StoredNode with HasArgumentIndex =
      new CallMethods(call).argument(1)

    def source: StoredNode with HasArgumentIndex =
      new CallMethods(call).argument(2)
  }

  implicit class OpAstNodeExt(val node: AstNode) extends AnyVal {

    def inAssignment: AssignmentTrav =
      new AssignmentTrav(
        node.start.inAstMinusLeaf.isCall.name(NodeTypeStarters.assignmentPattern).raw
      )

    def assignments: AssignmentTrav =
      new AssignmentTrav(rawTravForPattern(NodeTypeStarters.assignmentPattern).raw)

    def arithmetics: ArithmeticTrav =
      new ArithmeticTrav(rawTravForPattern(NodeTypeStarters.arithmeticPattern).raw)

    private def rawTravForPattern(pattern: String): NodeSteps[Call] =
      node.ast.isCall.name(pattern)
  }

  implicit class TargetExt(val expr: Expression) extends AnyVal {

    def isArrayAccess: ArrayAccessTrav =
      new ArrayAccessTrav(
        expr.ast.isCall
          .nameExact(Operators.computedMemberAccess,
                     Operators.indirectComputedMemberAccess,
                     Operators.indexAccess,
                     Operators.indirectIndexAccess)
          .raw)

  }

}

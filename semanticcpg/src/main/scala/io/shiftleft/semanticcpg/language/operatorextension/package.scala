package io.shiftleft.semanticcpg.language

import io.shiftleft.codepropertygraph.generated.Operators
import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.semanticcpg.language.nodemethods.CallMethods
import io.shiftleft.semanticcpg.language.types.expressions.IdentifierTrav

package object operatorextension {

  implicit class ArrayAccessExt(val call: nodes.Call) extends AnyVal {
    def array: nodes.Expression = call.argument(1)
    def subscripts: IdentifierTrav = call.argument(2).ast.isIdentifier
  }

  implicit class AssignmentExt(val call: nodes.Call) extends AnyVal {
    def target: nodes.Expression = new CallMethods(call).argument(1)
    def source: nodes.Expression = new CallMethods(call).argument(2)
  }

  implicit class OpAstNodeExt(val node: nodes.AstNode) extends AnyVal {
    def inAssignment: AssignmentTrav =
      new AssignmentTrav(
        node.start.inAstMinusLeaf.isCall.name(NodeTypeStarters.assignmentPattern).raw
      )

    def assignments: AssignmentTrav =
      new AssignmentTrav(rawTravForPattern(NodeTypeStarters.assignmentPattern).raw)

    def arithmetics: ArithmeticTrav =
      new ArithmeticTrav(rawTravForPattern(NodeTypeStarters.arithmeticPattern).raw)

    private def rawTravForPattern(pattern: String): NodeSteps[nodes.Call] =
      node.ast.isCall.name(pattern)
  }

  implicit class TargetExt(val expr: nodes.Expression) extends AnyVal {
    def isArrayAccess: ArrayAccessTrav =
      new ArrayAccessTrav(
        expr.ast.isCall
          .nameExact(Operators.computedMemberAccess,
                     Operators.indirectComputedMemberAccess,
                     Operators.indexAccess,
                     Operators.indirectIndexAccess)
          .raw)
  }

  implicit def toAssignmentTrav(steps: Steps[nodes.Call]): AssignmentTrav = new AssignmentTrav(steps.raw)

}

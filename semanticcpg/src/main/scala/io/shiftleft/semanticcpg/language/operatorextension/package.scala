package io.shiftleft.semanticcpg.language

import gremlin.scala.GremlinScala

import io.shiftleft.codepropertygraph.generated.nodes.{AstNode, Call, Expression}
import io.shiftleft.semanticcpg.language.nodemethods.CallMethods
import io.shiftleft.semanticcpg.language.types.expressions.Identifier

package object operatorextension {

  implicit class ArrayAccessExt(val call: Call) extends AnyVal {

    def array: Expression = call.argument(1)

    def subscripts: Identifier = call.argument(2).ast.isIdentifier
  }

  implicit class AssignmentExt(val call: Call) extends AnyVal {

    def target: Expression = new CallMethods(call).argument(1)

    def source: Expression = new CallMethods(call).argument(2)
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
          .nameExact("<operator>.computedMemberAccess")
          .raw)

  }

}

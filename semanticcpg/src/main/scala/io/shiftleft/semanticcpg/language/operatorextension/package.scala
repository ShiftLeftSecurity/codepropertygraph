package io.shiftleft.semanticcpg.language

import gremlin.scala.GremlinScala
import io.shiftleft.codepropertygraph.generated.Operators
import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.semanticcpg.language.nodemethods.CallMethods

package object operatorextension {

  implicit class ArrayAccessExt(val call: nodes.Call) extends AnyVal {
    def array: nodes.Expression = call.argument(1)
    def subscripts: NodeSteps[nodes.Identifier] = call.argument(2).ast.isIdentifier
  }

  implicit class ArrayAccessTrav(raw: GremlinScala[nodes.Call]) extends NodeSteps[nodes.Call](raw) {
    def array: NodeSteps[nodes.Expression] = map(_.array)
    def subscripts: NodeSteps[nodes.Identifier] = flatMap(_.subscripts)
  }

  implicit class AssignmentExt(val call: nodes.Call) extends AnyVal {
    def target: nodes.Expression = new CallMethods(call).argument(1)
    def source: nodes.Expression = new CallMethods(call).argument(2)
  }

  implicit class AssignmentTrav(val wrapped: Steps[nodes.Call]) extends AnyVal {
    def target: NodeSteps[nodes.Expression] = wrapped.map(_.target)
    def source: NodeSteps[nodes.Expression] = wrapped.map(_.source)
  }

  implicit class OpAstNodeExt[A <: nodes.AstNode](val node: A) extends AnyVal {
    def inAssignment: NodeSteps[nodes.Call] =
      node.start.inAstMinusLeaf.isCall.name(NodeTypeStarters.assignmentPattern)

    def assignments: NodeSteps[nodes.Call] =
      rawTravForPattern(NodeTypeStarters.assignmentPattern)

    def arithmetics: NodeSteps[nodes.Call] =
      rawTravForPattern(NodeTypeStarters.arithmeticPattern)

    private def rawTravForPattern(pattern: String): NodeSteps[nodes.Call] =
      node.ast.isCall.name(pattern)
  }

  implicit class OpAstNodeTrav[A <: nodes.AstNode](val wrapped: Steps[A]) extends AnyVal {
    def inAssignment: NodeSteps[nodes.Call] =
      wrapped.flatMap(_.inAssignment)

    def assignments: NodeSteps[nodes.Call] =
      wrapped.flatMap(_.assignments)

    def arithmetics: NodeSteps[nodes.Call] =
      wrapped.flatMap(_.arithmetics)
  }

  implicit class TargetExt(val expr: nodes.Expression) extends AnyVal {
    def isArrayAccess: NodeSteps[nodes.Call] =
        expr.ast.isCall
          .nameExact(Operators.computedMemberAccess,
                     Operators.indirectComputedMemberAccess,
                     Operators.indexAccess,
                     Operators.indirectIndexAccess)
  }

  implicit class TargetTrav(val wrapped: NodeSteps[nodes.Expression]) extends AnyVal {
    def isArrayAccess: NodeSteps[nodes.Call] =
      wrapped.flatMap(_.isArrayAccess)

    def expr: NodeSteps[nodes.Expression] = wrapped.map(_.expr)
  }
}

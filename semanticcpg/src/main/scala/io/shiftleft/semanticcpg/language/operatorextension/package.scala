package io.shiftleft.semanticcpg.language

import gremlin.scala.GremlinScala
import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.Operators
import io.shiftleft.codepropertygraph.generated.nodes

package object operatorextension {

  trait Implicits {
    implicit def toNodeTypeStartersOperatorExtension(cpg: Cpg): NodeTypeStarters = new NodeTypeStarters(cpg)
    implicit def toArrayAccessExt(call: nodes.Call): ArrayAccessExt = new ArrayAccessExt(call)
    implicit def toArrayAccessTrav(steps: Steps[nodes.Call]): ArrayAccessTrav = new ArrayAccessTrav(steps)
    implicit def toAssignmentExt(call: nodes.Call): AssignmentExt = new AssignmentExt(call)
    implicit def toAssignmentTrav(steps: Steps[nodes.Call]): AssignmentTrav = new AssignmentTrav(steps)
    implicit def toTargetExt(call: nodes.Expression): TargetExt = new TargetExt(call)
    implicit def toTargetTrav(steps: Steps[nodes.Expression]): TargetTrav = new TargetTrav(steps)
    implicit def toOpAstNodeExt[A <: nodes.AstNode](node: A): OpAstNodeExt[A] = new OpAstNodeExt(node)
    implicit def toOpAstNodeTrav[A <: nodes.AstNode](steps: Steps[A]): OpAstNodeTrav[A] = new OpAstNodeTrav(steps)
  }

  class ArrayAccessExt(val call: nodes.Call) extends AnyVal {
    def array: nodes.Expression = call.argument(1)
    def subscripts: NodeSteps[nodes.Identifier] = call.argument(2).ast.isIdentifier
  }

  class ArrayAccessTrav(val wrapped: Steps[nodes.Call]) extends AnyVal {
    private def raw: GremlinScala[nodes.Call] = wrapped.raw
    def array: NodeSteps[nodes.Expression] = wrapped.map(_.array)
    def subscripts: NodeSteps[nodes.Identifier] = wrapped.flatMap(_.subscripts)
  }

  class AssignmentExt(val call: nodes.Call) extends AnyVal {
    def target: nodes.Expression = call.argument(1)
    def source: nodes.Expression = call.argument(2)
  }

  class AssignmentTrav(val wrapped: Steps[nodes.Call]) extends AnyVal {
    private def raw: GremlinScala[nodes.Call] = wrapped.raw
    def target: NodeSteps[nodes.Expression] = wrapped.map(_.target)
    def source: NodeSteps[nodes.Expression] = wrapped.map(_.source)
  }

  class OpAstNodeExt[A <: nodes.AstNode](val node: A) extends AnyVal {
    def inAssignment: NodeSteps[nodes.Call] =
      node.start.inAstMinusLeaf.isCall.name(NodeTypeStarters.assignmentPattern)

    def assignments: NodeSteps[nodes.Call] =
      rawTravForPattern(NodeTypeStarters.assignmentPattern)

    def arithmetics: NodeSteps[nodes.Call] =
      rawTravForPattern(NodeTypeStarters.arithmeticPattern)

    private def rawTravForPattern(pattern: String): NodeSteps[nodes.Call] =
      node.ast.isCall.name(pattern)
  }

  class OpAstNodeTrav[A <: nodes.AstNode](val wrapped: Steps[A]) extends AnyVal {
    private def raw: GremlinScala[A] = wrapped.raw
    def inAssignment: NodeSteps[nodes.Call] = wrapped.flatMap(_.inAssignment)
    def assignments: NodeSteps[nodes.Call] = wrapped.flatMap(_.assignments)
    def arithmetics: NodeSteps[nodes.Call] = wrapped.flatMap(_.arithmetics)
  }

  class TargetExt(val expr: nodes.Expression) extends AnyVal {
    def isArrayAccess: NodeSteps[nodes.Call] =
      expr.ast.isCall
        .nameExact(Operators.computedMemberAccess,
                   Operators.indirectComputedMemberAccess,
                   Operators.indexAccess,
                   Operators.indirectIndexAccess)
  }

  class TargetTrav(val wrapped: NodeSteps[nodes.Expression]) extends AnyVal {
    private def raw: GremlinScala[nodes.Expression] = wrapped.raw
    def isArrayAccess: NodeSteps[nodes.Call] = wrapped.flatMap(_.isArrayAccess)
    def expr: NodeSteps[nodes.Expression] = wrapped.map(_.expr)
  }
}

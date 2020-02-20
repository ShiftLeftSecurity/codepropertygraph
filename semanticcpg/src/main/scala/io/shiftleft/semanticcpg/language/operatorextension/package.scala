package io.shiftleft.semanticcpg.language

import gremlin.scala.GremlinScala
import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.Operators
import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.codepropertygraph.generated.nodes.Call
import io.shiftleft.overflowdb.OdbGraph

package object operatorextension {
  class Assignment(call: Call) extends Call(call.graph.asInstanceOf[OdbGraph], call.id)
  class Arithmetic(call: Call) extends nodes.Call(call.graph.asInstanceOf[OdbGraph], call.id)
  class ArrayAccess(call: Call) extends nodes.Call(call.graph.asInstanceOf[OdbGraph], call.id)

  trait Implicits {
    implicit def toNodeTypeStartersOperatorExtension(cpg: Cpg): NodeTypeStarters = new NodeTypeStarters(cpg)
    implicit def toArrayAccessExt(arrayAccess: ArrayAccess): ArrayAccessExt = new ArrayAccessExt(arrayAccess)
    implicit def toArrayAccessTrav(steps: Steps[ArrayAccess]): ArrayAccessTrav = new ArrayAccessTrav(steps)
    implicit def toAssignmentExt(assignment: Assignment): AssignmentExt = new AssignmentExt(assignment)
    implicit def toAssignmentTrav(steps: Steps[Assignment]): AssignmentTrav = new AssignmentTrav(steps)
    implicit def toTargetExt(call: nodes.Expression): TargetExt = new TargetExt(call)
    implicit def toTargetTrav(steps: Steps[nodes.Expression]): TargetTrav = new TargetTrav(steps)
    implicit def toOpAstNodeExt[A <: nodes.AstNode](node: A): OpAstNodeExt[A] = new OpAstNodeExt(node)
    implicit def toOpAstNodeTrav[A <: nodes.AstNode](steps: Steps[A]): OpAstNodeTrav[A] = new OpAstNodeTrav(steps)
  }

  class ArrayAccessExt(val arrayAccess: ArrayAccess) extends AnyVal {
    def array: nodes.Expression = arrayAccess.argument(1)
    def subscripts: NodeSteps[nodes.Identifier] = arrayAccess.argument(2).ast.isIdentifier
  }

  class ArrayAccessTrav(val wrapped: Steps[ArrayAccess]) extends AnyVal {
    private def raw: GremlinScala[ArrayAccess] = wrapped.raw
    def array: NodeSteps[nodes.Expression] = wrapped.map(_.array)
    def subscripts: NodeSteps[nodes.Identifier] = wrapped.flatMap(_.subscripts)
  }

  class AssignmentExt(val assignment: Assignment) extends AnyVal {
    def target: nodes.Expression = assignment.argument(1)
    def source: nodes.Expression = assignment.argument(2)
  }

  class AssignmentTrav(val wrapped: Steps[Assignment]) extends AnyVal {
    private def raw: GremlinScala[Assignment] = wrapped.raw
    def target: NodeSteps[nodes.Expression] = wrapped.map(_.target)
    def source: NodeSteps[nodes.Expression] = wrapped.map(_.source)
  }

  class OpAstNodeExt[A <: nodes.AstNode](val node: A) extends AnyVal {
    def inAssignment: NodeSteps[Assignment] =
      node.start.inAstMinusLeaf.isCall.name(NodeTypeStarters.assignmentPattern).map(new Assignment(_))

    def assignments: NodeSteps[Assignment] =
      rawTravForPattern(NodeTypeStarters.assignmentPattern).map(new Assignment(_))

    def arithmetics: NodeSteps[Arithmetic] =
      rawTravForPattern(NodeTypeStarters.arithmeticPattern).map(new Arithmetic(_))

    private def rawTravForPattern(pattern: String): NodeSteps[nodes.Call] =
      node.ast.isCall.name(pattern)
  }

  class OpAstNodeTrav[A <: nodes.AstNode](val wrapped: Steps[A]) extends AnyVal {
    private def raw: GremlinScala[A] = wrapped.raw
    def inAssignment: NodeSteps[Assignment] = wrapped.flatMap(_.inAssignment)
    def assignments: NodeSteps[Assignment] = wrapped.flatMap(_.assignments)
    def arithmetics: NodeSteps[Arithmetic] = wrapped.flatMap(_.arithmetics)
  }

  class TargetExt(val expr: nodes.Expression) extends AnyVal {
    def isArrayAccess: NodeSteps[ArrayAccess] =
      expr.ast.isCall
        .nameExact(Operators.computedMemberAccess,
                   Operators.indirectComputedMemberAccess,
                   Operators.indexAccess,
                   Operators.indirectIndexAccess)
        .map(new ArrayAccess(_))
  }

  class TargetTrav(val wrapped: NodeSteps[nodes.Expression]) extends AnyVal {
    private def raw: GremlinScala[nodes.Expression] = wrapped.raw
    def isArrayAccess: NodeSteps[ArrayAccess] = wrapped.flatMap(_.isArrayAccess)
    def expr: NodeSteps[nodes.Expression] = wrapped.map(_.expr)
  }
}

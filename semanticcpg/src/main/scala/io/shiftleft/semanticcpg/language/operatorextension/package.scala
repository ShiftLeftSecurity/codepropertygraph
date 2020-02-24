package io.shiftleft.semanticcpg.language

import gremlin.scala.GremlinScala
import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.{Operators, nodes}
import io.shiftleft.overflowdb.OdbGraph

object opnodes {
  import io.shiftleft.codepropertygraph.generated.nodes.Call
  class Assignment(call: Call) extends nodes.Call(call.graph.asInstanceOf[OdbGraph], call.id)
  class Arithmetic(call: Call) extends nodes.Call(call.graph.asInstanceOf[OdbGraph], call.id)
  class ArrayAccess(call: Call) extends nodes.Call(call.graph.asInstanceOf[OdbGraph], call.id)
}

package object operatorextension {
  import io.shiftleft.codepropertygraph.generated.nodes

  trait Implicits {
    implicit def toNodeTypeStartersOperatorExtension(cpg: Cpg): NodeTypeStarters = new NodeTypeStarters(cpg)
    implicit def toArrayAccessExt(arrayAccess: opnodes.ArrayAccess): ArrayAccessExt = new ArrayAccessExt(arrayAccess)
    implicit def toArrayAccessTrav(steps: Steps[opnodes.ArrayAccess]): ArrayAccessTrav = new ArrayAccessTrav(steps)
    implicit def toAssignmentExt(assignment: opnodes.Assignment): AssignmentExt = new AssignmentExt(assignment)
    implicit def toAssignmentTrav(steps: Steps[opnodes.Assignment]): AssignmentTrav = new AssignmentTrav(steps)
    implicit def toTargetExt(call: nodes.Expression): TargetExt = new TargetExt(call)
    implicit def toTargetTrav(steps: Steps[nodes.Expression]): TargetTrav = new TargetTrav(steps)
    implicit def toOpAstNodeExt[A <: nodes.AstNode](node: A): OpAstNodeExt[A] = new OpAstNodeExt(node)
    implicit def toOpAstNodeTrav[A <: nodes.AstNode](steps: Steps[A]): OpAstNodeTrav[A] = new OpAstNodeTrav(steps)
  }

  class ArrayAccessExt(val arrayAccess: opnodes.ArrayAccess) extends AnyVal {
    def array: nodes.Expression = arrayAccess.argument(1)
    def subscripts: NodeSteps[nodes.Identifier] = arrayAccess.argument(2).ast.isIdentifier
  }

  class ArrayAccessTrav(val wrapped: Steps[opnodes.ArrayAccess]) extends AnyVal {
    private def raw: GremlinScala[opnodes.ArrayAccess] = wrapped.raw
    def array: NodeSteps[nodes.Expression] = wrapped.map(_.array)
    def subscripts: NodeSteps[nodes.Identifier] = wrapped.flatMap(_.subscripts)
  }

  class AssignmentExt(val assignment: opnodes.Assignment) extends AnyVal {
    def target: nodes.Expression = assignment.argument(1)
    def source: nodes.Expression = assignment.argument(2)
  }

  class AssignmentTrav(val wrapped: Steps[opnodes.Assignment]) extends AnyVal {
    private def raw: GremlinScala[opnodes.Assignment] = wrapped.raw
    def target: NodeSteps[nodes.Expression] = wrapped.map(_.target)
    def source: NodeSteps[nodes.Expression] = wrapped.map(_.source)
  }

  class OpAstNodeExt[A <: nodes.AstNode](val node: A) extends AnyVal {
    def inAssignment: NodeSteps[opnodes.Assignment] =
      node.start.inAstMinusLeaf.isCall.name(NodeTypeStarters.assignmentPattern).map(new opnodes.Assignment(_))

    def assignments: NodeSteps[opnodes.Assignment] =
      rawTravForPattern(NodeTypeStarters.assignmentPattern).map(new opnodes.Assignment(_))

    def arithmetics: NodeSteps[opnodes.Arithmetic] =
      rawTravForPattern(NodeTypeStarters.arithmeticPattern).map(new opnodes.Arithmetic(_))

    private def rawTravForPattern(pattern: String): NodeSteps[nodes.Call] =
      node.ast.isCall.name(pattern)
  }

  class OpAstNodeTrav[A <: nodes.AstNode](val wrapped: Steps[A]) extends AnyVal {
    private def raw: GremlinScala[A] = wrapped.raw
    def inAssignment: NodeSteps[opnodes.Assignment] = wrapped.flatMap(_.inAssignment)
    def assignments: NodeSteps[opnodes.Assignment] = wrapped.flatMap(_.assignments)
    def arithmetics: NodeSteps[opnodes.Arithmetic] = wrapped.flatMap(_.arithmetics)
  }

  class TargetExt(val expr: nodes.Expression) extends AnyVal {
    def isArrayAccess: NodeSteps[opnodes.ArrayAccess] =
      expr.ast.isCall
        .nameExact(Operators.computedMemberAccess,
                   Operators.indirectComputedMemberAccess,
                   Operators.indexAccess,
                   Operators.indirectIndexAccess)
        .map(new opnodes.ArrayAccess(_))
  }

  class TargetTrav(val wrapped: NodeSteps[nodes.Expression]) extends AnyVal {
    private def raw: GremlinScala[nodes.Expression] = wrapped.raw
    def isArrayAccess: NodeSteps[opnodes.ArrayAccess] = wrapped.flatMap(_.isArrayAccess)
  }
}

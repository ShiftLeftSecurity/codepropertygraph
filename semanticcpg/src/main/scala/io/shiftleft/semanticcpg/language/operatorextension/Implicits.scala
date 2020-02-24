package io.shiftleft.semanticcpg.language.operatorextension

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.semanticcpg.language.Steps
import io.shiftleft.semanticcpg.language.operatorextension.nodemethods._

trait Implicits {
  implicit def toNodeTypeStartersOperatorExtension(cpg: Cpg): NodeTypeStarters = new NodeTypeStarters(cpg)
  implicit def toArrayAccessExt(arrayAccess: opnodes.ArrayAccess): ArrayAccessMethods =
    new ArrayAccessMethods(arrayAccess)
  implicit def toArrayAccessTrav(steps: Steps[opnodes.ArrayAccess]): ArrayAccess = new ArrayAccess(steps)
  implicit def toAssignmentExt(assignment: opnodes.Assignment): AssignmentMethods = new AssignmentMethods(assignment)
  implicit def toAssignmentTrav(steps: Steps[opnodes.Assignment]): Assignment = new Assignment(steps)
  implicit def toTargetExt(call: nodes.Expression): TargetMethods = new TargetMethods(call)
  implicit def toTargetTrav(steps: Steps[nodes.Expression]): Target = new Target(steps)
  implicit def toOpAstNodeExt[A <: nodes.AstNode](node: A): OpAstNodeMethods[A] = new OpAstNodeMethods(node)
  implicit def toOpAstNodeTrav[A <: nodes.AstNode](steps: Steps[A]): OpAstNode[A] = new OpAstNode(steps)
}

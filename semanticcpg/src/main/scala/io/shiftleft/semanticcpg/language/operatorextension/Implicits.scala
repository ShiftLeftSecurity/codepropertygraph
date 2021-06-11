package io.shiftleft.semanticcpg.language.operatorextension

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.semanticcpg.language.operatorextension.nodemethods._
import overflowdb.traversal.Traversal

trait Implicits {
  implicit def toNodeTypeStartersOperatorExtension(cpg: Cpg): NodeTypeStarters = new NodeTypeStarters(cpg)
  implicit def toArrayAccessExt(arrayAccess: opnodes.ArrayAccess): ArrayAccessMethods =
    new ArrayAccessMethods(arrayAccess)
  implicit def toArrayAccessTrav(steps: Traversal[opnodes.ArrayAccess]): ArrayAccessTraversal = new ArrayAccessTraversal(steps)
  implicit def toAssignmentExt(assignment: opnodes.Assignment): AssignmentMethods = new AssignmentMethods(assignment)
  implicit def toAssignmentTrav(steps: Traversal[opnodes.Assignment]): AssignmentTraversal = new AssignmentTraversal(steps)
  implicit def toTargetExt(call: nodes.Expression): TargetMethods = new TargetMethods(call)
  implicit def toTargetTrav(steps: Traversal[nodes.Expression]): TargetTraversal = new TargetTraversal(steps)
  implicit def toOpAstNodeExt[A <: nodes.AstNode](node: A): OpAstNodeMethods[A] = new OpAstNodeMethods(node)
  implicit def toOpAstNodeTrav[A <: nodes.AstNode](steps: Traversal[A]): OpAstNode[A] = new OpAstNode(steps)
}

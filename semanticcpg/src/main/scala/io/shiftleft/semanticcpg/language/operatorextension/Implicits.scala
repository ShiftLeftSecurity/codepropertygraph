package io.shiftleft.semanticcpg.language.operatorextension

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.codepropertygraph.generated.nodes.{AstNode, Call, Expression}
import io.shiftleft.semanticcpg.language.operatorextension.nodemethods._
import overflowdb.traversal._
import io.shiftleft.semanticcpg.language._

trait Implicits {
  implicit def toNodeTypeStartersOperatorExtension(cpg: Cpg): NodeTypeStarters = new NodeTypeStarters(cpg)
  implicit def toArrayAccessExt(arrayAccess: OpNodes.ArrayAccess): ArrayAccessMethods =
    new ArrayAccessMethods(arrayAccess)
  implicit def toArrayAccessTrav(steps: Traversal[OpNodes.ArrayAccess]): ArrayAccessTraversal =
    new ArrayAccessTraversal(steps)
  implicit def toAssignmentExt(assignment: OpNodes.Assignment): AssignmentMethods = new AssignmentMethods(assignment)
  implicit def toAssignmentTrav(steps: Traversal[OpNodes.Assignment]): AssignmentTraversal =
    new AssignmentTraversal(steps)
  implicit def toTargetExt(call: Expression): TargetMethods = new TargetMethods(call)
  implicit def toTargetTrav(steps: Traversal[Expression]): TargetTraversal = new TargetTraversal(steps)
  implicit def toOpAstNodeExt[A <: AstNode](node: A): OpAstNodeMethods[A] = new OpAstNodeMethods(node)
  implicit def toOpAstNodeTrav[A <: AstNode](steps: Traversal[A]): OpAstNode[A] = new OpAstNode(steps)

//  implicit class MethodExtension(methodTrav: Traversal[nodes.Method]) {
//
//    def fieldAccess: Traversal[OpNodes.FieldAccess] =
//      callsWithNameIn(allFieldAccessTypes)
//        .map(new OpNodes.FieldAccess(_))
//
//    private def callsWithNameIn(set: Set[String]) : Traversal[Call] =
//      methodTrav.call.filter(x => set.contains(x.name))
//  }

}

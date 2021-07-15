package io.shiftleft.semanticcpg.language.operatorextension

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.semanticcpg.language.operatorextension
import overflowdb.traversal.Traversal

trait Implicits {
  implicit def toAssignmentOperatorExtension(node: operatorextension.Assignment): operatorextension.AssignmentExt =
    new operatorextension.AssignmentExt(node)
  implicit def toArrayAccessOperatorExtension(node: operatorextension.ArrayAccess): operatorextension.ArrayAccessExt =
    new operatorextension.ArrayAccessExt(node)
  implicit def toAstNodeOperatorExtension(node: nodes.AstNode): operatorextension.AstNodeExt =
    new operatorextension.AstNodeExt(node)
  implicit def toExpressionNodeOperatorExtension(node: nodes.Expression): operatorextension.ExpressionNodeExt =
    new operatorextension.ExpressionNodeExt(node)

  implicit def toAssignmentTraversalOperatorExtension[T <: operatorextension.Assignment](
      traversal: Traversal[T]): operatorextension.AssignmentTraversal[T] =
    new operatorextension.AssignmentTraversal(traversal)
  implicit def toArrayAccessTraversalOperatorExtension[T <: operatorextension.ArrayAccess](
      traversal: Traversal[T]): operatorextension.AstTraversal[T] = new operatorextension.AstTraversal(traversal)
  implicit def toAstTraversalOperatorExtension[T <: nodes.AstNode](
      traversal: Traversal[T]): operatorextension.AstTraversal[T] = new operatorextension.AstTraversal(traversal)
  implicit def toExpressionTraversalOperatorExtension[T <: nodes.Expression](
      traversal: Traversal[T]): operatorextension.ExpressionTraversal[T] =
    new operatorextension.ExpressionTraversal(traversal)
  implicit def toNodeStartersOperatorExtension(cpg: Cpg): operatorextension.NodeTypeStarters =
    new operatorextension.NodeTypeStarters(cpg)
}

package io.shiftleft.semanticcpg.language.operatorextension

import io.shiftleft.codepropertygraph.generated.nodes.AstNode
import io.shiftleft.semanticcpg.language._
import overflowdb.traversal._
import overflowdb.traversal.help.Doc

class OpAstNodeTraversal[A <: AstNode](val traversal: Traversal[A]) extends AnyVal {

  @Doc(info = "Any assignments that this node is a part of")
  def inAssignment: Traversal[OpNodes.Assignment] = traversal.flatMap(_.inAssignment)

  @Doc(info = "Assignments nested in this tree")
  def assignment: Traversal[OpNodes.Assignment] = traversal.flatMap(_.assignment)

  @Doc(info = "Arithmetic expressions nested in this tree")
  def arithmetic: Traversal[OpNodes.Arithmetic] = traversal.flatMap(_.arithmetic)
}

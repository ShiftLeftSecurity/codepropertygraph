package io.shiftleft.semanticcpg.language.operatorextension

import io.shiftleft.codepropertygraph.generated.nodes.AstNode
import io.shiftleft.semanticcpg.language._
import overflowdb.traversal._

class OpAstNode[A <: AstNode](val traversal: Traversal[A]) extends AnyVal {
  def inAssignment: Traversal[OpNodes.Assignment] = traversal.flatMap(_.inAssignment)
  def assignments: Traversal[OpNodes.Assignment] = traversal.flatMap(_.assignments)
  def arithmetics: Traversal[OpNodes.Arithmetic] = traversal.flatMap(_.arithmetics)
}

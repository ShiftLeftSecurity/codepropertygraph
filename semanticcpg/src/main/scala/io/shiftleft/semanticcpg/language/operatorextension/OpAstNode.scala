package io.shiftleft.semanticcpg.language.operatorextension

import io.shiftleft.codepropertygraph.generated.nodes.AstNode
import io.shiftleft.semanticcpg.language._
import overflowdb.traversal.Traversal

class OpAstNode[A <: AstNode](val traversal: Traversal[A]) extends AnyVal {
  def inAssignment: Traversal[opnodes.Assignment] = traversal.flatMap(_.inAssignment)
  def assignments: Traversal[opnodes.Assignment] = traversal.flatMap(_.assignments)
  def arithmetics: Traversal[opnodes.Arithmetic] = traversal.flatMap(_.arithmetics)
}

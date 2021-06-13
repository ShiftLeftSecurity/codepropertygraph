package io.shiftleft.semanticcpg.language.operatorextension

import io.shiftleft.codepropertygraph.generated.nodes.Expression
import io.shiftleft.semanticcpg.language._
import overflowdb.traversal.Traversal

class AssignmentTraversal(val traversal: Traversal[opnodes.Assignment]) extends AnyVal {
  def target: Traversal[Expression] = traversal.map(_.target)
  def source: Traversal[Expression] = traversal.map(_.source)
}

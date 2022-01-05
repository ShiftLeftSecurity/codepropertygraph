package io.shiftleft.semanticcpg.language.operatorextension

import io.shiftleft.codepropertygraph.generated.nodes.Expression
import io.shiftleft.semanticcpg.language._
import overflowdb.traversal._

class AssignmentTraversal(val traversal: Traversal[OpNodes.Assignment]) extends AnyVal {
  def target: Traversal[Expression] = traversal.map(_.target)
  def source: Traversal[Expression] = traversal.map(_.source)
}

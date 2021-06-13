package io.shiftleft.semanticcpg.language.operatorextension

import io.shiftleft.codepropertygraph.generated.nodes.Expression
import io.shiftleft.semanticcpg.language._
import overflowdb.traversal.Traversal

class TargetTraversal(val traversal: Traversal[Expression]) extends AnyVal {
  def isArrayAccess: Traversal[opnodes.ArrayAccess] = traversal.flatMap(_.isArrayAccess)
}

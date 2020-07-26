package io.shiftleft.semanticcpg.language.operatorextension

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.semanticcpg.language._
import overflowdb.traversal.Traversal

class Target(val traversal: Traversal[nodes.Expression]) extends AnyVal {
  def isArrayAccess: Traversal[opnodes.ArrayAccess] = traversal.flatMap(_.isArrayAccess)
}

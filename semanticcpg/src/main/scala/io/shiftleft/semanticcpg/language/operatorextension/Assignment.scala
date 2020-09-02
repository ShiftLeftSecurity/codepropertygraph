package io.shiftleft.semanticcpg.language.operatorextension

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.semanticcpg.language._
import overflowdb.traversal.Traversal

class Assignment(val traversal: Traversal[opnodes.Assignment]) extends AnyVal {
  def target: Traversal[nodes.Expression] = traversal.map(_.target)
  def source: Traversal[nodes.Expression] = traversal.map(_.source)
}

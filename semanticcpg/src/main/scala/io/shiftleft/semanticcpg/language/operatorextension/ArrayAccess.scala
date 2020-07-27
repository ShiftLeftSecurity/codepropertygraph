package io.shiftleft.semanticcpg.language.operatorextension

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.semanticcpg.language._
import overflowdb.traversal.Traversal

class ArrayAccess(val traversal: Traversal[opnodes.ArrayAccess]) extends AnyVal {
  def array: Traversal[nodes.Expression] = traversal.map(_.array)
  def subscripts: Traversal[nodes.Identifier] = traversal.flatMap(_.subscripts)
}

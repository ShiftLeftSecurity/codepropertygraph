package io.shiftleft.semanticcpg.language.operatorextension.nodemethods

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.semanticcpg.language._
import io.shiftleft.semanticcpg.language.operatorextension.opnodes
import overflowdb.traversal.Traversal

class ArrayAccessMethods(val arrayAccess: opnodes.ArrayAccess) extends AnyVal {
  def array: nodes.Expression =
    arrayAccess.argument(1)

  def subscripts: Traversal[nodes.Identifier] =
    arrayAccess.argument(2).ast.isIdentifier
}

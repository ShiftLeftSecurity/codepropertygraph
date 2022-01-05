package io.shiftleft.semanticcpg.language.operatorextension.nodemethods

import io.shiftleft.codepropertygraph.generated.nodes.{Expression, Identifier}
import io.shiftleft.semanticcpg.language._
import io.shiftleft.semanticcpg.language.operatorextension.OpNodes
import overflowdb.traversal._

class ArrayAccessMethods(val arrayAccess: OpNodes.ArrayAccess) extends AnyVal {
  def array: Expression =
    arrayAccess.argument(1)

  def subscripts: Traversal[Identifier] =
    arrayAccess.argument(2).ast.isIdentifier
}

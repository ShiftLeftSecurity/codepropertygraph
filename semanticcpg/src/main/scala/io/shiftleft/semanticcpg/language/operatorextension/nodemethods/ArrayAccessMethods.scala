package io.shiftleft.semanticcpg.language.operatorextension.nodemethods

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.semanticcpg.language._
import io.shiftleft.semanticcpg.language.operatorextension.opnodes

class ArrayAccessMethods(val arrayAccess: opnodes.ArrayAccess) extends AnyVal {
  def array: nodes.Expression = arrayAccess.argument(1)
  def subscripts: NodeSteps[nodes.Identifier] = arrayAccess.argument(2).ast.isIdentifier
}

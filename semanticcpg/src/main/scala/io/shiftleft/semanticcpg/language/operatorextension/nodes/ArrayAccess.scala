package io.shiftleft.semanticcpg.language.operatorextension.nodes

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.semanticcpg.language._
import io.shiftleft.semanticcpg.language.types.expressions.Identifier

class ArrayAccess(val call: nodes.Call) extends AnyRef {

  def array: nodes.Expression = call.argument(1)

  def subscripts: Identifier = call.argument(2).ast.isIdentifier

}

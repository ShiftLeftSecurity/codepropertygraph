package io.shiftleft.semanticcpg.language.operatorextension.nodes

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.semanticcpg.language._
import io.shiftleft.semanticcpg.language.types.expressions.Identifier

class ArrayAccess(node: nodes.Call) {

  def call: nodes.Call = node

  def array: nodes.Expression = node.argument(1)

  def subscripts: Identifier = node.argument(2).ast.isIdentifier

}

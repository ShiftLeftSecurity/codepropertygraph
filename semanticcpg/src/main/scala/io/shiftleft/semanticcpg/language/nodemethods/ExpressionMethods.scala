package io.shiftleft.semanticcpg.language.nodemethods

import io.shiftleft.Implicits.JavaIteratorDeco
import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.overflowdb.traversal.help
import io.shiftleft.semanticcpg.utils.MemberAccess

import scala.annotation.tailrec

class ExpressionMethods(val node: nodes.AstNode) extends AnyVal {

  /**
    * Traverse to it's parent expression (e.g. call or return) by following the incoming AST nodes.
    * It's continuing it's walk until it hits an expression that's not a generic
    * "member access operation", e.g., "<operator>.memberAccess".
    * */
  def parentExpression: nodes.Expression = _parentExpression(node)

  @tailrec
  private final def _parentExpression(argument: nodes.AstNode): nodes.Expression = {
    val parent = argument._astIn.onlyChecked
    parent match {
      case call: nodes.Call if MemberAccess.isGenericMemberAccessName(call.name) =>
        _parentExpression(call)
      case expression: nodes.Expression =>
        expression
    }
  }

}

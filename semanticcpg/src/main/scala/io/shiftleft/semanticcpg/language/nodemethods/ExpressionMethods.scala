package io.shiftleft.semanticcpg.language.nodemethods

import io.shiftleft.Implicits.JavaIteratorDeco
import io.shiftleft.codepropertygraph.generated.nodes.{AstNode, Call, Expression}
import io.shiftleft.semanticcpg.NodeExtension
import io.shiftleft.semanticcpg.utils.MemberAccess

import scala.annotation.tailrec

class ExpressionMethods(val node: AstNode) extends AnyVal with NodeExtension {

  /**
    * Traverse to it's parent expression (e.g. call or return) by following the incoming AST
    * It's continuing it's walk until it hits an expression that's not a generic
    * "member access operation", e.g., "<operator>.memberAccess".
    * */
  def parentExpression: Option[Expression] = _parentExpression(node)

  @tailrec
  private final def _parentExpression(argument: AstNode): Option[Expression] = {
    val parent = argument._astIn.onlyChecked
    parent match {
      case call: Call if MemberAccess.isGenericMemberAccessName(call.name) =>
        _parentExpression(call)
      case expression: Expression =>
        Some(expression)
      case _ =>
        None
    }
  }

}

package io.shiftleft.semanticcpg.language.operatorextension.nodemethods

import io.shiftleft.codepropertygraph.generated.Operators
import io.shiftleft.codepropertygraph.generated.nodes.{Call, Expression}
import io.shiftleft.semanticcpg.language._
import io.shiftleft.semanticcpg.language.operatorextension.{OpNodes, allArrayAccessTypes}
import overflowdb.traversal._

class TargetMethods(val expr: Expression) extends AnyVal {

  /**
    * Array access at highest level, e.g., in a(b(c)), the entire expression
    * is returned, but not b(c) alone.
    * */
  def arrayAccess: Traversal[OpNodes.ArrayAccess] =
    expr.ast.isCall
      .collectFirst { case x if allArrayAccessTypes.contains(x.name) => x }
      .map(new OpNodes.ArrayAccess(_))

  /**
    * Returns 'pointer' for assignments of the form *(pointer) = x;
    * */
  def pointer: Traversal[Expression] = {
    expr match {
      case call: Call if call.name == Operators.indirection => call.argument(1)
      case _                                                => Traversal()
    }
  }

}

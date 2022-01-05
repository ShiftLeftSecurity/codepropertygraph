package io.shiftleft.semanticcpg.language.operatorextension.nodemethods

import io.shiftleft.codepropertygraph.generated.nodes.Expression
import io.shiftleft.semanticcpg.language._
import io.shiftleft.semanticcpg.language.operatorextension.{OpNodes, allArrayAccessTypes}
import overflowdb.traversal._

class TargetMethods(val expr: Expression) extends AnyVal {

  /** arrayAccess traverses to all array accesses below in the AST. For example, when called on the assignment
    *   `x = buf[idxs[i]];``
    * then it will return two array accesses.
    */
  def arrayAccess: Traversal[OpNodes.ArrayAccess] =
    expr.ast.isCall
      .filter(x => allArrayAccessTypes.contains(x.name))
      .map(new OpNodes.ArrayAccess(_))

}

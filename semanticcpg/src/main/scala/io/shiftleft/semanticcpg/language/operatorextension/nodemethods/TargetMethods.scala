package io.shiftleft.semanticcpg.language.operatorextension.nodemethods

import io.shiftleft.codepropertygraph.generated.nodes.{Call, Expression}
import io.shiftleft.semanticcpg.language.operatorextension.{OpNodes, allArrayAccessTypes}
import overflowdb.traversal._

class TargetMethods(val expr: Expression) extends AnyVal {

  /**
    * Top-level array accesses, e.g., foo[bar], but not x->foo[bar].
    * */
  def arrayAccess: Traversal[OpNodes.ArrayAccess] = {
    expr match {
      case c: Call if allArrayAccessTypes.contains(c.name) => Traversal(c)
      case _                                               => Traversal()
    }
  }.map(new OpNodes.ArrayAccess(_))

}

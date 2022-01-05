package io.shiftleft.semanticcpg.language.operatorextension

import io.shiftleft.codepropertygraph.generated.nodes.Expression
import io.shiftleft.semanticcpg.language._
import overflowdb.traversal._
import overflowdb.traversal.help.Doc

class TargetTraversal(val traversal: Traversal[Expression]) extends AnyVal {

  @Doc(info = "(Outer-most) array accesses used as assignment targets")
  def arrayAccess: Traversal[OpNodes.ArrayAccess] = traversal.flatMap(_.arrayAccess)

  @Doc(info = "Returns 'pointer' in assignments of the form *(pointer) = x")
  def pointer: Traversal[Expression] = traversal.flatMap(_.pointer)

}

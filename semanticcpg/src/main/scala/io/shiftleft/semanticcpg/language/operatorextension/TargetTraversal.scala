package io.shiftleft.semanticcpg.language.operatorextension

import io.shiftleft.codepropertygraph.generated.nodes.Expression
import io.shiftleft.semanticcpg.language._
import overflowdb.traversal._

class TargetTraversal(val traversal: Traversal[Expression]) extends AnyVal {

  /** arrayAccess traverses to all array accesses, including nested array accesses.
    * For example, for `x = buf[idxs[i]];`, it will return two array accesses.
    */
  def arrayAccess: Traversal[OpNodes.ArrayAccess] = traversal.flatMap(_.arrayAccess)

}

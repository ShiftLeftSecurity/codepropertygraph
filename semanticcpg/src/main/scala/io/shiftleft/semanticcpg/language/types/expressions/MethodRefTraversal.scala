package io.shiftleft.semanticcpg.language.types.expressions

import io.shiftleft.codepropertygraph.generated.{EdgeTypes, nodes}
import overflowdb.traversal.Traversal

class MethodRefTraversal(val traversal: Traversal[nodes.MethodRef]) extends AnyVal {

  /**
    * Traverse to referenced method.
    * */
  def referencedMethod: Traversal[nodes.Method] =
    traversal.out(EdgeTypes.REF).cast[nodes.Method]
}

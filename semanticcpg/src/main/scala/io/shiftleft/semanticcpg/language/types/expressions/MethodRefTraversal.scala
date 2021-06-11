package io.shiftleft.semanticcpg.language.types.expressions

import io.shiftleft.codepropertygraph.generated.EdgeTypes
import io.shiftleft.codepropertygraph.generated.nodes.{Method, MethodRef}
import overflowdb.traversal.Traversal

class MethodRefTraversal(val traversal: Traversal[MethodRef]) extends AnyVal {

  /**
    * Traverse to referenced method.
    * */
  def referencedMethod: Traversal[Method] =
    traversal.out(EdgeTypes.REF).cast[Method]
}

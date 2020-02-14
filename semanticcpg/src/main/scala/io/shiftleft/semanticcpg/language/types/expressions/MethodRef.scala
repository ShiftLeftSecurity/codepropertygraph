package io.shiftleft.semanticcpg.language.types.expressions

import io.shiftleft.codepropertygraph.generated.{EdgeTypes, nodes}
import io.shiftleft.semanticcpg.language._

class MethodRef(val wrapped: NodeSteps[nodes.MethodRef]) extends AnyVal {

  /**
    * Traverse to referenced method.
    * */
  def referencedMethod: NodeSteps[nodes.Method] =
    new NodeSteps(wrapped.raw.out(EdgeTypes.REF).cast[nodes.Method])
}

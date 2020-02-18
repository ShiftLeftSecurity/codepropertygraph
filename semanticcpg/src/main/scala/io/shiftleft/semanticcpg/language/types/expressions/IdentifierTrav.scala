package io.shiftleft.semanticcpg.language.types.expressions

import io.shiftleft.codepropertygraph.generated.{EdgeTypes, nodes}
import io.shiftleft.semanticcpg.language._

/**
  An identifier, e.g., an instance of a local variable, or a temporary variable
  */
class IdentifierTrav(val wrapped: NodeSteps[nodes.Identifier]) extends AnyVal {

  /**
    * Traverse to all declarations of this identifier
    * */
  def refsTo: NodeSteps[nodes.Declaration] =
    new NodeSteps(wrapped.raw.out(EdgeTypes.REF).cast[nodes.Declaration])

}
